/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command;

import model.service.StatisticsService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests Index
 */
public class IndexTest {

    @Test
    public void execute() {
        Index index = new Index(new StatisticsService());
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("currentPage")).thenReturn("1");
        assertEquals("/index.jsp", index.execute(request, response));
    }
}