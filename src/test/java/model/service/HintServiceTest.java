/*
 * Copyright (C) 2019 Quiz Project
 */

package model.service;

import model.entities.statistics.Statistics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests HintService
 */
public class HintServiceTest {

    private static HintService hintService;

    @BeforeClass
    public static void setUp(){
        hintService = new HintService();
    }

    @Test
    public void addHintToCount() {
        Statistics statistics = new Statistics();
        statistics.setNumberOfHints(1);
        assertEquals(2, hintService.addHintToCount(statistics));

        statistics.setNumberOfHints(2);
        assertNotEquals(2, hintService.addHintToCount(statistics));
    }
}