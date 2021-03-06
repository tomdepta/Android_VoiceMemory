package com.pum.voicememory.view;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pum.voicememory.R;
import com.pum.voicememory.constants.ActivityTags;
import com.pum.voicememory.constants.BoardSize;
import com.pum.voicememory.constants.Coordinates;
import com.pum.voicememory.constants.Localization;
import com.pum.voicememory.logic.GameController;
import com.pum.voicememory.logic.voiceparsing.SpokenWordParser;
import com.pum.voicememory.logic.voiceparsing.eAction;
import com.pum.voicememory.model.Field;
import com.pum.voicememory.model.StringRepo;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    //TODO: Refactor - extract voice listener handling to another object

    private Integer seconds = 0;
    private Coordinates selectedItem = new Coordinates(){{this.X = 0; this.Y = 0;}};

    private SpeechRecognizer recognizer;
    private static final String TAG = ActivityTags.GameActivity;
    private SpokenWordParser spokenWordParser;

    private TextToSpeech speech;

    private GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, RESULT_OK);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameController = new GameController(getBaseContext());

        speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                StringRepo repo = new StringRepo(getBaseContext());
                String[] instructions = repo.getGameWelcomeString().split("\\r\\n|\\n|\\r");
                for (String line : instructions) {
                    speech.speak(line.trim(), TextToSpeech.QUEUE_ADD, null);
                }
            }
        });
        speech.setLanguage(Localization.getLocale());

        resetVoiceListener();

        updateBoardLayout(gameController.getUpdatedBoardDisplay());

        initializeStopwatch();
    }

    private void updateBoardLayout(Field[][] updatedBoardDisplay) {
        LinearLayout layout = (LinearLayout)findViewById(R.id.board_placeholder);
        layout.removeAllViews();
        BoardLayoutCreator.addRowsToLayout(getBaseContext(), layout, selectedItem, updatedBoardDisplay);
        String selectedUncoveredLetter = gameController.getLetterIfUncovered(selectedItem);
        if (selectedUncoveredLetter != null) {
            speech.speak(selectedUncoveredLetter, TextToSpeech.QUEUE_ADD, null);
        }
    }

    private void initializeStopwatch() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seconds++;
                        TextView tv = (TextView) findViewById(R.id.textView);
                        tv.setText(secondsToDisplayString(seconds));
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private String secondsToDisplayString(Integer seconds) {
        Integer minutesDsp = seconds / 60;
        Integer secondsDsp = seconds % 60;
        return minutesDsp.toString()+":"+secondsDsp.toString();
    }

    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        recognizer.startListening(intent);
        Log.i("111111","11111111");
    }

    private void resetVoiceListener() {
        RelativeLayout rlayout =(RelativeLayout) findViewById(R.id.activity_game);
        rlayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startListening();
            }
        });

        spokenWordParser = new SpokenWordParser(getBaseContext());

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(new voiceListener());
    }

    private void performAction(eAction parsingResult) {
        switch (parsingResult) {
            case MoveUp:
                selectedItem.Y--;
                if (selectedItem.Y < 0)
                    selectedItem.Y = BoardSize.Height - 1;
                break;
            case MoveDown:
                selectedItem.Y++;
                if (selectedItem.Y > BoardSize.Height - 1)
                    selectedItem.Y = 0;
                break;
            case MoveLeft:
                selectedItem.X--;
                if (selectedItem.X < 0)
                    selectedItem.X = BoardSize.Width - 1;
                break;
            case MoveRight:
                selectedItem.X++;
                if (selectedItem.X > BoardSize.Width - 1)
                    selectedItem.X = 0;
                break;
            case Select:
                selectButton();
                break;
        }
        updateBoardLayout(gameController.getUpdatedBoardDisplay());
    }

    private void selectButton() {
        String selectionResult = gameController.selectPosition(selectedItem.X, selectedItem.Y);
        speech.speak(selectionResult, TextToSpeech.QUEUE_FLUSH, null);
        if (gameController.isGameFinished()) {
            Intent intent = new Intent(this, GameOverActivity.class);
            intent.putExtra("result", seconds);
            startActivity(intent);
        }
    }

    class voiceListener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndOfSpeech");
        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);
            if (error == 8) {
                resetVoiceListener();
            }
        }
        public void onResults(Bundle results)
        {
            Log.d(TAG, "onResults " + results);
            ArrayList recognizedWords = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (Object wordObj : recognizedWords) {
                String word = wordObj.toString();
                eAction parsingResult = spokenWordParser.parse(word, TAG);
                if (parsingResult != null) {
                    performAction(parsingResult);
                }
            }
        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
}
