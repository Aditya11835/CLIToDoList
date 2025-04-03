//start of app.java

//package CLIToDoList;

import java.io.*;
import java.util.*;
import java.util.Base64;
import java.util.logging.*;

public class App {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        //file location wj=here the data is being stored
        final String basePath = System.getProperty("user.home") + File.separator +
                "Projects" + File.separator +
                "Java" + File.separator +
                "CLIToDoList" + File.separator +
                "Data Files";
        
        //if file does not exist, create new file with filename as in path
        File dataDir = new File(basePath);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        //creates users.txt where user credentials will stored
        File userFile = new File(dataDir, "users.txt");
        Scanner sc = new Scanner(System.in);

        //login menu
        while (true) {
            String userFolderPath = null;
            while (userFolderPath == null) {
                userFolderPath = User.loginOrRegister(sc, userFile);
            }

            //create user file if not exist
            File userDir = new File(userFolderPath);
            if (!userDir.exists()) {
                userDir.mkdirs();
            }

            File dataFile = new File(userDir, "data.txt");
            File counterFile = new File(userDir, "counter.txt");

            if (!counterFile.exists()) {
                try (FileWriter writer = new FileWriter(counterFile)) {
                    writer.write("0");
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error creating counter file", e);
                }
            }

            //counter for tasks created (serves as uid)
            int savedCount = 0;
            try (BufferedReader counterReader = new BufferedReader(new FileReader(counterFile))) {
                String counterLine = counterReader.readLine();
                if (counterLine != null && !counterLine.isEmpty()) {
                    savedCount = Integer.parseInt(counterLine.trim());
                }
            } catch (IOException | NumberFormatException e) {
                logger.log(Level.SEVERE, "Error reading counter file", e);
            }

            //this file stores tasks in form of hash (ensures security)
            if (!dataFile.exists()) {
                try {
                    dataFile.createNewFile();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error creating data file", e);
                }
            }

            try (BufferedReader dataReader = new BufferedReader(new FileReader(dataFile))) {
                String dataLine;
                while ((dataLine = dataReader.readLine()) != null) {
                    if (!dataLine.isEmpty()) {
                        String decodedLine;
                        try {
                            decodedLine = new String(Base64.getDecoder().decode(dataLine));
                        } catch (IllegalArgumentException e) {
                            decodedLine = dataLine;
                        }
                        Data.addTask(Task.parseFromLine(decodedLine));
                    }
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error reading data file", e);
            }

            //updates task count if any changes made
            Task.setCount(Math.max(Task.getCount(), savedCount));
            Data.categorizeTasks();

            //ui header stylish

            System.out.println("████████╗ ██████╗       ██████╗  ██████╗     ██╗     ██╗███████╗████████╗");
            System.out.println("╚══██╔══╝██╔═══██╗      ██╔══██╗██╔═══██╗    ██║     ██║██╔════╝╚══██╔══╝");
            System.out.println("   ██║   ██║   ██║█████╗██║  ██║██║   ██║    ██║     ██║███████╗   ██║   ");
            System.out.println("   ██║   ██║   ██║╚════╝██║  ██║██║   ██║    ██║     ██║╚════██║   ██║   ");
            System.out.println("   ██║   ╚██████╔╝      ██████╔╝╚██████╔╝    ███████╗██║███████║   ██║   ");
            System.out.println("   ╚═╝    ╚═════╝       ╚═════╝  ╚═════╝     ╚══════╝╚═╝╚══════╝   ╚═╝   ");
            System.out.println();

            int choice = 0;

            //user interactive choice menu
            while (choice != 6) {
                System.out.println(
                        """
                                ==============================
                                || 1. Add Task             ||
                                || 2. View Tasks           ||
                                || 3. Delete Task          ||
                                || 4. Mark as Completed    ||
                                || 5. Switch User / Log Out||
                                || 6. Exit                 ||
                                ==============================""");
                System.out.print("Enter your choice: ");

                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    sc.nextLine();
                } else {
                    sc.nextLine();
                    System.out.println("Please enter a valid number.");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        Task temp = inputFunc(sc);
                        Data.addTask(temp);
                        saveTasksToFile(dataFile);
                        saveCounter(counterFile);
                        System.out.println("Task added and saved successfully.");
                    }
                    case 2 -> Data.displayTasks(sc);
                    case 3 -> {
                        Data.deleteTask();
                        saveTasksToFile(dataFile);
                        saveCounter(counterFile);
                        System.out.println("Task deleted and removed successfully.");
                    }
                    case 4 -> {
                        Data.markCompleteTask();
                        saveTasksToFile(dataFile);
                        saveCounter(counterFile);
                        System.out.println("Operation successful!");
                    }
                    case 5 -> {
                        saveTasksToFile(dataFile);
                        saveCounter(counterFile);
                        Data.getAllTasks().clear();
                        System.out.println("Logged out. Switching user...");
                        choice = 6; // exit current session loop
                    }
                    case 6 -> {
                        saveTasksToFile(dataFile);
                        saveCounter(counterFile);
                        System.out.println("Exiting To Do List App. Goodbye!");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    //writes task into data.txt
    private static void saveTasksToFile(File dataFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Task task : Data.getAllTasks()) {
                String line = task.getId() + "|" + task.getTaskName() + "|" + task.getCategory() + "|" + task.isCompleted() + "|" + task.getDueDate();
                String encrypted = Base64.getEncoder().encodeToString(line.getBytes());
                writer.write(encrypted);
                writer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving tasks to file", e);
        }
    }

    //updates counter upon an action from user
    private static void saveCounter(File counterFile) {
        try (FileWriter writer = new FileWriter(counterFile)) {
            writer.write(String.valueOf(Task.getCount()));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving task counter", e);
        }
    }

    //user input for task details
    private static Task inputFunc(Scanner sc) {
        System.out.print("Enter name of Task: ");
        String taskName = sc.nextLine();

        int category;
        while (true) {
            System.out.print("Enter 0 for Urgent, 1 for Business, or 2 for Personal: ");
            try {
                category = Integer.parseInt(sc.nextLine().trim());
                if (category >= 0 && category <= 2) break;
                else System.out.println("Invalid category. Please enter 0, 1, or 2.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number (0, 1, or 2).\n");
            }
        }

        String dueDate;
        System.out.print("Enter due date? 1 for Yes, any other number for No: ");
        int flag;
        try {
            flag = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            flag = 0;
        }

        if (flag == 1) {
            while (true) {
                System.out.print("Enter due date in format DD-MM-YYYY: ");
                dueDate = sc.nextLine().trim();
                if (isValidDateFormat(dueDate)) break;
                else System.out.println("Invalid date format. Try again.");
            }
        } else {
            dueDate = "N/A";
        }

        return new Task(taskName, category, dueDate);
    }

    //checks date is in correct format
    private static boolean isValidDateFormat(String date) {
        if (date == null || date.length() != 10 || date.charAt(2) != '-' || date.charAt(5) != '-') return false;

        try {
            int day = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(3, 5));
            int year = Integer.parseInt(date.substring(6, 10));

            if (month < 1 || month > 12 || day < 1 || year < 1000 || year > 9999) return false;

            int[] daysInMonth = { 31, (isLeapYear(year) ? 29 : 28), 31, 30, 31, 30,
                    31, 31, 30, 31, 30, 31 };
            return day <= daysInMonth[month - 1];
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}


//end of app.java