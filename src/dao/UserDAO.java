package dao;

import java.sql.*;

import models.RegularUser;
import models.User;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createUser(User user) {
        String query = "INSERT INTO users (username, email, password, birth_date, created_at) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setDate(4, Date.valueOf(user.getBirthDate()));
            stmt.setTimestamp(5, Timestamp.valueOf(user.getCreatedAt()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long userId = generatedKeys.getLong(1);
                        user.setId((int) userId);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }

            return true;
        } catch(SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }

        return false;
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            stmt.executeQuery();

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                        return new RegularUser(rs.getInt("id_user"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getDate("birth_date").toLocalDate(), rs.getTimestamp("created_at").toLocalDateTime());
                }
            }
        } catch(SQLException e) {
            System.err.println("Error getting user by username: " + e.getMessage());
        }

        return null;
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);

            stmt.executeQuery();

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new RegularUser(rs.getInt("id_user"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getDate("birth_date").toLocalDate(), rs.getTimestamp("created_at").toLocalDateTime());
                }
            }
        } catch(SQLException e) {
            System.err.println("Error getting user by email: " + e.getMessage());
        }

        return null;
    }

    public boolean updateUser(User user) {
        String query = "UPDATE users SET username = ?, email = ?, password = ?, birth_date = ? WHERE id_user = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setDate(4, Date.valueOf(user.getBirthDate()));
            stmt.setInt(5, user.getId());

            stmt.executeUpdate();

            return true;
        } catch(SQLException e) {
            System.err.println("Error editing user: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteUser(int userId) {
        String deleteSessionsQuery = "DELETE FROM sessions WHERE id_user = ?";
        String deleteProfileQuery = "DELETE FROM profiles WHERE id_user = ?";
        String deleteUserQuery = "DELETE FROM users WHERE id_user = ?";

        try (PreparedStatement deleteSessionsStmt = connection.prepareStatement(deleteSessionsQuery);
             PreparedStatement deleteProfileStmt = connection.prepareStatement(deleteProfileQuery);
             PreparedStatement deleteUserStmt = connection.prepareStatement(deleteUserQuery)) {

            // delete sessions
            deleteSessionsStmt.setInt(1, userId);
            deleteSessionsStmt.executeUpdate();

            // delete profile
            deleteProfileStmt.setInt(1, userId);
            deleteProfileStmt.executeUpdate();

            // delete user
            deleteUserStmt.setInt(1, userId);
            deleteUserStmt.executeUpdate();

            return true;
        } catch(SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }

        return false;
    }
}
