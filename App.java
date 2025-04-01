package CLIToDoList;
import java.util.*;
import java.io.*;
public class App {
    public static void main(String args[]) {
        File counterFile = new File("counter.txt");
        File dataFile = new File("data.txt");
        if (!counterFile.exists()) {
            try (FileWriter writer = new FileWriter(counterFile)) {
                writer.write("0"); // write default count
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader counterReader = new BufferedReader(new FileReader(counterFile))) {
            String counterLine = counterReader.readLine();
            if (counterLine != null && !counterLine.isEmpty()) {
                Task.count = Integer.parseInt(counterLine.trim());
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            Task.count = 0; // fallback default
        }
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        Data.categorizeTasks();
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
                    Data.deleteTask();
                    break;
                case 4:
                    System.out.println("Mark as Completed feature not implemented yet.");
                    break;
                case 5:
                    //Update values to file
                    System.out.println("Exiting To Do List App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        try (FileWriter writer = new FileWriter(counterFile)) {
                writer.write(String.valueOf(Task.count));
        } catch (IOException e)
        {
                e.printStackTrace();
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
        System.out.print("Enter due date? 1 for Yes, any other number for No: ");
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
