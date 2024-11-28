package models;

public class Profile {
    private final String bio;

    public Profile(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return this.bio;
    }
}