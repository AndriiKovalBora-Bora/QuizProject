/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import model.dao.ConfigurationDao;
import model.dao.mapper.ConfigurationMapper;
import model.dao.mapper.HintMapper;
import model.entities.Locales;
import model.entities.configuration.Configuration;
import model.entities.hint.Hint;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements methods of ConfigurationDao interface
 */
public class JDBCConfigurationDao implements ConfigurationDao {

    /**
     * Connection to the DB
     */
    private Connection connection;

    /**
     * Creates class
     *
     * @param connection Connection to the DB
     */
    JDBCConfigurationDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Implements adding configuration to DB
     *
     * @param entity Entity to be stored in DB
     * @return Id of created record in DB
     */
    @Override
    public int create(Configuration entity) {
        Logger.getLogger(this.getClass()).info("JDBCConfigurationDao class, create method");
        String queryInsertConfiguration = "INSERT INTO configuration(" +
                "time, " +
                "number_of_players," +
                "max_score, " +
                "max_number_of_hints, " +
                "statistics_format, " +
                "statistics_format_formulation_en, " +
                "statistics_format_formulation_ua) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        String queryGetId = "SELECT LAST_INSERT_ID()";

        String queryInsertConfigurationHasHint = "INSERT IGNORE INTO configuration_has_hint(" +
                "id_configuration," +
                " id_hint) " +
                "VALUES(?, ?)";

        try (Statement st = connection.createStatement(); PreparedStatement ps = connection.prepareStatement(queryInsertConfiguration);
             PreparedStatement ps1 = connection.prepareStatement(queryInsertConfigurationHasHint)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ps.setInt(1, entity.getTime());
            ps.setInt(2, entity.getNumberOfPlayers());
            ps.setInt(3, entity.getMaxScore());
            ps.setInt(4, entity.getMaxNumberOfHints());
            ps.setString(5, entity.getStatisticsFormat().name().toLowerCase());
            ps.setString(6, entity.getLocalizedConfigurations().get(Locales.ENGLISH.getLocale()).getStatisticsFormatFormulation());
            ps.setString(7, entity.getLocalizedConfigurations().get(Locales.UKRAINIAN.getLocale()).getStatisticsFormatFormulation());
            ps.executeUpdate();

            ResultSet rs = st.executeQuery(queryGetId);
            rs.next();
            int idConfiguration = rs.getInt(1);

            entity.getHints().forEach(x -> {
                try {
                    ps1.setInt(1, idConfiguration);
                    ps1.setInt(2, x.getId());
                    ps1.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            connection.commit();
            return idConfiguration;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCConfigurationDao class, create error");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Implements retrieving configuration from DB by Id
     *
     * @param id Id of the configuration
     * @return Configuration
     */
    @Override
    public Configuration findById(int id) {
        Logger.getLogger(this.getClass()).info("JDBCConfigurationDao class, findById method");
        String query = "SELECT * FROM configuration " +
                "LEFT JOIN configuration_has_hint ON configuration.id_configuration = configuration_has_hint.id_configuration " +
                "LEFT JOIN hint ON configuration_has_hint.id_hint = hint.id_hint " +
                "WHERE configuration.id_configuration = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            ConfigurationMapper configurationMapper = new ConfigurationMapper();
            HintMapper hintMapper = new HintMapper();

            if (!rs.next()) return null;
            Configuration configuration = configurationMapper.extractFromResultSet(rs, "configuration");

            do {
                Hint hint = hintMapper.extractFromResultSet(rs, "hint");
                if (hint != null) configuration.getHints().add(hint);
            } while (rs.next());

            return configuration;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCConfigurationDao class, findById method");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all configurations from DB
     *
     * @return List of all configurations
     */
    @Override
    public List<Configuration> findAll() {
        Logger.getLogger(this.getClass()).info("JDBCConfigurationDao class, findAll method");
        Map<Integer, Configuration> configurations = new HashMap<>();

        String query = "SELECT * FROM configuration " +
                "LEFT JOIN configuration_has_hint ON configuration.id_configuration = configuration_has_hint.id_configuration " +
                "LEFT JOIN hint ON configuration_has_hint.id_hint = hint.id_hint ";

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            ConfigurationMapper configurationMapper = new ConfigurationMapper();
            HintMapper hintMapper = new HintMapper();

            while (rs.next()) {
                Configuration configuration = configurationMapper.extractFromResultSet(rs, "configuration");
                Hint hint = hintMapper.extractFromResultSet(rs, "hint");

                configurationMapper.makeUnique(configurations, configuration);
                configuration.getHints().add(hint);
            }
            return new ArrayList<>(configurations.values());

        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCConfigurationDao class, findAll error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates Configuration in DB
     *
     * @param entity Configuration to be updated in DB
     */
    @Override
    public void update(Configuration entity) {
        Logger.getLogger(this.getClass()).info("JDBCConfigurationDao class, update method");
        String queryConfiguration = "UPDATE configuration SET " +
                "time = ?, " +
                "number_of_players = ?, " +
                "max_score = ?, " +
                "max_number_of_hints = ?, " +
                "statistics_format = ?, " +
                "statistics_format_formulation_en = ?, " +
                "statistics_format_formulation_ua = ? " +
                "WHERE configuration.id_configuration = ?";

        String queryConfigurationHasHint = "INSERT IGNORE INTO configuration_has_hint(" +
                "id_configuration, " +
                "id_hint) " +
                "VALUES(?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(queryConfiguration);
             PreparedStatement ps1 = connection.prepareStatement(queryConfigurationHasHint)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ps.setInt(1, entity.getTime());
            ps.setInt(2, entity.getNumberOfPlayers());
            ps.setInt(3, entity.getMaxScore());
            ps.setInt(4, entity.getMaxNumberOfHints());
            ps.setString(5, entity.getStatisticsFormat().name().toLowerCase());
            ps.setString(6, entity.getLocalizedConfigurations().get(Locales.ENGLISH.getLocale()).getStatisticsFormatFormulation());
            ps.setString(7, entity.getLocalizedConfigurations().get(Locales.UKRAINIAN.getLocale()).getStatisticsFormatFormulation());
            ps.setInt(8, entity.getId());
            ps.executeUpdate();

            entity.getHints().forEach(x -> {
                try {
                    ps1.setInt(1, entity.getId());
                    ps1.setInt(2, x.getId());
                    ps1.executeUpdate();
                } catch (SQLException e) {
                    Logger.getLogger(this.getClass()).error("JDBCConfigurationDao class, update hints error");
                    e.printStackTrace();
                }
            });
            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCConfigurationDao class, update error");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Deletes configuration from DB by id
     *
     * @param id Id of the Configuration to be deleted
     */
    @Override
    public void delete(int id) {
        Logger.getLogger(this.getClass()).info("JDBCConfigurationDao class, delete method");
        String queryConfigurationHasHint = "DELETE FROM configuration_has_hint WHERE id_configuration = ? ";
        String queryStatistics = "UPDATE statistics SET id_configuration = NULL " +
                "WHERE id_configuration = ? ";
        String queryConfiguration = "DELETE FROM configuration WHERE id_configuration = ? ";

        try (PreparedStatement ps = connection.prepareStatement(queryConfigurationHasHint);
             PreparedStatement ps1 = connection.prepareStatement(queryStatistics);
             PreparedStatement ps2 = connection.prepareStatement(queryConfiguration)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ps.setInt(1, id);
            ps.executeUpdate();

            ps1.setInt(1, id);
            ps1.executeUpdate();

            ps2.setInt(1, id);
            ps2.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCConfigurationDao class, delete error");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Closes connection to DB
     */
    @Override
    public void close() {
        Logger.getLogger(this.getClass()).info("JDBCConfigurationDao class, close method");
        try {
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCConfigurationDao class, close error");
            throw new RuntimeException(e);
        }
    }
}
