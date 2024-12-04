package dao;

import models.Friendship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FriendshipDAO {
    private final Connection connection;

    public FriendshipDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createFriendship(int userIdRequester, int userIdReceiver) {
        final String query = "INSERT INTO friendships (id_user_requester, id_user_receiver) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS))  {
            stmt.setInt(1, userIdRequester);
            stmt.setInt(2, userIdReceiver);

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch(SQLException e) {
            System.err.println("Error creating friendship: " + e.getMessage());
        }

        return false;
    }

    public boolean updateFriendship(Friendship.FriendshipStatus status, int userIdRequester, int userIdReceiver) {
        final String query = "UPDATE friendships SET accepted = ? WHERE id_user_requester = ? AND id_user_receiver = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(status));
            stmt.setInt(2, userIdRequester);
            stmt.setInt(3, userIdReceiver);

            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch(SQLException e) {
            System.err.println("Error updating friendship: " + e.getMessage());
        }

        return false;
    }
}
