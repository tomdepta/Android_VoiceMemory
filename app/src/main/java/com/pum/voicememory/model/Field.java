package com.pum.voicememory.model;


public class Field {
    private char letter;

    public Field(char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Field))return false;
        Field otherField = (Field)other;

        if (otherField.getLetter() == this.letter)
            return true;
        return false;
    }
}
