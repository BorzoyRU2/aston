package org.example.validation;

public final class StudentValidator {

    private StudentValidator() {
    }

    public static void validateGroupNumber(String groupNumber) {
        if (groupNumber == null || groupNumber.isBlank()) {
            throw new IllegalArgumentException("Номер группы не может быть пустым");
        }
    }

    public static void validateGpa(double gpa) {
        if (gpa < 0.0 || gpa > 100.0) {
            throw new IllegalArgumentException("Средний балл должен быть от 0.0 до 100.0");
        }
    }

    public static void validateRecordBookId(String recordBookId) {
        if (recordBookId == null || recordBookId.isBlank()) {
            throw new IllegalArgumentException("Номер зачётной книжки не может быть пустым");
        }

        if (!recordBookId.matches("\\d+")) {
            throw new IllegalArgumentException("Номер зачётной книжки должен содержать только цифры");
        }
    }

    public static void validateCsvLine(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("CSV-строка не может быть пустой");
        }

        String[] parts = line.split(",", -1);

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "CSV-строка должна содержать ровно 3 значения: groupNumber,gpa,recordBookId"
            );
        }

        String groupNumber = parts[0].trim();
        String gpaText = parts[1].trim();
        String recordBookId = parts[2].trim();

        validateGroupNumber(groupNumber);

        try {
            double gpa = Double.parseDouble(gpaText);
            validateGpa(gpa);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Средний балл должен быть числом");
        }

        validateRecordBookId(recordBookId);
    }
}