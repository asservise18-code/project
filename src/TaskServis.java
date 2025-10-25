import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TaskServis {
    public static List<Task> tasks = new ArrayList<>();

    public void addTask(String name, int priority) {
        tasks.add(new Task(name, priority));
    }

    public void showTask() {
        tasks.forEach(System.out::println);
    }

    public static Task findTask(int id) {
        return TaskServis.tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean deleteTask(int id) {
        return tasks.removeIf(t -> t.getId() == id);
    }


    public boolean editTask(int id, String newName, int newPriority) {
        Task task = findTask(id);
        if (task != null) {
            task.setName(newName);
            task.setPriority(newPriority);
            return true;
        }
        return false;
    }

    public boolean changeStatus(int id, TaskStatus status) {
        Task task = findTask(id);
        if (task != null) {
            task.setStatus(status);
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