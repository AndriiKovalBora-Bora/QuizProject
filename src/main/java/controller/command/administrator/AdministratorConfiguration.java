/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator;

import controller.command.Command;
import model.entities.Locales;
import model.entities.configuration.Configuration;
import model.entities.configuration.LocalizedConfiguration;
import model.entities.hint.Hint;
import model.entities.configuration.StatisticsFormat;
import model.entities.hint.LocalizedHint;
import model.entities.question.TypeOfQuestion;
import model.exception.ConfigurationException;
import model.service.ConfigurationService;
import model.service.HintService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Processes administratorConfiguration command
 */
public class AdministratorConfiguration implements Command {

    /**
     * Configuration service
     */
    private ConfigurationService configurationService;

    /**
     * Hint service
     */
    private HintService hintService;

    /**
     * Creates AdministratorConfiguration class
     *
     * @param configurationService Configuration service
     * @param hintService          Hint service
     */
    public AdministratorConfiguration(ConfigurationService configurationService, HintService hintService) {
        Logger.getLogger(this.getClass()).info("AdministratorConfiguration class constructor");
        this.configurationService = configurationService;
        this.hintService = hintService;
    }

    /**
     * Executes AdministratorConfiguration command
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("Administrator class, execute method");
        Locale locale = (Locale) request.getSession().getAttribute("locale");

        List<LocalizedConfiguration> configurations = configurationService
                .getAllConfiguration()
                .stream()
                .map(x -> x.getLocalizedConfigurations().get(locale))
                .collect(Collectors.toList());
        request.setAttribute("configurations", configurations);

        List<StatisticsFormat> statisticsFormats = Arrays.asList(StatisticsFormat.values());
        statisticsFormats.forEach(x -> x.setLocale(locale));
        request.setAttribute("statisticsFormats", statisticsFormats);


        List<LocalizedHint> hintsWithoutChoices = hintService
                .getAllHintsByTypeOfQuestion(TypeOfQuestion.WITHOUT_CHOICES)
                .stream()
                .map(x -> x.getLocalizedHints().get(locale))
                .collect(Collectors.toList());
        request.setAttribute("hintsWithoutChoices", hintsWithoutChoices);

        List<LocalizedHint> hintsWithChoices = hintService
                .getAllHintsByTypeOfQuestion(TypeOfQuestion.WITH_CHOICES)
                .stream()
                .map(x -> x.getLocalizedHints().get(locale))
                .collect(Collectors.toList());
        request.setAttribute("hintsWithChoices", hintsWithChoices);

        if (request.getParameter("newConfiguration") == null) {
            Logger.getLogger(this.getClass()).info("No new configuration");
            return "/WEB-INF/administrator/administratorConfiguration.jsp";
        }

        String time = request.getParameter("time");
        String numberOfPlayers = request.getParameter("numberOfPlayers");
        String maxScore = request.getParameter("maxScore");
        String maxNumberOfHints = request.getParameter("numberOfHints");

        try {
            configurationService.validateData(time, numberOfPlayers, maxScore, maxNumberOfHints);
            if ((request.getParameterValues("hintsWithoutChoices") == null)
                    && (request.getParameterValues("hintsWithChoices") == null))
                throw new ConfigurationException("Wrong Parameters");
        } catch (NumberFormatException e) {
            Logger.getLogger(this.getClass()).info("New configuration validation was not successful");
            request.setAttribute("wrongParameters", true);
            return "/WEB-INF/administrator/administratorConfiguration.jsp";
        }

        StatisticsFormat statisticsFormat = StatisticsFormat.valueOf(request.getParameter("statisticsFormat"));

        List<Hint> hints = new ArrayList<>();
        Arrays.stream(request.getParameterValues("hintsWithoutChoices"))
                .forEach(x -> hints.add(hintService.getHintById(x)));
        Arrays.stream(request.getParameterValues("hintsWithChoices"))
                .forEach(x -> hints.add(hintService.getHintById(x)));

        Configuration configuration = new Configuration();
        configuration.setTime(Integer.valueOf(time));
        configuration.setNumberOfPlayers(Integer.valueOf(numberOfPlayers));
        configuration.setMaxScore(Integer.valueOf(maxScore));
        configuration.setMaxNumberOfHints(Integer.valueOf(maxNumberOfHints));
        configuration.setStatisticsFormat(statisticsFormat);
        configuration.setHints(hints);

        LocalizedConfiguration localizedConfigurationEN = new LocalizedConfiguration();
        localizedConfigurationEN.setTime(Integer.valueOf(time));
        localizedConfigurationEN.setNumberOfPlayers(Integer.valueOf(numberOfPlayers));
        localizedConfigurationEN.setMaxScore(Integer.valueOf(maxScore));
        localizedConfigurationEN.setMaxNumberOfHints(Integer.valueOf(maxNumberOfHints));
        localizedConfigurationEN.setStatisticsFormat(statisticsFormat);
        localizedConfigurationEN.setStatisticsFormatFormulation(statisticsFormat.getNames().get(Locales.ENGLISH.getLocale()));

        LocalizedConfiguration localizedConfigurationUA = new LocalizedConfiguration();
        localizedConfigurationUA.setId(configuration.getId());
        localizedConfigurationUA.setTime(configuration.getTime());
        localizedConfigurationUA.setNumberOfPlayers(configuration.getNumberOfPlayers());
        localizedConfigurationUA.setMaxScore(configuration.getMaxScore());
        localizedConfigurationUA.setMaxNumberOfHints(configuration.getMaxNumberOfHints());
        localizedConfigurationUA.setStatisticsFormat(configuration.getStatisticsFormat());
        localizedConfigurationUA.setStatisticsFormatFormulation(statisticsFormat.getNames().get(Locales.UKRAINIAN.getLocale()));

        configuration.getLocalizedConfigurations().put(Locales.ENGLISH.getLocale(), localizedConfigurationEN);
        configuration.getLocalizedConfigurations().put(Locales.UKRAINIAN.getLocale(), localizedConfigurationUA);

        configurationService.addConfigurationToDB(configuration);
        Logger.getLogger(this.getClass()).info("Configuration : " + configuration + " was added to DB");
        List<LocalizedConfiguration> newConfigurations = configurationService
                .getAllConfiguration()
                .stream()
                .map(x -> x.getLocalizedConfigurations().get(locale))
                .collect(Collectors.toList());
        request.setAttribute("configurations", newConfigurations);
        return "/WEB-INF/administrator/administratorConfiguration.jsp";
    }
}
