package org.example.sort;

import org.example.model.Student;

import java.util.List;

public class BubbleSortStrategy implements SortStrategy {

    @Override
    public void sort(List<Student> list, String field) {

        int n = list.size();

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (StudentComparator.compare(list.get(j), list.get(j + 1), field) > 0) {

                    Student temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}
