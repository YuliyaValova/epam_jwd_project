package com.jwd.dao.connection;

import com.jwd.dao.exception.DaoException;

import java.sql.Connection;

public interface ConnectionPool {

    Connection takeConnection() throws DaoException;

    void retrieveConnection(final Connection connection);
}
