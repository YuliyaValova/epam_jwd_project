package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.domain.Product;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Page;
import com.jwd.service.serviceLogic.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class ShowProductsCommand implements Command {

    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            String currentPageParam = req.getParameter(CURRENT_PAGE);
            if (isNullOrEmpty(currentPageParam)) {
                currentPageParam = "1";
            }
            String currentLimitParam = req.getParameter(PAGE_LIMIT);
            if (isNullOrEmpty(currentLimitParam)) {
                currentLimitParam = "2";
            }
            int currentPage = Integer.parseInt(currentPageParam);
            int pageLimit = Integer.parseInt(currentLimitParam);
            final Page<Product> pageRequest = new Page<>();
            pageRequest.setPageNumber(currentPage);
            pageRequest.setLimit(pageLimit);

            // todo validation

            final Page<Product> pageable = productService.showProducts(pageRequest);

            req.setAttribute(PAGEABLE, pageable);
            req.setAttribute(PAGE, "show");
            req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req)+JSP).forward(req, resp);
        } catch (Exception e) {
            resp.sendRedirect("menu?message=ShowProductsError");
        }
    }
}
