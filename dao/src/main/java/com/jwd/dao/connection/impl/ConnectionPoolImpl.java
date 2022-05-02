package com.jwd.dao.connection.impl;

import com.jwd.dao.config.DatabaseConfig;
import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

import static java.util.Objects.nonNull;

public class ConnectionPoolImpl implements ConnectionPool {
    private final DatabaseConfig databaseConfig;
    private final static int TOTAL_CONNECTIONS = 10;
    private final ArrayBlockingQueue <Connection> pool;
    private final ArrayBlockingQueue <Connection> taken;

    public ConnectionPoolImpl(final DatabaseConfig databaseConfig ) {
        this.databaseConfig = databaseConfig;
        pool = new ArrayBlockingQueue<>(TOTAL_CONNECTIONS);
        taken = new ArrayBlockingQueue<>(TOTAL_CONNECTIONS);
        initializeConnectionPool();
    }

    private void initializeConnectionPool() {
        try {
            for (int i = 0; i < TOTAL_CONNECTIONS; i++) {
                pool.add(databaseConfig.getConnection());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection takeConnection() throws DaoException {
        Connection connection;
        try {
            connection = pool.take();
            taken.add(connection);
        } catch (InterruptedException e) {
           throw new DaoException(e);
        }

        return connection;
    }

    @Override
    public void retrieveConnection(Connection connection) {
        if (nonNull(connection)){
            taken.remove(connection);
            pool.add(connection);
        }
    }


}
