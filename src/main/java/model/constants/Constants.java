/*
 * Copyright (C) 2019 Quiz Project
 */

package model.constants;

/**
 * Provides constants for project
 */
public interface Constants {
    int RECORDS_PER_PAGE = 10;

    String noAnswer = " - ";

    int ADMINISTRATOR_CONFIGURATION_PHASE = 1;
    int ADMINISTRATOR_PLAYERS_PHASE = 2;
    int ADMINISTRATOR_GAME_PHASE = 3;
    int ADMINISTRATOR_STATISTICS_PHASE = 4;

    int PLAYER_GAME_PHASE = 1;
    int PLAYER_STATISTICS_PHASE = 2;
    String GAME_PHASE_ERROR = "phase error";
    int TIME_HINT = 60;
    int ADMINISTRATOR_REFRESH_TIME = 5;
    int PLAYER_REFRESH_TIME = 5;

    int CORRECT_ANSWER_PROBABILITY = 40;
    int INCORRECT_ANSWER_PROBABILITY = 20;

    int MIN_TIME_FOR_ANSWER = 10;
    int MAX_TIME_FOR_ANSWER = 1000;
    int MIN_NUMBER_OF_PLAYERS = 1;
    int MAX_NUMBER_OF_PLAYERS = 10;
    int MIN_MAX_SCORE = 1;
    int MAX_MAX_SCORE = 100;
    int MIN_MAX_NUMBER_OF_HINTS = 1;
    int MAX_MAX_NUMBER_OF_HINTS = 1000;
}
