package com.pum.voicememory.view;


import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pum.voicememory.constants.BoardSize;
import com.pum.voicememory.constants.Coordinates;
import com.pum.voicememory.model.Field;

public class BoardLayoutCreator {
    public static void addRowsToLayout(Context context, LinearLayout layout, Coordinates highlightedCoords, Field[][] boardContent) {
        for (int i = 0; i < BoardSize.Height; i++) {
            LinearLayout row = new LinearLayout(context);
            row.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < BoardSize.Width; j++) {
                TextView tv = new TextView(context);
                tv.setWidth(40);
                tv.setHeight(40);
                tv.setBackgroundColor(Color.LTGRAY);
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

    private static boolean shouldFieldBeHighlighted(int i, int j, Coordinates highlightedChords) {
        return (i == highlightedChords.Y) && (j == highlightedChords.X );
    }
}
