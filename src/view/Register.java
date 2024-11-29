package view;

import controller.UserController;
import utils.PasswordUtil;
import utils.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public final class Register extends JFrame {
    private final JPanel registerPanel = new JPanel();
    private final JLabel registerLabel = new JLabel("Register");

    private final JLabel usernameLabel = new JLabel("Username:");
    private final JTextField usernameField = new JTextField(20);

    private final JLabel emailLabel = new JLabel("Email:");
    private final JTextField emailField = new JTextField(20);

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

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registerButtonActionPerformed(e);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelButtonActionPerformed(e);
            }
        });

        GroupLayout panelLayout = new GroupLayout(registerPanel);
        registerPanel.setLayout(panelLayout);

        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(usernameLabel)
                                        .addComponent(usernameField)
                                        .addComponent(emailLabel)
                                        .addComponent(emailField)
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
                                .addGap(50, 50, 50)
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

    private void registerButtonActionPerformed(ActionEvent e) throws NoSuchAlgorithmException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPass = String.valueOf(confirmPasswordField.getPassword());

        UserController userController = new UserController();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (userController.getUser(username) != null && userController.getUser(username).getUsername().equals(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (userController.getUser(email) != null && userController.getUser(email).getEmail().equals(email)) {
            JOptionPane.showMessageDialog(this, "Email has already been set", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password must have at least 8 characters", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!Util.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            password = PasswordUtil.generateSaltedHash(password);

            if (PasswordUtil.verifyPassword(password, confirmPass)) {
                if (userController.register(username, email, password)) {
                    JOptionPane.showMessageDialog(this, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new Login().setVisible(true);
                        }
                    });

                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error registering user", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cancelButtonActionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });

        this.dispose();
    }
}