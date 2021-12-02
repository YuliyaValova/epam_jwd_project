package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.domain.Order;
import com.jwd.dao.domain.Product;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.PageOrder;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static com.mysql.cj.util.StringUtils.isNullOrEmpty;
import static java.util.Objects.isNull;

public class ShowPaidOrdersCommand implements Command {

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
            final PageOrder<Order> pageRequest = new PageOrder<>();
            pageRequest.setPageNumber(currentPage);
            pageRequest.setLimit(pageLimit);
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                resp.sendRedirect("main?message=SessionError");
            } else {
                // todo validation
                final PageOrder<Order> pageableOrder = productService.showPaidOrders(pageRequest);
                req.setAttribute(PAGEABLE, pageableOrder);
                req.setAttribute(PAGE, "show");
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            resp.sendRedirect("main?message=GetPaidOrdersError");
        }
    }
}
