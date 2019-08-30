/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.Locales;
import model.entities.user.LocalizedUser;
import model.entities.user.Role;
import model.entities.user.Status;
import model.entities.user.User;
import org.junit.*;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Tests JDBCUserDao
 */
public class JDBCUserDaoTest {
    private static UserDao userDao;
    private static User testUser;

    @BeforeClass
    public static void setUp() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        userDao = daoFactory.createUserDao();

        testUser = new User();
        LocalizedUser localizedUserEN = new LocalizedUser();
        localizedUserEN.setName("TestName");
        localizedUserEN.setSurname("TestSurname");

        LocalizedUser localizedUserUA = new LocalizedUser();
        localizedUserUA.setName("ТестІм'я");
        localizedUserUA.setSurname("ТестПрізвище");

        testUser.getLocalizedUsers().put(Locales.ENGLISH.getLocale(), localizedUserEN);
        testUser.getLocalizedUsers().put(Locales.UKRAINIAN.getLocale(), localizedUserUA);
        testUser.setEmail("testEmail@gamil.com");
        testUser.setPassword("testTest");
        testUser.setRole(Role.PLAYER);
        testUser.setStatus(Status.BUSY);
    }

    @AfterClass
    public static void tearDown() {
        userDao.close();
    }

    @Test
    public void create() {
        int id = userDao.create(testUser);
        testUser.setId(id);
        assertEquals(testUser, userDao.findById(id));
        userDao.delete(id);
    }

    @Test
    public void findById() {
        User user = userDao.findById(1);
        assertEquals("Andrew", user.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getName());
        assertEquals("Kovalenko", user.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getSurname());
        assertEquals("Андрій", user.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getName());
        assertEquals("Коваленко", user.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getSurname());
        assertEquals("andrew@gmail.com", user.getEmail());
        assertEquals("andrew", user.getPassword());
        assertEquals(Role.PLAYER, user.getRole());
        assertEquals(Status.BUSY, user.getStatus());
    }

    @Test
    public void findByEmail() {
        Optional<User> user = userDao.findByEmail("andrew@gmail.com");
        if (user.isPresent()) {
            assertEquals("1", user.get().getId().toString());
            assertEquals("Andrew", user.get().getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getName());
            assertEquals("Kovalenko", user.get().getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getSurname());
            assertEquals("Андрій", user.get().getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getName());
            assertEquals("Коваленко", user.get().getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getSurname());
            assertEquals("andrew", user.get().getPassword());
            assertEquals(Role.PLAYER, user.get().getRole());
            assertEquals(Status.BUSY, user.get().getStatus());
        } else fail();


        user = userDao.findByEmail("noSuchEmail");
        if (user.isPresent()) fail();
    }

    @Test
    public void findAll() {
        int id = userDao.create(testUser);
        List<User> users = userDao.findAll();
        assertTrue(users.size() > 0);
        userDao.delete(id);
    }

    @Test
    public void findAllFreePlayers() {
        testUser.setStatus(Status.FREE);
        int id = userDao.create(testUser);
        List<User> users = userDao.findAll();
        assertTrue(users.size() > 0);
        userDao.delete(id);
    }

    @Test
    public void update() {
        int id = userDao.create(testUser);

        testUser.setId(id);
        testUser.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).setSurname("newSurname");
        testUser.setEmail("newEmail@gamil.com");
        testUser.setStatus(Status.BUSY);

        userDao.update(testUser);
        User updatedUser = userDao.findById(id);
        assertEquals(testUser.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getName(), updatedUser.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getName());
        assertEquals(testUser.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getSurname(), updatedUser.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getSurname());
        assertEquals(testUser.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getName(), updatedUser.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getName());
        assertEquals(testUser.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getSurname(), updatedUser.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getSurname());
        assertEquals(testUser.getPassword(), updatedUser.getPassword());
        assertEquals(testUser.getRole(), updatedUser.getRole());
        assertEquals(testUser.getStatus(), updatedUser.getStatus());
        userDao.delete(id);
    }

    @Test
    public void delete() {
        int id = userDao.create(testUser);
        userDao.delete(id);
        assertNull(userDao.findById(id));
    }
}