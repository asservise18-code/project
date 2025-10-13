import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public  class Task {
    private int id;
    private String name;
    private int priority;
    private final LocalDate creationDate;
    private String completed;
    private LocalDateTime complectionDate;

    public Task(int id, String name, int priority, LocalDate now) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.creationDate = LocalDate.now();
        this.completed = "Невыполнена";
        this.complectionDate = null;
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


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getCompletionDate() {
        return complectionDate;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public LocalDateTime getComplectionDate() {
        return complectionDate;
    }

    public void setComplectionDate(LocalDateTime complectionDate) {
        this.complectionDate = complectionDate;
    }

    @Override
    public String toString() {
        return id + ". " + name + " | " + priority
                + " | Статус: " + completed+
                "| Дата создания: " + creationDate
                + "| Дата выполнения: " + complectionDate;

    }
}