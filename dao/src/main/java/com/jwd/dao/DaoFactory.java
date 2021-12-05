package com.jwd.dao;

import com.jwd.dao.config.DatabaseConfig;
import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.repository.*;
import com.jwd.dao.repository.impl.*;
import com.jwd.dao.starter.InitDatabaseProcessor;

public class DaoFactory {

    private static final DaoFactory daoFactory = new DaoFactory();
    private final DatabaseConfig databaseConfig = new DatabaseConfig();
    private final ConnectionPool connectionPool = new ConnectionPoolImpl(databaseConfig);
    private final ConnectionUtil daoUtil = new ConnectionUtil(connectionPool);
    private final InitDatabaseProcessor dbProcessor = new InitDatabaseProcessor(connectionPool, daoUtil);
    private final ProductDao productDao = new MysqlProductDaoImpl(connectionPool, daoUtil);
    private final UserDao userDao = new MysqlUserDaoImpl(connectionPool, daoUtil);
    private final BasketDao basketDao = new MysqlBasketDaoImpl(connectionPool, daoUtil);
    private final PageDao pageDao = new MysqlPageDaoImpl(connectionPool, daoUtil);
    private final OrderDao orderDao = new MysqlOrderDaoImpl(connectionPool, daoUtil);

    private DaoFactory() {
    }

    public static DaoFactory getFactory() {
        return daoFactory;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public InitDatabaseProcessor getDbProcessor() {
        return dbProcessor;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public BasketDao getBasketDao() {
        return basketDao;
    }

    public PageDao getPageDao() {
        return pageDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }
}
