package models;

import java.io.InputStream;

public abstract class Profile {
    private Integer id;
    private Integer userId;
    private String username;
    private InputStream profilePicture;
    private String bio;

    public Profile(int id, int userId, String username, String bio, InputStream profilePicture) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.bio = bio;
        this.profilePicture = profilePicture;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return this.bio;
    }

    public void setProfilePicture(InputStream profilePicture) {
        this.profilePicture = profilePicture;
    }

    public InputStream getProfilePicture() {
        return this.profilePicture;
    }

    public abstract boolean createProfile(int userId, String username, String bio, InputStream profilePicture);
    public abstract boolean editProfile(Profile profile);
    public abstract boolean editProfilePicture(Profile profile, InputStream profilePicture);
}