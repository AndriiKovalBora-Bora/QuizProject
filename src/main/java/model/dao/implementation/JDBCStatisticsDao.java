/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import model.dao.StatisticsDao;
import model.dao.mapper.*;
import model.entities.user.Role;
import model.entities.configuration.Configuration;
import model.entities.hint.Hint;
import model.entities.question.Question;
import model.entities.statistics.Statistics;
import model.entities.user.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * Implements methods of StatisticsDao interface
 */
public class JDBCStatisticsDao implements StatisticsDao {

    /**
     * Connection to the DB
     */
    private Connection connection;

    /**
     * Creates class
     *
     * @param connection Connection to the DB
     */
    JDBCStatisticsDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Implements adding statistics to DB
     *
     * @param entity Entity to be stored in DB
     * @return Id of created record in DB
     */
    @Override
    public int create(Statistics entity) {
        Logger.getLogger(this.getClass()).info("JDBCStatisticsDao class, create method");
        String queryInsertStatistics = "INSERT INTO statistics(" +
                "players_score, " +
                "spectators_score, " +
                "number_of_hints, " +
                "id_configuration) " +
                "VALUES(?, ?, ?, ?)";

        String queryGetId = "SELECT LAST_INSERT_ID()";

        String queryInsertStatisticsHasUser = "INSERT IGNORE INTO user_has_statistics(" +
                "id_user, " +
                "id_statistics) " +
                "VALUES(?, ?)";

        try (Statement st = connection.createStatement(); PreparedStatement ps = connection.prepareStatement(queryInsertStatistics);
             PreparedStatement ps1 = connection.prepareStatement(queryInsertStatisticsHasUser)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ps.setInt(1, entity.getPlayersScore());
            ps.setInt(2, entity.getSpectatorsScore());
            ps.setInt(3, entity.getNumberOfHints());
            ps.setInt(4, entity.getConfiguration().getId());
            ps.executeUpdate();

            ResultSet rs = st.executeQuery(queryGetId);
            rs.next();
            int idStatistics = rs.getInt(1);

            ps1.setInt(1, entity.getAdministrator().getId());
            ps1.setInt(2, idStatistics);
            ps1.executeUpdate();

            entity.getPlayers().forEach(x -> {
                try {
                    ps1.setInt(1, x.getId());
                    ps1.setInt(2, idStatistics);
                    ps1.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            connection.commit();
            return idStatistics;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCStatisticsDao class, create error");
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
     * Implements retrieving statistics from DB by Id
     *
     * @param id Id of the statistics
     * @return Statistics
     */
    @Override
    public Statistics findById(int id) {
        Logger.getLogger(this.getClass()).info("JDBCStatisticsDao class, findById method");
        Map<Integer, Statistics> statisticsMap = new HashMap<>();

        String query = "SELECT * FROM statistics " +
                "LEFT JOIN user_has_statistics u on statistics.id_statistics = u.id_statistics " +
                "LEFT JOIN user u2 on u.id_user = u2.id_user " +
                "LEFT JOIN question q on statistics.id_statistics = q.id_statistics " +
                "LEFT JOIN question_has_hint h on q.id_question = h.id_question " +
                "LEFT JOIN hint h2 on h.id_hint = h2.id_hint " +
                "LEFT JOIN configuration c on statistics.id_configuration = c.id_configuration " +
                "LEFT JOIN configuration_has_hint h3 on c.id_configuration = h3.id_configuration " +
                "LEFT JOIN hint h4 on h3.id_hint = h4.id_hint " +
                "WHERE statistics.id_statistics = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            StatisticsMapper statisticsMapper = new StatisticsMapper();
            UserMapper userMapper = new UserMapper();
            QuestionMapper questionMapper = new QuestionMapper();
            ConfigurationMapper configurationMapper = new ConfigurationMapper();
            HintMapper hintMapper = new HintMapper();

            while (rs.next()) {
                Statistics statistics = statisticsMapper.extractFromResultSet(rs, "statistics");
                User user = userMapper.extractFromResultSet(rs, "u2");
                Question question = questionMapper.extractFromResultSet(rs, "q");
                Configuration configuration = configurationMapper.extractFromResultSet(rs, "c");
                Hint hintConfiguration = hintMapper.extractFromResultSet(rs, "h4");
                Optional<Hint> hintQuestion = Optional.ofNullable(hintMapper.extractFromResultSet(rs, "h2"));

                statistics = statisticsMapper.makeUnique(statisticsMap, statistics);
                if ((user.getRole() == Role.PLAYER) && !(statistics.getPlayers().contains(user)))
                    statistics.getPlayers().add(user);

                if (user.getRole() == Role.ADMINISTRATOR)
                    statistics.setAdministrator(user);

                if ((!statistics.getQuestions().contains(question)) && (question != null)) {
                    statistics.getQuestions().add(question);
                }

                if (hintQuestion.isPresent()
                        && !statistics.getQuestions().get(statistics.getQuestions().indexOf(question)).getHints().contains(hintQuestion.get())) {
                    statistics.getQuestions().get(statistics.getQuestions().indexOf(question)).getHints().add(hintQuestion.get());
                }

                if (statistics.getConfiguration() == null)
                    statistics.setConfiguration(configuration);

                if (!statistics.getConfiguration().getHints().contains(hintConfiguration)) {
                    statistics.getConfiguration().getHints().add(hintConfiguration);
                }
            }
            //if (!statisticsMap.entrySet().stream().findAny().isPresent()) return null;
            return statisticsMap.entrySet().stream().findAny().get().getValue();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCStatisticsDao class, findById error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves statistics which belong to the User
     *
     * @param entity User whose statistics to be retrieved
     * @return List of Statistics
     */
    @Override
    public List<Statistics> findUserStatistics(User entity) {
        Logger.getLogger(this.getClass()).info("JDBCStatisticsDao class, findUserStatistics method");
        Map<Integer, Statistics> statisticsMap = new HashMap<>();

        String query = "SELECT * FROM statistics " +
                "LEFT JOIN user_has_statistics u on statistics.id_statistics = u.id_statistics " +
                "LEFT JOIN user u2 on u.id_user = u2.id_user " +
                "LEFT JOIN question q on statistics.id_statistics = q.id_statistics " +
                "LEFT JOIN question_has_hint h on q.id_question = h.id_question " +
                "LEFT JOIN hint h2 on h.id_hint = h2.id_hint " +
                "LEFT JOIN configuration c on statistics.id_configuration = c.id_configuration " +
                "LEFT JOIN configuration_has_hint h3 on c.id_configuration = h3.id_configuration " +
                "LEFT JOIN hint h4 on h3.id_hint = h4.id_hint " +
                "WHERE u2.id_user = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, entity.getId());
            ResultSet rs = ps.executeQuery();
            StatisticsMapper statisticsMapper = new StatisticsMapper();
            UserMapper userMapper = new UserMapper();
            QuestionMapper questionMapper = new QuestionMapper();
            ConfigurationMapper configurationMapper = new ConfigurationMapper();
            HintMapper hintMapper = new HintMapper();

            while (rs.next()) {
                Statistics statistics = statisticsMapper.extractFromResultSet(rs, "statistics");
                User user = userMapper.extractFromResultSet(rs, "u2");
                Question question = questionMapper.extractFromResultSet(rs, "q");
                Configuration configuration = configurationMapper.extractFromResultSet(rs, "c");
                Hint hintConfiguration = hintMapper.extractFromResultSet(rs, "h4");
                Optional<Hint> hintQuestion = Optional.ofNullable(hintMapper.extractFromResultSet(rs, "h2"));

                statistics = statisticsMapper.makeUnique(statisticsMap, statistics);
                if ((user.getRole() == Role.PLAYER) && !(statistics.getPlayers().contains(user)))
                    statistics.getPlayers().add(user);

                if (user.getRole() == Role.ADMINISTRATOR)
                    statistics.setAdministrator(user);

                if (!statistics.getQuestions().contains(question)) {
                    statistics.getQuestions().add(question);
                }

                if (hintQuestion.isPresent()
                        && !statistics.getQuestions().get(statistics.getQuestions().indexOf(question)).getHints().contains(hintQuestion.get())) {
                    statistics.getQuestions().get(statistics.getQuestions().indexOf(question)).getHints().add(hintQuestion.get());
                }

                if (statistics.getConfiguration() == null)
                    statistics.setConfiguration(configuration);

                if (!statistics.getConfiguration().getHints().contains(hintConfiguration)) {
                    statistics.getConfiguration().getHints().add(hintConfiguration);
                }
            }

            return new ArrayList<>(statisticsMap.values());
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCStatisticsDao class, findUserStatistics error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves part of statistics records from DB
     *
     * @param currentPage    Number of page which will display statistics
     * @param recordsPerPage Number of records per one page
     * @return List of Statistics
     */
    @Override
    public List<Statistics> findStatisticsFromTo(int currentPage, int recordsPerPage) {
        Logger.getLogger(this.getClass()).info("JDBCStatisticsDao class, findStatisticsFromTo method");
        Map<Integer, Statistics> statisticsMap = new HashMap<>();

        int start = currentPage * recordsPerPage - recordsPerPage;

        String query = "SELECT * FROM (SELECT * FROM statistics LIMIT ?, ?) s " +
                "LEFT JOIN user_has_statistics u ON s.id_statistics = u.id_statistics " +
                "LEFT JOIN user u2 ON u.id_user = u2.id_user " +
                "LEFT JOIN question q ON s.id_statistics = q.id_statistics " +
                "LEFT JOIN question_has_hint h ON q.id_question = h.id_question " +
                "LEFT JOIN hint h2 ON h.id_hint = h2.id_hint " +
                "LEFT JOIN configuration c ON s.id_configuration = c.id_configuration " +
                "LEFT JOIN configuration_has_hint h3 ON c.id_configuration = h3.id_configuration " +
                "LEFT JOIN hint h4 ON h3.id_hint = h4.id_hint ";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, start);
            ps.setInt(2, recordsPerPage);
            ResultSet rs = ps.executeQuery();
            StatisticsMapper statisticsMapper = new StatisticsMapper();
            UserMapper userMapper = new UserMapper();
            QuestionMapper questionMapper = new QuestionMapper();
            ConfigurationMapper configurationMapper = new ConfigurationMapper();
            HintMapper hintMapper = new HintMapper();

            while (rs.next()) {
                Statistics statistics = statisticsMapper.extractFromResultSet(rs, "s");
                Optional<User> user = Optional.ofNullable(userMapper.extractFromResultSet(rs, "u2"));
                Optional<Question> question = Optional.ofNullable(questionMapper.extractFromResultSet(rs, "q"));
                Configuration configuration = configurationMapper.extractFromResultSet(rs, "c");
                Hint hintConfiguration = hintMapper.extractFromResultSet(rs, "h4");
                Optional<Hint> hintQuestion = Optional.ofNullable(hintMapper.extractFromResultSet(rs, "h2"));

                statistics = statisticsMapper.makeUnique(statisticsMap, statistics);
                if (user.isPresent() && (user.get().getRole() == Role.PLAYER) && !(statistics.getPlayers().contains(user.get())))
                    statistics.getPlayers().add(user.get());

                if (user.isPresent() && (user.get().getRole() == Role.ADMINISTRATOR))
                    statistics.setAdministrator(user.get());

                if (question.isPresent() && !statistics.getQuestions().contains(question.get())) {
                    statistics.getQuestions().add(question.get());
                }

                if (question.isPresent() && hintQuestion.isPresent()
                        && !statistics.getQuestions().get(statistics.getQuestions().indexOf(question.get())).getHints().contains(hintQuestion.get())) {
                    statistics.getQuestions().get(statistics.getQuestions().indexOf(question.get())).getHints().add(hintQuestion.get());
                }

                if (statistics.getConfiguration() == null)
                    statistics.setConfiguration(configuration);

                if (!statistics.getConfiguration().getHints().contains(hintConfiguration)) {
                    statistics.getConfiguration().getHints().add(hintConfiguration);
                }
            }

            return new ArrayList<>(statisticsMap.values());
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCStatisticsDao class, findStatisticsFromTo error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns number of statistics records in DB
     *
     * @return number of records
     */
    @Override
    public int getNumberOfRows() {
        Logger.getLogger(this.getClass()).info("JDBCStatisticsDao class, getNumberOfRows method");
        String query = "SELECT COUNT(id_statistics) FROM statistics";

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCStatisticsDao class, getNumberOfRows error");
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves all statistics from DB
     *
     * @return List of all statistics
     */
    @Override
    public List<Statistics> findAll() {
        Logger.getLogger(this.getClass()).info("JDBCStatisticsDao class, findAll method");
        Map<Integer, Statistics> statisticsMap = new HashMap<>();

        String query = "SELECT * FROM statistics " +
                "LEFT JOIN user_has_statistics u on statistics.id_statistics = u.id_statistics " +
                "LEFT JOIN user u2 on u.id_user = u2.id_user " +
                "LEFT JOIN question q on statistics.id_statistics = q.id_statistics " +
                "LEFT JOIN question_has_hint h on q.id_question = h.id_question " +
                "LEFT JOIN hint h2 on h.id_hint = h2.id_hint " +
                "LEFT JOIN configuration c on statistics.id_configuration = c.id_configuration " +
                "LEFT JOIN configuration_has_hint h3 on c.id_configuration = h3.id_configuration " +
                "LEFT JOIN hint h4 on h3.id_hint = h4.id_hint";

        try (Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery(query);
            StatisticsMapper statisticsMapper = new StatisticsMapper();
            UserMapper userMapper = new UserMapper();
            QuestionMapper questionMapper = new QuestionMapper();
            ConfigurationMapper configurationMapper = new ConfigurationMapper();
            HintMapper hintMapper = new HintMapper();

            while (rs.next()) {
                Statistics statistics = statisticsMapper.extractFromResultSet(rs, "statistics");
                Optional<User> user = Optional.ofNullable(userMapper.extractFromResultSet(rs, "u2"));
                Optional<Question> question = Optional.ofNullable(questionMapper.extractFromResultSet(rs, "q"));
                Configuration configuration = configurationMapper.extractFromResultSet(rs, "c");
                Hint hintConfiguration = hintMapper.extractFromResultSet(rs, "h4");
                Optional<Hint> hintQuestion = Optional.ofNullable(hintMapper.extractFromResultSet(rs, "h2"));

                statistics = statisticsMapper.makeUnique(statisticsMap, statistics);
                if (user.isPresent() && (user.get().getRole() == Role.PLAYER) && !(statistics.getPlayers().contains(user.get())))
                    statistics.getPlayers().add(user.get());

                if (user.isPresent() && (user.get().getRole() == Role.ADMINISTRATOR))
                    statistics.setAdministrator(user.get());

                if (question.isPresent() && !statistics.getQuestions().contains(question.get())) {
                    statistics.getQuestions().add(question.get());
                }

                if (question.isPresent() && hintQuestion.isPresent()
                        && !statistics.getQuestions().get(statistics.getQuestions().indexOf(question.get())).getHints().contains(hintQuestion.get())) {
                    statistics.getQuestions().get(statistics.getQuestions().indexOf(question.get())).getHints().add(hintQuestion.get());
                }

                if (statistics.getConfiguration() == null)
                    statistics.setConfiguration(configuration);

                if (!statistics.getConfiguration().getHints().contains(hintConfiguration)) {
                    statistics.getConfiguration().getHints().add(hintConfiguration);
                }
            }

            return new ArrayList<>(statisticsMap.values());
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCStatisticsDao class, findAll error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates statistics in DB
     *
     * @param entity Statistics to be updated in DB
     */
    @Override
    public void update(Statistics entity) {
        Logger.getLogger(this.getClass()).info("JDBCStatisticsDao class, update method");
        String queryStatistics = "UPDATE statistics SET " +
                "players_score = ?, " +
                "spectators_score = ?, " +
                "number_of_hints = ?, " +
                "id_configuration = ? " +
                "WHERE statistics.id_statistics = ?";

        String queryUserHasStatistics = "INSERT IGNORE INTO user_has_statistics(" +
                "id_user, " +
                "id_statistics) " +
                "VALUES(?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(queryStatistics);
             PreparedStatement ps1 = connection.prepareStatement(queryUserHasStatistics)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ps.setInt(1, entity.getPlayersScore());
            ps.setInt(2, entity.getSpectatorsScore());
            ps.setInt(3, entity.getNumberOfHints());
            ps.setInt(4, entity.getConfiguration().getId());
            ps.setInt(5, entity.getId());
            ps.executeUpdate();

            entity.getPlayers().forEach(x -> {
                try {
                    ps1.setInt(2, entity.getId());
                    ps1.setInt(1, x.getId());
                    ps1.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCStatisticsDao class, update error");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    /**
     * Deletes statistics from DB by id
     *
     * @param id Id of the Statistics to be deleted
     */
    @Override
    public void delete(int id) {
        Logger.getLogger(this.getClass()).info("JDBCStatisticsDao class, delete method");
        String queryUserHasStatistics = "DELETE FROM user_has_statistics WHERE id_statistics = ? ";
        String updateQuestion = "UPDATE question SET id_statistics = NULL " +
                "WHERE id_statistics = ?";
        String queryStatistics = "DELETE FROM statistics WHERE id_statistics = ? ";

        try (PreparedStatement ps = connection.prepareStatement(queryUserHasStatistics);
             PreparedStatement ps1 = connection.prepareStatement(updateQuestion);
             PreparedStatement ps2 = connection.prepareStatement(queryStatistics)) {
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
            Logger.getLogger(this.getClass()).error("JDBCStatisticsDao class, delete error");
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
            Logger.getLogger(this.getClass()).info("JDBCStatisticsDao class, close method");
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCStatisticsDao class, close error");
            throw new RuntimeException(e);
        }
    }
}
