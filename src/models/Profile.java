package models;

public class Profile {
    private final String bio;
    private final String profilePicture;

    public Profile(String bio, String profilePicture) {
        this.bio = bio;
        this.profilePicture = profilePicture;
    }

    public String getBio() {
        return this.bio;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }
}