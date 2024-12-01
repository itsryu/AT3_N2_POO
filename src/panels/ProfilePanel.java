package panels;

import controller.ProfileController;
import models.Profile;
import models.User;
import utils.DateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ProfilePanel extends JPanel {
    private final User user;
    private final Profile profile;

    private final JLabel profilePictureLabel = new JLabel();
    private JLabel usernameLabel;
    private JLabel bioLabel;

    private final JButton editButton = new JButton("Edit Profile");

    public ProfilePanel(User user, Profile profile) {
        this.user = user;
        this.profile = profile;

        initComponents();
    }

    private void initComponents() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        String accountCreatedAt = DateUtil.formatLocalDateTime(user.getCreatedAt(), "dd/MM/yyyy HH:mm:ss");

        setProfilePicture(profile.getProfilePicture());

        usernameLabel = new JLabel("Username: " + profile.getUsername());
        bioLabel = new JLabel(profile.getBio());
        JLabel creationDateLabel = new JLabel("Account Created: " + accountCreatedAt);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(profilePictureLabel)
                        .addComponent(usernameLabel)
                        .addComponent(bioLabel)
                        .addComponent(creationDateLabel)
                        .addComponent(editButton)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(profilePictureLabel)
                        .addComponent(usernameLabel)
                        .addComponent(bioLabel)
                        .addComponent(creationDateLabel)
                        .addComponent(editButton)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        editButton.addActionListener(_ -> editProfile());
    }

    private void setProfilePicture(String profilePicturePath) {
        BufferedImage profileImage;

        try {
            if (profilePicturePath == null || profilePicturePath.isEmpty()) {
                profileImage = ImageIO.read(new File("src/assets/profile.png"));
            } else {
                profileImage = ImageIO.read(new File(profilePicturePath));
            }

            int diameter = 100;
            BufferedImage circleBuffer = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circleBuffer.createGraphics();

            g2.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
            g2.drawImage(profileImage, 0, 0, diameter, diameter, null);
            g2.dispose();

            profilePictureLabel.setIcon(new ImageIcon(circleBuffer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editProfile() {
        JTextField usernameField = new JTextField(profile.getUsername());
        JTextField bioField = new JTextField(profile.getBio());
        JButton changePictureButton = getJButton();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Bio:"));
        panel.add(bioField);
        panel.add(changePictureButton);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Profile", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            profile.setUsername(usernameField.getText());
            profile.setBio(bioField.getText());

            ProfileController profileController = new ProfileController();
            boolean isUpdated = profileController.edit(profile);

            if (isUpdated) {
                usernameLabel.setText("Username: " + profile.getUsername());
                bioLabel.setText("Bio: " + profile.getBio());

                JOptionPane.showMessageDialog(this, "Profile updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JButton getJButton() {
        JButton changePictureButton = new JButton("Change Picture");

        changePictureButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".bmp");
                }

                @Override
                public String getDescription() {
                    return "Image Files (*.jpg, *.jpeg, *.png, *.gif, *.bmp)";
                }
            });

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                setProfilePicture(selectedFile.getAbsolutePath());

                profile.setProfilePicture(selectedFile.getAbsolutePath());

                ProfileController profileController = new ProfileController();
                boolean isUpdated = profileController.edit(profile);

                if (isUpdated) {
                    JOptionPane.showMessageDialog(this, "Profile picture updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update profile picture", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return changePictureButton;
    }
}