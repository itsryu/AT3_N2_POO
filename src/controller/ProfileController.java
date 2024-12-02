package controller;

import dao.ProfileDAO;
import models.Profile;
import models.UserProfile;
import utils.DatabaseUtil;

import java.io.InputStream;

public class ProfileController {
    private final ProfileDAO profileDAO;

    public ProfileController() {
        this.profileDAO = new ProfileDAO(DatabaseUtil.getConnection());
    }

    public Profile getProfile(int userId) {
        return profileDAO.getProfileByUserId(userId);
    }

    public boolean create(int userId, String username, String bio, InputStream profilePicture) {
        Profile profile = new UserProfile(0, userId, username, bio, profilePicture);
        return profileDAO.createProfile(profile);
    }

    public boolean edit(Profile profile) {
        return profileDAO.editProfile(profile);
    }

    public boolean editProfilePicture(Profile profile, InputStream profilePicture) {
        return profileDAO.editProfilePicture(profile, profilePicture);
    }
}
