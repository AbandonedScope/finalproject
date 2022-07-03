package com.mahanko.finalproject.controller;

import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.controller.command.CommandType;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.model.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The type Controller class. Manage requests and forms responses for clients.
 * Override GET and POST methods.
 * @see jakarta.servlet.http.HttpServlet
 */
@WebServlet(name = "mainServlet", urlPatterns = "/controller")
@MultipartConfig
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init() {
        logger.log(Level.INFO, "Servlet '{}' initialization.", this.getServletName());
        ConnectionPool.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandType.of(request.getParameter(ParameterType.COMMAND)).getCommand();
        try {
            Router route = command.execute(request, response);
            String page = route.getPage();
            switch (route.getType()) {
                case FORWARD: {
                    request.getRequestDispatcher(page).forward(request, response);
                    break;
                }
                case REDIRECT: {
                    response.sendRedirect(page);
                    break;
                }
                case NONE: {
                    break;
                }
                default: {
                    throw new ServletException("No such command exists");
                }
            }
        } catch (CommandException e) {
            logger.log(Level.FATAL, e);
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        logger.log(Level.INFO, "Servlet '{}' destroying", this.getServletName());
        ConnectionPool.getInstance().destroyPool();
    }
}