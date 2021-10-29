package com.jwd.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doExecute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doExecute(req, resp);
    }

    private void doExecute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = prepareUri(req);
        req.getRequestDispatcher(uri + ".jsp").forward(req, resp);
    }

    private String prepareUri(HttpServletRequest req) {
        String uri = req.getRequestURI().replace("/", "");
        if (uri.length() == 0) {
            uri = "home";
        }
        return uri;
    }


}
