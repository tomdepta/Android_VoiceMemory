package com.pum.voicememory.logic.voiceparsing;


import com.pum.voicememory.constants.ActivityTags;

public class SpokenWordParser {
    public eAction parse(String textToParse, String activityTag) {

        switch (activityTag) {
            case ActivityTags.MainActivityTag:
                return parseMainActivityAction(textToParse);
            default:
                return null;
        }
    }

    private eAction parseMainActivityAction(String textToParse) {
        if (textToParse.toLowerCase().equals("start")) {
            return eAction.StartGame;
        }

        return null;
    }
}
