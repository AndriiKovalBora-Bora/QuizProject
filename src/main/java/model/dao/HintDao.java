/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao;

import model.entities.hint.Hint;
import model.entities.question.TypeOfQuestion;

import java.util.List;

/**
 * Provides methods for HintDao
 */
public interface HintDao extends GenericDao<Hint> {

    /**
     * Retrieves hint which are related to the type of question
     *
     * @param typeOfQuestion Type of question
     * @return List of hints
     */
    List<Hint> findAllHintsByTypeOfQuestion(TypeOfQuestion typeOfQuestion);
}
