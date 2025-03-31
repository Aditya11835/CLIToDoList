package CLIToDoList;

public class Task {
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