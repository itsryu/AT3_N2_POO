package panels;

import java.awt.*;
import javax.swing.*;

public class NavigationPanel extends JPanel {
    private final JButton profileButton = new JButton("Profile");
    private final JButton settingsButton = new JButton("Settings");
    private final JButton homeButton = new JButton("Home");

    private NavigationListener navigationListener;

    public NavigationPanel() {
        initComponents();
    }

    private void initComponents() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        Dimension buttonSize = new Dimension(150, 30);
        homeButton.setMaximumSize(buttonSize);
        profileButton.setMaximumSize(buttonSize);
        settingsButton.setMaximumSize(buttonSize);

        homeButton.addActionListener(_ -> navigateTo("Home"));
        profileButton.addActionListener(_ -> navigateTo("Profile"));
        settingsButton.addActionListener(_ -> navigateTo("Settings"));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(homeButton)
                        .addComponent(profileButton)
                        .addComponent(settingsButton)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(homeButton)
                        .addComponent(profileButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(settingsButton)
        );
    }

    private void navigateTo(String destination) {
        if (navigationListener != null) {
            navigationListener.navigateTo(destination);
        }
    }

    public void setNavigationListener(NavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    public interface NavigationListener {
        void navigateTo(String destination);
    }
}