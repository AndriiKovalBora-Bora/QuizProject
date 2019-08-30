/*
 * Copyright (C) 2019 Quiz Project
 */

package model.service;

import model.constants.Constants;
import model.dao.DaoFactory;
import model.dao.HintDao;
import model.entities.hint.Hint;
import model.entities.question.TypeOfQuestion;
import model.entities.statistics.Statistics;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Provides service for Hint entity
 */
public class HintService {

    /**
     * Factory for creating entities' dao
     */
    private DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * Retrieves all hints from DB by the type of the question
     *
     * @param typeOfQuestion Type of the question for the hins
     * @return List of hints
     */
    public List<Hint> getAllHintsByTypeOfQuestion(TypeOfQuestion typeOfQuestion) {
        try (HintDao hintDao = daoFactory.createHintDao()) {
            Logger.getLogger(this.getClass()).info("HintService class, getAllHintsByTypeOfQuestion method");
            return hintDao.findAllHintsByTypeOfQuestion(typeOfQuestion);
        }
    }

    /**
     * Retrieves hint from DB by id
     *
     * @param id Id of the hint to be retrieved
     * @return Hint by Id
     */
    public Hint getHintById(int id) {
        try (HintDao hintDao = daoFactory.createHintDao()) {
            Logger.getLogger(this.getClass()).info("HintService class, getHintById method");
            return hintDao.findById(id);
        }
    }

    /**
     * Retrieves hint from DB by id
     *
     * @param id Id of the hint to be retrieved
     * @return Hint by Id
     */
    public Hint getHintById(String id) {
        try (HintDao hintDao = daoFactory.createHintDao()) {
            Logger.getLogger(this.getClass()).info("HintService class, getHintById method");
            return hintDao.findById(Integer.valueOf(id));
        }
    }

    /**
     * Calculates probability of correct answer for question with answer choices
     *
     * @return Array of probabilities for each answer
     */
    public String[] calculateProbabilities() {
        Logger.getLogger(this.getClass()).info("HintService class, calculateProbabilities method");
        return new String[]{Constants.CORRECT_ANSWER_PROBABILITY + " %", Constants.INCORRECT_ANSWER_PROBABILITY + " %"};
    }

    /**
     * Adds plus one hint to hint count
     *
     * @param activeGame Statistics of active game, which is played
     * @return Count of hints used
     */
    public int addHintToCount(Statistics activeGame) {
        Logger.getLogger(this.getClass()).info("HintService class, addHintToCount method");
        return activeGame.getNumberOfHints() + 1;
    }
}
