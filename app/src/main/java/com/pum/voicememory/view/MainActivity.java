package com.pum.voicememory.view;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.SparseArray;
        import android.widget.Button;

        import com.pum.voicememory.R;

        import static android.graphics.Color.LTGRAY;
        import static android.graphics.Color.MAGENTA;

public class MainActivity extends AppCompatActivity {

    private Integer selectedOptionIndex = 0;
    private SparseArray<Button> menuButtons = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeMenuItemsMap();
        updateButtonView();
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
    }
}
