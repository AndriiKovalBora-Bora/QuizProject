/*
 * Copyright (C) 2019 Quiz Project
 */


package model.dao.mapper;

import model.entities.statistics.Statistics;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Provides methods for extracting Statistics data from ResultSet
 */
public class StatisticsMapper implements ObjectMapper<Statistics> {

    /**
     * Extracts data from ResultSet and creates Statistics
     *
     * @param rs        ResultSet to be processed
     * @param tableName Name of the table
     * @return Statistics entity
     * @throws SQLException if error was occurred
     */
    @Override
    public Statistics extractFromResultSet(ResultSet rs, String tableName) throws SQLException {
        Logger.getLogger(this.getClass()).info("StatisticsMapper class, extractFromResultSet method");
        Statistics statistics = new Statistics();
        statistics.setId(rs.getInt(tableName + ".id_statistics"));
        statistics.setPlayersScore(rs.getInt(tableName + ".players_score"));
        statistics.setSpectatorsScore(rs.getInt(tableName + ".spectators_score"));
        statistics.setNumberOfHints(rs.getInt(tableName + ".number_of_hints"));
        return statistics;
    }

    /**
     * Checks if Statistics is unique or not
     *
     * @param cache  Map of statistics, which were extracted earlier
     * @param entity Current statistics
     * @return Unique statistics
     */
    @Override
    public Statistics makeUnique(Map<Integer, Statistics> cache, Statistics entity) {
        Logger.getLogger(this.getClass()).info("StatisticsMapper class, makeUnique method");
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
