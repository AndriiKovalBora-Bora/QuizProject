/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.mapper;

import model.entities.Locales;
import model.entities.user.LocalizedUser;
import model.entities.user.User;
import model.entities.user.Role;
import model.entities.user.Status;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Provides methods for extracting User data from ResultSet
 */
public class UserMapper implements ObjectMapper<User> {

    /**
     * Extracts data from ResultSet and creates User
     *
     * @param rs        ResultSet to be processed
     * @param tableName Name of the table
     * @return User entity
     * @throws SQLException if error was occurred
     */
    @Override
    public User extractFromResultSet(ResultSet rs, String tableName) throws SQLException {
        Logger.getLogger(this.getClass()).info("UserMapper class, extractFromResultSet method");
        if (rs.getString(tableName + ".id_user") == null) {
            Logger.getLogger(this.getClass()).info("id_user is null");
            return null;
        }

        User user = new User();
        user.setId(rs.getInt(tableName + ".id_user"));
        user.setEmail(rs.getString(tableName + ".email"));
        user.setPassword(rs.getString(tableName + ".password"));
        user.setRole(Role.valueOf(rs.getString(tableName + ".role").toUpperCase()));
        user.setStatus(Status.valueOf(rs.getString(tableName + ".status").toUpperCase()));

        LocalizedUser localizedUserEN = new LocalizedUser();
        localizedUserEN.setId(user.getId());
        localizedUserEN.setEmail(user.getEmail());
        localizedUserEN.setPassword(user.getPassword());
        localizedUserEN.setRole(user.getRole());
        localizedUserEN.setStatus(user.getStatus());
        localizedUserEN.setName(rs.getString(tableName + ".name_en"));
        localizedUserEN.setSurname(rs.getString(tableName + ".surname_en"));

        LocalizedUser localizedUserUA = new LocalizedUser();
        localizedUserUA.setId(user.getId());
        localizedUserUA.setEmail(user.getEmail());
        localizedUserUA.setPassword(user.getPassword());
        localizedUserUA.setRole(user.getRole());
        localizedUserUA.setStatus(user.getStatus());
        localizedUserUA.setName(rs.getString(tableName + ".name_ua"));
        localizedUserUA.setSurname(rs.getString(tableName + ".surname_ua"));

        user.getLocalizedUsers().put(Locales.ENGLISH.getLocale(), localizedUserEN);
        user.getLocalizedUsers().put(Locales.UKRAINIAN.getLocale(), localizedUserUA);

        return user;
    }

    /**
     * Checks if User is unique or not
     *
     * @param cache  Map of user, which were extracted earlier
     * @param entity Current user
     * @return Unique user
     */
    @Override
    public User makeUnique(Map<Integer, User> cache, User entity) {
        Logger.getLogger(this.getClass()).info("UserMapper class, makeUnique method");
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
