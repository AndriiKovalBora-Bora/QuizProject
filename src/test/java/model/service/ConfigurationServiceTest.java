/*
 * Copyright (C) 2019 Quiz Project
 */

package model.service;

import org.junit.*;

/**
 * Tests ConfigurationService
 */
public class ConfigurationServiceTest {
    private static ConfigurationService configurationService;

    @BeforeClass
    public static void setUp() {
        configurationService = new ConfigurationService();
    }

    @Test(expected = NumberFormatException.class)
    public void validateDataException() {
        String time = "100";
        String numberOfPlayers = "five";
        String maxScore = "10";
        String maxNumberOfHints = "5";
        configurationService.validateData(time, numberOfPlayers, maxScore, maxNumberOfHints);
    }

    @Test
    public void validateDataNoException() {
        String time = "100";
        String numberOfPlayers = "10";
        String maxScore = "10";
        String maxNumberOfHints = "5";
        configurationService.validateData(time, numberOfPlayers, maxScore, maxNumberOfHints);
    }
}