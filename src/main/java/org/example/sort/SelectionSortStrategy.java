package org.example.sort;

import org.example.model.Student;

import java.util.List;

public class SelectionSortStrategy implements SortStrategy {

    @Override
    public void sort(List<Student> list, String field) {

        int n = list.size();

        for (int i = 0; i < n - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < n; j++) {

                if (StudentComparator.compare(
                        list.get(j),
                        list.get(minIndex),
                        field
                ) < 0) {
                    minIndex = j;
                }
            }

            Student temp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, temp);
        }
    }

    @Override
    public String getName() {
        return "Selection Sort";
    }
}