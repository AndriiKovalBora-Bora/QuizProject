/*
 * Copyright (C) 2019 Quiz Project
 */

package model.entities.statistics;

import model.entities.configuration.Configuration;
import model.entities.question.Question;
import model.entities.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Presents statistics of the game
 */
public class Statistics implements Serializable {
    private Integer id;
    private int playersScore;
    private int spectatorsScore;
    private int numberOfHints;
    private List<User> players = new ArrayList<>();
    private User administrator;
    private List<Question> questions = new ArrayList<>();
    private Configuration configuration;

    public Statistics() {
    }

    public Statistics(Integer id, int playersScore, int spectatorsScore, int numberOfHints, List<User> players, User administrator, List<Question> questions, Configuration configuration) {
        this.id = id;
        this.playersScore = playersScore;
        this.spectatorsScore = spectatorsScore;
        this.numberOfHints = numberOfHints;
        this.players = players;
        this.administrator = administrator;
        this.questions = questions;
        this.configuration = configuration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPlayersScore() {
        return playersScore;
    }

    public void setPlayersScore(int playersScore) {
        this.playersScore = playersScore;
    }

    public int getSpectatorsScore() {
        return spectatorsScore;
    }

    public void setSpectatorsScore(int spectatorsScore) {
        this.spectatorsScore = spectatorsScore;
    }

    public int getNumberOfHints() {
        return numberOfHints;
    }

    public void setNumberOfHints(int numberOfHints) {
        this.numberOfHints = numberOfHints;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Compares two statistics
     *
     * @param o Object to be compared with current statistics
     * @return True if statistics are equal, otherwise False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistics)) return false;
        Statistics that = (Statistics) o;
        return getPlayersScore() == that.getPlayersScore() &&
                getSpectatorsScore() == that.getSpectatorsScore() &&
                getNumberOfHints() == that.getNumberOfHints() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getPlayers(), that.getPlayers()) &&
                Objects.equals(getAdministrator(), that.getAdministrator()) &&
                Objects.equals(getQuestions(), that.getQuestions()) &&
                Objects.equals(getConfiguration(), that.getConfiguration());
    }

    /**
     * Calculates hash code of the statistics
     *
     * @return int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlayersScore(), getSpectatorsScore(), getNumberOfHints(), getPlayers(), getAdministrator(), getQuestions(), getConfiguration());
    }

    /**
     * Presents statistics information in String type
     *
     * @return String representation of statistics
     */
    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", playersScore=" + playersScore +
                ", spectatorsScore=" + spectatorsScore +
                ", numberOfHints=" + numberOfHints +
                ", players={" + players.stream().map(x -> String.valueOf(x.getId())).reduce("", (acc, x) -> acc + x + ", ") + "}" +
                ", administrator=" + administrator +
                ", questions={" + questions.stream().map(x -> String.valueOf(x.getId())).reduce("", (acc, x) -> acc + x + ", ") + "}" +
                ", configuration=" + configuration +
                '}';
    }
}
