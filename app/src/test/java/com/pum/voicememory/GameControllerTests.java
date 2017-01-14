package com.pum.voicememory;

import android.test.mock.MockContext;

import com.pum.voicememory.logic.GameController;
import com.pum.voicememory.model.Field;
import com.pum.voicememory.model.eFieldState;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameControllerTests {
    @Test
    public void shouldChangeSelectedFieldViewState() {
        //GIVEN
        GameController controller = new GameController(new MockContext());

        //WHEN
        controller.selectPosition(0, 0);
        Field[][] boardDisplay = controller.getUpdatedBoardDisplay();

        //THEN
        assertEquals(eFieldState.Selected, boardDisplay[0][0].getState());
    }

    @Test
    public void shouldResetFieldViewStateOnDoubleSelection() {
        //GIVEN
        GameController controller = new GameController(new MockContext());

        //WHEN
        controller.selectPosition(0, 0);
        controller.selectPosition(0, 0);
        Field[][] boardDisplay = controller.getUpdatedBoardDisplay();

        //THEN
        assertEquals(eFieldState.Pending, boardDisplay[0][0].getState());
    }
}
