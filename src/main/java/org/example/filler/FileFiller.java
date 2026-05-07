package org.example.filler;

import org.example.model.Student;
import org.example.validation.StudentValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FileFiller implements ArrayFiller {

    private final String path;

    public FileFiller(String path) {
        this.path = path;
    }

    @Override
    public ArrayList<Student> fill(int length) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            ArrayList<Student> students = reader.lines()
                    .limit(length)
                    .peek(StudentValidator::validateCsvLine)
                    .map(this::parseStudent)
                    .collect(Collectors.toCollection(ArrayList::new));

            if (students.size() < length) {
                throw new IllegalArgumentException(
                        "В файле меньше строк, чем требуется. Нужно: " + length + ", найдено: " + students.size()
                );
            }

            return students;

        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private Student parseStudent(String line) {
        String[] parts = line.split(",", -1);

        return new Student.Builder()
                .groupNumber(parts[0].trim())
                .gpa(Double.parseDouble(parts[1].trim()))
                .recordBookId(parts[2].trim())
                .build();
    }
}
