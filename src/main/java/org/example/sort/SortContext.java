package org.example.sort;

import org.example.model.Student;

import java.util.List;

public class SortContext {

    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(List<Student> list, String field) {

        if (strategy == null) {
            throw new IllegalStateException(
                    "Стратегия сортировки не установлена"
            );
        }

        strategy.sort(list, field);
    }

    public void sortEvenFieldOnly(List<Student> list, String field) {

        if (strategy == null) {
            throw new IllegalStateException(
                    "Стратегия сортировки не установлена"
            );
        }

        EvenFieldSorter sorter = new EvenFieldSorter();

        sorter.sortEvenOnly(list, field, strategy);
    }
}