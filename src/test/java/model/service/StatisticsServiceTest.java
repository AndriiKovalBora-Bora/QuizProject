/*
 * Copyright (C) 2019 Quiz Project
 */

package model.service;

import model.entities.statistics.Statistics;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests StatisticsService
 */
public class StatisticsServiceTest {

    private static StatisticsService statisticsService;

    @BeforeClass
    public static void setUp(){
        statisticsService = new StatisticsService();
    }

    @Test
    public void givePointToPlayers() {
        Statistics statistics = new Statistics();
        statistics.setPlayersScore(10);
        statisticsService.givePointToPlayers(statistics);
        assertEquals(11, statistics.getPlayersScore());

        statistics.setPlayersScore(1);
        statisticsService.givePointToPlayers(statistics);
        assertNotEquals(1, statistics.getPlayersScore());
    }

    @Test
    public void givePointToSpectators() {
        Statistics statistics = new Statistics();
        statistics.setSpectatorsScore(10);
        statisticsService.givePointToSpectators(statistics);
        assertEquals(11, statistics.getSpectatorsScore());

        statistics.setSpectatorsScore(1);
        statisticsService.givePointToSpectators(statistics);
        assertNotEquals(1, statistics.getSpectatorsScore());
    }
}