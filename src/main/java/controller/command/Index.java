/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command;

import model.constants.Constants;
import model.entities.statistics.Statistics;
import model.service.StatisticsService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Processes index command
 */
public class Index implements Command {

    /**
     * Statistics Service
     */
    private StatisticsService statisticsService;

    /**
     * Creates Index class
     *
     * @param statisticsService Statistics Service
     */
    public Index(StatisticsService statisticsService) {
        Logger.getLogger(this.getClass()).info("Index class constructor");
        this.statisticsService = statisticsService;
    }

    /**
     * Executes index command
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("Index class, execute method");
        int currentPage = 1;
        if (request.getParameter("currentPage") != null)
            currentPage = Integer.valueOf(request.getParameter("currentPage"));

        List<Statistics> statisticsHistory = statisticsService.getStatisticsFromTo(currentPage, Constants.RECORDS_PER_PAGE);

        request.setAttribute("statisticsHistory", statisticsHistory);

        int rows = statisticsService.getNumberOfRows();
        int nOfPages = rows / Constants.RECORDS_PER_PAGE;
        if (nOfPages % Constants.RECORDS_PER_PAGE > 0) {
            nOfPages++;
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", Constants.RECORDS_PER_PAGE);

        return "/index.jsp";
    }
}
