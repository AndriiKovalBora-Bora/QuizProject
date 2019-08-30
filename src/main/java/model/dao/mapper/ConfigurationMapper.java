/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.mapper;

import model.entities.Locales;
import model.entities.configuration.Configuration;
import model.entities.configuration.LocalizedConfiguration;
import model.entities.configuration.StatisticsFormat;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Provides methods for extracting Configuration data from ResultSet
 */
public class ConfigurationMapper implements ObjectMapper<Configuration> {

    /**
     * Extracts data from ResultSet and creates Configuration
     *
     * @param rs        ResultSet to be processed
     * @param tableName Name of the table
     * @return Configuration entity
     * @throws SQLException if error was occurred
     */
    @Override
    public Configuration extractFromResultSet(ResultSet rs, String tableName) throws SQLException {
        Logger.getLogger(this.getClass()).info("ConfigurationMapper class, extractFromResultSet method");
        if (rs.getString(tableName + ".id_configuration") == null) {
            Logger.getLogger(this.getClass()).info("id_configuration is null");
            return null;
        }

        Configuration configuration = new Configuration();
        configuration.setId(rs.getInt(tableName + ".id_configuration"));
        configuration.setTime(rs.getInt(tableName + ".time"));
        configuration.setNumberOfPlayers(rs.getInt(tableName + ".number_of_players"));
        configuration.setMaxScore(rs.getInt(tableName + ".max_score"));
        configuration.setMaxNumberOfHints(rs.getInt(tableName + ".max_number_of_hints"));
        configuration.setStatisticsFormat(StatisticsFormat.valueOf(rs.getString(tableName + ".statistics_format").toUpperCase()));

        LocalizedConfiguration localizedConfigurationEN = new LocalizedConfiguration();
        localizedConfigurationEN.setId(configuration.getId());
        localizedConfigurationEN.setTime(configuration.getTime());
        localizedConfigurationEN.setNumberOfPlayers(configuration.getNumberOfPlayers());
        localizedConfigurationEN.setMaxScore(configuration.getMaxScore());
        localizedConfigurationEN.setMaxNumberOfHints(configuration.getMaxNumberOfHints());
        configuration.setStatisticsFormat(configuration.getStatisticsFormat());
        localizedConfigurationEN.setStatisticsFormatFormulation(rs.getString(tableName + ".statistics_format_formulation_en"));

        LocalizedConfiguration localizedConfigurationUA = new LocalizedConfiguration();
        localizedConfigurationUA.setId(configuration.getId());
        localizedConfigurationUA.setTime(configuration.getTime());
        localizedConfigurationUA.setNumberOfPlayers(configuration.getNumberOfPlayers());
        localizedConfigurationUA.setMaxScore(configuration.getMaxScore());
        localizedConfigurationUA.setMaxNumberOfHints(configuration.getMaxNumberOfHints());
        configuration.setStatisticsFormat(configuration.getStatisticsFormat());
        localizedConfigurationUA.setStatisticsFormatFormulation(rs.getString(tableName + ".statistics_format_formulation_ua"));

        configuration.getLocalizedConfigurations().put(Locales.ENGLISH.getLocale(), localizedConfigurationEN);
        configuration.getLocalizedConfigurations().put(Locales.UKRAINIAN.getLocale(), localizedConfigurationUA);

        return configuration;
    }

    /**
     * Checks if Configuration is unique or not
     *
     * @param cache  Map of configurations, which were extracted earlier
     * @param entity Current configuration
     * @return Unique configuration
     */
    @Override
    public Configuration makeUnique(Map<Integer, Configuration> cache, Configuration entity) {
        Logger.getLogger(this.getClass()).info("ConfigurationMapper class, makeUnique method");
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
