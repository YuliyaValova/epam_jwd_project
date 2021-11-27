package com.jwd.controller.command.impl;

import com.jwd.controller.command.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static com.jwd.controller.util.Constants.*;
import static java.util.Objects.isNull;

public class GoToBasketCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            HttpSession session;
            session = req.getSession(false);
            if (isNull(session)) {
                resp.sendRedirect("home?message=BasketError");
            } else {
                session.setAttribute(PAGE, BASKET);
                req.getRequestDispatcher(PATH_TO_JSP + Command.prepareUri(req) + JSP).forward(req, resp);
            }
        } catch (Exception e) {
            resp.sendRedirect("home?message=BasketError");
        }
    }
}
