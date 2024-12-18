package view;

import controller.UserController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;
import javax.swing.*;

import fields.DateField;
import utils.PasswordUtil;
import utils.Util;

public final class Register extends JFrame {
    private final JPanel registerPanel = new JPanel();
    private final JLabel registerLabel = new JLabel("Register");

    private final JLabel usernameLabel = new JLabel("Username:");
    private final JTextField usernameField = new JTextField(20);

    private final JLabel emailLabel = new JLabel("Email:");
    private final JTextField emailField = new JTextField(20);

    private final JLabel birthDateLabel = new JLabel("Birth Date:");
    private final DateField dateField = new DateField();

    private final JLabel passwordLabel = new JLabel("Password:");
    private final JPasswordField passwordField = new JPasswordField(20);

    private final JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
    private final JPasswordField confirmPasswordField = new JPasswordField(20);

    private final JButton registerButton = new JButton("Register");
    private final JButton cancelButton = new JButton("Cancel");

    public Register() {
        this.initComponents();
    }

    public void initComponents() {
        setTitle("Register");
        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        registerLabel.setFont(new Font("sansserif", Font.BOLD, 24));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        registerButton.addActionListener(_ -> {
            try {
                registerButtonActionPerformed();
            } catch (NoSuchAlgorithmException error) {
                JOptionPane.showMessageDialog(registerPanel, "An error occurred: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("An error occurred: " + error.getLocalizedMessage());
            }
        });

        cancelButton.addActionListener(_ -> {
            cancelButtonActionPerformed();
        });

        InputMap inputMap = registerPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = registerPanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "press");
        actionMap.put("press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component focusedComponent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

                if (focusedComponent instanceof JButton jButton) {
                    jButton.doClick();
                }
            }
        });

        GroupLayout panelLayout = new GroupLayout(registerPanel);
        registerPanel.setLayout(panelLayout);

        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(150, 150, Short.MAX_VALUE)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(usernameLabel)
                                        .addComponent(usernameField)
                                        .addComponent(emailLabel)
                                        .addComponent(emailField)
                                        .addComponent(birthDateLabel)
                                        .addComponent(dateField)
                                        .addComponent(passwordLabel)
                                        .addComponent(passwordField)
                                        .addComponent(confirmPasswordLabel)
                                        .addComponent(confirmPasswordField)
                                        .addComponent(registerLabel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addGroup(panelLayout.createSequentialGroup()
                                                .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(150, Short.MAX_VALUE))
        );

        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(100, 100, Short.MAX_VALUE)
                                .addComponent(registerLabel)
                                .addGap(30, 30, 30)
                                .addComponent(usernameLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(emailLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(birthDateLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateField)
                                .addGap(18, 18, 18)
                                .addComponent(passwordLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(confirmPasswordLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(confirmPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(registerButton)
                                        .addComponent(cancelButton))
                                .addContainerGap(100, Short.MAX_VALUE))
        );

        getContentPane().add(registerPanel);

        pack();
        setLocationRelativeTo(null);
    }

    private void registerButtonActionPerformed() throws NoSuchAlgorithmException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPass = String.valueOf(confirmPasswordField.getPassword());
        LocalDate birthDate = dateField.getDate();
        Period age = Period.between(birthDate, LocalDate.now());
        UserController userController = new UserController();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (userController.getUser(username) != null && userController.getUser(username).getUsername().equals(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (userController.getUser(email) != null && userController.getUser(email).getEmail().equals(email)) {
            JOptionPane.showMessageDialog(this, "Email has already been set", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password must have at least 8 characters", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (Util.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (age.getYears() < 18) {
            JOptionPane.showMessageDialog(this, "You must be at least 18 years old", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            password = PasswordUtil.generateSaltedHash(password);

            if (PasswordUtil.verifyPassword(password, confirmPass)) {
                if (userController.register(username, email, password, birthDate)) {
                    JOptionPane.showMessageDialog(this, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                    SwingUtilities.invokeLater(() -> new Login().setVisible(true));

                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error registering user", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cancelButtonActionPerformed() {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        this.dispose();
    }
}