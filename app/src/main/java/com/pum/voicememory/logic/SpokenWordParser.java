package com.pum.voicememory.logic;


public class SpokenWordParser {
    public String parse(String textToParse) {
        if (textToParse.toLowerCase().equals("start")) {
            return textToParse;
        }
        return null;
    }
}
