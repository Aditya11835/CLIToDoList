import java.io.*;
import java.util.*;

public class User {

    // Load users from users.txt into a Map
    public static Map<String, String> loadUsers(File userFile) {
        Map<String, String> userMap = new HashMap<>();
        if (!userFile.exists()) return userMap;

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    userMap.put(parts[0], parts[1]); // username -> password
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userMap;
    }

    // Handle login or register prompt
    public static String loginOrRegister(Scanner sc, File userFile) {
        Map<String, String> users = loadUsers(userFile);

        try {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Enter username: ");
            String username = sc.nextLine().trim();

            if (username.length() < 8 || username.contains(" ")) {
                System.out.println("❌ Username must be at least 8 characters long and contain no spaces.");
                return null;
            }

            System.out.print("Enter password: ");
            String password = sc.nextLine().trim();

            if (password.length() < 8 || password.contains(" ") || !password.matches("[a-zA-Z0-9@]+")) {
                System.out.println("❌ Password must be at least 8 characters long, contain no spaces, and only include letters, digits, or '@'.");
                return null;
            }

            if (choice == 1) {
                if (users.containsKey(username) && users.get(username).equals(password)) {
                    System.out.println("✅ Login successful!");
                    return username;
                } else {
                    System.out.println("❌ Invalid credentials.");
                    return null;
                }
            } else if (choice == 2) {
                if (users.containsKey(username)) {
                    System.out.println("❗ User already exists.");
                    return null;
                } else {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
                        writer.write(username + "|" + password);
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("🎉 Registered successfully!");
                    return username;
                }
            } else {
                System.out.println("Invalid option.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a number (1 or 2).");
            return null;
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred.");
            e.printStackTrace();
            return null;
        }
    }
}
