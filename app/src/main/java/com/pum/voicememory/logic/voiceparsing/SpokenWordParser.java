package com.pum.voicememory.logic.voiceparsing;


import android.content.Context;
import android.content.res.AssetManager;

import com.pum.voicememory.constants.ActivityTags;

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

    private Document actionsXml;
    private String systemLanguage;

    public SpokenWordParser(Context baseContext) {
        systemLanguage = resolveSystemLanguage();
        InputStream inputStream;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            inputStream = baseContext.getAssets().open("string_resources.xml");
            DocumentBuilder builder = factory.newDocumentBuilder();
            actionsXml = builder.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        if (textToParse.toLowerCase().equals(getStringResource("move_up"))) {
            return eAction.StartGame;
        }

        return null;
    }

    private String getStringResource(String commandName) {
        try {
            String xpathStr = "StringResources/Commands/Command[@name='"+commandName+"']/"+systemLanguage;
            XPath xPath = XPathFactory.newInstance().newXPath();
            Node node = (Node) xPath.evaluate(xpathStr, actionsXml, XPathConstants.NODE);
            String result = node.getFirstChild().getNodeValue();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String resolveSystemLanguage() {
        String language = Locale.getDefault().getLanguage();
        if (!language.equals("pl")) {
            return "en";
        }
        return language;
    }
}
