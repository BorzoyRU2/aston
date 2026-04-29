package org.example.sort;

import org.example.model.Student;

public class SortContext {

    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(Student[] array, String field) {

        if (strategy == null) {
            throw new IllegalStateException(
                    "Стратегия сортировки не установлена"
            );
        }

        strategy.sort(array, field);
    }

    public void sortEvenFieldOnly(Student[] array, String field) {

        if (strategy == null) {
            throw new IllegalStateException(
                    "Стратегия сортировки не установлена"
            );
        }

        EvenFieldSorter sorter = new EvenFieldSorter();

        sorter.sortEvenOnly(array, field, strategy);
    }
}