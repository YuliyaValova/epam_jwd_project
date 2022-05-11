package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.domain.Order;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static com.jwd.controller.util.Constants.JSP;
import static java.util.Objects.isNull;

public class GoToPayCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(GoToPayCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#GoToPay starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#GoToPay session is null.");
                resp.sendRedirect("basket?message=MainError");
            } else {
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("#GoToHistory throws exception.");
            resp.sendRedirect("basket?message=MainError");
        }
    }
}
