package com.pum.voicememory.constants;


import java.util.Locale;

public class Localization {
    public static Locale getLocale() {
        Locale locale = Locale.getDefault();
        if (!locale.toString().equals("pl_PL")) {
            return Locale.ENGLISH;
        }
        return locale;
    }

    public static String getLanguage() {
        String language = Locale.getDefault().getLanguage();
        if (!language.equals("pl")) {
            return "en";
        }
        return language;
    }
}
