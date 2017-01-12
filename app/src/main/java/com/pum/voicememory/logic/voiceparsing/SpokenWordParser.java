package com.pum.voicememory.logic.voiceparsing;


import android.content.Context;
import android.content.res.AssetManager;

import com.pum.voicememory.constants.ActivityTags;
import com.pum.voicememory.model.StringRepo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class SpokenWordParser {

    private StringRepo stringRepo;

    public SpokenWordParser(Context context) {
        stringRepo = new StringRepo(context);
    }

    public eAction parse(String textToParse, String activityTag) {

        switch (activityTag) {
            case ActivityTags.MainActivityTag:
                return parseMainActivityAction(textToParse);
            default:
                return null;
        }
    }

    private eAction parseMainActivityAction(String textToParse) {

        if (textToParse.toLowerCase().equals(stringRepo.getCommand("move_up"))) {
            return eAction.StartGame;
        }

        return null;
    }
}
