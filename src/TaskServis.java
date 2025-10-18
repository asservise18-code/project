import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class TaskServis {
    public static List<Task> tasks = new ArrayList<>();

    public static void addTask(String name, int priority) {
        tasks.add(new Task(name, priority));
    }

    public static void showTask() {
        if (tasks.isEmpty()) {
            System.out.println(" Список задач пуст. ");
            return;
        }
        System.out.println(" Список задач ");
        tasks.forEach(System.out::println);
    }

    public static void deleteTask(int id) {

        if (id >= 1 && id <= tasks.size()) {
            tasks.remove(id - 1);
            System.out.println("Задача под номером " + id + " успешно удалена.");
        } else {
            System.out.println("Неверный номер задачи.");
        }

    }

    public static void editTask(int id, String newName, int newPriority) {
        Task task;
        task = Task.findTask(id);
        if (task != null) {
            task.setName(newName);
            task.setPriority(newPriority);
            System.out.println("Задача обновлена.");
        } else {
            System.out.println("Неверный номер задачи.");
        }
    }

    public static void changeStatus(int id, TaskStatus status) {

        Task task = Task.findTask(id);
        if (task != null) {
            task.setStatus(status);
            System.out.println("Задача обновлена! Статус:  " + status.getDescription());
        } else {
            System.out.println("Неверный номер задачи.");
        }
    }

    public static List<Task> filterCompletedStatus(TaskStatus status) {
        return tasks.stream().filter(s -> s.getStatus() == status)
                .collect(toList());
    }


    public static List<Task> findTask(String keyWord) {
        String underword = keyWord.toLowerCase();
        return tasks.stream()
                .filter(t -> t.getName().toLowerCase().contains(underword))
                .collect(Collectors.toList());
    }

    public static double averagePriority(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return 0.0;
        }
        OptionalDouble average = tasks.stream()
                .map(Task::getPriority)
                .mapToInt(Integer::intValue)
                .average();
        return average.orElse(0.0);
    }
    public static Map<TaskStatus, List<Task>> groupTasksByStatus(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return Map.of();
        }
        return tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus));
    }


    public static Map<TaskStatus, Long> countTasksByStatus(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return Map.of();
        }
        return tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }
}