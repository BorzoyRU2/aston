package org.example.sort;

import org.example.model.Student;

import java.util.List;

public class InsertionSortStrategy implements SortStrategy {

    @Override
    public void sort(List<Student> list, String field) {

        for (int i = 1; i < list.size(); i++) {

            Student current = list.get(i);
            int j = i - 1;

            while (j >= 0 &&
                    StudentComparator.compare(list.get(j), current, field) > 0) {

                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, current);
        }
    }

    @Override
    public String getName() {
        return "Insertion Sort";
    }
}