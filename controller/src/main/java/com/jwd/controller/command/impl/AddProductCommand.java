package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.dao.domain.Product;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Address;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.ProductService;
import com.jwd.service.serviceLogic.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;

public class AddProductCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
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
                        resp.sendRedirect("addProduct?message=productExists");
                        break;
                    case -2:
                        resp.sendRedirect("addProduct?message=IncompleteInfo");
                        break;
                    case 1:
                        req.setAttribute(MESSAGE, "Product registered successfully.");
                        req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
                        break;
                    default:
                        resp.sendRedirect("addProduct?message=AddProductError");
                        break;
                }
            } else {
                resp.sendRedirect("addProduct?message=InvalidPriceError");
            }
        } catch (Exception e) {
            resp.sendRedirect("addProduct?message=AddProductError");
        }
    }

}


