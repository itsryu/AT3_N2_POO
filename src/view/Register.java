package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame {
    private final JPanel registerPanel = new JPanel();
    private final JLabel registerLabel = new JLabel("Register");

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
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
                registerButtonActionPerformed(e);
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
                                        .addComponent(usernameField)
                                        .addComponent(passwordField)
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
                                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
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

    private void registerButtonActionPerformed(ActionEvent e) {
        String user = usernameField.getText();
        String pass = String.valueOf(passwordField.getPassword());
        String confirmPass = String.valueOf(confirmPasswordField.getPassword());

        if (pass.equals(confirmPass)) {
            System.out.println("User: " + user);
            System.out.println("Password: " + pass);
        } else {
            System.out.println("Passwords do not match");
        }
    }

    private void cancelButtonActionPerformed(ActionEvent e) {
        new Login().setVisible(true);
        this.dispose();
    }
}