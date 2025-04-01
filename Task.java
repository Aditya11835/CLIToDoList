package CLIToDoList;

public class Task{
    static long count;
    
    long id;
    String taskName;
    String dueDate;
    int category; // 0: important, 1: business, 2: personal
    boolean isCompleted;
    
    public Task(String taskName , int category, String dueDate) {
        count++;
        this.id = count;
        this.taskName = taskName;
        this.category = category;
        this.isCompleted = false;
        this.dueDate = dueDate;
    }

    public static Task parseFromLine(String line) {
        String[] parts = line.split("\\|");
        Task task = new Task(parts[1], Integer.parseInt(parts[2]), parts[4]);
        task.id = Long.parseLong(parts[0]);
        task.isCompleted = Boolean.parseBoolean(parts[3]);
        Task.count = Math.max(Task.count, task.id);
        return task;
    }
}