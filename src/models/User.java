package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private LocalDate birthDate;
    private LocalDateTime createdAt;

    public User(int id, String username, String email, String password, LocalDate birthDate, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public abstract boolean login(User user);
    public abstract boolean logout(User user);
    public abstract boolean deleteUser(User user);
    public abstract boolean editUser(User user);
    public abstract boolean registerUser(String username, String email, String password, LocalDate birthDate);
}