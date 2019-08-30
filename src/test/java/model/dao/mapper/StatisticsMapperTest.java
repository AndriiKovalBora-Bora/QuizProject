/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.mapper;

import model.dao.implementation.ConnectionPoolHolder;
import model.entities.statistics.Statistics;
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
 * Test StatisticsMapper
 */
public class StatisticsMapperTest {
    private static StatisticsMapper statisticsMapper;

    @BeforeClass
    public static void setUp() {
        statisticsMapper = new StatisticsMapper();
    }

    @Test
    public void extractFromResultSet() throws SQLException {
        Connection connection = ConnectionPoolHolder.getDataSource().getConnection();
        final String query = "SELECT * FROM statistics " +
                "WHERE statistics.id_statistics = 1";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) fail();
            Statistics statistics = statisticsMapper.extractFromResultSet(rs, "statistics");
            assertEquals(1, statistics.getId().intValue());
            assertEquals(0, statistics.getPlayersScore());
            assertEquals(0, statistics.getSpectatorsScore());
            assertEquals(0, statistics.getNumberOfHints());
            connection.close();
        }
    }

    @Test
    public void makeUnique() {
        Map<Integer, Statistics> statisticsMap = new HashMap<>();
        Statistics statistics1 = new Statistics();
        statistics1.setId(1);

        Statistics statistics2 = new Statistics();
        statistics2.setId(2);

        statisticsMap.put(statistics1.getId(), statistics1);

        assertEquals(statistics1, statisticsMapper.makeUnique(statisticsMap, statistics1));
        assertEquals(statistics2, statisticsMapper.makeUnique(statisticsMap, statistics2));
    }
}