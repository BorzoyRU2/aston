package org.example.sort;

import org.example.model.Student;

import java.util.ArrayList;
import java.util.List;

public class EvenFieldSorter {

    public void sortEvenOnly(Student[] array,
                             String numericField,
                             SortStrategy strategy) {

        // Защита от null/пустого массива
        if (array == null || array.length == 0) {
            return;
        }

        List<Integer> evenIndexes = new ArrayList<>();
        List<Student> evenStudents = new ArrayList<>();

        // Ищем только чётные элементы
        for (int i = 0; i < array.length; i++) {

            long value = getNumericValue(array[i], numericField);

            if (value % 2 == 0) {
                evenIndexes.add(i);
                evenStudents.add(array[i]);
            }
        }

        if (evenStudents.size() < 2) {
            return;
        }

        // Конвертация в массив
        Student[] evenArray = evenStudents.toArray(new Student[0]);

        // Используем существующую стратегию
        strategy.sort(evenArray, numericField);

        // Возвращаем обратно ТОЛЬКО на места чётных
        for (int i = 0; i < evenIndexes.size(); i++) {
            array[evenIndexes.get(i)] = evenArray[i];
        }
    }

    /**
     * Получение числового значения поля.
     */
    private long getNumericValue(Student student, String field) {

        return switch (field) {

            case "recordBookId" -> {
                try {
                    yield Long.parseLong(student.getRecordBookId());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "recordBookId должен содержать только цифры"
                    );
                }
            }

            case "gpa" ->
                    Math.round(student.getGpa());

            default ->
                    throw new IllegalArgumentException(
                            "Поле не является числовым: " + field
                    );
        };
    }
}