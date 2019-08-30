/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import model.dao.ConfigurationDao;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.Locales;
import model.entities.configuration.Configuration;
import model.entities.configuration.LocalizedConfiguration;
import model.entities.configuration.StatisticsFormat;
import model.entities.user.LocalizedUser;
import model.entities.user.Role;
import model.entities.user.Status;
import model.entities.user.User;
import org.junit.*;

import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Tests JDBCConfigurationDao
 */
public class JDBCConfigurationDaoTest {
    private static ConfigurationDao configurationDao;
    private static Configuration testConfiguration;

    @BeforeClass
    public static void setUp() throws Exception {
        DaoFactory daoFactory = DaoFactory.getInstance();
        configurationDao = daoFactory.createConfigurationDao();

        testConfiguration = new Configuration();
        LocalizedConfiguration localizedConfigurationEN  = new LocalizedConfiguration();
        localizedConfigurationEN.setStatisticsFormatFormulation(StatisticsFormat.LONG.getNames().get(Locales.ENGLISH.getLocale()));

        LocalizedConfiguration localizedConfigurationUA  = new LocalizedConfiguration();
        localizedConfigurationUA.setStatisticsFormatFormulation(StatisticsFormat.LONG.getNames().get(Locales.UKRAINIAN.getLocale()));

        testConfiguration.getLocalizedConfigurations().put(Locales.ENGLISH.getLocale(), localizedConfigurationEN);
        testConfiguration.getLocalizedConfigurations().put(Locales.UKRAINIAN.getLocale(), localizedConfigurationUA);

        testConfiguration.setTime(20);
        testConfiguration.setNumberOfPlayers(10);
        testConfiguration.setMaxScore(10);
        testConfiguration.setMaxNumberOfHints(5);
        testConfiguration.setStatisticsFormat(StatisticsFormat.LONG);
    }

    @AfterClass
    public static void tearDown() {
        configurationDao.close();
    }

    @Test
    public void create() {
        int id = configurationDao.create(testConfiguration);
        testConfiguration.setId(id);
        configurationDao.findById(1);
        configurationDao.delete(id);
    }

    @Test
    public void findById() {
        Configuration configuration = configurationDao.findById(1);
        assertEquals(15, configuration.getTime());
        assertEquals(1, configuration.getNumberOfPlayers());
        assertEquals(2, configuration.getMaxScore());
        assertEquals(2, configuration.getMaxNumberOfHints());
        assertEquals(StatisticsFormat.LONG, configuration.getStatisticsFormat());
        assertEquals("long", configuration.getLocalizedConfigurations().get(Locales.ENGLISH.getLocale()).getStatisticsFormatFormulation());
        assertEquals("довга", configuration.getLocalizedConfigurations().get(Locales.UKRAINIAN.getLocale()).getStatisticsFormatFormulation());
    }

    @Test
    public void findAll() {
        int id = configurationDao.create(testConfiguration);
        List<Configuration> configurations = configurationDao.findAll();
        assertTrue(configurations.size() > 0);
        configurationDao.delete(id);
    }

    @Test
    public void update() {
        int id = configurationDao.create(testConfiguration);
        testConfiguration.setId(id);
        testConfiguration.setTime(45);
        testConfiguration.setMaxNumberOfHints(30);
        testConfiguration.setNumberOfPlayers(40);
        configurationDao.update(testConfiguration);

        Configuration updatedConfiguration = configurationDao.findById(id);
        assertEquals(testConfiguration.getTime(), updatedConfiguration.getTime());
        assertEquals(testConfiguration.getNumberOfPlayers(), updatedConfiguration.getNumberOfPlayers());
        assertEquals(testConfiguration.getMaxScore(), updatedConfiguration.getMaxScore());
        assertEquals(testConfiguration.getMaxNumberOfHints(), updatedConfiguration.getMaxNumberOfHints());
        assertEquals(testConfiguration.getStatisticsFormat(), updatedConfiguration.getStatisticsFormat());
        assertEquals(testConfiguration.getLocalizedConfigurations().get(Locales.ENGLISH.getLocale()).getStatisticsFormatFormulation(), updatedConfiguration.getLocalizedConfigurations().get(Locales.ENGLISH.getLocale()).getStatisticsFormatFormulation());
        assertEquals(testConfiguration.getLocalizedConfigurations().get(Locales.UKRAINIAN.getLocale()).getStatisticsFormatFormulation(),updatedConfiguration.getLocalizedConfigurations().get(Locales.UKRAINIAN.getLocale()).getStatisticsFormatFormulation());

        configurationDao.delete(id);
    }

    @Test
    public void delete() {
        int id = configurationDao.create(testConfiguration);
        configurationDao.delete(id);
        assertNull(configurationDao.findById(id));
    }
}