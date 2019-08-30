/*
 * Copyright (C) 2019 Quiz Project
 */

package model.entities.configuration;

import model.entities.hint.Hint;

import java.io.Serializable;
import java.util.*;

/**
 * Presents Configuration
 */
public class Configuration implements Serializable {
    private Integer id;
    private int time;
    private int numberOfPlayers;
    private int maxScore;
    private int maxNumberOfHints;
    private StatisticsFormat statisticsFormat;
    private List<Hint> hints = new ArrayList<>();
    private Map<Locale, LocalizedConfiguration> localizedConfigurations = new HashMap<>();

    public Configuration() {
    }

    public Configuration(Integer id, int time, int numberOfPlayers, int maxScore, int maxNumberOfHints, StatisticsFormat statisticsFormat, List<Hint> hints, Map<Locale, LocalizedConfiguration> localizedConfigurations) {
        this.id = id;
        this.time = time;
        this.numberOfPlayers = numberOfPlayers;
        this.maxScore = maxScore;
        this.maxNumberOfHints = maxNumberOfHints;
        this.statisticsFormat = statisticsFormat;
        this.hints = hints;
        this.localizedConfigurations = localizedConfigurations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        localizedConfigurations.forEach((key, value) -> value.setId(id));
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
        localizedConfigurations.forEach((key, value) -> value.setTime(time));
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        localizedConfigurations.forEach((key, value) -> value.setNumberOfPlayers(numberOfPlayers));
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
        localizedConfigurations.forEach((key, value) -> value.setMaxScore(maxScore));
    }

    public int getMaxNumberOfHints() {
        return maxNumberOfHints;
    }

    public void setMaxNumberOfHints(int maxNumberOfHints) {
        this.maxNumberOfHints = maxNumberOfHints;
        localizedConfigurations.forEach((key, value) -> value.setMaxNumberOfHints(maxNumberOfHints));
    }

    public StatisticsFormat getStatisticsFormat() {
        return statisticsFormat;
    }

    public void setStatisticsFormat(StatisticsFormat statisticsFormat) {
        this.statisticsFormat = statisticsFormat;
        localizedConfigurations.forEach((key, value) -> value.setStatisticsFormat(statisticsFormat));
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void setHints(List<Hint> hints) {
        this.hints = hints;
        localizedConfigurations.forEach((key, value) -> value.setHints(hints));
    }

    public Map<Locale, LocalizedConfiguration> getLocalizedConfigurations() {
        return localizedConfigurations;
    }

    public void setLocalizedConfigurations(Map<Locale, LocalizedConfiguration> localizedConfigurations) {
        this.localizedConfigurations = localizedConfigurations;
    }

    /**
     * Compares two configurations
     *
     * @param o Object to be compared with current configuration
     * @return True if configurations are equal, False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Configuration)) return false;
        Configuration that = (Configuration) o;
        return getTime() == that.getTime() &&
                getNumberOfPlayers() == that.getNumberOfPlayers() &&
                getMaxScore() == that.getMaxScore() &&
                getMaxNumberOfHints() == that.getMaxNumberOfHints() &&
                Objects.equals(getId(), that.getId()) &&
                getStatisticsFormat() == that.getStatisticsFormat() &&
                Objects.equals(getHints(), that.getHints()) &&
                Objects.equals(getLocalizedConfigurations(), that.getLocalizedConfigurations());
    }

    /**
     * Calculates hash code of the configuration
     *
     * @return int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTime(), getNumberOfPlayers(), getMaxScore(), getMaxNumberOfHints(), getStatisticsFormat(), getHints(), getLocalizedConfigurations());
    }

    /**
     * Presents configuration information in String type
     *
     * @return String representation of configuration
     */
    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", time=" + time +
                ", numberOfPlayers=" + numberOfPlayers +
                ", maxScore=" + maxScore +
                ", maxNumberOfHints=" + maxNumberOfHints +
                ", statisticsFormat=" + statisticsFormat +
                ", hints={" + hints.stream().map(x -> String.valueOf(x.getId())).reduce("", (acc, x) -> acc + x + ", ") + "}" +
                ", localizedConfigurations={" + localizedConfigurations.entrySet().stream().map(x -> x.getKey().getCountry() + " : " + x.getValue()).reduce("", (acc, x) -> acc + x + ", ") + "}" +
                '}';
    }
}
