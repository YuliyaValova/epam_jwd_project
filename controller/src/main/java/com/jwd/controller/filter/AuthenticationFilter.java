package com.jwd.controller.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.jwd.controller.util.CommandEnum.*;
import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        final List<String> alwaysAvailableCommands = Arrays.asList(
                DEFAULT.getName(),
                REGISTRATION.getName(),
                LOGIN.getName(),
                LOCALE.getName()
        );

        String param = req.getParameter(COMMAND);
        if (isNull(param)) param = DEFAULT_;

        if (alwaysAvailableCommands.contains(param) ||
                req.getSession().getAttribute(ROLE) != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            res.sendRedirect("home?message=invalideCommand");
        }
    }
}
