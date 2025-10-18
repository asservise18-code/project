
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MainClass {
    Scanner scanner = new Scanner(System.in);
    static void main() {
        MainClass mainclass = new MainClass();
        mainclass.run();}
        int id;
        public void run() {
            while (true) {
                printMenu();
                int choice = getUserChoice();
                switch (choice) {
                    case 1:
                        scanner.nextLine();
                        System.out.print("Введите описание задачи: ");
                        String name = scanner.nextLine();
                        System.out.print("Введите приоритет (число): ");
                        int priority = scanner.nextInt();
                        TaskServis.addTask(name, priority);
                        System.out.println("Задача добавлена.");
                        break;
                    case 2:
                        System.out.print("Введите номер задачи для удаления: ");
                        id = scanner.nextInt();
                        TaskServis.deleteTask(id);
                        break;
                    case 3:
                        System.out.println("Введите номер задачи для редактирования:");
                        id = scanner.nextInt();
                        System.out.println("Введите новой приоритет(число)");
                        int newPriority = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Введите новое название задачи");
                        String newName = scanner.nextLine();
                        TaskServis.editTask(id,newName,newPriority);
                        break;
                    case 4:
                        TaskServis.showTask();
                        break;
                    case 5:
                         System.out.println("Выберете статус");
                        for (TaskStatus s : TaskStatus.values()) {
                            System.out.println(s.ordinal() + 1 + ". " + s.getDescription());
                        }
                        int filterChoice = scanner.nextInt();
                        TaskStatus filterStatus = TaskStatus.values()[filterChoice - 1];
                        System.out.println("Задачи со статусом: " + filterStatus.getDescription());
                        TaskServis.filterCompletedStatus(filterStatus)
                                .forEach(System.out::println);
                        break;
                    case 6:
                        System.out.print("Введите ключевое слово: ");
                        scanner.nextLine();
                        String keyWord = scanner.nextLine();
                        var found = TaskServis.findTask(keyWord);
                        if (found.isEmpty()) {
                            System.out.println("Задачи не найдены по ключевому слову: " + keyWord);
                        } else {
                            System.out.println("Задачи по ключевому слову: " + keyWord);
                            found.forEach(System.out::println);
                        }
                        break;
                    case 7:{
                        System.out.print("Введите номер задачи:");
                        id = scanner.nextInt();
                            System.out.println("Выберете новый статус");
                        for (TaskStatus s : TaskStatus.values()) {
                            System.out.println(s.ordinal() + 1 + ". " + s.getDescription());
                        }
                        int statusChoice = scanner.nextInt();
                        TaskStatus completed = switch (statusChoice) {
                            case 1 -> TaskStatus.COMPLETED;
                            case 2 ->  TaskStatus.IN_PROGRESS;
                            default ->  TaskStatus.NOT_COMPLETED;
                        };
                        System.out.println("Ваш выбор: " + statusChoice);
                        TaskServis.changeStatus( id, completed);}
                        break;
                    case 8:
                        System.out.println("Статистика задач:");
                        Map<TaskStatus, List<Task>> tasksByStatus = TaskServis.groupTasksByStatus(TaskServis.tasks);
                        tasksByStatus.forEach((status, taskList) -> {
                            System.out.println(status.getDescription());
                            taskList.forEach(System.out::println);
                            System.out.println();
                        });

                        System.out.println("Статистика задач:");
                        Map<TaskStatus, Long> tasksCountByStatus = TaskServis.countTasksByStatus(TaskServis.tasks);
                        tasksCountByStatus.forEach((status, count) ->
                                System.out.println(" - " + status.getDescription() + " : " + count)
                        );

                        double averagePriority = TaskServis.averagePriority(TaskServis.tasks);
                        System.out.println("Среднее приоритет: " + averagePriority);
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

         }
