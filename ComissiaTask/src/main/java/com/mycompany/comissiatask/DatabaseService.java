/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comissiatask;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
/**
 *
 * @author elenagoncarova
 */

public class DatabaseService {
    private static final String DB_URL = "jdbc:h2:./database/comissia_db";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    
    private Connection connection;
    private int currentSessionId;

    public DatabaseService() {
        initializeDatabase();
        startNewSession();
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Create sessions table
            String createSessionsTable = """
                CREATE TABLE IF NOT EXISTS sessions (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    start_time TIMESTAMP,
                    description VARCHAR(255)
                )
            """;
            
            // Create users table
            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id VARCHAR(36) PRIMARY KEY,
                    session_id INT,
                    name VARCHAR(255),
                    email VARCHAR(255),
                    user_type VARCHAR(20),
                    data_source VARCHAR(20),
                    FOREIGN KEY (session_id) REFERENCES sessions(id) ON DELETE CASCADE
                )
            """;
            
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createSessionsTable);
                stmt.execute(createUsersTable);
            }
            
        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }

    private void startNewSession() {
        String insertSession = """
            INSERT INTO sessions (start_time, description) 
            VALUES (?, ?)
        """;
        
        String getLastId = "SELECT IDENTITY()";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSession);
             Statement stmt = connection.createStatement()) {
            
            LocalDateTime now = LocalDateTime.now();
            String description = "Сеанс от " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            pstmt.setTimestamp(1, Timestamp.valueOf(now));
            pstmt.setString(2, description);
            pstmt.executeUpdate();
            
            ResultSet rs = stmt.executeQuery(getLastId);
            if (rs.next()) {
                currentSessionId = rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Session creation error: " + e.getMessage());
        }
    }

    public void saveUser(User user, String dataSource) {
        String insertUser = """
            INSERT INTO users (id, session_id, name, email, user_type, data_source) 
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
            pstmt.setString(1, user.getId().toString());
            pstmt.setInt(2, currentSessionId);
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getUserType().toString());
            pstmt.setString(6, dataSource);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("User save error: " + e.getMessage());
        }
    }

    public void clearDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // Delete users first due to foreign key constraint
            stmt.execute("DELETE FROM users");
            stmt.execute("DELETE FROM sessions");
            
            // Restart session
            startNewSession();
            
        } catch (SQLException e) {
            System.err.println("Database clear error: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Database close error: " + e.getMessage());
        }
    }

    public int getCurrentSessionId() {
        return currentSessionId;
    }
}