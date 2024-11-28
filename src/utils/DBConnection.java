package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "Jv8272480213#123098ScJv#";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException error) {
            DBConnection.handleError(error);
        } catch (ClassNotFoundException e) {
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