package com.pum.voicememory.view;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pum.voicememory.R;
import com.pum.voicememory.constants.Localization;
import com.pum.voicememory.model.StringRepo;

public class HowToPlayActivity extends AppCompatActivity {

    private TextToSpeech speech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        StringRepo repo = new StringRepo(this);
        String instructions = repo.getInstructionsString();

        TextView instructionsTextView = (TextView) findViewById(R.id.instructionsTextView);
        instructionsTextView.setText(instructions);

        speech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });
        speech.setLanguage(Localization.getLocale());

        final String splittedInstructions[] = instructions.split("\\r\\n|\\n|\\r");

        /*for (String line : splittedInstructions) {
            speech.speak(line.trim(), TextToSpeech.QUEUE_FLUSH, null);
        }*/

        RelativeLayout rlayout =(RelativeLayout) findViewById(R.id.activity_how_to_play);
        rlayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tellInstructions(splittedInstructions);
            }
        });

    }

    private void tellInstructions(String[] splittedInstructions) {
        for (String line : splittedInstructions) {
            speech.speak(line.trim(), TextToSpeech.QUEUE_ADD, null);
        }
    }
}
