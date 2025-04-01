//package CLIToDoList;

import java.io.*;
import java.util.*;

public class App {

    public static void main(String[] args) {
        final String basePath = System.getProperty("user.home") + File.separator +
                "Projects" + File.separator +
                "Java" + File.separator +
                "CLIToDoList" + File.separator +
                "Data Files";


        File dataDir = new File(basePath);
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // create if not exists
        }

        File counterFile = new File(dataDir, "counter.txt");
        File dataFile = new File(dataDir, "data.txt");


        // Ensure counter.txt exists
        if (!counterFile.exists()) {
            try (FileWriter writer = new FileWriter(counterFile)) {
                writer.write("0"); // default count
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int savedCount = 0;
        try (BufferedReader counterReader = new BufferedReader(new FileReader(counterFile))) {
            String counterLine = counterReader.readLine();
            if (counterLine != null && !counterLine.isEmpty()) {
                savedCount = Integer.parseInt(counterLine.trim());
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        // Ensure data.txt exists
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Load tasks from data.txt
        try (BufferedReader dataReader = new BufferedReader(new FileReader(dataFile))) {
            String dataLine;
            while ((dataLine = dataReader.readLine()) != null) {
                if (!dataLine.isEmpty()) {
                    Data.allTasks.add(Task.parseFromLine(dataLine));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Task.count = Math.max(Task.count, savedCount);
        Data.categorizeTasks();

        // ASCII Banner
        System.out.println("████████╗ ██████╗       ██████╗  ██████╗     ██╗     ██╗███████╗████████╗");
        System.out.println("╚══██╔══╝██╔═══██╗      ██╔══██╗██╔═══██╗    ██║     ██║██╔════╝╚══██╔══╝");
        System.out.println("   ██║   ██║   ██║█████╗██║  ██║██║   ██║    ██║     ██║███████╗   ██║   ");
        System.out.println("   ██║   ██║   ██║╚════╝██║  ██║██║   ██║    ██║     ██║╚════██║   ██║   ");
        System.out.println("   ██║   ╚██████╔╝      ██████╔╝╚██████╔╝    ███████╗██║███████║   ██║   ");
        System.out.println("   ╚═╝    ╚═════╝       ╚═════╝  ╚═════╝     ╚══════╝╚═╝╚══════╝   ╚═╝   ");
        System.out.println();

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (choice != 5) {
            System.out.println(
                    """
                            ==============================
                            || 1. Add Task             ||
                            || 2. View Tasks           ||
                            || 3. Delete Task          ||
                            || 4. Mark as Completed    ||
                            || 5. Exit                 ||
                            ==============================""");
            System.out.print("Enter your choice: ");

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
            } else {
                sc.nextLine(); // Skip invalid input
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
                    System.out.println("Exiting To Do List App. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }

    public static void saveTasksToFile(File dataFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Task task : Data.allTasks) {
                writer.write(task.id + "|" + task.taskName + "|" + task.category + "|" + task.isCompleted + "|" + task.dueDate);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
            e.printStackTrace();
        }
    }

    public static void saveCounter(File counterFile) {
        try (FileWriter writer = new FileWriter(counterFile)) {
            writer.write(String.valueOf(Task.count));
        } catch (IOException e) {
            System.out.println("Error saving task counter.");
            e.printStackTrace();
        }
    }

    static Task inputFunc(Scanner sc) {
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
                System.out.println("Invalid input. Please enter a valid number (0, 1, or 2).");
            }
        }

        String dueDate;
        System.out.print("Enter due date? 1 for Yes, any other number for No: ");
        int flag;
        try {
            flag = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            flag = 0; // default to "No"
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

    static boolean isValidDateFormat(String date) {
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