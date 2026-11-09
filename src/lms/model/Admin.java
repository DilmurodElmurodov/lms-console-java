package lms.model;

public class Admin extends User{
    private String adminCode;

        public Admin(String firstName, String lastName, String email, String password) {
            super(firstName, lastName, email, password, Role.ADMIN);
            this.adminCode = "ADM" + String.format("%04d", getId());
        }

        public String getAdminCode() { return adminCode; }

        @Override
        public void displayInfo() {
            System.out.println("=== ADMIN INFO ===");
            System.out.println("Code  : " + adminCode);
            System.out.println("Name  : " + getFullName());
            System.out.println("Email : " + getEmail());
            System.out.println("Role  : ADMINISTRATOR");
        }

        @Override
        public String toString() {
            return String.format("[%s] %s | ADMIN", adminCode, getFullName());
        }
    }