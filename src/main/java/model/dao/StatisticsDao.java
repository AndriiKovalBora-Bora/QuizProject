/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao;

import model.entities.statistics.Statistics;
import model.entities.user.User;

import java.util.List;

/**
 * Provides methods for StatisticsDao
 */
public interface StatisticsDao extends GenericDao<Statistics> {

    /**
     * Retrieves user's statistics from DB
     *
     * @param user User whose statistics to be retrieved
     * @return List of user's statistics
     */
    List<Statistics> findUserStatistics(User user);

    /**
     * Retrieves a part of all statistics from DB
     *
     * @param currentPage    Page which will display statistics
     * @param recordsPerPage Number of records per one page
     * @return List of statistics
     */
    List<Statistics> findStatisticsFromTo(int currentPage, int recordsPerPage);

    /**
     * Returns the number of statistics rows in DB
     *
     * @return number of rows in DB
     */
    int getNumberOfRows();
}
