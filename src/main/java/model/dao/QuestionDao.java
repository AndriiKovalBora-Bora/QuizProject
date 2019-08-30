/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao;

import model.entities.question.Question;

import java.util.List;

/**
 * Provides methods for QuestionDao
 */
public interface QuestionDao extends GenericDao<Question> {

    /**
     * Retrieves all free questions from DB
     *
     * @return List of free question
     */
    List<Question> findAllFreeQuestion();
}
