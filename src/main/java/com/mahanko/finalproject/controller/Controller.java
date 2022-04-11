package com.mahanko.finalproject.controller;

import com.mahanko.finalproject.command.Command;
import com.mahanko.finalproject.command.CommandType;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.pool.ConnectionPool;
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

    public void init() {
        logger.log(Level.INFO, "Servlet '{}' initialization.", this.getServletName());
        ConnectionPool.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        String commandStr = request.getParameter("command");
        Command command = CommandType.define(commandStr);
        String page = null;
        try {
            page = command.execute(request).getPage();// FIXME: 09.04.2022 type check
            request.getRequestDispatcher(page).forward(request, response);
        } catch (CommandException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public void destroy() {
        logger.log(Level.INFO, "Servlet '{}' destroying", this.getServletName());
        ConnectionPool.getInstance().destroyPool();
    }
}