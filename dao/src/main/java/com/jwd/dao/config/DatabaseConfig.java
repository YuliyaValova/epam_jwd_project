package com.jwd.dao.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final String DATABASE_CONFIG_PATH = "db.properties";
    private static final String
            DRIVER = "database.driver",
            URL = "database.url",
            DATABASE_NAME = "database.name",
            USERNAME = "database.username",
            PASSWORD = "database.password";
    private Properties properties;

    private boolean driverIsLoaded = false;

    public DatabaseConfig() {
        loadProperties();
    }

    public Connection getConnection() throws SQLException {
        loadJdbcDriver();
        Connection connection;
        Properties properties = new Properties();
        properties.setProperty("user", getProperty(USERNAME));
        properties.setProperty("password", getProperty(PASSWORD));
        connection = DriverManager.getConnection(getProperty(URL) + getProperty(DATABASE_NAME), properties);
        return connection;
    }

    private String getProperty(String key) {
        return properties.getProperty(key);
    }

    private void loadJdbcDriver() {
        if (!driverIsLoaded) {
            try {
                Class.forName(getProperty(DRIVER));
                driverIsLoaded = true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadProperties() {
        try(InputStream is = DatabaseConfig.class.getClassLoader().getResourceAsStream(DATABASE_CONFIG_PATH)) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
