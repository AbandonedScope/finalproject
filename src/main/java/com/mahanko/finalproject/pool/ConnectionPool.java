package com.mahanko.finalproject.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final int MAX_CONNECTIONS = 8;
    private static final String URL = "jdbc:mysql://localhost:3306/cafedb";// FIXME: 09.04.2022 Read about these properties
    private static ConnectionPool instance;
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final ReentrantLock lock = new ReentrantLock(true);
    private BlockingDeque<Connection> freeConnections = new LinkedBlockingDeque<>(MAX_CONNECTIONS);
    private BlockingDeque<Connection> takenConnections = new LinkedBlockingDeque<>(MAX_CONNECTIONS);
    private static Properties properties;

    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) { // FIXME: 09.04.2022 Exception
            throw new ExceptionInInitializerError(e.getMessage());
        }
        properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "K_POPone_1_Love");
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        properties.put("useSSL", "true");
        properties.put("useJDBCCompliantTimezoneShift", "true");
        properties.put("useLegacyDatetimeCode", "false");
        properties.put("serverTimezone", "UTC");
        properties.put("serverSslCert", "classpath:server.crt");
    }

    private ConnectionPool() {
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            try {
                Connection connection = DriverManager.getConnection(URL, properties);
                freeConnections.put(connection);
            } catch (InterruptedException | SQLException e) { // FIXME: 09.04.2022 Exceptions
                throw new ExceptionInInitializerError(e.getMessage());
            }
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
        Connection connection = null;
        try {
            connection = freeConnections.take();
            takenConnections.put(connection);
        } catch (InterruptedException e) { // FIXME: 09.04.2022 Exception
            //log
            Thread.currentThread().interrupt();
        }

        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        boolean isRemoved = false;
        if (takenConnections.contains(connection)) {
            isRemoved = takenConnections.remove(connection);
            try {
                freeConnections.put(connection);
            } catch (InterruptedException e) { // FIXME: 09.04.2022 Exception
                //log
                Thread.currentThread().interrupt();
            }
        }

        return isRemoved;
    }

    //deregister driver
    public void destroyPool() {

        try {
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                freeConnections.take().close();
            }

            Driver driver = DriverManager.getDriver(URL);
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e ){ // FIXME: 09.04.2022 Exceptions, logger
            //log
            e.printStackTrace();
        } catch (InterruptedException e) {
            //log
            Thread.currentThread().interrupt();
        }
    }

}
