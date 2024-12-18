package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String url = "jdbc:mysql://localhost:3306/poo";
    private static final String user = "root";
    private static final String password = "victor";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException error) {
            DatabaseUtil.handleError(error);
        } catch (ClassNotFoundException e) {
            System.err.println("Verify if the JDBC Driver was setup (MySQL Connector)");
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }

        return null;
    }

    public static void handleError(SQLException error) {
        int errorCode = Integer.parseInt(error.getSQLState());

        switch (errorCode) {
            case 28000: {
                System.err.println("SQL Error: Authentication failed, verify credentials: " + error.getMessage());
                break;
            }

            case 42000: {
                System.err.println("SQL Error: Syntax error: " + error.getMessage());
                break;
            }

            default: {
                System.err.println("SQL Error: " + error.getMessage());
                break;
            }
        }
    }
}