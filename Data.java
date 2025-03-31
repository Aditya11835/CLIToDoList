package CLIToDoList;
import java.util.ArrayList;

public class Data {
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

    public static void displayTasks() {
        System.out.println("Important Tasks: " + importantTasks);
        System.out.println("Business Tasks: " + businessTasks);
        System.out.println("Personal Tasks: " + personalTasks);
    }
}

