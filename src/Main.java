import utils.DBConnection;
import view.Login;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Main {
    private static final Connection database = DBConnection.getConnection();

    public static void main(String[] args) {
        if (database != null) {
            System.out.println("Connected to the database");

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Login().setVisible(true);
                }
            });
        } else {
            System.out.println("Failed to connect to the database");
        }
    }
}