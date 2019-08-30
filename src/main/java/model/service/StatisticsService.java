/*
 * Copyright (C) 2019 Quiz Project
 */

package model.service;

import model.dao.DaoFactory;
import model.dao.StatisticsDao;
import model.entities.statistics.Statistics;
import model.entities.user.User;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Provides service for Statistics entity
 */
public class StatisticsService {

    /**
     * Factory for creating entities' dao
     */
    private DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * Retrieves all statistics from DB
     *
     * @return List of all statistics
     */
    public List<Statistics> getAllStatistics() {
        try (StatisticsDao statisticsDao = daoFactory.createStatisticsDao()) {
            Logger.getLogger(this.getClass()).info("StatisticsService class, getAllStatistics method");
            return statisticsDao.findAll();
        }
    }

    /**
     * Adds statistics to DB
     *
     * @param statistics Statistics to be added to DB
     * @return id of the statistics added to DB
     */
    public int addStatisticsToDB(Statistics statistics) {
        try (StatisticsDao statisticsDao = daoFactory.createStatisticsDao()) {
            Logger.getLogger(this.getClass()).info("StatisticsService class, addStatisticsToDB method");
            return statisticsDao.create(statistics);
        }
    }

    /**
     * Updates statistics in DB
     *
     * @param statistics Statistics to be updated in DB
     */
    public void updateStatistics(Statistics statistics) {
        try (StatisticsDao statisticsDao = daoFactory.createStatisticsDao()) {
            Logger.getLogger(this.getClass()).info("StatisticsService class, updateStatistics method");
            statisticsDao.update(statistics);
        }
    }

    /**
     * Retrieves all statistics, which belongs to certain user
     *
     * @param user User whose statistics will be retrieved
     * @return List of the statistics of certain user
     */
    public List<Statistics> getUserStatistics(User user) {
        try (StatisticsDao statisticsDao = daoFactory.createStatisticsDao()) {
            Logger.getLogger(this.getClass()).info("StatisticsService class, getUserStatistics method");
            return statisticsDao.findUserStatistics(user);
        }
    }

    /**
     * Retrieves certain amount of statistics from DB
     *
     * @param currentPage    Current page of statistics, which will be displayed
     * @param recordsPerPage The number of statistics records to be displayed on one page
     * @return List of statistics for current page
     */
    public List<Statistics> getStatisticsFromTo(int currentPage, int recordsPerPage) {
        try (StatisticsDao statisticsDao = daoFactory.createStatisticsDao()) {
            Logger.getLogger(this.getClass()).info("StatisticsService class, getStatisticsFromTo method");
            return statisticsDao.findStatisticsFromTo(currentPage, recordsPerPage);
        }
    }

    /**
     * Returns the number of rows of statistics records in DB
     *
     * @return the number of rows of statistics records in DB
     */
    public int getNumberOfRows() {
        try (StatisticsDao statisticsDao = daoFactory.createStatisticsDao()) {
            Logger.getLogger(this.getClass()).info("StatisticsService class, getNumberOfRows method");
            return statisticsDao.getNumberOfRows();
        }
    }

    /**
     * Gives plus one point to players team
     *
     * @param activeGame Statistics of active game, which is played
     */
    public void givePointToPlayers(Statistics activeGame) {
        Logger.getLogger(this.getClass()).info("StatisticsService class, givePointToPlayers method");
        activeGame.setPlayersScore(activeGame.getPlayersScore() + 1);
    }

    /**
     * Gives plus one point to spectators team
     *
     * @param activeGame Statistics of active game, which is played
     */
    public void givePointToSpectators(Statistics activeGame) {
        Logger.getLogger(this.getClass()).info("StatisticsService class, givePointToSpectators method");
        activeGame.setSpectatorsScore(activeGame.getSpectatorsScore() + 1);
    }
}
