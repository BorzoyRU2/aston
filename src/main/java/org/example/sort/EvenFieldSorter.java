package org.example.sort;

import org.example.model.Student;

import java.util.ArrayList;
import java.util.List;

public class EvenFieldSorter {

    public void sortEvenOnly(List<Student> list,
                             String numericField,
                             SortStrategy strategy) {

        if (list == null || list.isEmpty()) {
            return;
        }

        List<Integer> evenIndexes = new ArrayList<>();
        List<Student> evenStudents = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            long value = getNumericValue(list.get(i), numericField);

            if (value % 2 == 0) {
                evenIndexes.add(i);
                evenStudents.add(list.get(i));
            }
        }

        if (evenStudents.size() < 2) {
            return;
        }

        strategy.sort(evenStudents, numericField);

        for (int i = 0; i < evenIndexes.size(); i++) {
            list.set(evenIndexes.get(i), evenStudents.get(i));
        }
    }

    private long getNumericValue(Student student, String field) {

        return switch (field) {

            case "recordBookId" ->
                    Long.parseLong(student.getRecordBookId());

            case "gpa" ->
                    Math.round(student.getGpa());

            default -> throw new IllegalArgumentException(
                    "Поле не является числовым"
            );
        };
    }
}