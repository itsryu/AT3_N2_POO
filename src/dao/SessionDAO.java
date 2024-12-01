package dao;

import models.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAO {
    private final Connection connection;

    public SessionDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createSession(Session session) {
        String query = "INSERT INTO sessions (id_user, token, entry_timestamp) VALUES (?, ?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, session.getUserId());
            stmt.setString(2, session.getToken());
            stmt.setTimestamp(3, session.getEntryTimestamp());

            int affectedRows = stmt.executeUpdate();

            if(affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long sessionId = generatedKeys.getLong(1);
                        session.setId((int) sessionId);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
            return true;
        } catch(SQLException error) {
            System.err.println("Error creating user session: " + error.getMessage());
        }

        return false;
    }

    public Session getSessionByUserId(int userId) {
        String query = "SELECT * FROM sessions WHERE id_user = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);

            stmt.executeQuery();

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    return new Session(rs.getInt("id_session"), rs.getInt("id_user"), rs.getString("token"), rs.getTimestamp("entry_timestamp"), rs.getTimestamp("exit_timestamp"));
                }
            }
        } catch(SQLException error) {
            System.err.println("Error getting user session: " + error.getMessage());
        }

        return null;
    }

    public boolean updateSession(Session session) {
        String query = "UPDATE sessions SET token = ?, entry_timestamp = ?, exit_timestamp = ? WHERE id_session = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, session.getToken());
            stmt.setTimestamp(2, session.getEntryTimestamp());
            stmt.setTimestamp(3, session.getExitTimestamp());
            stmt.setInt(4, session.getId());

            stmt.executeUpdate();

            return true;
        } catch(SQLException error) {
            System.err.println("Error updating user session: " + error.getMessage());
        }

        return false;
    }

    public boolean deleteSession(Session session) {
        String query = "DELETE FROM sessions WHERE id_session = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, session.getId());

            stmt.executeUpdate();

            return true;
        } catch(SQLException error) {
            System.err.println("Error deleting user session: " + error.getMessage());
        }

        return false;
    }
}
