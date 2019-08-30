/*
 * Copyright (C) 2019 Quiz Project
 */

package model.entities;

import java.util.Locale;

/**
 * Provides enum locales for the project
 */
public enum Locales {

    /**
     * English locale
     */
    ENGLISH(new Locale("en", "EN")),

    /**
     * Ukrainian locale
     */
    UKRAINIAN(new Locale("uk", "UA"));

    /**
     * Locale parameter of enum
     */
    private Locale locale;

    /**
     * Creates locale
     *
     * @param locale Locale class
     */
    Locales(Locale locale) {
        this.locale = locale;
    }

    /**
     * Gets locale
     *
     * @return Locale
     */
    public Locale getLocale() {
        return locale;
    }
}
