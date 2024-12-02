package models;

import controller.ProfileController;

import java.io.InputStream;

public class UserProfile extends Profile {
    ProfileController profileController = new ProfileController();

    public UserProfile(int id, int userId, String username, String bio, InputStream profilePicture) {
        super(id, userId, username, bio, profilePicture);
    }

    @Override
    public boolean editProfile(Profile profile) {
        return profileController.edit(profile);
    }

    @Override
    public boolean editProfilePicture(Profile profile, InputStream profilePicture) {
        return profileController.editProfilePicture(profile, profilePicture);
    }

    @Override
    public boolean createProfile(int userId, String username, String bio, InputStream profilePicture) {
        return profileController.create(userId, username, bio, profilePicture);
    }
}
