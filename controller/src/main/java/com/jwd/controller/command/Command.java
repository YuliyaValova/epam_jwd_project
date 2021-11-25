package com.jwd.controller.command;

import com.jwd.controller.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {

    void execute(final HttpServletRequest req, final HttpServletResponse resp) throws ControllerException, IOException;

    static String prepareUri(HttpServletRequest req) {
        String uri = req.getRequestURI().replace("/", "");
        if (uri.length() == 0) {
            uri = "home";
        }
        return uri;
    }
}
