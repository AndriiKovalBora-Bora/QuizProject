/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides method for executing command
 */
public interface Command {

    /**
     * Executes specific command according to the request
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}
