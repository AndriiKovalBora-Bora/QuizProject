/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import model.dao.DaoFactory;
import model.dao.StatisticsDao;
import model.entities.statistics.Statistics;
import model.entities.user.User;
import org.junit.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Tests JDBCStatisticsDao
 */
public class JDBCStatisticsDaoTest {
    private static StatisticsDao statisticsDao;
    private static Statistics testStatistics;
    private static  DaoFactory daoFactory;

    @BeforeClass
    public static void setUp() {
        daoFactory = DaoFactory.getInstance();
        statisticsDao = daoFactory.createStatisticsDao();

        testStatistics = new Statistics();
        testStatistics.setPlayersScore(1);
        testStatistics.setSpectatorsScore(2);
        testStatistics.setNumberOfHints(5);
        testStatistics.setConfiguration(daoFactory.createConfigurationDao().findById(1));
        testStatistics.setAdministrator(daoFactory.createUserDao().findById(2));
    }

    @AfterClass
    public static void tearDown() {
        statisticsDao.close();
    }

    @Test
    public void create() {
        int id = statisticsDao.create(testStatistics);
        testStatistics.setId(id);
        assertEquals(testStatistics, statisticsDao.findById(id));
        statisticsDao.delete(id);
    }

    @Test
    public void findById() {
        Statistics statistics = statisticsDao.findById(1);
        assertEquals(0,statistics.getPlayersScore());
        assertEquals(0, statistics.getSpectatorsScore());
        assertEquals(0, statistics.getNumberOfHints());
        assertEquals(1, statistics.getConfiguration().getId().intValue());
    }

    @Test
    public void findUserStatistics() {
        User user = daoFactory.createUserDao().findById(1);
        List<Statistics> statistics = statisticsDao.findUserStatistics(user);
        assertTrue(statistics.size() > 0);
    }

    @Test
    public void findStatisticsFromTo() {
        List<Statistics> statistics = statisticsDao.findStatisticsFromTo(1, 10);
        assertSame(statistics.size(), 10);
    }

    @Test
    public void getNumberOfRows() {
        assertTrue(statisticsDao.getNumberOfRows() > 0);
    }

    @Test
    public void findAll() {
        int id = statisticsDao.create(testStatistics);
        List<Statistics> statistics = statisticsDao.findAll();
        assertTrue(statistics.size() > 0);
        statisticsDao.delete(id);
    }

    @Test
    public void update() {
        int id = statisticsDao.create(testStatistics);

        testStatistics.setId(id);
        testStatistics.setNumberOfHints(10);
        testStatistics.setPlayersScore(10);
        statisticsDao.update(testStatistics);

        Statistics updatedStatistics = statisticsDao.findById(id);
        assertEquals(testStatistics.getPlayersScore(), updatedStatistics.getPlayersScore());
        assertEquals(testStatistics.getSpectatorsScore(), updatedStatistics.getSpectatorsScore());
        assertEquals(testStatistics.getNumberOfHints(), updatedStatistics.getNumberOfHints());
        assertEquals(testStatistics.getConfiguration().getId().intValue(), updatedStatistics.getConfiguration().getId().intValue());
    }

    @Test(expected = NoSuchElementException.class)
    public void delete() {
        int id = statisticsDao.create(testStatistics);
        statisticsDao.delete(id);
        assertNull(statisticsDao.findById(id));
    }
}