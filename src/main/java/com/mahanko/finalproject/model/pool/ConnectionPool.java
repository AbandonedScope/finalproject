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

/**
 * The type ConnectionPool class. Announces two limited thread - safe queues
 * for free and taken connections. It is a singleton class with private
 * constructor and static method to initialize this class.
 */
public class ConnectionPool {
    private static final int MAX_CONNECTIONS;
    private static final String DATABASE_DRIVER;
    private static final String URL;
    private static final Properties properties;
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool instance = new ConnectionPool();
    private final BlockingDeque<ProxyConnection> freeConnections = new LinkedBlockingDeque<>(MAX_CONNECTIONS);
    private final BlockingDeque<ProxyConnection> takenConnections = new LinkedBlockingDeque<>(MAX_CONNECTIONS);


    static {
        properties = new Properties();
        try (InputStream input = ConnectionPool.class.getClassLoader().getResourceAsStream(ConnectionProperty.CONFIG_FILE_NAME)) {
            properties.load(input);
            DATABASE_DRIVER = properties.getProperty(ConnectionProperty.DRIVER);
            URL = properties.getProperty(ConnectionProperty.URL);
            MAX_CONNECTIONS = Integer.parseInt(properties.getProperty(ConnectionProperty.MAX_CONNECTIONS_AMOUNT));
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
        } catch (InterruptedException | SQLException e) {
            logger.log(Level.FATAL, e);
            throw new ExceptionInInitializerError(e.getMessage());
        }
    }

    /**
     * Get instance connection pool.
     *
     * @return the connection pool
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * Get connection.
     *
     * @return the connection
     */
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

    /**
     * Release connection. The connection returns
     * to the pool.
     *
     * @param connection the connection
     */
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

    /**
     * Destroy pool and deregister drivers.
     */
    public void destroyPool() {
        try {
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                freeConnections.take().closeProxy();
            }

            Driver driver = DriverManager.getDriver(URL);
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            logger.log(Level.INFO, e);
        } catch (InterruptedException e) {
            logger.log(Level.INFO, e);
            Thread.currentThread().interrupt();
        }
    }

}
