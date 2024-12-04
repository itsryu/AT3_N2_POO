package models;

import java.time.LocalDateTime;

public class Friendship {
    private int friendshipId;
    private int userIdRequester;
    private int userIdReceiver;
    private FriendshipStatus friendshipStatus;
    private LocalDateTime requestDate;
    private LocalDateTime acceptanceDate;

    public enum FriendshipStatus {
        PENDING, ACCEPTED, BLOCKED;
    }

    public Friendship() {}

    public Friendship(int friendshipId, int userIdRequester, int userIdReceiver, FriendshipStatus friendshipStatus, LocalDateTime requestDate, LocalDateTime acceptanceDate) {
        this.friendshipId = friendshipId;
        this.userIdRequester = userIdRequester;
        this.userIdReceiver = userIdReceiver;
        this.friendshipStatus = friendshipStatus;
        this.requestDate = requestDate;
        this.acceptanceDate = acceptanceDate;
    }

    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }

    public int getUserIdRequester() {
        return userIdRequester;
    }

    public void setUserIdRequester(int userIdRequester) {
        this.userIdRequester = userIdRequester;
    }

    public int getUserIdReceiver() {
        return userIdReceiver;
    }

    public void setUserIdReceiver(int userIdReceiver) {
        this.userIdReceiver = userIdReceiver;
    }

    public FriendshipStatus getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateTime getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(LocalDateTime acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "friendshipId=" + friendshipId +
                ", userIdRequester=" + userIdRequester +
                ", userIdReceiver=" + userIdReceiver +
                ", friendshipStatus=" + friendshipStatus +
                ", requestDate=" + requestDate +
                ", acceptanceDate=" + acceptanceDate +
                '}';
    }
}

