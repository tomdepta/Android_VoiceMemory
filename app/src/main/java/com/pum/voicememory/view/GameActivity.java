package com.pum.voicememory.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pum.voicememory.R;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private Timer timer;
    private Integer seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timer = new Timer();
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
}
