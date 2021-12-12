package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class GoToMenuCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(GoToMenuCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            logger.info("#GoToMenuCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#GoToMenuCommand session is null.");
                resp.sendRedirect("main?message=MenuError");
            } else {
                session.setAttribute(PAGE, MENU);
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("#GoToMenuCommand throws exception.");
            resp.sendRedirect("main?message=MenuError");
        }
    }
}
