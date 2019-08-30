/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao;

import model.dao.implementation.JDBCDaoFactory;
import org.apache.log4j.Logger;

/**
 * Provides DaoFactory for crating entities' dao
 */
public abstract class DaoFactory {

    /**
     * DaoFactory
     */
    private static DaoFactory daoFactory;

    /**
     * Creates UserDao
     *
     * @return UserDao
     */
    public abstract UserDao createUserDao();

    /**
     * Creates StatisticsDao
     *
     * @return StatisticsDao
     */
    public abstract StatisticsDao createStatisticsDao();

    /**
     * Creates QuestionDao
     *
     * @return QuestionDao
     */
    public abstract QuestionDao createQuestionDao();

    /**
     * Creates ConfigurationDao
     *
     * @return ConfigurationDao
     */
    public abstract ConfigurationDao createConfigurationDao();

    /**
     * Creates HintDao
     *
     * @return HintDao
     */
    public abstract HintDao createHintDao();

    /**
     * Returns instance of DaoFactory
     *
     * @return DaoFactory
     */
    public static DaoFactory getInstance() {
        Logger.getLogger(DaoFactory.class).info("DaoFactory class, getInstance method");
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
