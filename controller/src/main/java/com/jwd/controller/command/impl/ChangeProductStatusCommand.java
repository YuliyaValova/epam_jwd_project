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

import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class ChangeProductStatusCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(ChangeProductStatusCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#ChangeProductStatusCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#ChangeProductStatusCommand session is null.");
                resp.sendRedirect("main?message=SessionError");
            } else {
                //todo trim
                long productId = Long.parseLong(req.getParameter(PROD_ID).replace("/", ""));
                String oldStatus = req.getParameter(STATUS).replace("/", "").trim();
                boolean isSuccess = productService.changeProductStatus(productId, oldStatus);
                if (isSuccess) {
                    req.setAttribute(MESSAGE, STATUS_CHANGED);
                    req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                }
            }
        } catch (Exception e) {
            logger.error("#ChangeProductStatusCommand throws exception.");
            resp.sendRedirect("main?message=ChangeProductStatusError");
        }
    }
}
