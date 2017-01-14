package com.pum.voicememory.view;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pum.voicememory.constants.BoardSize;
import com.pum.voicememory.constants.Coordinates;
import com.pum.voicememory.model.Field;
import com.pum.voicememory.model.eFieldState;

public class BoardLayoutCreator {
    public static void addRowsToLayout(Context context, LinearLayout layout, Coordinates highlightedCoords, Field[][] boardContent) {
        for (int i = 0; i < BoardSize.Height; i++) {
            LinearLayout row = new LinearLayout(context);
            row.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < BoardSize.Width; j++) {
                TextView tv = new TextView(context);
                tv.setWidth(95);
                tv.setHeight(95);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(40);
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundColor(getColorFor(boardContent[i][j].getState()));
                tv.setText(String.valueOf(boardContent[i][j].getLetter()));

                if (shouldFieldBeHighlighted(i, j, highlightedCoords)) {
                   tv.setBackgroundColor(Color.CYAN);
                }

                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llp.setMargins(20, 0, 0, 0); // (left, top, right, bottom)
                tv.setLayoutParams(llp);

                row.addView(tv);
            }
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.setMargins(0, 0, 0, 10); // (left, top, right, bottom)
            layout.addView(row, llp);
        }
    }

    private static int getColorFor(eFieldState state) {
        switch (state) {
            case Pending:
                return Color.LTGRAY;
            case Selected:
                return Color.RED;
            case Finalized:
                return Color.GREEN;
            default:
                return Color.LTGRAY;
        }
    }

    private static boolean shouldFieldBeHighlighted(int i, int j, Coordinates highlightedChords) {
        return (i == highlightedChords.Y) && (j == highlightedChords.X );
    }
}
