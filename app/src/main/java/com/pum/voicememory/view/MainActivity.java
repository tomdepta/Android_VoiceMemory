package com.pum.voicememory.view;

    import android.content.Intent;
    import android.speech.RecognitionListener;
    import android.speech.RecognizerIntent;
    import android.speech.SpeechRecognizer;
    import android.speech.tts.TextToSpeech;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.util.SparseArray;
    import android.view.View;
    import android.widget.Button;
    import android.widget.RelativeLayout;
    import android.widget.Toast;

    import com.pum.voicememory.R;
    import com.pum.voicememory.constants.ActivityTags;
    import com.pum.voicememory.constants.Localization;
    import com.pum.voicememory.logic.voiceparsing.SpokenWordParser;
    import com.pum.voicememory.logic.voiceparsing.eAction;
    import com.pum.voicememory.model.StringRepo;

    import java.util.ArrayList;
    import java.util.Locale;

    import static android.graphics.Color.LTGRAY;
    import static android.graphics.Color.MAGENTA;

public class MainActivity extends AppCompatActivity {

    private Integer selectedOptionIndex = 0;
    private SparseArray<Button> menuButtons = new SparseArray<>();

    private SpeechRecognizer recognizer;
    private static final String TAG = ActivityTags.MainActivityTag;
    private SpokenWordParser spokenWordParser;

    private TextToSpeech speech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeDisplayedText();
        initializeMenuItemsMap();

        resetVoiceListener();

        speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });
        speech.setLanguage(Localization.getLocale());
        updateButtonView();
    }

    private void initializeDisplayedText() {
        StringRepo stringRepo = new StringRepo(getBaseContext());

        Button button = (Button) findViewById(R.id.button_startGame);
        button.setText(stringRepo.getButtonText("start_game"));

        button = (Button) findViewById(R.id.button_highScores);
        button.setText(stringRepo.getButtonText("high_scores"));

        button = (Button) findViewById(R.id.button_howToPlay);
        button.setText(stringRepo.getButtonText("how_to_play"));

        button = (Button) findViewById(R.id.button_about);
        button.setText(stringRepo.getButtonText("about"));

        button = (Button) findViewById(R.id.button_quit);
        button.setText(stringRepo.getButtonText("quit"));
    }

    private void initializeMenuItemsMap() {
        menuButtons.put(0, (Button) findViewById(R.id.button_startGame));
        menuButtons.put(1, (Button) findViewById(R.id.button_highScores));
        menuButtons.put(2, (Button) findViewById(R.id.button_howToPlay));
        menuButtons.put(3, (Button) findViewById(R.id.button_about));
        menuButtons.put(4, (Button) findViewById(R.id.button_quit));
    }

    private void updateButtonView() {
        for(int i = 0; i < menuButtons.size(); i++) {
            int key = menuButtons.keyAt(i);
            Button button = menuButtons.get(key);
            if (i == selectedOptionIndex) {
                button.setBackgroundColor(MAGENTA);
            }
            else {
                button.setBackgroundColor(LTGRAY);
            }
        }
        speech.speak(String.valueOf(menuButtons.get(selectedOptionIndex).getText()), TextToSpeech.QUEUE_FLUSH, null);
    }

    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        recognizer.startListening(intent);
        Log.i("111111","11111111");
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
            Toast.makeText(getApplicationContext(), "error " + error, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), parsingResult.toString(), Toast.LENGTH_SHORT).show();
                    performAction(parsingResult);
                }
            }
            //Toast.makeText(getApplicationContext(), "results: "+String.valueOf(recognizedWords.size()), Toast.LENGTH_SHORT).show();
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

    private void resetVoiceListener() {
        RelativeLayout rlayout =(RelativeLayout) findViewById(R.id.activity_main);
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
                selectedOptionIndex--;
                if (selectedOptionIndex < 0)
                    selectedOptionIndex = 4;
                    updateButtonView();
                break;
            case MoveDown:
                selectedOptionIndex++;
                if (selectedOptionIndex > 4)
                    selectedOptionIndex = 0;
                    updateButtonView();
                break;
            case Select:
                selectButton();
                break;
        }
    }

    private void selectButton() {
        Intent intent;
        if (selectedOptionIndex == 0) {
            intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
    }

}
