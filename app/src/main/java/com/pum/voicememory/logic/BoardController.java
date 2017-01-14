package com.pum.voicememory.logic;


import com.pum.voicememory.model.Board;
import com.pum.voicememory.model.Field;
import com.pum.voicememory.model.eFieldState;

public class BoardController {

    private Board board;

    public BoardController() {
        this.board = new Board();
    }

    public Field getFieldAt(int x, int y) {
        Field[][] fields = board.getBoard();
        Field selectedField = fields[y][x];
        if (!selectedField.getState().equals(eFieldState.Finalized)) {
            selectedField.setState(eFieldState.Selected);
        }
        board.updateField(x, y, selectedField);

        return selectedField;
    }

    public Field[][] getFieldTable() {
        return board.getBoard();
    }

    public void resetField(int x, int y) {
        Field[][] fields = board.getBoard();
        Field selectedField = fields[y][x];
        selectedField.setState(eFieldState.Pending);
        board.updateField(x, y, selectedField);
    }

    public void finalizeField(int x, int y) {
        Field[][] fields = board.getBoard();
        Field selectedField = fields[y][x];
        selectedField.setState(eFieldState.Finalized);
        board.updateField(x, y, selectedField);
    }

    public boolean areAllFieldsFinalized() {
        Field[][] fields = board.getBoard();

        for (Field[] fieldDim : fields) {
            for (Field field : fieldDim) {
                if (!field.getState().equals(eFieldState.Finalized)) {
                    return false;
                }
            }
        }
        return true;
    }
}
