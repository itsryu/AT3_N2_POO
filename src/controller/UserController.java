package controller;

import dao.UserDAO;
import models.RegularUser;
import models.User;
import utils.DatabaseUtil;
import utils.Util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserController {
    private final UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO(DatabaseUtil.getConnection());
    }

    public User getUser(String input) {
        if(userDAO.getUserByUsername(input) != null) {
            return userDAO.getUserByUsername(input);
        }

        if (userDAO.getUserByEmail(input) != null) {
            return userDAO.getUserByEmail(input);
        }

        return null;
    }

    public boolean login(User user) {
        String token = Util.generateToken();
        Timestamp entryTimestamp = Timestamp.valueOf(LocalDateTime.now());
        SessionController sessionController = new SessionController();

        return sessionController.login(user.getId(), token, entryTimestamp);
    }

    public boolean logout(User user) {
        Timestamp exitTimestamp = Timestamp.valueOf(LocalDateTime.now());
        SessionController sessionController = new SessionController();

        return sessionController.logout(user.getId(), exitTimestamp);
    }

    public boolean delete(User user) {
        return userDAO.deleteUser(user.getId());
    }

    public boolean edit(User user) {
        return userDAO.updateUser(user);
    }

    public boolean register(String username, String email, String password, LocalDate birthDate) {
        LocalDateTime createdAt = LocalDateTime.now();
        User user = new RegularUser(0, username, email, password, birthDate, createdAt);

        return userDAO.createUser(user);
    }
}