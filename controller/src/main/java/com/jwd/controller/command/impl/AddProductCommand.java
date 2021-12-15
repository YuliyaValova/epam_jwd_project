package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.dao.domain.Product;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Address;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.serviceLogic.UserService;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;

public class AddProductCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();
    private static final Logger logger = LoggerFactory.getLogger(AddProductCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            logger.info("#AddProductCommand starts.");
            final String name = req.getParameter(NAME);
            final String type = req.getParameter(TYPE);
            final String description = req.getParameter(DESCRIPTION);
            final String status = req.getParameter(STATUS);
            double price = productService.convertStringToDouble(req.getParameter(PRICE));
            boolean isAvailable = productService.convertStringToBoolean(status);

            if (price != -1) {
                Product product = new Product(name, type, description, price, isAvailable);
                int isAdded = productService.addProductToMenu(product);
                switch (isAdded) {
                    case -1:
                        logger.info("#AddProductCommand product exists.");
                        resp.sendRedirect("addProduct?message=productExists");
                        break;
                    case -2:
                        logger.info("#AddProductCommand incomplete info.");
                        resp.sendRedirect("addProduct?message=IncompleteInfo");
                        break;
                    case 1:
                        req.setAttribute(MESSAGE, PRODUCT_SUCCESSFULLY_ADDED);
                        req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                        break;
                    default:
                        logger.info("#AddProductCommand add-product unexpected result.");
                        resp.sendRedirect("addProduct?message=AddProductError");
                        break;
                }
            } else {
                logger.info("#AddProductCommand invalid price.");
                resp.sendRedirect("addProduct?message=InvalidPriceError");
            }
        } catch (Exception e) {
            logger.error("#AddProductCommand throws exception.");
            resp.sendRedirect("addProduct?message=AddProductError");
        }
    }

}


