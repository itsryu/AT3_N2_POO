package models;

import controller.UserController;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdminUser extends User {
    UserController userController = new UserController();

    public AdminUser(int id, String username, String email, String password, LocalDate birthDate, LocalDateTime createdAt) {
        super(id, username, email, password, birthDate, createdAt);
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
