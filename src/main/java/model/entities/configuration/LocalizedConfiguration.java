/*
 * Copyright (C) 2019 Quiz Project
 */

package model.entities.configuration;

import model.entities.hint.Hint;

import java.io.Serializable;
import java.util.*;

/**
 * Presents configuration in certain language
 */
public class LocalizedConfiguration implements Serializable {
    private Integer id;
    private int time;
    private int numberOfPlayers;
    private int maxScore;
    private int maxNumberOfHints;
    private StatisticsFormat statisticsFormat;
    private List<Hint> hints = new ArrayList<>();
    private String statisticsFormatFormulation;

    public LocalizedConfiguration() {
    }

    public LocalizedConfiguration(Integer id, int time, int numberOfPlayers, int maxScore, int maxNumberOfHints, StatisticsFormat statisticsFormat, List<Hint> hints, String statisticsFormatFormulation) {
        this.id = id;
        this.time = time;
        this.numberOfPlayers = numberOfPlayers;
        this.maxScore = maxScore;
        this.maxNumberOfHints = maxNumberOfHints;
        this.statisticsFormat = statisticsFormat;
        this.hints = hints;
        this.statisticsFormatFormulation = statisticsFormatFormulation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getMaxNumberOfHints() {
        return maxNumberOfHints;
    }

    public void setMaxNumberOfHints(int maxNumberOfHints) {
        this.maxNumberOfHints = maxNumberOfHints;
    }

    public StatisticsFormat getStatisticsFormat() {
        return statisticsFormat;
    }

    public void setStatisticsFormat(StatisticsFormat statisticsFormat) {
        this.statisticsFormat = statisticsFormat;
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void setHints(List<Hint> hints) {
        this.hints = hints;
    }

    public String getStatisticsFormatFormulation() {
        return statisticsFormatFormulation;
    }

    public void setStatisticsFormatFormulation(String statisticsFormatFormulation) {
        this.statisticsFormatFormulation = statisticsFormatFormulation;
    }

    /**
     * Compares two localized configurations
     *
     * @param o Object to be compared with current configuration
     * @return True if localized configurations are equal, False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalizedConfiguration)) return false;
        LocalizedConfiguration that = (LocalizedConfiguration) o;
        return getTime() == that.getTime() &&
                getNumberOfPlayers() == that.getNumberOfPlayers() &&
                getMaxScore() == that.getMaxScore() &&
                getMaxNumberOfHints() == that.getMaxNumberOfHints() &&
                Objects.equals(getId(), that.getId()) &&
                getStatisticsFormat() == that.getStatisticsFormat() &&
                Objects.equals(getStatisticsFormatFormulation(), that.getStatisticsFormatFormulation());
    }

    /**
     * Calculates hash code of the localized configuration
     *
     * @return int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTime(), getNumberOfPlayers(), getMaxScore(), getMaxNumberOfHints(), getStatisticsFormat(), getStatisticsFormatFormulation());
    }

    /**
     * Presents localized configuration information in String type
     *
     * @return String representation of localized configuration
     */
    @Override
    public String toString() {
        return "LocalizedConfiguration{" +
                "statisticsFormatFormulation='" + statisticsFormatFormulation + '\'' +
                '}';
    }
}
