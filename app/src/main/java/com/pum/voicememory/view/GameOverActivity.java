package com.pum.voicememory.view;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pum.voicememory.R;
import com.pum.voicememory.constants.Localization;
import com.pum.voicememory.model.StringRepo;

public class GameOverActivity extends AppCompatActivity {

    private TextToSpeech speech;
    private String message;
    private String displayMessage;
    private String voiceMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, RESULT_OK);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        Integer seconds = (Integer) intent.getSerializableExtra("result");

        setMessages(seconds);

        TextView tv = (TextView) findViewById(R.id.notificationTextView);
        tv.setText(displayMessage);

        speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                String[] lines = voiceMessage.split("\\r\\n|\\n|\\r");
                for (String line : lines) {
                    speech.speak(line.trim(), TextToSpeech.QUEUE_ADD, null);
                }
            }
        });
        speech.setLanguage(Localization.getLocale());


        RelativeLayout rlayout = (RelativeLayout) findViewById(R.id.activity_game_over);
        rlayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goBackToMainScreen();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBackToMainScreen();
    }

    private void goBackToMainScreen() {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setMessages(Integer seconds) {
        StringRepo stringRepo = new StringRepo(this);
        message = stringRepo.getGameOverString();

        displayMessage = message.replace("{RESULT}", secondsToDisplayString(seconds));
        voiceMessage = message.replace("{RESULT}", secondsToSpeechString(seconds));
    }

    private String secondsToDisplayString(Integer seconds) {
        Integer minutesDsp = seconds / 60;
        Integer secondsDsp = seconds % 60;
        return minutesDsp.toString()+":"+secondsDsp.toString();
    }

    private String secondsToSpeechString(Integer seconds) {
        Integer minutesDsp = seconds / 60;
        Integer secondsDsp = seconds % 60;
        if (Localization.getLanguage().equals("pl")) {
            return minutesDsp + "minuty, " + secondsDsp + "sekundy.";
        }
        return minutesDsp + "minutes, " + secondsDsp + "seconds.";
    }
}
