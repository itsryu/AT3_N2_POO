package view;

import controller.UserController;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import models.Profile;
import models.User;
import panels.HomePanel;
import panels.NavigationPanel;
import panels.ProfilePanel;
import panels.SettingsPanel;

public final class Home extends JFrame {
    private final User user;
    private final Profile profile;

    private final NavigationPanel navigationPanel = new NavigationPanel();
    private final JPanel contentPanel = new JPanel(new CardLayout());
    private final JButton logoutButton = new JButton("Logout");

    public Home(User user, Profile profile) {
        this.user = user;
        this.profile = profile;

        initComponents();
    }

    private void initComponents() {
        setTitle("Homepage");
        setPreferredSize(new Dimension(1200, 800));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addContentPanels();

        navigationPanel.setNavigationListener(destination -> {
            CardLayout card = (CardLayout) (contentPanel.getLayout());
            card.show(contentPanel, destination);
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navigationPanel, contentPanel);
        splitPane.setDividerLocation(200);
        splitPane.setEnabled(false);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(splitPane)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(logoutButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(splitPane)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logoutButton)
                        .addContainerGap()
        );

        logoutButton.addActionListener(_ -> logoutButtonActionPerformed());

        InputMap inputMap = navigationPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = navigationPanel.getActionMap();

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

        pack();
        setLocationRelativeTo(null);
    }

    private void addContentPanels() {
        contentPanel.add(new HomePanel(user, profile), "Home");
        contentPanel.add(new ProfilePanel(user, profile), "Profile");
        contentPanel.add(new SettingsPanel(user), "Settings");
    }

    private void logoutButtonActionPerformed() {
        UserController userController = new UserController();

        if (userController.logout(user)) {
            JOptionPane.showMessageDialog(this, "Logout successful");
        } else {
            JOptionPane.showMessageDialog(this, "Internal Error", "Error", JOptionPane.ERROR_MESSAGE);
        }

        SwingUtilities.invokeLater(() -> new Login().setVisible(true));

        dispose();
    }
}