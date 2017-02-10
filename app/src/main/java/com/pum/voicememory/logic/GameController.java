package com.pum.voicememory.logic;

import android.content.Context;

import com.pum.voicememory.constants.Coordinates;
import com.pum.voicememory.model.Field;
import com.pum.voicememory.model.StringRepo;
import com.pum.voicememory.model.eFieldState;


public class GameController {

    private BoardController boardController;
    private Coordinates previouslySelected;
    private Character previousLetter;
    private Context context;

    public GameController(Context context) {
        this.context = context;
        this.boardController = new BoardController();
        this.previouslySelected = null;
        this.previousLetter = null;
    }

    public String selectPosition(final int x, final int y) {
        Field field = boardController.selectFieldAt(x, y);
        StringRepo repo = new StringRepo(context);
        if (field.getState().equals(eFieldState.Finalized)) {
            return repo.getFinalizedFieldAccessedString();
        }
        String result;
        if (previouslySelected == null) {
            previouslySelected = new Coordinates() {{
                this.X = x;
                this.Y = y;
            }};
            previousLetter = field.getLetter();
            result = String.valueOf(previousLetter);
        }
        else {
            if(x == previouslySelected.X && y == previouslySelected.Y) {
                boardController.resetField(x, y);
                result = String.valueOf(field.getLetter());
            }
            else if (previousLetter.equals(field.getLetter())) {
                boardController.finalizeField(x, y);
                boardController.finalizeField(previouslySelected.X, previouslySelected.Y);
                result = String.valueOf(field.getLetter()) + ". " + repo.getIsMatchString();
            }
            else {
                boardController.resetField(x, y);
                boardController.resetField(previouslySelected.X, previouslySelected.Y);
                result = String.valueOf(field.getLetter()) + ". " + repo.getNoMatchString();
            }
            previouslySelected = null;
            previousLetter = null;
        }
        return result;
    }

    public Field[][] getUpdatedBoardDisplay() {
        return boardController.getFieldTable();
    }

    public boolean isGameFinished() {
        return boardController.areAllFieldsFinalized();
    }

    public String getLetterIfUncovered(Coordinates selectedItem) {
        Field field = boardController.getFieldAt(selectedItem.X, selectedItem.Y);
        if (field.getState().equals(eFieldState.Finalized)) {
            return String.valueOf(field.getLetter());
        }
        return null;
    }
}

