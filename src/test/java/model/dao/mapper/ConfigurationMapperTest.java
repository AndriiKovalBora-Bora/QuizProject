/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.mapper;

import model.dao.implementation.ConnectionPoolHolder;
import model.entities.Locales;
import model.entities.configuration.Configuration;
import model.entities.configuration.StatisticsFormat;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests ConfigurationMapper
 */
public class ConfigurationMapperTest {
    private static ConfigurationMapper configurationMapper;

    @BeforeClass
    public static void setUp() {
        configurationMapper = new ConfigurationMapper();
    }

    @Test
    public void extractFromResultSet() throws SQLException {
        Connection connection = ConnectionPoolHolder.getDataSource().getConnection();
        final String query = "SELECT * FROM configuration " +
                "WHERE configuration.id_configuration = 1";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) fail();
            Configuration configuration = configurationMapper.extractFromResultSet(rs, "configuration");
            assertEquals(15, configuration.getTime());
            assertEquals(1, configuration.getNumberOfPlayers());
            assertEquals(2, configuration.getMaxScore());
            assertEquals(2, configuration.getMaxNumberOfHints());
            assertEquals(StatisticsFormat.LONG, configuration.getStatisticsFormat());
            assertEquals(StatisticsFormat.LONG.getNames().get(Locales.ENGLISH.getLocale()), configuration.getLocalizedConfigurations().get(Locales.ENGLISH.getLocale()).getStatisticsFormatFormulation());
            assertEquals(StatisticsFormat.LONG.getNames().get(Locales.UKRAINIAN.getLocale()), configuration.getLocalizedConfigurations().get(Locales.UKRAINIAN.getLocale()).getStatisticsFormatFormulation());
            connection.close();
        }
    }

    @Test
    public void makeUnique() {
        Map<Integer, Configuration> configurations = new HashMap<>();
        Configuration configuration1 = new Configuration();
        configuration1.setId(1);

        Configuration configuration2 = new Configuration();
        configuration2.setId(2);

        configurations.put(configuration1.getId(), configuration1);

        assertEquals(configuration1, configurationMapper.makeUnique(configurations, configuration1));
        assertEquals(configuration2, configurationMapper.makeUnique(configurations, configuration2));
    }
}