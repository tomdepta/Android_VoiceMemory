package com.pum.voicememory.model;

import android.content.Context;

import com.pum.voicememory.constants.Localization;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.InputStream;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;


public class StringRepo {

    private Document actionsXml;
    private String systemLanguage;

    public StringRepo(Context context) {
        systemLanguage = Localization.getLanguage();
        InputStream inputStream;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            inputStream = context.getAssets().open("string_resources.xml");
            DocumentBuilder builder = factory.newDocumentBuilder();
            actionsXml = builder.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCommand(String commandName) {
        String xpathStr = "StringResources/Commands/Command[@name='"+commandName+"']/"+systemLanguage;
        return getStringResource(xpathStr);
    }

    public String getButtonText(String buttonName) {
        String xpathStr = "StringResources/Buttons/Button[@name='"+buttonName+"']/"+systemLanguage;
        return getStringResource(xpathStr);
    }

    public String getFinalizedFieldAccessedString() {
        String xpathStr = "StringResources/Notifications/Notification[@name='finalized_field']/"+systemLanguage;
        return getStringResource(xpathStr);
    }

    public String getGameOverString() {
        String xpathStr = "StringResources/Notifications/Notification[@name='game_over']/"+systemLanguage;
        return getStringResource(xpathStr);
    }

    public String getInstructionsString() {
        String xpathStr = "StringResources/Notifications/Notification[@name='how_to_play']/"+systemLanguage;
        return getStringResource(xpathStr);
    }

    public String getAboutString() {
        String xpathStr = "StringResources/Notifications/Notification[@name='about']/"+systemLanguage;
        return getStringResource(xpathStr);
    }

    public String getMenuWelcomeString() {
        String xpathStr = "StringResources/Notifications/Notification[@name='main_screen_welcome']/"+systemLanguage;
        return getStringResource(xpathStr);
    }

    public String getGameWelcomeString() {
        String xpathStr = "StringResources/Notifications/Notification[@name='game_screen_welcome']/"+systemLanguage;
        return getStringResource(xpathStr);
    }

    private String getStringResource(String xpathStr) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            Node node = (Node) xPath.evaluate(xpathStr, actionsXml, XPathConstants.NODE);
            String result = node.getFirstChild().getNodeValue();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
