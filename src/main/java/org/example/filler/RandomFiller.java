package org.example.filler;

import org.example.model.Student;

import java.util.Random;

public class RandomFiller implements ArrayFiller {

    private static final String[] GROUP_NUMBERS = {
            "KI-21", "KI-22", "KI-23", "PI-21", "PI-22", "IS-23"
    };

    private final Random random = new Random();

    @Override
    public Student[] fill(int length) {
        Student[] students = new Student[length];

        for (int i = 0; i < length; i++) {
            students[i] = new Student.Builder()
                    .groupNumber(generateGroupNumber())
                    .gpa(generateGpa())
                    .recordBookId(generateRecordBookId())
                    .build();
        }

        return students;
    }

    private String generateGroupNumber() {
        return GROUP_NUMBERS[random.nextInt(GROUP_NUMBERS.length)];
    }

    private double generateGpa() {
        return Math.round(random.nextDouble() * 10000.0) / 100.0;
    }

    private String generateRecordBookId() {
        int number = 100000 + random.nextInt(900000);
        return String.valueOf(number);
    }
}