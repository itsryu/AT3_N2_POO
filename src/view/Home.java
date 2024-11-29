package view;

import controller.SessionController;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class Home extends JFrame {
    private final User user;

    private final JPanel homePanel = new JPanel();
    private final JLabel homeLabel = new JLabel("Home");

    private final JButton logoutButton = new JButton("Logout");

    public Home(User user) {
        this.user = user;

        this.initComponents();
    }

    public void initComponents() {
        setTitle("Home");
        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        homeLabel.setFont(new Font("sansserif", Font.BOLD, 24));
        homeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logoutButtonActionPerformed(e);
            }
        });

        GroupLayout homePanelLayout = new GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);

        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(homeLabel)
                .addGap(250, 250, 250))
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(logoutButton)
                .addGap(250, 250, 250))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(homeLabel)
                .addGap(50, 50, 50)
                .addComponent(logoutButton)
                .addGap(50, 50, 50))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(homePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(homePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void logoutButtonActionPerformed(ActionEvent e) {
        Timestamp exitTimestamp = Timestamp.valueOf(LocalDateTime.now());

        SessionController sessionController = new SessionController();

        if(sessionController.logout(user.getId(), exitTimestamp)) {
            JOptionPane.showMessageDialog(this, "Logout successful");
        } else {
            JOptionPane.showMessageDialog(this, "Internal Error", "Error", JOptionPane.ERROR_MESSAGE);
        }

        new Login().setVisible(true);
        this.dispose();
    }
}
