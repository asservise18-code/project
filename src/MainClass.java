import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public class MainClass {
    private ArrayList<Task> tasks;
    private Scanner scanner;

    private int nextId = 1;

    public MainClass() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

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
                    filterCompleted();
                    break;
                case 6:
                    findTask();
                    break;
                case 7:
                    changeStatus();
                    break;
                case 8:
                    statistics();
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


    public void addTask() {
        scanner.nextLine();
        System.out.print("Введите описание задачи: ");
        String name = scanner.nextLine();

        System.out.print("Введите приоритет (число): ");
        int priority = scanner.nextInt();
        tasks.add(new Task(nextId++, name, priority, LocalDate.now()));
        System.out.println("Задача добавлена.");
    }

    private void deleteTask() {
        System.out.print("Введите номер задачи для удаления: ");
        int taskNumber = scanner.nextInt();
        if (taskNumber >= 1 && taskNumber <= tasks.size()) {
            tasks.remove(taskNumber - 1);
            System.out.println("Задача под номером " + taskNumber + " успешно удалена.");
        } else {
            System.out.println("Неверный номер задачи.");
        }
    }

    private void editTask() {
        System.out.println("Введите номер задачи для редактирования:");
        int taskNumber = scanner.nextInt();
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Неверный номер задачи.");
            return;
        }
        Task task = tasks.get(taskNumber - 1);
        scanner.nextLine();
        System.out.println("Введите новой приоритет");
        int newPriority = scanner.nextInt();
        task.setPriority(newPriority);
        scanner.nextLine();
        System.out.println("Введите новое название задачи");
        String newName = scanner.nextLine();

        task.setName(newName);

        System.out.println("Задача обновлена.");


    }

    private void changeStatus() {
        System.out.println("Введите номер задачи:");
        int taskNumber = scanner.nextInt();
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Неверный номер задачи.");
            return;
        }
            Task task = tasks.get(taskNumber - 1);
            scanner.nextLine();

            System.out.println("Выберете новый статус");
            System.out.println("1. Выполнена");
            System.out.println("2. В процессе");
            System.out.println("3. Невыполнена");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    task.setCompleted ("Выполнена");
                    task.setComplectionDate(LocalDateTime.now());
                    System.out.println("Задача обновлена! Статус: Выполнена " + task.getComplectionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                    break;
                case "2":
                    task.setCompleted ("В процессе");
                    task.setComplectionDate(null);
                    System.out.println("Задача обновлена! Статус: В процессе ");
                    break;
                case "3":
                    task.setCompleted ("Невыполнена");
                    task.setComplectionDate(null);
                    System.out.println("Задача обновлена! Статус: Невыполнена ");
                    break;
            }
        }
    private void filterCompleted(){
        System.out.println("Выберете статус");
        System.out.println("1. Выполнена");
        System.out.println("2. В процессе");
        System.out.println("3. Невыполнена");
        scanner.nextLine();
        String choice = scanner.nextLine();
        String completed = switch (choice){
            case "1" -> "Выполнена";
            case "2" -> "В процессе";
            case "3" -> "Невыполнена";
            default -> null;
        };
        if (completed == null){
            System.out.println("Неверный статус");
            return;
        }
        List<Task> filter = tasks.stream().filter(s ->s.getCompleted().equalsIgnoreCase(completed))
                                          .collect(toList());
        System.out.println("Задачи со статусом: " + completed);
        filter.forEach(System.out::println);
    }

    private void findTask(){
        System.out.println("Введите ключевое слово: ");
        scanner.nextLine();
        String keyWord = scanner.nextLine();
        List<Task> filter = tasks.stream().filter(word -> word.getName().contains(keyWord))
                .collect(toList());
        System.out.println("Задачи по ключевому слову: " + keyWord);
        filter.forEach(System.out::println);
    }
    private void statistics(){
        System.out.println("Статистика задач:");
        long filter1 =tasks.stream().filter(word -> word.getCompleted().contains("Выполнена"))
                                          .count();
        System.out.println("- Выполнена: " + filter1);
        long filter2 =tasks.stream().filter(word -> word.getCompleted().contains("В процессе"))
                .count();
        System.out.println("- В процессе: " + filter2);
        long filter3 =tasks.stream().filter(word -> word.getCompleted().contains("Невыполнена"))
                .count();
        System.out.println("- Невыполнена: " + filter3);

        OptionalDouble average = tasks.stream()
                .map(Task::getPriority)
                .mapToInt(Integer::intValue)
                .average();

        if (average.isPresent()) {
            System.out.println("Среднее приоритет: " + average.getAsDouble());
        } else {
            System.out.println("Список пуст.");
        }

    }


    private void showTask () {
            if (tasks.isEmpty()) {
                System.out.println(" Список задач пуст. ");
                return;
            }
            System.out.println(" Список задач ");
            tasks.stream().forEach(System.out::println);
        }
    }
