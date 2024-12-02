package dao;

import models.Profile;
import models.UserProfile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class ProfileDAO {
    private final Connection connection;

    public ProfileDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createProfile(Profile profile) {
        String query = "INSERT INTO profiles (id_user, username, profile_picture, bio) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, profile.getUserId());
            stmt.setString(2, profile.getUsername());
            stmt.setBinaryStream(3, null);
            stmt.setString(4, null);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long profileId = generatedKeys.getLong(1);
                        profile.setId((int) profileId);
                    } else {
                        throw new SQLException("Creating user profile failed, no ID was obtained.");
                    }
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Error creating user profile: " + e.getMessage());
        }

        return false;
    }

    public Profile getProfileByUserId(int userId) {
        String query = "SELECT * FROM profiles WHERE id_user = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);

            stmt.executeQuery();

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserProfile(rs.getInt("id_profile"), rs.getInt("id_user"), rs.getString("username"), rs.getString("bio"), rs.getBinaryStream("profile_picture"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user profile: " + e.getMessage());
        }

        return null;
    }

    public boolean editProfilePicture(Profile profile, InputStream profilePicture) {
        String query = "UPDATE profiles SET profile_picture = ? WHERE id_user = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            if (profilePicture.available() > 0) {
                stmt.setBinaryStream(1, profilePicture, profilePicture.available());
                stmt.setInt(2, profile.getUserId());

                int affectedRows = stmt.executeUpdate();

                return affectedRows > 0;
            } else {
                System.err.println("Profile picture InputStream is empty.");
                return false;
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error editing user profile picture: " + e.getMessage());
        }

        return false;
    }

    public boolean editProfile(Profile profile) {
        String query = "UPDATE profiles SET username = ?, bio = ? WHERE id_user = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, profile.getUsername());
            stmt.setString(2, profile.getBio());
            stmt.setInt(3, profile.getUserId());

            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error editing user profile: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteProfile(int userId) {
        String query = "DELETE FROM profiles WHERE id_user = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);

            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting user profile: " + e.getMessage());
        }

        return false;
    }
}
