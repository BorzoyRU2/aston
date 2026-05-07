package org.example.search;

import org.example.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentSearcher {

    private final int threadCount;

    public StudentSearcher(int threadCount) {
        if (threadCount < 1) {
            throw new IllegalArgumentException("Количество потоков должно быть >= 1");
        }
        this.threadCount = threadCount;
    }

    /**
     * Подсчитывает количество студентов в коллекции,
     * у которых значение поля field совпадает с value.
     * Поиск ведётся в нескольких потоках.
     */
    public int countOccurrences(ArrayList<Student> students,
                                String field,
                                String value) throws InterruptedException {

        if (students == null || students.isEmpty()) {
            return 0;
        }

        AtomicInteger count = new AtomicInteger(0);

        int size = students.size();
        int chunkSize = Math.max(1, (size + threadCount - 1) / threadCount);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {

            int from = i * chunkSize;
            int to = Math.min(from + chunkSize, size);

            if (from >= size) {
                break;
            }

            final int finalFrom = from;
            final int finalTo = to;

            Thread thread = new Thread(() -> {
                for (int j = finalFrom; j < finalTo; j++) {
                    if (matches(students.get(j), field, value)) {
                        count.incrementAndGet();
                    }
                }
            });

            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return count.get();
    }

    private boolean matches(Student student, String field, String value) {
        return switch (field) {
            case "groupNumber" ->
                    student.getGroupNumber().equalsIgnoreCase(value);
            case "gpa" -> {
                try {
                    double gpa = Double.parseDouble(value);
                    yield Double.compare(student.getGpa(), gpa) == 0;
                } catch (NumberFormatException e) {
                    yield false;
                }
            }
            case "recordBookId" ->
                    student.getRecordBookId().equals(value);
            default ->
                    throw new IllegalArgumentException("Неизвестное поле: " + field);
        };
    }
}
