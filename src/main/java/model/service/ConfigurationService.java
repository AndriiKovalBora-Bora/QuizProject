/*
 * Copyright (C) 2019 Quiz Project
 */

package model.service;

import model.dao.ConfigurationDao;
import model.dao.DaoFactory;
import model.entities.configuration.Configuration;
import model.exception.ConfigurationException;
import org.apache.log4j.Logger;

import java.util.List;

import static model.constants.Constants.*;

/**
 * Provides service for Configuration entity
 */
public class ConfigurationService {

    /**
     * Factory for creating entities' dao
     */
    private DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * Retrieves all configuration from DB
     *
     * @return List of all configuration from DB
     */
    public List<Configuration> getAllConfiguration() {
        try (ConfigurationDao configurationDao = daoFactory.createConfigurationDao()) {
            Logger.getLogger(this.getClass()).info("ConfigurationService class, getAllConfiguration method");
            return configurationDao.findAll();
        }
    }

    /**
     * Retrieves configuration from DB by id
     *
     * @param id Id of configuration to be retrieved
     * @return Configuration by id
     */
    public Configuration getConfigurationById(int id) {
        try (ConfigurationDao configurationDao = daoFactory.createConfigurationDao()) {
            Logger.getLogger(this.getClass()).info("ConfigurationService class, getAllConfigurationById int method");
            return configurationDao.findById(id);
        }
    }

    /**
     * Retrieves configuration from DB by id
     *
     * @param id Id of configuration to be retrieved
     * @return Configuration by id
     */
    public Configuration getConfigurationById(String id) {
        try (ConfigurationDao configurationDao = daoFactory.createConfigurationDao()) {
            Logger.getLogger(this.getClass()).info("ConfigurationService class, getAllConfigurationById string method");
            return configurationDao.findById(Integer.valueOf(id));
        }
    }

    /**
     * Adds configuration to DB
     *
     * @param configuration Configuration to be added to DB
     */
    public void addConfigurationToDB(Configuration configuration) {
        try (ConfigurationDao configurationDao = daoFactory.createConfigurationDao()) {
            Logger.getLogger(this.getClass()).info("ConfigurationService class, addConfigurationToDB method");
            configurationDao.create(configuration);
        }
    }

    /**
     * Validates configuration data
     *
     * @param time             Time for answer
     * @param numberOfPlayers  Number of players in the game
     * @param maxScore         Best of maxScore
     * @param maxNumberOfHints Maximum number of hint. which can be used
     * @throws NumberFormatException if configuration data is not valid
     */
    public void validateData(String time, String numberOfPlayers, String maxScore, String maxNumberOfHints) throws NumberFormatException {
        Logger.getLogger(this.getClass()).info("ConfigurationService class, validateData method");
        if ((Integer.valueOf(time) < MIN_TIME_FOR_ANSWER) || (Integer.valueOf(time) > MAX_TIME_FOR_ANSWER)
                || (Integer.valueOf(numberOfPlayers) < MIN_NUMBER_OF_PLAYERS) || (Integer.valueOf(numberOfPlayers) > MAX_NUMBER_OF_PLAYERS)
                || (Integer.valueOf(maxScore) < MIN_MAX_SCORE) || ((Integer.valueOf(maxScore)) > MAX_MAX_SCORE)
                || (Integer.valueOf(maxNumberOfHints) < MIN_MAX_NUMBER_OF_HINTS) || (Integer.valueOf(maxNumberOfHints) > MAX_MAX_NUMBER_OF_HINTS))
            throw new ConfigurationException("Wrong Parameters");
    }
}
