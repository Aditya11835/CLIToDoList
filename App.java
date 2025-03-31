package CLIToDoList;
import java.util.*;
import java.io.*;
public class App {
    public static void main(String args[]) {
        BufferedReader personalReader, completedReader, businessReader, urgentReader;
        File completedFile = new File("completed.txt");
        File personalFile = new File("personal.txt");
        File businessFile = new File("business.txt");
        File urgentFile = new File("urgent.txt");

        if (completedFile.exists() && personalFile.exists() && businessFile.exists() && urgentFile.exists()) {
            System.out.println("Loading data...");
            try {
                personalReader = new BufferedReader(new FileReader("personal.txt"));
                completedReader = new BufferedReader(new FileReader("completed.txt"));
                businessReader = new BufferedReader(new FileReader("business.txt"));
                urgentReader = new BufferedReader(new FileReader("urgent.txt"));
                
                // Make sure to close these later
            } catch (IOException e) {
                System.out.println("An error occurred while reading the files.");
                e.printStackTrace();
                System.exit(-1);
            }
        } else {
            try {
                if (completedFile.createNewFile() && personalFile.createNewFile()
                        && businessFile.createNewFile() && urgentFile.createNewFile()) {
                    System.out.println("Creating data files...");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file.");
                e.printStackTrace();
                System.exit(-1);
            }
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("████████╗ ██████╗       ██████╗  ██████╗     ██╗     ██╗███████╗████████╗");
        System.out.println("╚══██╔══╝██╔═══██╗      ██╔══██╗██╔═══██╗    ██║     ██║██╔════╝╚══██╔══╝");
        System.out.println("   ██║   ██║   ██║█████╗██║  ██║██║   ██║    ██║     ██║███████╗   ██║   ");
        System.out.println("   ██║   ██║   ██║╚════╝██║  ██║██║   ██║    ██║     ██║╚════██║   ██║   ");
        System.out.println("   ██║   ╚██████╔╝      ██████╔╝╚██████╔╝    ███████╗██║███████║   ██║   ");
        System.out.println("   ╚═╝    ╚═════╝       ╚═════╝  ╚═════╝     ╚══════╝╚═╝╚══════╝   ╚═╝   ");
        System.out.println();

        int choice = 0;
        while (choice != 5) {
            System.out.println(
                    "==============================\n" +
                    "|| 1. Add Task             ||\n" +
                    "|| 2. View Tasks           ||\n" +
                    "|| 3. Delete Task          ||\n" +
                    "|| 4. Mark as Completed    ||\n" +
                    "|| 5. Exit                 ||\n" +
                    "==============================");
            System.out.print("Enter your choice: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
            } else {
                sc.nextLine(); // Invalid input, skip it
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    Task temp = inputFunc(sc);
                    Data.addTask(temp);
                    break;
                case 2:
                    Data.displayTasks(sc);
                    break;
                case 3:
                    System.out.println("Delete Task feature not implemented yet.");
                    break;
                case 4:
                    System.out.println("Mark as Completed feature not implemented yet.");
                    break;
                case 5:
                    System.out.println("Exiting To Do List App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        
        sc.close();
    }

    static Task inputFunc(Scanner sc) {
        System.out.print("Enter name of Task: ");
        String taskName = sc.nextLine();

        System.out.print("Enter 0 for Urgent, 1 for Business, or 2 for Personal: ");
        int category = sc.nextInt();
        sc.nextLine(); 

        String dueDate = "";
        System.out.print("Enter due date? 1 for Yes, anything else for No: ");
        int flag = sc.nextInt();
        sc.nextLine();

        if (flag == 1) {
            while (true) {
                System.out.print("Enter due date in format DD-MM-YYYY: ");
                dueDate = sc.nextLine().trim();
                if (isValidDateFormat(dueDate)) {
                    break;
                } else {
                    System.out.println("Invalid date format. Try again.");
                }
            }
        } else {
            dueDate = "N/A";
        }

        return new Task(taskName, category, dueDate);
    }

    static boolean isValidDateFormat(String date) {
        if (date == null || date.length() != 10 || date.charAt(2) != '-' || date.charAt(5) != '-') {
            return false;
        }

        try {
            int day = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(3, 5));
            int year = Integer.parseInt(date.substring(6, 10));

            if (month < 1 || month > 12 || day < 1 || year < 1000 || year > 9999) {
                return false;
            }

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
