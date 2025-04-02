import java.io.*;
import java.util.*;
import java.util.logging.*;

public final class User {

    private static final Logger logger = Logger.getLogger(User.class.getName());

    // Load users from users.txt into a Map
    private static Map<String, String> loadUsers(File userFile) {
        Map<String, String> userMap = new HashMap<>();
        if (!userFile.exists()) return userMap;

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    userMap.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading users file", e);
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

            File userFolder = new File(userFile.getParent(), username);

            if (choice == 1) {
                if (users.containsKey(username) && users.get(username).equals(password)) {
                    System.out.println("✅ Login successful!");
                    return userFolder.getAbsolutePath();
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
                        logger.log(Level.SEVERE, "Error writing to users file", e);
                    }

                    if (!userFolder.exists()) {
                        userFolder.mkdirs();
                    }

                    File dataFile = new File(userFolder, "data.txt");
                    File counterFile = new File(userFolder, "counter.txt");

                    try {
                        if (!dataFile.exists()) dataFile.createNewFile();
                        if (!counterFile.exists()) {
                            try (FileWriter writer = new FileWriter(counterFile)) {
                                writer.write("0");
                            }
                        }
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "Error initializing user files", e);
                    }

                    System.out.println("🎉 Registered successfully!");
                    return userFolder.getAbsolutePath();
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
            logger.log(Level.SEVERE, "Unexpected error during login/register", e);
            return null;
        }
    }
}
