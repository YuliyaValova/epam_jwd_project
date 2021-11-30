package com.jwd.dao.starter;

import com.jwd.dao.config.DatabaseConfig;
import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.ConnectionUtil;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;

public class InitDatabaseProcessor {

    private static final String CREATE_DATABASE_QUERY = "create database if not exists jwd_db;";
    private static final String USE_DATABASE_QUERY = "use jwd_db;";
    private static final String CREATE_ADDRESSES_QUERY = "create table if not exists Addresses(\n" +
            "id bigint primary key auto_increment,\n" +
            "city varchar(100) not null,\n" +
            "street text not null,\n" +
            "building varchar(15) not null,\n" +
            "apartment varchar(15)\n" +
            ");";
    private static final String CREATE_USER_ACCOUNTS_QUERY = "create table if not exists UserAccounts(\n" +
            "id bigint primary key auto_increment,\n" +
            "login varchar(50) unique not null,\n" +
            "password varchar(50) not null,\n" +
            "role varchar(25) not null,\n" +
            "fName varchar(25),\n" +
            "lName varchar(25),\n" +
            "phone varchar(15) not null,\n" +
            "address_id bigint\n" +
            ");";
    private static final String CREATE_PRODUCTS_QUERY = "create table if not exists Products(\n" +
            "id bigint primary key auto_increment,\n" +
            "name varchar(100) unique not null,\n" +
            "type varchar(100) not null,\n" +
            "description text,\n" +
            "price double not null,\n" +
            "isAvailable bool not null\n" +
            ");\n";
    private static final String CREATE_ORDERS_QUERY = "create table if not exists Orders(\n" +
            "id bigint primary key auto_increment,\n" +
            "date datetime not null,\n" +
            "status varchar(30) not null,\n" +
            "product_amount integer default 1,\n" +
            "product_id bigint,\n" +
            "customer_id bigint\n" +
            ");";
    private static final String INSERT_TO_ADDRESSES_QUERY = "insert into Addresses (city, street, building, apartment)\n" +
            "values\n" +
            "(\"Гродно\", \"Центральная\", \"128\", \"4\"),\n" +
            "(\"Минск\", \"Сурганова\", \"4-1\", \"603а\"),\n" +
            "(\"Лида\", \"Победы\", \"12\", \"\"),\n" +
            "(\"Минск\", \"П.Бровки\", \"17\", \"22\");";
    private static final String INSERT_TO_USER_ACCOUNTS_QUERY = "insert into UserAccounts (login, password, role, fName, lName, phone, address_id)\n" +
            "values\n" +
            "(\"PashaKek\", \"88526\", \"default\", \"Pavel\", \"Malashko\", \"+375299874512\", 1),\n" +
            "(\"Marish\", \"12512555\", \"default\", \"Marina\", \"Kolesinskaya\", \"+375293654568\", 2),\n" +
            "(\"Dima666\", \"14141414\", \"default\", \"Dmitriy\", \"Kohnovich\", \"+375447842536\", 3),\n" +
            "(\"Admin\", \"111\", \"admin\", \"Lulka\", \"Xo\", \"+375299740419\", 4);";
    private static final String INSERT_TO_PRODUCTS_QUERY = "insert into Products (name, type, description, price, isAvailable)\n" +
            "values\n" +
            "(\"Margarita\", \"Pizza\", \"Juicy tomato pizza with mozzarella and cheddar\", 18, true),\n" +
            "(\"Pesto\", \"Pizza\", \"Consists of: green - from basil, red - from tomatoes, and white - from mozzarella.\", 16, true),\n" +
            "(\"Pepperoni\", \"Pizza\", \"Pepperoni, chili peppers, tomatoes in their own juice, oregano, dried basil\", 17, true);";
    private static final String INSERT_TO_ORDERS_QUERY = "insert into Orders (date, status, product_id, customer_id)\n" +
            "values\n" +
            "(now(), \"Waiting for payment\", 1, 1),\n" +
            "(now(), \"Paid up\", 1, 2),\n" +
            "(now(), \"Waiting for payment\", 2, 1),\n" +
            "(now(), \"Waiting for payment\", 3, 1),\n" +
            "(now(), \"Waiting for payment\", 2, 2);";

    private final ConnectionPool connectionPool;
    private final ConnectionUtil daoUtil;

    public InitDatabaseProcessor(ConnectionPool connectionPool,ConnectionUtil daoUtil) {
        this.connectionPool = connectionPool;
        this.daoUtil = daoUtil;
    }

    void initDatabase(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = daoUtil.getPreparedStatement(CREATE_DATABASE_QUERY, connection, Collections.emptyList());
            preparedStatement.executeUpdate();

            preparedStatement = daoUtil.getPreparedStatement(USE_DATABASE_QUERY, connection, Collections.emptyList());
            preparedStatement.executeUpdate();

            preparedStatement = daoUtil.getPreparedStatement(CREATE_ADDRESSES_QUERY, connection, Collections.emptyList());
            preparedStatement.executeUpdate();

            preparedStatement = daoUtil.getPreparedStatement(CREATE_USER_ACCOUNTS_QUERY, connection, Collections.emptyList());
            preparedStatement.executeUpdate();

            preparedStatement = daoUtil.getPreparedStatement(CREATE_PRODUCTS_QUERY, connection, Collections.emptyList());
            preparedStatement.executeUpdate();

            preparedStatement = daoUtil.getPreparedStatement(CREATE_ORDERS_QUERY, connection, Collections.emptyList());
            preparedStatement.executeUpdate();

            preparedStatement = daoUtil.getPreparedStatement(INSERT_TO_ADDRESSES_QUERY, connection, Collections.emptyList());
            preparedStatement.executeUpdate();

            preparedStatement = daoUtil.getPreparedStatement(INSERT_TO_USER_ACCOUNTS_QUERY, connection, Collections.emptyList());
            preparedStatement.executeUpdate();

            preparedStatement = daoUtil.getPreparedStatement(INSERT_TO_PRODUCTS_QUERY, connection, Collections.emptyList());
            preparedStatement.executeUpdate();

            preparedStatement = daoUtil.getPreparedStatement(INSERT_TO_ORDERS_QUERY, connection, Collections.emptyList());
            preparedStatement.executeUpdate();

        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            daoUtil.close(preparedStatement);
            connectionPool.retrieveConnection(connection);
        }
    }
}
