package CLIToDoList;
import java.util.*;

public class Data{
    static ArrayList<Task> allTasks = new ArrayList<>();
    static ArrayList<Task> importantTasks = new ArrayList<>();
    static ArrayList<Task> businessTasks = new ArrayList<>();
    static ArrayList<Task> personalTasks = new ArrayList<>();

    public static void markCompleteTask() {
        Scanner sc = new Scanner(System.in);

        System.out.printf("%-25s %-15s %-15s %-10s %-10s\n", "Task Name", "Due Date", "Category", "Completed", "ID");
        System.out.println("-------------------------------------------------------------------------");
        printTaskListWithID(allTasks);
        System.out.println("\nEnter the ID of the task you want to mark as complete. Enter 0 to go back.");

        try {
            long idToComplete = Long.parseLong(sc.nextLine().trim());

            if (idToComplete == 0) {
                System.out.println("Cancelled.");
                return;
            }

            boolean found = false;
            for (Task task : allTasks) {
                if (task.id == idToComplete) {
                    if (task.isCompleted) {
                        task.isCompleted = false;
                        System.out.println("Task with ID " + idToComplete + "marked as incomplete.");
                    } else {
                        task.isCompleted = true;
                        System.out.println("Task with ID " + idToComplete + " marked as complete.");
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("No task found with that ID.");
            }

            categorizeTasks(); // Optional: to maintain consistency in categorized lists

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid task ID.");
        }
    }


    public static void categorizeTasks() {
        importantTasks.clear();
        businessTasks.clear();
        personalTasks.clear();

        for (Task task : allTasks) {
            switch (task.category) {
                case 0 -> importantTasks.add(task);
                case 1 -> businessTasks.add(task);
                case 2 -> personalTasks.add(task);
            }
        }

        // Sort all lists by creation ID (oldest to newest)
        Comparator<Task> byId = Comparator.comparingLong(t -> t.id);
        allTasks.sort(byId);
        importantTasks.sort(byId);
        businessTasks.sort(byId);
        personalTasks.sort(byId);
    }

    public static void addTask(Task task) {
        allTasks.add(task);
        categorizeTasks();
    }

    public static void deleteTask() {
        Scanner sc = new Scanner(System.in);

        System.out.printf("%-25s %-15s %-15s %-10s %-10s\n", "Task Name", "Due Date", "Category", "Completed", "ID");
        System.out.println("-------------------------------------------------------------------------");

        printTaskListWithID(allTasks);

        System.out.println("\nEnter ID of task to be deleted. Enter 0 to go back.");
        long idToDelete;

        try {
            idToDelete = Long.parseLong(sc.nextLine());

            if (idToDelete == 0) {
                System.out.println("Cancelled.");
                return;
            }

            boolean removed = allTasks.removeIf(task -> task.id == idToDelete);

            if (removed) {
                System.out.println("Task with ID " + idToDelete + " deleted successfully.");
                categorizeTasks(); // Re-sync categorized lists
            } else {
                System.out.println("No task found with that ID.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid task ID.");
        }
    }

    public static void displayTasks(Scanner sc) {
        System.out.println("\nWhich tasks do you want to view?");
        System.out.println("1. Urgent (Important)");
        System.out.println("2. Business");
        System.out.println("3. Personal");
        System.out.println("4. View All");
        System.out.print("Enter your choice: ");

        int choice;

        try {
            choice = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            if (choice == 4) {
                System.out.printf("%-25s %-15s %-15s %-10s\n", "Task Name", "Due Date", "Category", "Completed");
                System.out.println("---------------------------------------------------------------------");
                printTaskListWithCategory(importantTasks, "Urgent");
                printTaskListWithCategory(businessTasks, "Business");
                printTaskListWithCategory(personalTasks, "Personal");
            } else {
                System.out.printf("%-25s %-15s %-10s\n", "Task Name", "Due Date", "Completed");
                System.out.println("----------------------------------------------------------");

                switch (choice) {
                    case 1 -> printTaskList(importantTasks);
                    case 2 -> printTaskList(businessTasks);
                    case 3 -> printTaskList(personalTasks);
                    default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine(); // clear the invalid input
        }
    }


    private static void printTaskList(ArrayList<Task> taskList) {
        if (taskList == null || taskList.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        for (Task task : taskList) {
            System.out.printf("%-25s %-15s %-10s\n",
                    task.taskName,
                    task.dueDate,
                    task.isCompleted ? "Yes" : "No");
        }
    }

    private static void printTaskListWithCategory(ArrayList<Task> taskList, String category) {
        if (taskList == null || taskList.isEmpty()) {
            return;
        }
        for (Task task : taskList) {
            System.out.printf("%-25s %-15s %-15s %-10s\n",
                    task.taskName,
                    task.dueDate,
                    category,
                    task.isCompleted ? "Yes" : "No");
        }
    }
    
    private static void printTaskListWithID(ArrayList<Task> taskList) {
        if (taskList == null || taskList.isEmpty()) {
            return;
        }
        for (Task task : taskList) {
            System.out.printf("%-25s %-15s %-15s %-10s %-10s\n",
                    task.taskName,
                    task.dueDate,
                    task.category,
                    task.isCompleted ? "Yes" : "No",
                    task.id);
        }
    }
}

