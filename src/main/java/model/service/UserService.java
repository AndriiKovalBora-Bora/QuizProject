/*
 * Copyright (C) 2019 Quiz Project
 */

package model.service;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.Locales;
import model.entities.user.User;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Provides service for User entity
 */
public class UserService {

    /**
     * Factory for creating entities' dao
     */
    private DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * Adds user to DB
     *
     * @param user will be added to DB
     * @return id of the user added to DB
     */
    public int addUserToDB(User user) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            Logger.getLogger(this.getClass()).info("UserService class, addUserToDB method");
            return userDao.create(user);
        }
    }

    /**
     * Retrieves user from DB by certain Id
     *
     * @param id of the User
     * @return User from DB
     */
    public User getUserById(int id) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            Logger.getLogger(this.getClass()).info("UserService class, getUserById method");
            return userDao.findById(id);
        }
    }

    /**
     * Retrieves user from DB by certain Id
     *
     * @param id of the User
     * @return User from DB
     */
    public User getUserById(String id) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            Logger.getLogger(this.getClass()).info("UserService class, getUserById method");
            return userDao.findById(Integer.valueOf(id));
        }
    }

    /**
     * Retrieves user from DB by certain email
     *
     * @param email of the User
     * @return Optional<User> from DB
     */
    public Optional<User> getUserByEmail(String email) {
        Optional<User> result;

        try (UserDao userDao = daoFactory.createUserDao()) {
            Logger.getLogger(this.getClass()).info("UserService class, getUserByEmail method");
            result = userDao.findByEmail(email);
        }
        return result;
    }

    /**
     * Retrieves all users from DB
     *
     * @return List of all users
     */
    public List<User> getAllFreeUsers() {
        try (UserDao userDao = daoFactory.createUserDao()) {
            Logger.getLogger(this.getClass()).info("UserService class, getAllFreeUsers method");
            return userDao.findAllFreePlayers();
        }
    }

    /**
     * Updates user in DB
     *
     * @param user to be updated in DB
     */
    public void updateUser(User user) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            Logger.getLogger(this.getClass()).info("UserService class, updateUser method");
            userDao.update(user);
        }
    }

    /**
     * Validates user data
     *
     * @param user is checked for correctness
     * @return true if user has valid data, false if user data is not valid
     */
    public boolean validateData(User user) {
        Logger.getLogger(this.getClass()).info("UserService class, validateData method");
        Pattern firstNamePatternEN = Pattern.compile("[a-zA-Z]{1,50}");
        Pattern firstNamePatternUA = Pattern.compile("[А-Яа-яЁёЇїІіЄєҐґ']{1,50}");
        Pattern surnamePatternEN = Pattern.compile("[a-zA-Z]{1,50}");
        Pattern surnamePatternUA = Pattern.compile("[А-Яа-яЁёЇїІіЄєҐґ']{1,50}");
        Pattern emailPattern = Pattern.compile("^(.+)@(.+)${1,50}");
        Pattern passwordPattern = Pattern.compile(".{3,16}");

        return (firstNamePatternEN.matcher(user.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getName()).matches()
                && firstNamePatternUA.matcher(user.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getName()).matches()
                && surnamePatternEN.matcher(user.getLocalizedUsers().get(Locales.ENGLISH.getLocale()).getSurname()).matches()
                && surnamePatternUA.matcher(user.getLocalizedUsers().get(Locales.UKRAINIAN.getLocale()).getSurname()).matches()
                && emailPattern.matcher(user.getEmail()).matches()
                && passwordPattern.matcher(user.getPassword()).matches());
    }
}
