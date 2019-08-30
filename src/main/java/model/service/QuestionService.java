/*
 * Copyright (C) 2019 Quiz Project
 */

package model.service;

import model.dao.DaoFactory;
import model.dao.QuestionDao;
import model.entities.question.Question;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Provides service for Question entity
 */
public class QuestionService {

    /**
     * Factory for creating entities' dao
     */
    private DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * Retrieves all questions from DB
     *
     * @return List of all question from DB
     */
    public List<Question> getAllFreeQuestions() {
        try (QuestionDao questionDao = daoFactory.createQuestionDao()) {
            Logger.getLogger(this.getClass()).info("QuestionService class, getAllFreeQuestions method");
            return questionDao.findAllFreeQuestion();
        }
    }

    /**
     * Retrieves question from DB by Id
     *
     * @param id Id of question to be retrieved
     * @return Question from DB
     */
    public Question getQuestionById(int id) {
        try (QuestionDao questionDao = daoFactory.createQuestionDao()) {
            Logger.getLogger(this.getClass()).info("QuestionService class, getQuestionById method");
            return questionDao.findById(id);
        }
    }

    /**
     * Retrieves question from DB by Id
     *
     * @param id Id of question to be retrieved
     * @return Question from DB
     */
    public Question getQuestionById(String id) {
        try (QuestionDao questionDao = daoFactory.createQuestionDao()) {
            Logger.getLogger(this.getClass()).info("QuestionService class, getQuestionById method");
            return questionDao.findById(Integer.valueOf(id));
        }
    }

    /**
     * Updates question in DB
     *
     * @param question Question to be updated in DB
     */
    public void updateQuestion(Question question) {
        try (QuestionDao questionDao = daoFactory.createQuestionDao()) {
            Logger.getLogger(this.getClass()).info("QuestionService class, updateQuestion method");
            questionDao.update(question);
        }
    }
}
