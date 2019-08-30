/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao;

import model.entities.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Provides methods for UserDao
 */
public interface UserDao extends GenericDao<User> {

    /**
     * Retrieves user from DB by email
     *
     * @param email Email of the user
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * Retrieves all users from DB
     *
     * @return List of all users
     */
    List<User> findAllFreePlayers();
}
