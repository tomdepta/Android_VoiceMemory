package com.pum.voicememory.model;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Board {

    Field[][] board;

    public Board()
    {
        board = initializeBoard();

    }

    private Field[][] initializeBoard() {
        Field[][] result =  new Field[3][4];

        Character[] fieldValues = {'A','A','B','B','C','C','D','D','E','E','F','F'};
        List<Character> listOfFieldValues = new LinkedList<>(Arrays.asList(fieldValues));

        Collections.shuffle(listOfFieldValues);

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = new Field(listOfFieldValues.get(0));
                listOfFieldValues.remove(0);
            }
        }

        return result;
    }

    public Field[][] getBoard() {
        return board;
    }

    public void updateField(int x, int y, Field field) {
        board[x][y] = field;
    }
}
