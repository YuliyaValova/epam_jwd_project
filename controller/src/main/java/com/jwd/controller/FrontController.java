package com.jwd.controller;


import com.jwd.controller.command.Command;
import com.jwd.controller.command.impl.*;
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

import static com.jwd.controller.util.CommandEnum.*;
import static com.jwd.controller.util.Constants.COMMAND;
import static com.jwd.controller.util.Constants.MESSAGE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


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
        commands.put(LOGIN, new LogInCommand());
        commands.put(LOGOUT, new LogOutCommand());
        commands.put(GOTOMAIN, new GoToMainCommand());
        commands.put(LOCALE, new ChangeLanguageCommand());
        commands.put(SHOWPRODUCTS, new ShowProductsCommand());
        commands.put(SHOWBASKET, new ShowBasketCommand());
        commands.put(GOTOPROFILE, new GoToProfileCommand());
        commands.put(CLEANBASKET, new CleanBasketCommand());
        commands.put(SENDORDER, new SendOrderCommand());
        commands.put(DELETEFROMBASKET, new DeleteFromBasketCommand());
        commands.put(ADDTOBASKET, new AddToBasketCommand());
        commands.put(DELETEACCOUNT, new DeleteAccountCommand());
        commands.put(CHANGEPASSWORD, new ChangePasswordCommand());
        commands.put(GOTOADDPRODUCTPAGE, new GoToAddProductPageCommand());
        commands.put(ADDPRODUCTTOMENU, new AddProductCommand());
        commands.put(GETPAIDORDERS, new ShowPaidOrdersCommand());
        commands.put(GETALLORDERS, new ShowAllOrdersCommand());
        commands.put(DELETEFROMMENU, new DeleteFromMenuCommand());
        commands.put(ADDADMIN, new AddAdminCommand());
        commands.put(FINDUSERBYID, new GetUserByIdCommand());
        commands.put(FINDPRODUCTBYID, new GetProductByIdCommand());
        commands.put(CHANGEPRODUCTSTATUS, new ChangeProductStatusCommand());
        commands.put(GETALLPRODUCTS, new GetAllProductsCommand());
        commands.put(HIDEPRODUCT, new HideProductCommand());
        commands.put(UPDATEUSER, new UpdateUserCommand());
        commands.put(UPDATEPRODUCT, new UpdateProductCommand());
        commands.put(CHANGEORDERSTATUS, new ChangeOrderStatusCommand());
        commands.put(WRITETOCSV, new WriteToCsvCommand());
        commands.put(SEARCH, new SearchCommand());
        commands.put(GETORDERS, new GetOrdersCommand());
        commands.put(GOTOHISTORY, new GoToHistoryCommand());
        commands.put(GOTOPAY, new GoToPayCommand());
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
            Throwable cause = getCause(e);
            req.setAttribute(MESSAGE, "Exception: " + cause.getMessage());
            req.getRequestDispatcher("home").forward(req, resp);
        }
    }

    private CommandEnum getCommand(final HttpServletRequest req) {
        String commandNameParam = req.getParameter(COMMAND);
        if (isNull(commandNameParam)) {
            commandNameParam = DEFAULT.getName();
        }
        return CommandEnum.valueOf(commandNameParam.toUpperCase());
    }

    private Throwable getCause(Throwable cause) {
        if (nonNull(cause.getCause())) {
            cause = getCause(cause.getCause());
        }
        return cause;
    }
}
