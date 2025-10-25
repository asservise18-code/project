import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TaskServis {
    private List<Task> tasks;
    private int nextID;

    public TaskServis(){
        this.tasks = new ArrayList<>();
        this.nextID = 1;
    }

    public List<Task> getAllTasks(){
        return new ArrayList<>(tasks);
    }

    public void addTask(String name, int priority) {
        tasks.add(new Task(nextID++, name, priority));
    }

    public void showTask() {
        tasks.forEach(System.out::println);
    }

    public boolean deleteTask(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    public Optional<Task> getTaskById(int id) {
        return tasks.stream() .filter(task -> task.getId() == id) .findFirst();
    }

    public boolean editTask(int id, String newName, int newPriority) {
        Optional<Task> taskOptional = getTaskById(id);
        if (taskOptional.isPresent()) {
            taskOptional.get().setName(newName);
            taskOptional.get().setPriority(newPriority);
            return true;
        }
        return false;
    }

    public boolean changeStatus(int id, TaskStatus status) {
        Optional<Task> taskOptional = getTaskById(id);
        if (taskOptional.isPresent()) {
            taskOptional.get().setStatus(status);
            return true;
        }
        return false;
    }

    public List<Task> filterCompletedStatus(TaskStatus status) {
        return tasks.stream().filter(s -> s.getStatus() == status)
                .collect(toList());
    }

    public List<Task> findWordTask(String keyWord) {
        String underword = keyWord.toLowerCase();
        return tasks.stream()
                .filter(t -> t.getName().toLowerCase().contains(underword))
                .collect(Collectors.toList());
    }

    public double averagePriority() {
        if (tasks == null || tasks.isEmpty()) {
            return 0.0;
        }
        OptionalDouble average = tasks.stream()
                .map(Task::getPriority)
                .mapToInt(Integer::intValue)
                .average();
        return average.orElse(0.0);
    }

    public Map<TaskStatus, List<Task>> groupTasksByStatus() {
        if (tasks == null || tasks.isEmpty()) {
            return Map.of();
        }
        return tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus));
    }

    public Map<TaskStatus, Long> countTasksByStatus() {
        if (tasks == null || tasks.isEmpty()) {
            return Map.of();
        }
        return tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }
}