package org.example.filler;

import org.example.model.Student;
import org.example.validation.StudentValidator;

import java.util.Scanner;

public class ManualFiller implements ArrayFiller {

    private final Scanner scanner;

    public ManualFiller(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Student[] fill(int length) {
        Student[] students = new Student[length];

        for (int i = 0; i < length; i++) {
            System.out.println("Введите данные студента №" + (i + 1));

            String groupNumber = readGroupNumber();
            double gpa = readGpa();
            String recordBookId = readRecordBookId();

            students[i] = new Student.Builder()
                    .groupNumber(groupNumber)
                    .gpa(gpa)
                    .recordBookId(recordBookId)
                    .build();
        }

        return students;
    }

    private String readGroupNumber() {
        while (true) {
            try {
                System.out.print("Номер группы: ");
                String value = scanner.nextLine().trim();
                StudentValidator.validateGroupNumber(value);
                return value;
            } catch (IllegalArgumentException e) {
                System.out.println("[Ошибка] " + e.getMessage());
            }
        }
    }

    private double readGpa() {
        while (true) {
            try {
                System.out.print("Средний балл: ");
                double value = Double.parseDouble(scanner.nextLine().trim());
                StudentValidator.validateGpa(value);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("[Ошибка] Средний балл должен быть числом");
            } catch (IllegalArgumentException e) {
                System.out.println("[Ошибка] " + e.getMessage());
            }
        }
    }

    private String readRecordBookId() {
        while (true) {
            try {
                System.out.print("Номер зачётной книжки: ");
                String value = scanner.nextLine().trim();
                StudentValidator.validateRecordBookId(value);
                return value;
            } catch (IllegalArgumentException e) {
                System.out.println("[Ошибка] " + e.getMessage());
            }
        }
    }
}
