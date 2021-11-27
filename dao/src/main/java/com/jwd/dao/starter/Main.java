package com.jwd.dao.starter;

import com.jwd.dao.DaoFactory;

public class Main {
    public static void main(String[] args) {
        InitDatabaseProcessor processor = DaoFactory.getFactory().getDbProcessor();
        processor.initDatabase();
    }
}
