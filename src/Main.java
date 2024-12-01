import utils.DatabaseUtil;
import view.Login;

import javax.swing.*;
import java.sql.Connection;

public class Main {
    private static final Connection database = DatabaseUtil.getConnection();

    public static void main(String[] args) {
        if (database != null) {
            SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        } else {
            System.err.println("Failed to connect to the database");
            JOptionPane.showMessageDialog(null, "Failed to connect to the database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}