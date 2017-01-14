package com.pum.voicememory.logic;

import com.pum.voicememory.constants.Coordinates;
import com.pum.voicememory.model.Field;


public class GameController {

    private BoardController boardController;
    private Coordinates previouslySelected;

    public GameController() {
        this.boardController = new BoardController();
        this.previouslySelected = null;
    }

    public void selectPosition(final int x, final int y) {
        Field field = boardController.getFieldAt(x, y);
        if (previouslySelected == null) {
            previouslySelected = new Coordinates() {{
                this.X = x;
                this.Y = y;
            }};
        }
        else {
            if(x == previouslySelected.X && y == previouslySelected.Y) {
                boardController.resetField(x, y);
            }
        }
    }

    public Field[][] getUpdatedBoardDisplay() {
        return boardController.getFieldTable();
    }
}

