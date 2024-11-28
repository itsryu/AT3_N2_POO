package dao;

import models.RegularUser;
import models.User;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createUser(User user) {
        String query = "INSERT INTO usuarios (nome_usuario, email, senha) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long userId = generatedKeys.getLong(1);
                        System.out.println("User created with ID: " + userId);
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
        String query = "SELECT * FROM usuarios WHERE nome_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            stmt.executeQuery();

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new RegularUser(rs.getInt("id_usuario"), rs.getString("nome_usuario"), rs.getString("email"), rs.getString("senha"));
                }
            }
        } catch(SQLException e) {
            System.err.println("Error getting user by username: " + e.getMessage());
        }

        return null;
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM usuarios WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);

            stmt.executeQuery();

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new RegularUser(rs.getInt("id_usuario"), rs.getString("nome_usuario"), rs.getString("email"), rs.getString("senha"));
                }
            }
        } catch(SQLException e) {
            System.err.println("Error getting user by email: " + e.getMessage());
        }

        return null;
    }
}
