package com.jwd.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AntiInjectionFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AntiInjectionFilter.class);
    private static final String NOT_CONTAIN = "^((?!<|>|script).)*$";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> params = req.getParameterMap();
        for (String[] v : params.values()) {
            sb.append(v[0]);
        }
        if (sb.toString().trim().matches(NOT_CONTAIN)) {
            filterChain.doFilter(req, resp);
        } else {
            logger.warn("#AntiInjectionFilter js injection.");
            resp.sendRedirect("home?message=InjectionDanger");
        }
    }

    @Override
    public void destroy() {
    }
}
