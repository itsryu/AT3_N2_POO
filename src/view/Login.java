package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private final JPanel loginPanel = new JPanel();
    private final JLabel loginLabel = new JLabel("Login");

    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);

    private final JButton loginButton = new JButton("Login");
    private final JButton registerButton = new JButton("Register");

    public Login() {
        this.initComponents();
    }

    public void initComponents() {
        setTitle("Login");
        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        loginLabel.setFont(new Font("sansserif", Font.BOLD, 24));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButtonActionPerformed(e);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerButtonActionPerformed(e);
            }
        });

        InputMap inputMap = loginPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = loginPanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "press");
        actionMap.put("press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginButton.hasFocus()) {
                    loginButton.doClick();
                } else if (registerButton.hasFocus()) {
                    registerButton.doClick();
                }
            }
        });

        GroupLayout panelLayout = new GroupLayout(loginPanel);
        loginPanel.setLayout(panelLayout);

        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(usernameField)
                                        .addComponent(passwordField)
                                        .addComponent(loginLabel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addGroup(panelLayout.createSequentialGroup()
                                                .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(150, Short.MAX_VALUE))
        );

        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(loginLabel)
                                .addGap(30, 30, 30)
                                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginButton)
                                        .addComponent(registerButton))
                                .addContainerGap(100, Short.MAX_VALUE))
        );

        getContentPane().add(loginPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void loginButtonActionPerformed(ActionEvent e) {
        String user = usernameField.getText();
        String pass = String.valueOf(passwordField.getPassword());

        System.out.println("User: " + user);
        System.out.println("Password: " + pass);
    }

    private void registerButtonActionPerformed(ActionEvent e) {
        new Register().setVisible(true);
        this.dispose();
    }
}