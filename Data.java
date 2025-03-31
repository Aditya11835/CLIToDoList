package CLIToDoList;
import java.util.ArrayList;

class Task {
    String taskName;
    String dueDate;
    int category; // 0: important, 1: business, 2: personal
    boolean isCompleted;
    public Task(String taskName , int category, String dueDate) {
        this.taskName = taskName;
        this.category = category;
        this.isCompleted = false;
        this.dueDate = dueDate;
    }
    
    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", category='" + category + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}

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

