package com.jwd.controller;


import com.jwd.controller.command.Command;
import com.jwd.controller.command.impl.DefaultCommand;
import com.jwd.controller.command.impl.RegistrationCommand;
import com.jwd.controller.exception.ControllerException;
import com.jwd.controller.util.CommandEnum;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.jwd.controller.util.CommandEnum.DEFAULT;
import static com.jwd.controller.util.CommandEnum.REGISTRATION;
import static java.util.Objects.isNull;


public class FrontController extends HttpServlet {
    private Map<CommandEnum, Command> commands;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        commandMapInit();
    }

    private void commandMapInit() {
        if (isNull(commands)) {
            commands = new HashMap<>();
        }
        commands.put(DEFAULT, new DefaultCommand());
        commands.put(REGISTRATION, new RegistrationCommand());
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doExecute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doExecute(req, resp);
    }

    private void doExecute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final CommandEnum command = getCommand(req);
            commands.get(command).execute(req, resp);
        } catch (ControllerException e) {
            // req.setAttribute("message", "error from #doExecute");
           // req.getRequestDispatcher("/").forward(req, resp);
        }
    }

    private CommandEnum getCommand(final HttpServletRequest req) {
        String commandNameParam = req.getParameter("command");
        if (isNull(commandNameParam)) {
            commandNameParam = DEFAULT.getName();
        }
        return CommandEnum.valueOf(commandNameParam.toUpperCase());
    }

}
