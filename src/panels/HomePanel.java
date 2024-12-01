package panels;

import models.Profile;
import models.User;

import javax.swing.*;

public class HomePanel extends JPanel {
    private User user;
    private Profile profile;

    public HomePanel(User user, Profile profile) {
        this.user = user;
        this.profile = profile;

        initComponents();
    }

    private void initComponents() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel welcomeLabel = new JLabel("Home Page!");
        JLabel descriptionLabel = new JLabel("Welcome " + profile.getUsername() + " to the Home Page!");

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(welcomeLabel)
                        .addComponent(descriptionLabel)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(welcomeLabel)
                        .addComponent(descriptionLabel)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
    }
}
