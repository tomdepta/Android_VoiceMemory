package com.pum.voicememory.logic;

import com.pum.voicememory.constants.Coordinates;
import com.pum.voicememory.model.Field;


public class GameController {

    private BoardController boardController;
    private Coordinates previouslySelected;
    private Character previousLetter;

    public GameController() {
        this.boardController = new BoardController();
        this.previouslySelected = null;
        this.previousLetter = null;
    }

    public char selectPosition(final int x, final int y) {
        Field field = boardController.getFieldAt(x, y);
        if (previouslySelected == null) {
            previouslySelected = new Coordinates() {{
                this.X = x;
                this.Y = y;
            }};
            previousLetter = field.getLetter();
        }
        else {
            if(x == previouslySelected.X && y == previouslySelected.Y) {
                boardController.resetField(x, y);
            }
            else if (previousLetter.equals(field.getLetter())) {
                boardController.finalizeField(x, y);
                boardController.finalizeField(previouslySelected.X, previouslySelected.Y);
            }
            else {
                boardController.resetField(x, y);
                boardController.resetField(previouslySelected.X, previouslySelected.Y);
            }
            previouslySelected = null;
            previousLetter = null;
        }
        return field.getLetter();
    }

    public Field[][] getUpdatedBoardDisplay() {
        return boardController.getFieldTable();
    }
}

