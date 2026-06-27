package com.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static Connection connection = null;
    
    public static Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }
        
        try {
            String dbUrl, dbUser, dbPassword, driver;
            
            String railwayUrl = System.getenv("DATABASE_URL");
            String railwayUser = System.getenv("DATABASE_USERNAME");
            String railwayPass = System.getenv("DATABASE_PASSWORD");
            
            if (railwayUrl != null && !railwayUrl.isEmpty()) {
                if (railwayUrl.startsWith("postgresql://")) {
                    dbUrl = railwayUrl.replace("postgresql://", "jdbc:postgresql://");
                    driver = "org.postgresql.Driver";
                } else {
                    dbUrl = railwayUrl;
                    driver = "org.postgresql.Driver";
                }
                dbUser = railwayUser != null ? railwayUser : "";
                dbPassword = railwayPass != null ? railwayPass : "";
            } else {
                driver = "com.mysql.cj.jdbc.Driver";
                dbUrl = "jdbc:mysql://localhost:3306/laundry_db?useSSL=false&serverTimezone=UTC";
                dbUser = "root";
                dbPassword = "";
            }
            
            Class.forName(driver);
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver not found");
        }
        
        return connection;
    }
}