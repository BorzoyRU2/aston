package org.example.sort;

import org.example.model.Student;

public class StudentComparator {

    public static int compare(Student a, Student b, String field) {

        return switch (field) {

            case "groupNumber" ->
                    a.getGroupNumber().compareToIgnoreCase(b.getGroupNumber());

            case "gpa" ->
                    Double.compare(a.getGpa(), b.getGpa());

            case "recordBookId" ->
                    a.getRecordBookId().compareTo(b.getRecordBookId());

            default -> throw new IllegalArgumentException(
                    "Неизвестное поле: " + field
            );
        };
    }
}