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
        selectedField.setState(eFieldState.Selected);
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
}
