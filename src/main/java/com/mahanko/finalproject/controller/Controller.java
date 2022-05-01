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

@WebServlet(name = "helloServlet", urlPatterns = "/controller")
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
        // FIXME: 27.04.2022 to filter
        response.setContentType("text/html");
        Command command = CommandType.define(request.getParameter(ParameterType.COMMAND));
        try {
            Router route = command.execute(request, response);
            String page = route.getPage();
            if (!route.isCacheAllowed()) {
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                response.setDateHeader("Expires", 0); // Proxies.
            }
            if (route.getType() == Router.Type.FORWARD) {
                request.getRequestDispatcher(page).forward(request, response);
            } else if (route.getType() == Router.Type.REDIRECT) {
                response.sendRedirect(page);
            }
        } catch (CommandException e) {
            logger.log(Level.FATAL, e);
            throw  new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        logger.log(Level.INFO, "Servlet '{}' destroying", this.getServletName());
        ConnectionPool.getInstance().destroyPool();
    }
}