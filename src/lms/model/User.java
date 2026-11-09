package lms.model;

public abstract class User {
    private static int idCounter = 1;
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public String getFullName() { return firstName + " " + lastName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public abstract void displayInfo();

    @Override
    public String toString() {
        return String.format("[ID:%d] %s %s | Email: %s | Role: %s",
                id, firstName, lastName, email, role);
    }
}
