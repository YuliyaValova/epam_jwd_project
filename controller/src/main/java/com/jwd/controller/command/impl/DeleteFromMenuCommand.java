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

public class DeleteFromMenuCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(DeleteFromMenuCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#DeleteFromMenuCommand starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#DeleteFromMenuCommand session is null.");
                resp.sendRedirect("main?message=SessionError");
            } else {
                // todo validation
                long productId = Long.parseLong(req.getParameter(PROD_ID).replace("/", ""));
                productService.deleteFromMenu(productId);
                req.setAttribute(MESSAGE, PRODUCT_DELETED);
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("#DeleteFromMenuCommand throws exception.");
            resp.sendRedirect("menu?message=DeletingError");
        }
    }
}
