package org.example.filler;

import org.example.model.Student;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomFiller implements ArrayFiller {

    private static final String[] GROUP_NUMBERS = {
            "KI-21", "KI-22", "KI-23", "PI-21", "PI-22", "IS-23"
    };

    private final Random random = new Random();

    @Override
    public ArrayList<Student> fill(int length) {
        return Stream.generate(this::generateStudent)
                .limit(length)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Student generateStudent() {
        return new Student.Builder()
                .groupNumber(generateGroupNumber())
                .gpa(generateGpa())
                .recordBookId(generateRecordBookId())
                .build();
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