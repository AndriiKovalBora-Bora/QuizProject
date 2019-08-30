/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.filter;

import controller.config.SecurityConfig;
import controller.request.UserRoleRequestWrapper;
import controller.utils.SecurityUtils;
import controller.utils.UrlPatternUtils;
import model.entities.user.Role;
import model.entities.user.User;
import model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Provides security for JSPs
 */
public class SecurityFilter implements Filter {

    /**
     * Initializes filter
     * @param filterConfig Filter configuration
     */
    @Override
    public void init(FilterConfig filterConfig){
        Logger.getLogger(this.getClass()).info("SecurityFilter initialization");
    }

    /**
     * Checks if the current user has access to the page or not
     * @param request Http request
     * @param response Http response
     * @param chain Filter chain
     * @throws IOException If error was occurred
     * @throws ServletException If error was occurred
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Logger.getLogger(this.getClass()).info("SecurityFilter doFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Optional<User> authorizedUser = new UserService().getUserByEmail((String)req.getSession().getAttribute("email"));
        String requestedUri = UrlPatternUtils.getUrlPattern(req);

        HttpServletRequest wrapRequest = req;

        if(!authorizedUser.isPresent()){
            List<String> visitorUrl = SecurityConfig.getUrlPatternsForRole(Role.VISITOR);
            if (visitorUrl.contains(requestedUri)) {
                chain.doFilter(wrapRequest, res);
                return;
            }
        }

        if (authorizedUser.isPresent()) {
            String userEmail = authorizedUser.get().getEmail();
            Role role = authorizedUser.get().getRole();
            wrapRequest = new UserRoleRequestWrapper(userEmail, role, req);
        }

        if (SecurityUtils.isSecurityPage(req)) {
            if (!authorizedUser.isPresent()) {
                String redirectUri = UrlPatternUtils.getUrlPattern(req);
                req.getSession().setAttribute("redirectPage", redirectUri);
                res.sendRedirect(wrapRequest.getServletPath()+"/login");
                return;
            }

            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {
                RequestDispatcher dispatcher
                        = req.getSession().getServletContext().getRequestDispatcher("/WEB-INF/view/accessDeniedView.jsp");
                dispatcher.forward(req, res);
                return;
            }
        }
        chain.doFilter(wrapRequest, res);
    }

    /**
     * Destroys filter
     */
    @Override
    public void destroy() {
        Logger.getLogger(this.getClass()).info("SecurityFilter destroy");
    }
}
