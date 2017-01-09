package com.pum.voicememory;

import com.pum.voicememory.model.Field;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTests {
    @Test
    public void shouldReturnItsLetterWhenAskedOnce()
    {
        //GIVEN
        Field field = new Field('A');

        //WHEN
        char result = field.getLetter();

        //ASSERT
        assertEquals('A', result);
    }
}
