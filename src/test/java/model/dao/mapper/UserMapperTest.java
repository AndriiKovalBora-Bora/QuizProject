/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.mapper;

import model.dao.implementation.ConnectionPoolHolder;
import model.entities.Locales;
import model.entities.user.Role;
import model.entities.user.Status;
import model.entities.user.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Test UserMapper
 */
public class UserMapperTest {
    private static UserMapper userMapper;

    @BeforeClass
    public static void setUp(){
        userMapper = new UserMapper();
    }

    @Test
    public void extractFromResultSet() throws SQLException {
        Connection connection = ConnectionPoolHolder.getDataSource().getConnection();
        final String query = "SELECT * FROM user " +
                "WHERE user.id_user = 1";
        try(Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) fail();
            User user = userMapper.extractFromResultSet(rs, "user");
            assertEquals("1", user.getId().toString());
            assertEquals("Andrew", user.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getName());
            assertEquals("Kovalenko", user.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getSurname());
            assertEquals("Андрій", user.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getName());
            assertEquals("Коваленко", user.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getSurname());
            assertEquals("andrew", user.getPassword());
            assertEquals(Role.PLAYER, user.getRole());
            assertEquals(Status.BUSY, user.getStatus());
            connection.close();
        }
    }

    @Test
    public void makeUnique() {
        Map<Integer, User> users = new HashMap<>();
        User user1 = new User();
        user1.setId(1);

        User user2 = new User();
        user2.setId(2);

        users.put(user1.getId(), user1);

        assertEquals(user1, userMapper.makeUnique(users, user1));
        assertEquals(user2, userMapper.makeUnique(users, user2));
    }
}