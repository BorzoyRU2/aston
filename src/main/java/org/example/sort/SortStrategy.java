package org.example.sort;

import org.example.model.Student;

public interface SortStrategy {
    void sort(Student[] array, String field);

    String getName();
}