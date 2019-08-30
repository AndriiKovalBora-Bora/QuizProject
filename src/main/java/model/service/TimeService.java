/*
 * Copyright (C) 2019 Quiz Project
 */

package model.service;

import model.constants.Constants;
import model.entities.configuration.Configuration;
import model.entities.question.Question;
import model.entities.statistics.Statistics;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Provides time service
 */
public class TimeService {

    /**
     * Calculates time spent on question reply
     *
     * @param question Question, whose time of answer is calculated
     * @return int value of time in seconds
     */
    public int calculateTimeForAnswer(Question question) {
        Logger.getLogger(this.getClass()).info("TimeService class, calculateTimeForAnswer method");
        return (int) ((question.getEndTime() - question.getStartTime()) / 1000);
    }

    /**
     * Calculates time left for answer
     *
     * @param question      Question to which the answer is given
     * @param configuration Configuration of current game
     * @return time left for answer in seconds
     */
    public int calculateTimeLeft(Question question, Configuration configuration) {
        Logger.getLogger(this.getClass()).info("TimeService class, calculateTimeLeft method");
        return (int) (question.getStartTime() + configuration.getTime() * 1000 - new Date().getTime());
    }

    /**
     * Adds time for answer
     *
     * @param activeGame Active game which is played
     */
    public void addTime(Statistics activeGame) {
        Logger.getLogger(this.getClass()).info("TimeService class, addTime method");
        activeGame.getConfiguration().setTime(activeGame.getConfiguration().getTime() + Constants.TIME_HINT);
    }

    /**
     * Calculated time to refresh the page
     *
     * @param timeLeft Time left to page refresh in millisecond
     * @return time left to page refresh in seconds
     */
    public int calculateTimeToFresh(int timeLeft) {
        Logger.getLogger(this.getClass()).info("TimeService class, calculateTimeToFresh method");
        return timeLeft / 1000 + 1;
    }
}
