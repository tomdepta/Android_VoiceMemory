package com.pum.voicememory;

import com.pum.voicememory.model.Board;
import com.pum.voicememory.model.Field;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BoardTests {
    @Test
    public void shouldBeCreatedWithProperContents()
    {
        //GIVEN
        List<Character> expectedContent = getExpectedContent();
        Board board = new Board();

        //WHEN
        Field[][] boardContent = board.getBoard();

        //THEN
        try {
            for (Field[] fieldDim : boardContent) {
                for (Field field : fieldDim) {
                    int foundIndex = expectedContent.indexOf(field.getLetter());
                    expectedContent.remove(foundIndex);
                }
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertTrue(expectedContent.isEmpty());
    }

    @Test
    public void shouldCreateRandomBoardEachTime()
    {
        //GIVEN
        Board board1 = new Board();
        Board board2 = new Board();

        //WHEN
        Field[][] board1Content = board1.getBoard();
        Field[][] board2Content = board2.getBoard();

        Assert.assertFalse(java.util.Arrays.deepEquals(board1Content, board2Content));
    }

    private List<Character> getExpectedContent() {
        return new ArrayList<Character>() {{
            add('A');
            add('A');
            add('B');
            add('B');
            add('C');
            add('C');
            add('D');
            add('D');
            add('E');
            add('E');
            add('F');
            add('F');
        }};
    }
}
