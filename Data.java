//start of data.java

//package CLIToDoList;

import java.util.*;

public class Data {
    private static final ArrayList<Task> allTasks = new ArrayList<>();
    private static final ArrayList<Task> importantTasks = new ArrayList<>();
    private static final ArrayList<Task> businessTasks = new ArrayList<>();
    private static final ArrayList<Task> personalTasks = new ArrayList<>();

    //choice# 3.Mark task as complete
    public static void markCompleteTask() {
        Scanner sc = new Scanner(System.in);

        //table format output
        System.out.printf("%-25s %-15s %-15s %-10s %-10s\n", "Task Name", "Due Date", "Category", "Completed", "ID");
        System.out.println("-------------------------------------------------------------------------");
        printTaskListWithID(allTasks);
        System.out.println("\nEnter the ID of the task you want to mark as complete. Enter 0 to go back.");

        try {
            long idToComplete = Long.parseLong(sc.nextLine().trim());

            if (idToComplete == 0) {
                System.out.println("Cancelled.");
                sc.close();
                return;
            }

            boolean found = false;
            for (Task task : allTasks) {
                if (task.getId() == idToComplete) {
                    task.setCompleted(!task.isCompleted());
                    System.out.println("Task with ID " + idToComplete + " marked as " + (task.isCompleted() ? "complete." : "incomplete."));
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("No task found with that ID.");
            }

            categorizeTasks();

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid task ID.");
        }
        sc.close();
    }

    //categorisation of tasks under three categories
    public static void categorizeTasks() {
        importantTasks.clear();
        businessTasks.clear();
        personalTasks.clear();

        for (Task task : allTasks) {
            switch (task.getCategory()) {
                case 0 -> importantTasks.add(task);
                case 1 -> businessTasks.add(task);
                case 2 -> personalTasks.add(task);
            }
        }

        //comparator to arrange task based on priority
        //Priority:: (high)Important->Business->Personal(low)
        Comparator<Task> byId = Comparator.comparingLong(Task::getId);
        allTasks.sort(byId);
        importantTasks.sort(byId);
        businessTasks.sort(byId);
        personalTasks.sort(byId);
    }

    //add task to array
    public static void addTask(Task task) {
        allTasks.add(task);
        categorizeTasks();
    }

    //asks user for what task to delete and remove task from array
    //based on uid from user input
    public static void deleteTask(Scanner sc) {
        System.out.printf("%-25s %-15s %-15s %-10s %-10s\n", "Task Name", "Due Date", "Category", "Completed", "ID");
        System.out.println("-------------------------------------------------------------------------");

        printTaskListWithID(allTasks);

        System.out.println("\nEnter ID of task to be deleted. Enter 0 to go back.");

        try {
            long idToDelete = Long.parseLong(sc.nextLine().trim());

            if (idToDelete == 0) {
                System.out.println("Cancelled.");
                return;
            }

            boolean removed = allTasks.removeIf(task -> task.getId() == idToDelete);

            if (removed) {
                System.out.println("Task with ID " + idToDelete + " deleted successfully.");
                categorizeTasks();
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

        try {
            int choice = sc.nextInt();
            sc.nextLine();

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
            sc.nextLine();
        }
    }

    //display in tabular format
    private static void printTaskList(ArrayList<Task> taskList) {
        if (taskList == null || taskList.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        for (Task task : taskList) {
            System.out.printf("%-25s %-15s %-10s\n",
                    task.getTaskName(),
                    task.getDueDate(),
                    task.isCompleted() ? "Yes" : "No");
        }
    }

    //tabular format for all task display
    private static void printTaskListWithCategory(ArrayList<Task> taskList, String category) {
        if (taskList == null || taskList.isEmpty()) {
            return;
        }
        for (Task task : taskList) {
            System.out.printf("%-25s %-15s %-15s %-10s\n",
                    task.getTaskName(),
                    task.getDueDate(),
                    category,
                    task.isCompleted() ? "Yes" : "No");
        }
    }

    //tabular format for deletion
    private static void printTaskListWithID(ArrayList<Task> taskList) {
        if (taskList == null || taskList.isEmpty()) {
            return;
        }
        for (Task task : taskList) {
            System.out.printf("%-25s %-15s %-15s %-10s %-10s\n",
                    task.getTaskName(),
                    task.getDueDate(),
                    task.getCategory(),
                    task.isCompleted() ? "Yes" : "No",
                    task.getId());
        }
    }

    public static ArrayList<Task> getAllTasks() {
        return allTasks;
    }
}


//end of data.java