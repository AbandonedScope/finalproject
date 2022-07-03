package com.mahanko.finalproject.controller.listener;

import com.mahanko.finalproject.model.pool.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

/**
 * The type Context listener. It closes the connection pool.
 * @see ConnectionPool
 */
@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ConnectionPool.getInstance().destroyPool();
    }
}
