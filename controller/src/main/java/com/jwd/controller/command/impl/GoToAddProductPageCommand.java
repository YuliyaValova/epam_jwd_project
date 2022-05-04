package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.serviceLogic.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class GoToAddProductPageCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(GoToAddProductPageCommand.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#GoToAddProductPageCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#GoToAddProductPageCommand session is null.");
                resp.sendRedirect("main?message=SessionError");
            } else {
                session.setAttribute(PAGE, ADD_PRODUCT);
                req.getRequestDispatcher(PATH_TO_JSP+ Command.prepareUri(req) + JSP).forward(req, resp);

            }
        } catch (Exception e) {
            logger.error("#GoToAddProductPageCommand throws exception.");
            resp.sendRedirect("main?message=AddProductPageError");
        }
    }
}
