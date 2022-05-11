package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import com.jwd.controller.exception.ControllerException;
import com.jwd.dao.domain.Order;
import com.jwd.service.ServiceFactory;
import com.jwd.service.domain.Page;
import com.jwd.service.domain.UserAccount;
import com.jwd.service.serviceLogic.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.jwd.controller.util.Constants.*;
import static com.jwd.controller.util.Constants.JSP;
import static java.util.Objects.isNull;

public class GoToHistoryCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(GoToHistoryCommand.class);
    private final ProductService productService = ServiceFactory.getServiceFactory().getProductService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ControllerException, IOException {
        try {
            logger.info("#GoToHistory starts.");
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                logger.info("#GoToHistory session is null.");
                resp.sendRedirect("basket?message=MainError");
            } else {
                String currentPageParam = "1";
                String currentLimitParam = "5";
                int currentPage = Integer.parseInt(currentPageParam);
                int pageLimit = Integer.parseInt(currentLimitParam);
                final Page<Order> pageRequest = new Page<>();
                pageRequest.setPageNumber(currentPage);
                pageRequest.setLimit(pageLimit);
                UserAccount account = (UserAccount) session.getAttribute("user");
                long id = account.getId();
                final Page<Order> pageableOrder = productService.showAllOrders(pageRequest, id);
                session.setAttribute(PAGEABLE4, pageableOrder);
                session.setAttribute(PAGE, "history");
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            logger.error("#GoToHistory throws exception.");
            resp.sendRedirect("basket?message=MainError");
        }
    }
}
