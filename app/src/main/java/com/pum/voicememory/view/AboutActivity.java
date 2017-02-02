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

public class AboutActivity extends AppCompatActivity {

    private TextToSpeech speech;
    private String[] lines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, RESULT_OK);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        StringRepo repo = new StringRepo(this);
        String about = repo.getAboutString();
        lines = about.split("\\r\\n|\\n|\\r");

        TextView instructionsTextView = (TextView) findViewById(R.id.aboutTextView);
        instructionsTextView.setText(about);

        speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });
        speech.setLanguage(Localization.getLocale());

        RelativeLayout rlayout =(RelativeLayout) findViewById(R.id.activity_about);
        rlayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                readInfo();
            }
        });
    }

    private void readInfo() {
        for (String line : lines) {
            speech.speak(line.trim(), TextToSpeech.QUEUE_ADD, null);
        }
    }
}
