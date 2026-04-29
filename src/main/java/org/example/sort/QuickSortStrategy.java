package org.example.sort;

import org.example.model.Student;

import java.util.List;

public class QuickSortStrategy implements SortStrategy {

    @Override
    public void sort(List<Student> list, String field) {
        quickSort(list, field, 0, list.size() - 1);
    }

    private void quickSort(List<Student> list,
                           String field,
                           int low,
                           int high) {

        if (low < high) {

            int pivotIndex = partition(list, field, low, high);

            quickSort(list, field, low, pivotIndex - 1);
            quickSort(list, field, pivotIndex + 1, high);
        }
    }

    private int partition(List<Student> list,
                          String field,
                          int low,
                          int high) {

        Student pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (StudentComparator.compare(list.get(j), pivot, field) <= 0) {

                i++;

                Student temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        Student temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }
}