/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import model.dao.*;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Provides DaoFactory for creating entities' Dao
 */
public class JDBCDaoFactory extends DaoFactory {

    /**
     * DataSource
     */
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    /**
     * Creates UserDao
     *
     * @return UserDao
     */
    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    /**
     * Creates StatisticsDao
     *
     * @return StatisticsDao
     */
    @Override
    public StatisticsDao createStatisticsDao() {
        return new JDBCStatisticsDao(getConnection());
    }

    /**
     * Creates QuestionDao
     *
     * @return QuestionDao
     */
    @Override
    public QuestionDao createQuestionDao() {
        return new JDBCQuestionDao(getConnection());
    }

    /**
     * Creates ConfigurationDao
     *
     * @return ConfigurationDao
     */
    @Override
    public ConfigurationDao createConfigurationDao() {
        return new JDBCConfigurationDao(getConnection());
    }

    /**
     * Creates HintDao
     *
     * @return HintDao
     */
    @Override
    public HintDao createHintDao() {
        return new JDBCHintDao(getConnection());
    }

    /**
     * Returns connection to DB
     *
     * @return connection to DB
     */
    private Connection getConnection() {
        try {
            Logger.getLogger(this.getClass()).info("JDBCDaoFactory class, getConnection");
            return dataSource.getConnection();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCDaoFactory class, getConnection error");
            throw new RuntimeException(e);
        }
    }
}
