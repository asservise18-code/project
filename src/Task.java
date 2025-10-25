import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Task {
    private static int nextID = 0;
    private final int id;
    private String name;
    private int priority;
    private final LocalDate creationDate;
    private TaskStatus status;
    private LocalDateTime completionDate;

    public Task(String name, int priority) {
        this.id = ++nextID;
        this.name = name;
        this.priority = priority;
        this.creationDate = LocalDate.now();
        this.status = TaskStatus.NOT_COMPLETED;
        this.completionDate = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus newStatus) {
        if (this.status != newStatus) {
            this.status = newStatus;
            if (newStatus == TaskStatus.COMPLETED) {
                this.completionDate = LocalDateTime.now();
            } else {
                this.completionDate = null;
            }
        }

    }

    @Override
    public String toString() {
        return id + ". " + name + " | " + priority
                + " | Статус: " + status.getDescription() +
                "| Дата создания: " + creationDate
                + "| Время выполнения: " + (completionDate != null
                ? completionDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                : "отсутствует");

    }


}

