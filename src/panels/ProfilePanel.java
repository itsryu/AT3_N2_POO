package panels;

import models.Profile;
import models.User;
import utils.DateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public final class ProfilePanel extends JPanel {
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

        // profile picture
        setProfilePicture(profile.getProfilePicture());

        // username and bio
        usernameLabel = new JLabel(profile.getUsername());
        bioLabel = new JLabel(profile.getBio());

        // account creation date
        String accountCreatedAt = DateUtil.formatLocalDateTime(user.getCreatedAt(), "dd/MM/yyyy HH:mm:ss");
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

    private void setProfilePicture(InputStream profilePicture) {
        BufferedImage bufferedImage;

        try {
            if (profilePicture == null || profilePicture.available() == 0) {
                bufferedImage = ImageIO.read(new File("assets/profile.png"));
            } else {
                bufferedImage = ImageIO.read(profilePicture);
            }

            int diameter = 100;
            BufferedImage circleBuffer = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circleBuffer.createGraphics();

            g2.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
            g2.drawImage(bufferedImage, 0, 0, diameter, diameter, null);
            g2.dispose();

            profilePictureLabel.setIcon(new ImageIcon(circleBuffer));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to process the profile picture.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editProfile() {
        JLabel usernameLabel = new JLabel("Profile Username:" );
        JTextField usernameField = new JTextField(profile.getUsername());
        JLabel bioLabel = new JLabel("Bio:");
        JTextField bioField = new JTextField(profile.getBio());
        JButton changePictureButton = getJButton();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(bioLabel);
        panel.add(bioField);
        panel.add(changePictureButton);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Profile", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            profile.setUsername(usernameField.getText());
            profile.setBio(bioField.getText());

            if (profile.editProfile(profile)) {
                usernameLabel.setText(profile.getUsername());
                bioLabel.setText(profile.getBio());

                this.bioLabel.setText(profile.getBio());
                this.usernameLabel.setText(profile.getUsername());

                JOptionPane.showMessageDialog(this, "Profile updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JButton getJButton() {
        JButton changePictureButton = new JButton("Change Picture");

        changePictureButton.addActionListener(_ -> {
            JFileChooser fileChooser = getJFileChooser();
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try (FileInputStream fileInputStream = new FileInputStream(selectedFile)) {
                    byte[] imageData = fileInputStream.readAllBytes();
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
                    setProfilePicture(byteArrayInputStream);

                    if (profile.editProfilePicture(profile, new ByteArrayInputStream(imageData))) {
                        profile.setProfilePicture(new ByteArrayInputStream(imageData));
                        JOptionPane.showMessageDialog(this, "Profile picture updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update profile picture", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException error) {
                    error.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to read the file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return changePictureButton;
    }

    private static JFileChooser getJFileChooser() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) return true;

                String name = file.getName().toLowerCase();

                return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "Image Files (*.jpg, *.jpeg, *.png)";
            }
        });
        return fileChooser;
    }
}