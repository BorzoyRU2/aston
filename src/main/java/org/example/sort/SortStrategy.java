package org.example.sort;

import org.example.model.Student;

import java.util.List;

public interface SortStrategy {
    void sort(List<Student> list, String field);

    String getName();
}