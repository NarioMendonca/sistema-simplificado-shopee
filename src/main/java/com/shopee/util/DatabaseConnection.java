package com.shopee.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DatabaseConnection {
    static DatabaseConnection instance;
    private Connection connection;
    
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/shopee_db", "docker", "docker");
        } catch (SQLException connectionException) {
            connectionException.printStackTrace();
            // o servidor não roda se não for possível se conectar no banco de dados
            throw new RuntimeException("Error to connect on database");
        }
    }

    public static DatabaseConnection getInstance() {
        if (Objects.isNull(instance)) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
