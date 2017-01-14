package com.pum.voicememory.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pum.voicememory.R;
import com.pum.voicememory.model.StringRepo;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        StringRepo stringRepo = new StringRepo(this);
        TextView tv = (TextView) findViewById(R.id.notificationTextView);
        tv.setText(stringRepo.getGameOverString());
    }
}
