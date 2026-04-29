package org.example;

import org.example.filler.ArrayFiller;
import org.example.filler.FileFiller;
import org.example.filler.ManualFiller;
import org.example.filler.RandomFiller;
import org.example.model.Student;
import org.example.sort.SortContext;
import org.example.sort.SortStrategy;

import java.util.Scanner;

/**
 * ConsoleController — управляющий класс приложения.
 *
 * Отвечает за:
 * - цикл взаимодействия с пользователем
 * - приём выбора режима заполнения массива
 * - приём выбора стратегии сортировки
 * - приём выбора поля для сортировки
 * - вывод результатов
 *
 * Задачи для команды:
 * - ArrayFiller (заполнение массива) — Участник 1
 * - SortStrategy / SortContext (стратегии сортировки) — Участник 2
 * - Student + StudentValidator (модель и валидация) — Участник 3
 * - EvenFieldSorter (дополнительное задание) — Участник 4
 */

public class ConsoleController {
    private static final String SEPARATOR = "─".repeat(50);

    private final Scanner scanner;
    private final SortContext sortContext;

    public ConsoleController() {
        this.scanner = new Scanner(System.in);
        this.sortContext = new SortContext();
    }


    /** Главный цикл приложения. Выход — только через меню. */
    public void run() {
        printWelcome();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Ваш выбор: ", 0, 3);

            switch (choice) {
                case 1 -> handleSort();
                case 2 -> handleSortEvenField();
                case 3 -> printHelp();
                case 0 -> running = false;
            }
        }

