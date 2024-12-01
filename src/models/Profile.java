package models;

public class Profile {
    private Integer id;
    private Integer userId;
    private String username;
    private String profilePicture;
    private String bio;

    public Profile(int id, int userId, String username, String bio, String profilePicture) {
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

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }
}