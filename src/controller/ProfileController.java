package controller;

import dao.ProfileDAO;
import models.Profile;
import utils.DatabaseUtil;

public class ProfileController {
    private final ProfileDAO profileDAO;

    public ProfileController() {
        this.profileDAO = new ProfileDAO(DatabaseUtil.getConnection());
    }

    public Profile getProfile(int userId) {
        return profileDAO.getProfileByUserId(userId);
    }

    public boolean create(int userId, String username, String bio, String profilePicture) {
        Profile profile = new Profile(0, userId, username, bio, profilePicture);

        return profileDAO.createProfile(profile);
    }

    public boolean edit(Profile profile) {
        return profileDAO.editProfile(profile);
    }
}
