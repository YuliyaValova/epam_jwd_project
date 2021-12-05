package com.jwd.dao.connection;

import com.jwd.dao.exception.DaoException;

import java.sql.Connection;

public interface ConnectionPool {

    /**
     * Retrieves an idle database connection from pool
     * @return free connection
     * @throws DaoException is a module exception
     */
    Connection takeConnection() throws DaoException;

    /**
     * Returns the unnecessary connection
     * @param connection to return into pool
     */
    void retrieveConnection(final Connection connection);
}
