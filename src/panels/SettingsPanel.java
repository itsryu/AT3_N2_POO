package panels;

import controller.UserController;

import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import javax.swing.*;

import models.User;
import utils.DateUtil;
import utils.PasswordUtil;
import utils.Util;
import view.Login;

public final class SettingsPanel extends JPanel {
    private final User user;

    private final JButton deleteAccountButton = new JButton("Delete Account");
    private final JButton editAccountButton = new JButton("Edit Account");
    private final JButton changePasswordButton = new JButton("Change Password");

    public SettingsPanel(User user) {
        this.user = user;

        initComponents();
    }

    private void initComponents() {
        setLayout(new GroupLayout(this));
        GroupLayout layout = (GroupLayout) getLayout();
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        Dimension buttonSize = new Dimension(150, 30);
        deleteAccountButton.setPreferredSize(buttonSize);
        editAccountButton.setPreferredSize(buttonSize);
        changePasswordButton.setPreferredSize(buttonSize);

        deleteAccountButton.addActionListener(_ -> {
            try {
                deleteAccountActionPerformed();
            } catch (NoSuchAlgorithmException error) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editAccountButton.addActionListener(_ -> {
            try {
                editAccountActionPerformed();
            } catch (NoSuchAlgorithmException error) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        changePasswordButton.addActionListener(_ -> {
            try {
                changePasswordActionPerformed();
            } catch (NoSuchAlgorithmException error) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }
        });

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(deleteAccountButton)
                        .addComponent(editAccountButton)
                        .addComponent(changePasswordButton)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(deleteAccountButton)
                        .addComponent(editAccountButton)
                        .addComponent(changePasswordButton)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, deleteAccountButton, editAccountButton, changePasswordButton);
        layout.linkSize(SwingConstants.VERTICAL, deleteAccountButton, editAccountButton, changePasswordButton);
    }

    private void deleteAccountActionPerformed() throws NoSuchAlgorithmException {
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(this, passwordField, "Enter your password to confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String password = new String(passwordField.getPassword());

            if (PasswordUtil.verifyPassword(user.getPassword(), password)) {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) deleteAccount();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteAccount() {
        UserController userController = new UserController();

        if (userController.delete(user)) {
            JOptionPane.showMessageDialog(this, "Account deleted successfully");

            SwingUtilities.invokeLater(() -> new Login().setVisible(true));
            SwingUtilities.getWindowAncestor(this).dispose();
        } else {
            JOptionPane.showMessageDialog(this, "An error occurred while deleting your account", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editAccountActionPerformed() throws NoSuchAlgorithmException {
        JTextField usernameField = new JTextField(user.getUsername());
        JTextField emailField = new JTextField(user.getEmail());
        String birthDate = DateUtil.formatLocalDate(user.getBirthDate(), "dd-MM-yyyy");
        DatePickerPanel birthDatePanel = new DatePickerPanel(DateUtil.parseLocalDate(birthDate, "dd-MM-yyyy"));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Account Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Birth Date:"));
        panel.add(birthDatePanel);

        int option = JOptionPane.showConfirmDialog(this, panel, "Edit Account Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String newUsername = usernameField.getText();
                String newEmail = emailField.getText();
                LocalDate formatedDate = birthDatePanel.getDate();
                Period age = Period.between(formatedDate, LocalDate.now());
                UserController userController = new UserController();

                if (newUsername.isEmpty() || newEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (Util.isValidEmail(newEmail)) {
                    JOptionPane.showMessageDialog(this, "Invalid email", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (userController.getUser(newUsername) != null && userController.getUser(newUsername).getUsername().equals(newUsername) && !newUsername.equals(user.getUsername())) {
                    JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (userController.getUser(newEmail) != null && userController.getUser(newEmail).getEmail().equals(newEmail) && !newEmail.equals(user.getEmail())) {
                    JOptionPane.showMessageDialog(this, "Email already exists", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (age.getYears() < 18) {
                    JOptionPane.showMessageDialog(this, "You must be at least 18 years old", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (newUsername.equals(user.getUsername()) && newEmail.equals(user.getEmail()) && Objects.equals(formatedDate, user.getBirthDate())) {
                    JOptionPane.showMessageDialog(this, "No changes were made", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    user.setUsername(newUsername);
                    user.setEmail(newEmail);
                    user.setBirthDate(formatedDate);

                    if (user.edit(user)) {
                        JOptionPane.showMessageDialog(this, "Account information updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "An error occurred while updating your account information", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changePasswordActionPerformed() throws NoSuchAlgorithmException {
        JPasswordField currentPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Current Password:"));
        panel.add(currentPasswordField);
        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Change Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String currentPassword = new String(currentPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());

            if (!PasswordUtil.verifyPassword(user.getPassword(), currentPassword)) {
                JOptionPane.showMessageDialog(this, "Incorrect current password", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (newPassword.length() < 8) {
                JOptionPane.showMessageDialog(this, "Password must have at least 8 characters", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                newPassword = PasswordUtil.generateSaltedHash(newPassword);
                user.setPassword(newPassword);

                if (user.edit(user)) {
                    JOptionPane.showMessageDialog(this, "Password changed successfully, please login again", "Success", JOptionPane.INFORMATION_MESSAGE);

                    SwingUtilities.invokeLater(() -> new Login().setVisible(true));
                    SwingUtilities.getWindowAncestor(this).dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "An error occurred while changing your password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}