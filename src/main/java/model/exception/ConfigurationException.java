/*
 * Copyright (C) 2019 Quiz Project
 */

package model.exception;

/**
 * Provides exception for wrong configuration data
 */
public class ConfigurationException extends NumberFormatException {

    /**
     * Creates exception
     *
     * @param message Message of the exception
     */
    public ConfigurationException(String message) {
        super(message);
    }
}
