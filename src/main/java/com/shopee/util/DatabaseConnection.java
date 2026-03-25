package com.shopee.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DatabaseConnection {
    static DatabaseConnection instance;
    private Connection connection;
    private Properties properties = new Properties();
    
    private DatabaseConnection() {
        getProperties("src/main/java/resources/config.properties");
        try {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://"
                 + properties.getProperty("postgres_host") 
                 + ":" 
                 + properties.getProperty("postgres_port") 
                 + "/" 
                 + properties.getProperty("postgres_database"),
                 properties.getProperty("postgres_name"),
                properties.getProperty("postgres_password")
            );
        } catch (SQLException exception) {
            Logger.getInstance().logError("Start database error", exception);
            exception.printStackTrace();
            // o servidor não roda se não for possível se conectar no banco de dados
            throw new RuntimeException("Error to connect to database");
        }
    }

    public void getProperties(String propertiesFile) {
        try (FileInputStream fileInput = new FileInputStream(propertiesFile)) {
            properties.load(fileInput);
        } catch (IOException exception) {
            Logger.getInstance().logError("fail to read config.properties", exception);
            exception.printStackTrace();
            // Falha ao ler arquivo de props
            throw new RuntimeException("Error to read props file");
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
