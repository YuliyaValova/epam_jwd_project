package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.service.ServiceFactory;
import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.serviceLogic.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static com.jwd.controller.util.Constants.JSP;
import static java.util.Objects.isNull;

public class WriteToCsvCommand implements Command {
    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(AddAdminCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#WriteToCsvCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#WriteToCsvCommand session is null.");
                resp.sendRedirect("main?message=SessionError");
            } else {
                // todo validation
                if (productService.writeToCSV(req.getParameter("path"))) {
                    req.setAttribute(MESSAGE, "Successfully");
                    req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                } else {
                    logger.info("#WriteToCsvCommand failed.");
                    resp.sendRedirect("main?message=failed");
                }
            }

        } catch (Exception e) {
            logger.error("#WriteToCsvCommand throws exception.");
            resp.sendRedirect("main?message=Error");
        }
    }
}
