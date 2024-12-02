package models;

import controller.UserController;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegularUser extends User {
    UserController userController = new UserController();

    public RegularUser(int id, String username, String email, String password, LocalDate birhtDate, LocalDateTime createdAt) {
        super(id, username, email, password, birhtDate, createdAt);
    }

    @Override
    public boolean login(User user) {
        return userController.login(user);
    }

    public boolean logout(User user) {
        return userController.logout(user);
    }

    public boolean deleteUser(User user) {
        return userController.delete(user);
    }

    public boolean editUser(User user) {
        return userController.edit(user);
    }

    @Override
    public boolean registerUser(String username, String email, String password, LocalDate birthDate) {
        return userController.register(username, email, password, birthDate);
    }
}
