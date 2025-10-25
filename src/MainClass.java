import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MainClass {
    int id;
    Scanner scanner = new Scanner(System.in);
    TaskServis taskServis = new TaskServis();

    public static void main(String[] args) {
        MainClass mainclass = new MainClass();
        mainclass.run();
    }

    public void run() {
        while (true) {
            printMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    deleteTask();
                    break;
                case 3:
                    editTask();
                    break;
                case 4:
                    showTask();
                    break;
                case 5:
                    filterStatus();
                    break;
                case 6:
                    findWord();
                    break;
                case 7:
                    changeStatus();
                    break;
                case 8:
                    showStatistics();
                    break;
                case 9:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный ввод. Пожалуйста, выберите пункт меню.");
            }
        }
    }

    private void printMenu() {
        System.out.println(" Добро пожаловать в приложение <<Управление списком задач!>>");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Удалить задачу");
        System.out.println("3. Отредактировать задачу");
        System.out.println("4. Показать все задачи");
        System.out.println("5. Фильтровать задачи по статусу");
        System.out.println("6. Найти задачу по ключевому слову");
        System.out.println("7. Изменить статус задачи");
        System.out.println("8. Показать статистику");
        System.out.println("9. Выход");
    }

    private int getUserChoice() {
        return scanner.nextInt();
    }

    private void deleteTask() {
        System.out.print("Введите номер задачи для удаления: ");
        try {
            scanner.nextLine();
            id = Integer.parseInt(scanner.nextLine());
            if (taskServis.deleteTask(id)) {
                System.out.println("Задача под номером " + id + ". успешно удалена.");
            } else {
                System.out.println("Неверный номер задачи.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Пожалуйста, введите число.");
        }
    }

    private void addTask() {
        try {
            scanner.nextLine();
            System.out.print("Введите описание задачи: ");
            String name = scanner.nextLine();
            System.out.print("Введите приоритет (число): ");
            int priority = Integer.parseInt(scanner.nextLine());
            taskServis.addTask(name, priority);
            System.out.println("Задача добавлена.");
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Пожалуйста, введите число.");
        }
    }

    private void showTask() {
        if (taskServis.tasks.isEmpty()) {
            System.out.println(" Список задач пуст. ");
            return;
        }
        System.out.println(" Список задач ");
        taskServis.showTask();
    }

    private void editTask() {
        System.out.println("Введите номер задачи для редактирования:");
        try {
            scanner.nextLine();
            id = Integer.parseInt(scanner.nextLine());
            System.out.println("Введите новой приоритет(число)");
            int newPriority = Integer.parseInt(scanner.nextLine());
            System.out.println("Введите новое название задачи");
            String newName = scanner.nextLine();
            if (taskServis.editTask(id, newName, newPriority)) {
                System.out.println("Задача обновлена.");
            } else {
                System.out.println("Неверный номер задачи.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Пожалуйста, введите число.");

        }
    }

    private void filterStatus() {
        System.out.println("Выберете статус");
        for (TaskStatus s : TaskStatus.values()) {
            System.out.println(s.ordinal() + 1 + ". " + s.getDescription());
        }
        int filterChoice = scanner.nextInt();
        TaskStatus filterStatus = TaskStatus.values()[filterChoice - 1];
        System.out.println("Задачи со статусом: " + filterStatus.getDescription());
        taskServis.filterCompletedStatus(filterStatus)
                .forEach(System.out::println);
    }

    private void findWord() {
        System.out.print("Введите ключевое слово: ");
        scanner.nextLine();
        String keyWord = scanner.nextLine();
        var found = taskServis.findWordTask(keyWord);
        if (found.isEmpty()) {
            System.out.println("Задачи не найдены по ключевому слову: " + keyWord);
        } else {
            System.out.println("Задачи по ключевому слову: " + keyWord);
            found.forEach(System.out::println);
        }
    }

    private void changeStatus() {
        System.out.print("Введите номер задачи:");
        try {
            scanner.nextLine();
            id = Integer.parseInt(scanner.nextLine());
            System.out.println("Выберете новый статус");
            for (TaskStatus s : TaskStatus.values()) {
                System.out.println(s.ordinal() + 1 + ". " + s.getDescription());
            }
            int statusChoice = scanner.nextInt();
            TaskStatus completed = switch (statusChoice) {
                case 1 -> TaskStatus.COMPLETED;
                case 2 -> TaskStatus.IN_PROGRESS;
                default -> TaskStatus.NOT_COMPLETED;
            };
            System.out.println("Ваш выбор: " + statusChoice);
            if (taskServis.changeStatus(id, completed)) {
                System.out.println("Задача обновлена! Статус:  " + completed.getDescription());
            } else {
                System.out.println("Неверный номер задачи.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Пожалуйста, введите число для номера.");
        }

    }

    private void showStatistics() {
        System.out.println("Статистика задач:");

        Map<TaskStatus, List<Task>> tasksByStatus = taskServis.groupTasksByStatus();
        tasksByStatus.forEach((status, taskList) -> {
            System.out.println(status.getDescription());
            taskList.forEach(System.out::println);
            System.out.println();
        });

        Map<TaskStatus, Long> tasksCountByStatus = taskServis.countTasksByStatus();
        tasksCountByStatus.forEach((status, count) ->
                System.out.println(" - " + status.getDescription() + " : " + count)
        );

        double averagePriority = taskServis.averagePriority();
        System.out.println("Среднее приоритет: " + averagePriority);
    }
}
