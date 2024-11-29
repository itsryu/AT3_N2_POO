package controller;

import dao.UserDAO;
import models.RegularUser;
import models.User;
import utils.DBConnection;

public class UserController {
    private final UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO(DBConnection.getConnection());
    }

    public User getUser(String input) {
        if(userDAO.getUserByUsername(input) != null) {
            return userDAO.getUserByUsername(input);
        }

        if (userDAO.getUserByEmail(input) != null) {
            System.out.println("User found by email");
            return userDAO.getUserByEmail(input);
        }

        return null;
    }

    public boolean register(String username, String email, String password) {
        User user = new RegularUser(0, username, email, password);
        return userDAO.createUser(user);
    }
}