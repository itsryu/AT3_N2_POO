package view;

import controller.ProfileController;
import controller.UserController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;
import models.Profile;
import models.User;
import utils.PasswordUtil;

public final class Login extends JFrame {
    private final JPanel loginPanel = new JPanel();
    private final JLabel loginLabel = new JLabel("Login");

    private final JLabel usernameLabel = new JLabel("Username:");
    private final JTextField usernameField = new JTextField(20);

    private final JLabel passwordLabel = new JLabel("Password:");
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

        loginButton.addActionListener(_-> {
            try {
                loginButtonActionPerformed();
            } catch (NoSuchAlgorithmException error) {
                JOptionPane.showMessageDialog(loginPanel, "An error occurred: " + error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("An error occurred: "+ error.getLocalizedMessage());
            }
        });

        registerButton.addActionListener(_ -> {
            registerButtonActionPerformed();
        });

        InputMap inputMap = loginPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = loginPanel.getActionMap();

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

        GroupLayout panelLayout = new GroupLayout(loginPanel);
        loginPanel.setLayout(panelLayout);

        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(0, 100, Short.MAX_VALUE)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(usernameLabel)
                                        .addComponent(usernameField)
                                        .addComponent(passwordLabel)
                                        .addComponent(passwordField)
                                        .addComponent(loginLabel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addGroup(panelLayout.createSequentialGroup()
                                                .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(100, Short.MAX_VALUE)
                        )
        );

        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGap(0, 100, Short.MAX_VALUE)
                                .addComponent(loginLabel)
                                .addGap(30, 30, 30)
                                .addComponent(usernameLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(passwordLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(loginButton)
                                        .addComponent(registerButton))
                                .addContainerGap(100, Short.MAX_VALUE)
                        )
        );

        getContentPane().add(loginPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void loginButtonActionPerformed() throws NoSuchAlgorithmException {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        UserController userController = new UserController();
        User user = userController.getUser(username);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!PasswordUtil.verifyPassword(user.getPassword(), password)) {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Profile profile = getOrCreateProfile(user);

            if (profile == null) {
                JOptionPane.showMessageDialog(this, "Login failed: Internal error", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (userController.login(user)) {
                    SwingUtilities.invokeLater(() -> new Home(user, profile).setVisible(true));
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Login failed: Internal error", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private Profile getOrCreateProfile(User user) {
        ProfileController profileController = new ProfileController();
        Profile profile = profileController.getProfile(user.getId());

        if (profile == null && profileController.create(user.getId(), user.getUsername(), null, null)) {
            profile = profileController.getProfile(user.getId());
        }

        return profile;
    }

    private void registerButtonActionPerformed() {
        SwingUtilities.invokeLater(() -> new Register().setVisible(true));
        this.dispose();
    }
}
