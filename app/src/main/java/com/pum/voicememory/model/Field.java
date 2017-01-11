package com.pum.voicememory.model;


public class Field {

    private char letter;
    private eFieldState state;

    public Field(char letter) {
        this.letter = letter;
        this.state = eFieldState.Pending;
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

    public void setState(eFieldState state) {
        this.state = state;
    }

    public eFieldState getState() {
        return this.state;
    }

    public char getLetter() {
        return letter;
    }

}
