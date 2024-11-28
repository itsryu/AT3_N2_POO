package models;

public class RegularUser extends User {
    public RegularUser(int id, String username, String email, String password) {
        super(id, username, email, password);
    }

    @Override
    public boolean register() {
        System.out.println("Registering user: " + this.getUsername());
        return true;
    }

    @Override
    public boolean login() {
        System.out.println("Logging in user: " + this.getUsername());
        return true;
    }
}
