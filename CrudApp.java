import java.util.*; // Import utilities for List, Scanner, etc.

/**
 * A simple CRUD (Create, Read, Update, Delete) console application in Java.
 * This example manages a list of "Users" with id, name, and email.
 */
public class CrudApp {

    // A small "User" class to represent each record in our CRUD system.
    static class User {
        int id;
        String name;
        String email;

        // Constructor
        User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // Helper method to display user info as text
        @Override
        public String toString() {
            return "ID: " + id + " | Name: " + name + " | Email: " + email;
        }
    }

    // This list will act as our "database" (in-memory storage)
    static List<User> users = new ArrayList<>();

    // A counter to generate unique IDs for new users
    static int nextId = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Main menu loop
        do {
            System.out.println("\n=== SIMPLE CRUD APPLICATION ===");
            System.out.println("1. Create User");
            System.out.println("2. Read (List Users)");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            // Read the user's menu choice
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    createUser(scanner);
                    break;
                case 2:
                    readUsers();
                    break;
                case 3:
                    updateUser(scanner);
                    break;
                case 4:
                    deleteUser(scanner);
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select from 1–5.");
            }
        } while (choice != 5);

        scanner.close();
    }

    // CREATE: Add a new user
    private static void createUser(Scanner scanner) {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();

        System.out.print("Enter user email: ");
        String email = scanner.nextLine();

        // Create a new User object and add it to the list
        User newUser = new User(nextId++, name, email);
        users.add(newUser);

        System.out.println("User added successfully: " + newUser);
    }

    // READ: Display all users
    private static void readUsers() {
        System.out.println("\n=== USER LIST ===");
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User u : users) {
                System.out.println(u);
            }
        }
    }

    // UPDATE: Modify an existing user's name or email
    private static void updateUser(Scanner scanner) {
        System.out.print("Enter the ID of the user to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Find the user by ID
        User userToUpdate = null;
        for (User u : users) {
            if (u.id == id) {
                userToUpdate = u;
                break;
            }
        }

        if (userToUpdate == null) {
            System.out.println("User not found!");
            return;
        }

        // Ask for new data
        System.out.print("Enter new name (leave blank to keep current): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            userToUpdate.name = newName;
        }

        System.out.print("Enter new email (leave blank to keep current): ");
        String newEmail = scanner.nextLine();
        if (!newEmail.isEmpty()) {
            userToUpdate.email = newEmail;
        }

        System.out.println("User updated successfully: " + userToUpdate);
    }

    // DELETE: Remove a user by ID
    private static void deleteUser(Scanner scanner) {
        System.out.print("Enter the ID of the user to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Use Iterator to safely remove an item while looping
        Iterator<User> iterator = users.iterator();
        boolean found = false;

        while (iterator.hasNext()) {
            User u = iterator.next();
            if (u.id == id) {
                iterator.remove();
                found = true;
                System.out.println("User deleted successfully!");
                break;
            }
        }

        if (!found) {
            System.out.println("User not found!");
        }
    }
}
