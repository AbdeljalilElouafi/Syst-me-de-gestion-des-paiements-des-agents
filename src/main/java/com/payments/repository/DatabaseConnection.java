package main.java.com.payments.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;


    private static final String URL = "jdbc:mysql://localhost:3306/payments_db?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    private DatabaseConnection() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASS);
    }

    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
