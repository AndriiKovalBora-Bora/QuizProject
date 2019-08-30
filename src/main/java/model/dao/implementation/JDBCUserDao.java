/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import model.dao.UserDao;
import model.dao.mapper.StatisticsMapper;
import model.dao.mapper.UserMapper;
import model.entities.Locales;
import model.entities.statistics.Statistics;
import model.entities.user.User;
import model.entities.user.Role;
import model.entities.user.Status;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * Implements methods of UserDao interface
 */
public class JDBCUserDao implements UserDao {

    /**
     * Connection to the DB
     */
    private Connection connection;

    /**
     * Creates class
     *
     * @param connection Connection to the DB
     */
    JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Implements adding user to DB
     *
     * @param entity Entity to be stored in DB
     * @return Id of created record in DB
     */
    @Override
    public int create(User entity) {
        Logger.getLogger(this.getClass()).info("JDBCUserDao class, create method");
        String query = "INSERT INTO user(" +
                "name_en, " +
                "name_ua, " +
                "surname_en, " +
                "surname_ua, " +
                "email, " +
                "password, " +
                "role, " +
                "status) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        String queryGetId = "SELECT LAST_INSERT_ID()";

        try (Statement st = connection.createStatement();
             PreparedStatement ps = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ps.setString(1, entity.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getName());
            ps.setString(2, entity.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getName());
            ps.setString(3, entity.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getSurname());
            ps.setString(4, entity.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getSurname());
            ps.setString(5, entity.getEmail());
            ps.setString(6, entity.getPassword());
            ps.setString(7, entity.getRole().name().toLowerCase());
            ps.setString(8, entity.getStatus().name().toLowerCase());
            ps.executeUpdate();

            ResultSet rs = st.executeQuery(queryGetId);
            connection.commit();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCUserDao class, create error");
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
     * Implements retrieving user from DB by Id
     *
     * @param id Id of the user
     * @return User
     */
    @Override
    public User findById(int id) {
        Logger.getLogger(this.getClass()).info("JDBCUserDao class, findById method");
        final String query = "SELECT * FROM user " +
                "LEFT JOIN user_has_statistics ON user.id_user = user_has_statistics.id_user " +
                "LEFT JOIN statistics ON user_has_statistics.id_statistics = statistics.id_statistics " +
                "WHERE user.id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            UserMapper userMapper = new UserMapper();
            StatisticsMapper statisticsMapper = new StatisticsMapper();

            if (!rs.next()) return null;
            User user = userMapper.extractFromResultSet(rs, "user");

            do {
                Statistics statistics = statisticsMapper.extractFromResultSet(rs, "statistics");
                if (statistics != null) user.getStatistics().add(statistics);
            } while (rs.next());

            return user;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCUserDao class, findById error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Implements retrieving user from DB by email
     *
     * @param email Email of the user
     * @return Optional<User>
     */
    @Override
    public Optional<User> findByEmail(String email) {
        Logger.getLogger(this.getClass()).info("JDBCUserDao class, findByEmail method");
        String query = "SELECT * FROM user " +
                "LEFT JOIN user_has_statistics ON user.id_user = user_has_statistics.id_user " +
                "LEFT JOIN statistics ON user_has_statistics.id_statistics = statistics.id_statistics " +
                "WHERE user.email = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next())
                return Optional.empty();

            UserMapper userMapper = new UserMapper();
            StatisticsMapper statisticsMapper = new StatisticsMapper();
            Optional<User> user = Optional.of(userMapper.extractFromResultSet(rs, "user"));

            do {
                Statistics statistics = statisticsMapper.extractFromResultSet(rs, "statistics");
                if (statistics != null) user.get().getStatistics().add(statistics);
            } while (rs.next());

            return user;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCUserDao class, findByEmail error");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Retrieves all users from DB
     *
     * @return List of all users
     */
    @Override
    public List<User> findAll() {
        Logger.getLogger(this.getClass()).info("JDBCUserDao class, findAll method");
        Map<Integer, User> users = new HashMap<>();

        String query = "SELECT * FROM user " +
                "LEFT JOIN user_has_statistics ON user.id_user = user_has_statistics.id_user " +
                "LEFT JOIN statistics ON user_has_statistics.id_statistics = statistics.id_statistics ";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            UserMapper userMapper = new UserMapper();
            StatisticsMapper statisticsMapper = new StatisticsMapper();

            while (rs.next()) {
                User user = userMapper.extractFromResultSet(rs, "user");
                Statistics statistics = statisticsMapper.extractFromResultSet(rs, "statistics");

                user = userMapper.makeUnique(users, user);
                user.getStatistics().add(statistics);
            }
            return new ArrayList<>(users.values());
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCUserDao class, findAll error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all users with Role.PLAYER and Status.FREE
     *
     * @return List of users
     */
    @Override
    public List<User> findAllFreePlayers() {
        Logger.getLogger(this.getClass()).info("JDBCUserDao class, findAllFreePlayers method");
        Map<Integer, User> users = new HashMap<>();

        String query = "SELECT * FROM user " +
                "LEFT JOIN user_has_statistics ON user.id_user = user_has_statistics.id_user " +
                "LEFT JOIN statistics ON user_has_statistics.id_statistics = statistics.id_statistics " +
                "WHERE user.status = ? AND user.role = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, Status.FREE.name().toLowerCase());
            ps.setString(2, Role.PLAYER.name().toLowerCase());
            ResultSet rs = ps.executeQuery();

            UserMapper userMapper = new UserMapper();
            StatisticsMapper statisticsMapper = new StatisticsMapper();

            while (rs.next()) {
                User user = userMapper.extractFromResultSet(rs, "user");
                Statistics statistics = statisticsMapper.extractFromResultSet(rs, "statistics");

                user = userMapper.makeUnique(users, user);
                user.getStatistics().add(statistics);
            }
            return new ArrayList<>(users.values());
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCUserDao class, findAllFreePlayers error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates user in DB
     *
     * @param entity User to be updated in DB
     */
    @Override
    public void update(User entity) {
        Logger.getLogger(this.getClass()).info("JDBCUserDao class, update method");
        String queryUser = "UPDATE user SET " +
                "name_en = ?, " +
                "name_ua = ?, " +
                "surname_en = ?, " +
                "surname_ua = ?, " +
                "email = ?, " +
                "password = ?, " +
                "role = ?, " +
                "status = ? " +
                "WHERE user.id_user = ?";

        String queryUserHasStatistics = "INSERT IGNORE INTO user_has_statistics(" +
                "id_user, " +
                "id_statistics) " +
                "VALUES(?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(queryUser);
             PreparedStatement ps1 = connection.prepareStatement(queryUserHasStatistics)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ps.setString(1, entity.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getName());
            ps.setString(2, entity.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getName());
            ps.setString(3, entity.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getSurname());
            ps.setString(4, entity.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getSurname());
            ps.setString(5, entity.getEmail());
            ps.setString(6, entity.getPassword());
            ps.setString(7, entity.getRole().name().toLowerCase());
            ps.setString(8, entity.getStatus().name().toLowerCase());
            ps.setInt(9, entity.getId());
            ps.executeUpdate();

            entity.getStatistics().forEach(x -> {
                try {
                    ps1.setInt(1, entity.getId());
                    ps1.setInt(2, x.getId());
                    ps1.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCUserDao class, update error");
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Deletes user from DB by id
     *
     * @param id Id of the User to be deleted
     */
    @Override
    public void delete(int id) {
        Logger.getLogger(this.getClass()).info("JDBCUserDao class, delete method");
        String queryUserHasStatistics = "DELETE FROM user_has_statistics WHERE id_user = ? ";
        String queryUser = "DELETE FROM user WHERE id_user = ? ";

        try (PreparedStatement ps = connection.prepareStatement(queryUserHasStatistics);
             PreparedStatement ps1 = connection.prepareStatement(queryUser)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ps.setInt(1, id);
            ps.executeUpdate();

            ps1.setInt(1, id);
            ps1.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCUserDao class, delete error");
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
            Logger.getLogger(this.getClass()).info("JDBCUserDao class, close method");
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCUserDao class, close error");
            throw new RuntimeException(e);
        }
    }
}
