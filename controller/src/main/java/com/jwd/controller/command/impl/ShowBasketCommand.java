package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.domain.Product;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static com.mysql.cj.util.StringUtils.isNullOrEmpty;
import static java.util.Objects.isNull;

public class ShowBasketCommand implements Command {

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
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                resp.sendRedirect("basket?message=BasketError");
            } else {
                // todo validation
                UserAccount user = (UserAccount) session.getAttribute(USER);

                final Page<Product> pageable = productService.showBasket(pageRequest,user.getId());
                final double totalSum = productService.getSum(user.getId());
                req.setAttribute(PAGEABLE, pageable);
                session.setAttribute(SUM, totalSum);
                req.setAttribute(PAGE, "show");
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            resp.sendRedirect("basket?message=ShowBasketError");
        }
    }
}
