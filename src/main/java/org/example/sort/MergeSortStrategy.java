package org.example.sort;

import org.example.model.Student;

import java.util.ArrayList;
import java.util.List;

public class MergeSortStrategy implements SortStrategy {

    @Override
    public void sort(List<Student> list, String field) {
        mergeSort(list, field, 0, list.size() - 1);
    }

    private void mergeSort(List<Student> list,
                           String field,
                           int left,
                           int right) {

        if (left >= right) {
            return;
        }

        int middle = (left + right) / 2;

        mergeSort(list, field, left, middle);
        mergeSort(list, field, middle + 1, right);

        merge(list, field, left, middle, right);
    }

    private void merge(List<Student> list,
                       String field,
                       int left,
                       int middle,
                       int right) {

        List<Student> temp = new ArrayList<>();

        int i = left;
        int j = middle + 1;

        while (i <= middle && j <= right) {

            if (StudentComparator.compare(list.get(i), list.get(j), field) <= 0) {
                temp.add(list.get(i++));
            } else {
                temp.add(list.get(j++));
            }
        }

        while (i <= middle) {
            temp.add(list.get(i++));
        }

        while (j <= right) {
            temp.add(list.get(j++));
        }

        for (int k = 0; k < temp.size(); k++) {
            list.set(left + k, temp.get(k));
        }
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }
}