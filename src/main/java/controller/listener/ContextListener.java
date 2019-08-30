/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.listener;

import model.constants.Constants;
import model.entities.statistics.Statistics;
import model.service.StatisticsService;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Realises context listener
 */
public class ContextListener implements ServletContextListener {

    /**
     * Initializes context
     *
     * @param sce Servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger.getLogger(this.getClass()).info("ContextListener initialization");
        sce.getServletContext().setAttribute("activeGames", new ArrayList<Statistics>());
        sce.getServletContext().setAttribute("chats", new HashMap<Integer, ArrayList<ArrayList<String>>>());
        sce.getServletContext().setAttribute("statisticsHistory", new StatisticsService().getStatisticsFromTo(1, Constants.RECORDS_PER_PAGE));
        sce.getServletContext().setAttribute("loggedUsers", new HashSet<String>());

        int rows = new StatisticsService().getNumberOfRows();

        int nOfPages = rows / Constants.RECORDS_PER_PAGE;
        if (nOfPages % Constants.RECORDS_PER_PAGE > 0) {
            nOfPages++;
        }

        sce.getServletContext().setAttribute("noOfPages", nOfPages);
        sce.getServletContext().setAttribute("currentPage", 1);
        sce.getServletContext().setAttribute("recordsPerPage", Constants.RECORDS_PER_PAGE);
    }

    /**
     * Destroys context
     *
     * @param sce Servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Logger.getLogger(this.getClass()).info("ContextListener destroy");
        sce.getServletContext().removeAttribute("activeGames");
        sce.getServletContext().removeAttribute("chats");
        sce.getServletContext().removeAttribute("statisticsHistory");
        sce.getServletContext().removeAttribute("noOfPages");
        sce.getServletContext().removeAttribute("currentPage");
        sce.getServletContext().removeAttribute("recordsPerPage");
    }
}
