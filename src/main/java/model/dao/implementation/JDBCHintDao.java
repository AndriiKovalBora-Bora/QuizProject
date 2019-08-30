/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import model.dao.HintDao;
import model.dao.mapper.ConfigurationMapper;
import model.dao.mapper.HintMapper;
import model.entities.Locales;
import model.entities.configuration.Configuration;
import model.entities.hint.Hint;
import model.entities.question.TypeOfQuestion;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements methods of HintDao interface
 */
public class JDBCHintDao implements HintDao {

    /**
     * Connection to the DB
     */
    private Connection connection;

    /**
     * Creates class
     *
     * @param connection Connection to the DB
     */
    JDBCHintDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Implements adding hint to DB
     *
     * @param entity Entity to be stored in DB
     * @return Id of created record in DB
     */
    @Override
    public int create(Hint entity) {
        Logger.getLogger(this.getClass()).info("JDBCHintDao class, create method");
        String query = "INSERT INTO hint( " +
                "type_of_hint, " +
                "hint_formulation_en, " +
                "hint_formulation_ua, " +
                "type_of_question) " +
                "VALUES(?, ?, ?, ?)";

        String queryGetId = "SELECT LAST_INSERT_ID()";

        try (Statement st = connection.createStatement();
             PreparedStatement ps = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ps.setString(1, entity.getTypeOfHint().name().toLowerCase());
            ps.setString(2, entity.getLocalizedHints().get(Locales.ENGLISH.getLocale()).getHintFormulation());
            ps.setString(3, entity.getLocalizedHints().get(Locales.UKRAINIAN.getLocale()).getHintFormulation());
            ps.setString(4, entity.getTypeOfQuestion().name().toLowerCase());
            ps.executeUpdate();

            ResultSet rs = st.executeQuery(queryGetId);
            connection.commit();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCHintDao class, create error");
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
     * Implements retrieving hint from DB by Id
     *
     * @param id Id of the hint
     * @return Hint
     */
    @Override
    public Hint findById(int id) {
        Logger.getLogger(this.getClass()).info("JDBCHintDao class, findById method");
        String query = "SELECT * FROM hint " +
                "LEFT JOIN configuration_has_hint h on hint.id_hint = h.id_hint " +
                "LEFT JOIN configuration c on h.id_configuration = c.id_configuration " +
                "WHERE hint.id_hint = ? ";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            HintMapper hintMapper = new HintMapper();
            ConfigurationMapper configurationMapper = new ConfigurationMapper();

            if (!rs.next()) return null;
            Hint hint = hintMapper.extractFromResultSet(rs, "hint");
            do {
                Configuration configuration = configurationMapper.extractFromResultSet(rs, "c");
                if ((!hint.getConfigurations().contains(configuration)) && (configuration != null))
                    hint.getConfigurations().add(configuration);
            } while (rs.next());
            return hint;

        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCHintDao class, findById error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all hints from DB
     *
     * @return List of all hints
     */
    @Override
    public List<Hint> findAll() {
        Logger.getLogger(this.getClass()).info("JDBCHintDao class, findAll method");
        Map<Integer, Hint> hints = new HashMap<>();

        String query = "SELECT * FROM hint " +
                "LEFT JOIN configuration_has_hint h on hint.id_hint = h.id_hint " +
                "LEFT JOIN configuration c on h.id_configuration = c.id_configuration ";

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            HintMapper hintMapper = new HintMapper();
            ConfigurationMapper configurationMapper = new ConfigurationMapper();

            while (rs.next()) {
                Hint hint = hintMapper.extractFromResultSet(rs, "hint");
                Configuration configuration = configurationMapper.extractFromResultSet(rs, "c");

                hint = hintMapper.makeUnique(hints, hint);
                if (!hint.getConfigurations().contains(configuration))
                    hint.getConfigurations().add(configuration);
            }
            return new ArrayList<>(hints.values());

        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCHintDao class, findAll error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves hints which can be applied to question with given typeOfQuestion
     *
     * @param typeOfQuestion Type of question
     * @return List of Questions
     */
    @Override
    public List<Hint> findAllHintsByTypeOfQuestion(TypeOfQuestion typeOfQuestion) {
        Logger.getLogger(this.getClass()).info("JDBCHintDao class, findAllHintsByTypeOfQuestion method");
        Map<Integer, Hint> hints = new HashMap<>();

        String query = "SELECT * FROM hint " +
                "LEFT JOIN configuration_has_hint h on hint.id_hint = h.id_hint " +
                "LEFT JOIN configuration c on h.id_configuration = c.id_configuration " +
                "WHERE hint.type_of_question = ? ";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, typeOfQuestion.name().toLowerCase());
            ResultSet rs = ps.executeQuery();

            HintMapper hintMapper = new HintMapper();
            ConfigurationMapper configurationMapper = new ConfigurationMapper();

            while (rs.next()) {
                Hint hint = hintMapper.extractFromResultSet(rs, "hint");
                Configuration configuration = configurationMapper.extractFromResultSet(rs, "c");

                hint = hintMapper.makeUnique(hints, hint);
                if (!hint.getConfigurations().contains(configuration))
                    hint.getConfigurations().add(configuration);
            }
            return new ArrayList<>(hints.values());

        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCHintDao class, findAllHintsByTypeOfQuestion error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates hint in DB
     *
     * @param entity Hint to be updated in DB
     */
    @Override
    public void update(Hint entity) {
        Logger.getLogger(this.getClass()).info("JDBCHintDao class, update method");
        String queryHint = "UPDATE hint SET " +
                "type_of_hint = ?, " +
                "hint_formulation_en = ?, " +
                "hint_formulation_ua = ?, " +
                "type_of_question = ? " +
                "WHERE hint.id_hint = ?";

        String queryConfigurationHasHint = "INSERT IGNORE INTO configuration_has_hint(" +
                "id_configuration, " +
                "id_hint) " +
                "VALUES(?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(queryHint);
             PreparedStatement ps1 = connection.prepareStatement(queryConfigurationHasHint)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ps.setString(1, entity.getTypeOfHint().name().toLowerCase());
            ps.setString(2, entity.getLocalizedHints().get(Locales.ENGLISH.getLocale()).getHintFormulation());
            ps.setString(3, entity.getLocalizedHints().get(Locales.UKRAINIAN.getLocale()).getHintFormulation());
            ps.setString(4, entity.getTypeOfQuestion().name().toLowerCase());
            ps.setInt(5, entity.getId());
            ps.executeUpdate();

            entity.getConfigurations().forEach(x -> {
                try {
                    ps1.setInt(1, x.getId());
                    ps1.setInt(2, entity.getId());
                    ps1.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCHintDao class, update error");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Deletes question from DB by id
     *
     * @param id Id of the Question to be deleted
     */
    @Override
    public void delete(int id) {
        Logger.getLogger(this.getClass()).info("JDBCHintDao class, delete method");

        String queryQuestionHasHint = "DELETE FROM question_has_hint WHERE id_hint = ? ";
        String queryConfigurationHasHint = "DELETE FROM configuration_has_hint WHERE id_hint = ? ";
        String queryHint = "DELETE FROM hint WHERE id_hint = ? ";

        try (PreparedStatement ps = connection.prepareStatement(queryQuestionHasHint);
             PreparedStatement ps1 = connection.prepareStatement(queryConfigurationHasHint);
             PreparedStatement ps2 = connection.prepareStatement(queryHint)) {
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
            Logger.getLogger(this.getClass()).error("JDBCHintDao class, delete error");
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
        try {
            Logger.getLogger(this.getClass()).info("JDBCHintDao class, close method");
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCHintDao class, close error");
            throw new RuntimeException(e);
        }
    }
}
