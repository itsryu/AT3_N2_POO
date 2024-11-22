import utils.DBConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection database = DBConnection.getConnection();

        if (database != null) {
            System.out.println("Connected to the database");
        } else {
            System.out.println("Failed to connect to the database");
        }
    }
}