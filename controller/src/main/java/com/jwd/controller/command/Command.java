package com.jwd.controller.command;

import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    /**
     * Executes the calling command
     * @param req Servlet request
     * @param resp Servlet response
     * @throws ControllerException is a module exception
     * @throws IOException can be thrown out through sendRedirect
     */
    void execute(final HttpServletRequest req, final HttpServletResponse resp) throws ControllerException, IOException;

    /**
     * Prepares uri for forwarding
     * @param req Servlet request
     * @return String path
     */
    static String prepareUri(HttpServletRequest req) {
        String uri = req.getRequestURI().replace("/", "");
        if (uri.length() == 0) {
            uri = "home";
        }
        return uri;
    }
}
