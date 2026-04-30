package org.example.filler;

import org.example.model.Student;
import org.example.validation.StudentValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileFiller implements ArrayFiller {

    private final String path;

    public FileFiller(String path) {
        this.path = path;
    }

    @Override
    public ArrayList<Student> fill(int length) {
        ArrayList<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            for (int i = 0; i < length; i++) {
                line = reader.readLine();

                if (line == null) {
                    throw new IllegalArgumentException(
                            "В файле меньше строк, чем требуется. Нужно: " + length + ", найдено: " + i
                    );
                }

                StudentValidator.validateCsvLine(line);

                students.add(parseStudent(line));
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
