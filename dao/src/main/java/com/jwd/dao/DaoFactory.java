package com.jwd.dao;

import com.jwd.dao.config.DatabaseConfig;
import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.repository.UserDao;
import com.jwd.dao.repository.impl.MysqlUserDaoImpl;

public class DaoFactory {

    private static final DaoFactory daoFactory = new DaoFactory();
    private final DatabaseConfig databaseConfig = new DatabaseConfig();
    private final ConnectionPool connectionPool = new ConnectionPoolImpl(databaseConfig);
    private final ConnectionUtil daoUtil = new ConnectionUtil(connectionPool);
    private final UserDao userDao = new MysqlUserDaoImpl(connectionPool, daoUtil);
    //private final ProductDao productDao = new ProductDaoImpl(connectionPool); todo

    private DaoFactory() {}

    public static DaoFactory getFactory() {
        return daoFactory;
    }

    public UserDao getUserDao() {
        return userDao;
    }

   /* public ProductDao getProductDao() {
        return productDao;  todo
    }*/
}