        System.out.println("\nДо свидания!");
        scanner.close();
    }


    private void handleSort() {
        Student[] students = buildArray();
        if (students == null) return;

        SortStrategy strategy = chooseSortStrategy();
        String field = chooseField();

        sortContext.setStrategy(strategy);
        sortContext.sort(students, field);

        printResult(students, "Результат сортировки (" + strategy.getName() + " по полю \"" + field + "\"):");
    }

    // ──────────────────────────────────────────────────
    //  Дополнительная сортировка (чётные / нечётные)
    // ──────────────────────────────────────────────────

    private void handleSortEvenField() {
        Student[] students = buildArray();
        if (students == null) return;

        SortStrategy strategy = chooseSortStrategy();
        String numericField = chooseNumericField();

        sortContext.setStrategy(strategy);
        sortContext.sortEvenFieldOnly(students, numericField);

        printResult(students,
                "Результат (чётные " + numericField + " — отсортированы, нечётные — на месте):");
    }

    // ──────────────────────────────────────────────────
    //  Построение массива
    // ──────────────────────────────────────────────────

    private Student[] buildArray() {
        printFillMenu();
        int fillMode = readInt("Способ заполнения: ", 1, 3);
        int length = readInt("Длина массива (1–100): ", 1, 100);

        ArrayFiller filler = switch (fillMode) {
            case 1 -> new RandomFiller();
            case 2 -> {
                String path = readLine("Путь к файлу: ");
                yield new FileFiller(path);
            }
            case 3 -> new ManualFiller(scanner);
            default -> throw new IllegalStateException("Недопустимый выбор");
        };

        try {
            Student[] students = filler.fill(length);
            System.out.println(SEPARATOR);
            System.out.println("Массив создан. Исходные данные:");
            printStudents(students);
            return students;
        } catch (Exception e) {
            System.out.println("[Ошибка заполнения] " + e.getMessage());
            return null;
        }
    }

    // ──────────────────────────────────────────────────
    //  Выбор стратегии сортировки
    // ──────────────────────────────────────────────────

    private SortStrategy chooseSortStrategy() {
        System.out.println(SEPARATOR);
        System.out.println("Выберите алгоритм сортировки:");
        System.out.println("  1. Пузырьковая (Bubble Sort)");
        System.out.println("  2. Выбором (Selection Sort)");
        System.out.println("  3. Вставками (Insertion Sort)");
        System.out.println("  4. Слиянием (Merge Sort)");
        System.out.println("  5. Быстрая (Quick Sort)");

        return switch (readInt("Алгоритм: ", 1, 5)) {
            case 1 -> new BubbleSortStrategy();
            case 2 -> new SelectionSortStrategy();
            case 3 -> new InsertionSortStrategy();
            case 4 -> new MergeSortStrategy();
            case 5 -> new QuickSortStrategy();
            default -> throw new IllegalStateException("Недопустимый выбор");
        };
    }

    // ──────────────────────────────────────────────────
    //  Выбор поля
    // ──────────────────────────────────────────────────

    /** Все три поля класса Student. */
    private String chooseField() {
        System.out.println(SEPARATOR);
        System.out.println("Сортировать по полю:");
        System.out.println("  1. Номер группы (groupNumber)");
        System.out.println("  2. Средний балл (gpa)");
        System.out.println("  3. Номер зачётной книжки (recordBookId)");

        return switch (readInt("Поле: ", 1, 3)) {
            case 1 -> "groupNumber";
            case 2 -> "gpa";
            case 3 -> "recordBookId";
            default -> throw new IllegalStateException("Недопустимый выбор");
        };
    }

    /** Только числовые поля — для дополнительного задания. */
    private String chooseNumericField() {
        System.out.println(SEPARATOR);
        System.out.println("Числовое поле для чётных/нечётных:");
        System.out.println("  1. Средний балл (gpa)");
        System.out.println("  2. Номер зачётной книжки (recordBookId)");

        return switch (readInt("Поле: ", 1, 2)) {
            case 1 -> "gpa";
            case 2 -> "recordBookId";
            default -> throw new IllegalStateException("Недопустимый выбор");
        };
    }

    // ──────────────────────────────────────────────────
    //  Вывод
    // ──────────────────────────────────────────────────


    private void printResult(Student[] students, String title) {
        System.out.println(SEPARATOR);
        System.out.println(title);
        printStudents(students);
        System.out.println(SEPARATOR);
    }

    private void printStudents(Student[] students) {
        System.out.printf("%-6s %-18s %-8s %-14s%n",
                "№", "Номер группы", "ГПА", "№ зачёт. кн.");
        System.out.println("─".repeat(50));
        for (int i = 0; i < students.length; i++) {
            Student s = students[i];
            System.out.printf("%-6d %-18s %-8.2f %-14s%n",
                    i + 1, s.getGroupNumber(), s.getGpa(), s.getRecordBookId());
        }
    }

    // ──────────────────────────────────────────────────
    //  Меню и приветствие
    // ──────────────────────────────────────────────────

    private void printWelcome() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║       Приложение сортировки студентов            ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    private void printMainMenu() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("Главное меню:");
        System.out.println("  1. Отсортировать массив студентов");
        System.out.println("  2. Сортировка чётных по числовому полю (доп. задание)");
        System.out.println("  3. Справка");
        System.out.println("  0. Выход");
        System.out.println(SEPARATOR);
    }

    private void printFillMenu() {
        System.out.println(SEPARATOR);
        System.out.println("Способ заполнения массива:");
        System.out.println("  1. Случайные данные");
        System.out.println("  2. Из файла");
        System.out.println("  3. Вручную");
    }

    private void printHelp() {
        System.out.println(SEPARATOR);
        System.out.println("Справка:");
        System.out.println("  Приложение сортирует массив объектов Student.");
        System.out.println("  Поля класса Student:");
        System.out.println("    groupNumber   — номер группы (строка, напр. «КІ-23»)");
        System.out.println("    gpa           — средний балл (0.0 – 100.0)");
        System.out.println("    recordBookId  — номер зачётной книжки (число)");
        System.out.println("  Форматы файла (CSV): groupNumber,gpa,recordBookId");
        System.out.println("  Пример строки: КІ-23,87.5,12345");
        System.out.println(SEPARATOR);
    }

    // ──────────────────────────────────────────────────
    //  Утилиты ввода
    // ──────────────────────────────────────────────────

    /**
     * Читает целое число из диапазона [min, max].
     * Повторяет запрос при некорректном вводе.
     */

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) return value;
                System.out.printf("[Ошибка] Введите число от %d до %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("[Ошибка] Это не целое число.");
            }
        }
    }


    /** Читает непустую строку. */

    private String readLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.println("[Ошибка] Строка не может быть пустой.");
        }
    }

}
