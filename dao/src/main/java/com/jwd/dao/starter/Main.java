package com.jwd.dao.starter;

public class Main {
    public static void main(String[] args) {
        InitDatabaseProcessor processor = new InitDatabaseProcessor();
        processor.initDatabase();
    }
}
