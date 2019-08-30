/*
 * Copyright (C) 2019 Quiz Project
 */

package model.service;

import model.entities.configuration.Configuration;
import model.entities.question.Question;
import model.entities.statistics.Statistics;
import org.junit.*;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Tests TimeService
 */
public class TimeServiceTest {
    private static TimeService timeService;

    @BeforeClass
    public static void setUp() {
        timeService = new TimeService();
    }

    @Test
    public void calculateTimeForAnswer() {
        Question question = new Question();
        question.setStartTime(5_000);
        question.setEndTime(10_000);
        assertEquals(5, timeService.calculateTimeForAnswer(question));

        question.setStartTime(15_000);
        question.setEndTime(20_000);
        assertNotEquals(1, timeService.calculateTimeForAnswer(question));
    }

    @Test
    public void calculateTimeLeft() {
        Configuration configuration = new Configuration();
        configuration.setTime(60);

        Question question = new Question();
        question.setStartTime(new Date().getTime());

        assertTrue(timeService.calculateTimeLeft(question, configuration) > 0);

        question.setStartTime(0);
        assertFalse(timeService.calculateTimeLeft(question, configuration) < 0);
    }

    @Test
    public void addTime() {
        Configuration configuration = new Configuration();
        configuration.setTime(60);

        Statistics statistics = new Statistics();
        statistics.setConfiguration(configuration);

        timeService.addTime(statistics);
        assertEquals(120, statistics.getConfiguration().getTime());
    }

    @Test
    public void calculateTimeToFresh() {
        assertEquals(10, timeService.calculateTimeToFresh(9_000));
    }
}