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
        String query = "INSERT INTO sessoes (id_usuario, token, hora_entrada) VALUES (?, ?, ?)";

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
        String query = "SELECT * FROM sessoes WHERE id_usuario = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);

            stmt.executeQuery();

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    return new Session(rs.getInt("id_sessao"), rs.getInt("id_usuario"), rs.getString("token"), rs.getTimestamp("hora_entrada"), rs.getTimestamp("hora_saida"));
                }
            }
        } catch(SQLException error) {
            System.err.println("Error getting user session: " + error.getMessage());
        }

        return null;
    }

    public boolean updateSession(Session session) {
        String query = "UPDATE sessoes SET token = ?, hora_entrada = ?, hora_saida = ? WHERE id_sessao = ?";

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
}
