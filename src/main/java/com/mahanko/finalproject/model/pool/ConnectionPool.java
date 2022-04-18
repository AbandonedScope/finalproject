package com.mahanko.finalproject.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool { //FIXME: 09.04.2022 Read about these properties
    private static final String CONFIG_FILE_NAME = "config.properties";
    private static final int MAX_CONNECTIONS;
    private static final String DATABASE_DRIVER;
    private static final String URL;
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final ReentrantLock lock = new ReentrantLock(true);
    private static final Properties properties;
    private static final Logger logger = LogManager.getLogger();
    private static ConnectionPool instance;
    private final BlockingDeque<ProxyConnection> freeConnections = new LinkedBlockingDeque<>(MAX_CONNECTIONS);
    private final BlockingDeque<ProxyConnection> takenConnections = new LinkedBlockingDeque<>(MAX_CONNECTIONS);


    static {
        properties = new Properties();
        try (InputStream input = ConnectionPool.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            properties.load(input);
            DATABASE_DRIVER = properties.getProperty("driver");
            URL = properties.getProperty("url");
            MAX_CONNECTIONS = Integer.parseInt(properties.getProperty("maxConnections"));
            Class.forName(DATABASE_DRIVER);
        } catch (ClassNotFoundException | NumberFormatException | IOException e) {
            logger.log(Level.ERROR, e);
            throw new ExceptionInInitializerError(e.getMessage());
        }
    }

    private ConnectionPool() {
        try {
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                Connection connection = DriverManager.getConnection(URL, properties);
                freeConnections.put(new ProxyConnection(connection));
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
            Thread.currentThread().interrupt();
            throw new ExceptionInInitializerError(e.getMessage());
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new ExceptionInInitializerError(e.getMessage());
        }
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            takenConnections.put(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
            Thread.currentThread().interrupt();
        }

        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        boolean isRemoved = false;
        if (connection instanceof ProxyConnection) {
            isRemoved = takenConnections.remove(connection);
            if (isRemoved) {
                try {
                    freeConnections.put((ProxyConnection) connection);
                } catch (InterruptedException e) {
                    logger.log(Level.ERROR, e);
                    Thread.currentThread().interrupt();
                }
            }
        }

        return isRemoved;
    }

    //deregister driver
    public void destroyPool() {
        try {
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                freeConnections.take().closeProxy();
            }

            Driver driver = DriverManager.getDriver(URL);
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) { // FIXME: 09.04.2022 Exceptions?
            logger.log(Level.INFO, e);
        } catch (InterruptedException e) {
            logger.log(Level.INFO, e);
            Thread.currentThread().interrupt();
        }
    }

}
