package org.example.search;

import org.example.model.Student;
import org.example.validation.StudentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class StudentSearcher {
    private int threadsNumber;

    public StudentSearcher(int threadsNumber) {
        if (threadsNumber <= 0) {
            throw new IllegalArgumentException("Количество потоков должно быть положительным");
        }
        this.threadsNumber = threadsNumber;
    }

    public int countOccurrences(List<Student> students, String field, String value)
            throws InterruptedException, ExecutionException {

        validateInput(field, value);
        if (students == null || students.isEmpty()) {
            return 0;
        }

        int totalSize = students.size();
        int actualThreads = Math.min(totalSize, threadsNumber);
        int chunkSize = Math.ceilDiv(totalSize, actualThreads);

        ExecutorService executor = Executors.newFixedThreadPool(actualThreads);
        List<Future<Integer>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < actualThreads; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize, totalSize);

                if (start >= totalSize) break;

                final List<Student> subList = students.subList(start, end);
                futures.add(executor.submit(() -> {
                    int count = 0;
                    for (Student student : subList) {
                        if (isMatch(student, field, value)) {
                            count++;
                        }
                    }
                    return count;
                }));
            }

            int totalCount = 0;
            for (Future<Integer> future : futures) {
                totalCount += future.get();
            }
            return totalCount;

        } finally {
            executor.shutdown();
        }
    }

    private void validateInput(String field, String value) {
        switch (field) {
            case "groupNumber" -> StudentValidator.validateGroupNumber(value);
            case "gpa" -> StudentValidator.validateGpa(Double.parseDouble(value));
            case "recordBookId" -> StudentValidator.validateRecordBookId(value);
            default -> throw new IllegalArgumentException("Недопустимое поле для поиска: " + field);
        }
    }

    private boolean isMatch(Student student, String field, String value) {
        return switch (field) {
            case "groupNumber" -> student.getGroupNumber().equals(value);
            case "gpa" -> {
                double targetGpa = Double.parseDouble(value);
                yield Math.round(student.getGpa() * 100) == Math.round(targetGpa * 100);
            }
            case "recordBookId" -> student.getRecordBookId().equals(value);
            default -> false;
        };
    }
}
