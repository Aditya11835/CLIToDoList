//package CLIToDoList;

public class Task {
    private static long count;

    private long id;
    private String taskName;
    private String dueDate;
    private int category; // 0: important, 1: business, 2: personal
    private boolean isCompleted;

    public Task(String taskName, int category, String dueDate) {
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
        task.setId(Long.parseLong(parts[0]));
        task.setCompleted(Boolean.parseBoolean(parts[3]));
        count = Math.max(count, task.getId());
        return task;
    }

    public static long getCount() {
        return count;
    }

    public static void setCount(long count) {
        Task.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
