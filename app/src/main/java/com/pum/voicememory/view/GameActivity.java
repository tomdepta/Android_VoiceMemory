package com.pum.voicememory.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

        LinearLayout layout = (LinearLayout)findViewById(R.id.board_placeholder);
        for (int i = 0; i < 4; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 3; j++) {
                TextView tv = new TextView(this);
                tv.setWidth(40);
                tv.setHeight(40);
                tv.setBackgroundColor(Color.CYAN);

                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llp.setMargins(20, 0, 0, 0); // (left, top, right, bottom)
                tv.setLayoutParams(llp);

                row.addView(tv);
            }
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, 0, 0, 10); // (left, top, right, bottom)
            layout.addView(row, llp);
        }


        initializeStopwatch();
    }

    private void initializeStopwatch() {
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
