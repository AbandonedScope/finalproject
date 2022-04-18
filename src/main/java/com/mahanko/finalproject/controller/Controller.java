package com.mahanko.finalproject.controller;

import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.controller.command.CommandType;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONNECTION_POOL_CLASS_NAME = "com.mahanko.finalproject.model.pool.ConnectionPool";

    @Override
    public void init() {
        logger.log(Level.INFO, "Servlet '{}' initialization.", this.getServletName());
        try {
            Class.forName(CONNECTION_POOL_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        String commandStr = request.getParameter(ParametersType.COMMAND.toString());
        Command command = CommandType.define(commandStr);
        try {
            Router route = command.execute(request, response);
            String page = route.getPage();
            if (route.getType() == Router.Type.FORWARD) {
                request.getRequestDispatcher(page).forward(request, response);
            } else if(route.getType() == Router.Type.REDIRECT) {
                response.sendRedirect(page);
            }
        } catch (CommandException e) { // FIXME: 14.04.2022 logging, what type of exception?
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String commandStr = request.getParameter(ParametersType.COMMAND.toString());
        Command command = CommandType.define(commandStr);
        try {
            Router route = command.execute(request, response);
            String page = route.getPage();
            if (route.getType() == Router.Type.FORWARD) {
                request.getRequestDispatcher(page).forward(request, response);
            } else if(route.getType() == Router.Type.REDIRECT) {
                response.sendRedirect(page);
            }
        } catch (CommandException e) { // FIXME: 14.04.2022 logging, what type of exception?
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        logger.log(Level.INFO, "Servlet '{}' destroying", this.getServletName());
        ConnectionPool.getInstance().destroyPool();
    }
}