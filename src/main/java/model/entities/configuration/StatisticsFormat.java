/*
 * Copyright (C) 2019 Quiz Project
 */

package model.entities.configuration;

import model.entities.Locales;

import java.util.*;

/**
 * Provides format of the statistics
 */
public enum StatisticsFormat {

    /**
     * Short type of the statistics
     */
    SHORT(new HashMap<Locale, String>() {{
        put(Locales.ENGLISH.getLocale(), "short");
        put(Locales.UKRAINIAN.getLocale(), "коротка");
    }}),

    /**
     * Long type of the statistics
     */
    LONG(new HashMap<Locale, String>() {{
        put(Locales.ENGLISH.getLocale(), "long");
        put(Locales.UKRAINIAN.getLocale(), "довга");
    }});

    /**
     * Map with two localized types
     */
    private Map<Locale, String> names;

    /**
     * Locale
     */
    private Locale locale;

    /**
     * Creates statistics format
     *
     * @param names Map of localized names of statistics
     */
    StatisticsFormat(Map<Locale, String> names) {
        this.names = names;
    }

    public Map<Locale, String> getNames() {
        return names;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getLocalizedName() {
        return getNames().get(locale);
    }
}
