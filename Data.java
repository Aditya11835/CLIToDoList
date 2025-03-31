package CLIToDoList;
import java.util.*;

public class Data{
    static ArrayList<Task> importantTasks = new ArrayList<>();
    static ArrayList<Task> businessTasks = new ArrayList<>();
    static ArrayList<Task> personalTasks = new ArrayList<>();

    public static void addTask(Task task) {
        switch (task.category) {
            case 0:
                importantTasks.add(task);
                break;
            case 1:
                businessTasks.add(task);
                break;
            case 2:
                personalTasks.add(task);
                break;
            default:
                System.out.println("Invalid category: " + task.category);
        }
    }
    
    public static void deleteTask(){
        System.out.printf("%-25s %-15s %-15s %-10s %-10s\n", "Task Name", "Due Date", "Category", "Completed", "ID");
        System.out.println("-------------------------------------------------------------------------");
        printTaskListWithID(importantTasks, "Urgent");
        printTaskListWithID(businessTasks, "Business");
        printTaskListWithID(personalTasks, "Personal");
        System.out.println("Enter ID of task to be deleted. Enter 0 to go back.");
        
    }

    public static void displayTasks(Scanner sc) {
        System.out.println("\nWhich tasks do you want to view?");
        System.out.println("1. Urgent (Important)");
        System.out.println("2. Business");
        System.out.println("3. Personal");
        System.out.println("4. View All");
        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

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
                case 1:
                    printTaskList(importantTasks);
                    break;
                case 2:
                    printTaskList(businessTasks);
                    break;
                case 3:
                    printTaskList(personalTasks);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
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
    
    private static void printTaskListWithID(ArrayList<Task> taskList, String category) {
        if (taskList == null || taskList.isEmpty()) {
            return;
        }
        for (Task task : taskList) {
            System.out.printf("%-25s %-15s %-15s %-10s %-10s\n",
                    task.taskName,
                    task.dueDate,
                    category,
                    task.isCompleted ? "Yes" : "No",
                    task.id);
        }
    }
}

