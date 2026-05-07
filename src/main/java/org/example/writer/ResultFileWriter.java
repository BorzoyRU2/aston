package org.example.writer;

import org.example.model.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ResultFileWriter {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public void writeResults(ArrayList<Student> students,
                             String title,
                             String path) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {

            writer.write("=".repeat(50));
            writer.newLine();

            writer.write("Дата: " + LocalDateTime.now().format(FORMATTER));
            writer.newLine();

            writer.write(title);
            writer.newLine();

            writer.write("-".repeat(50));
            writer.newLine();

            writer.write(String.format("%-6s %-18s %-8s %-14s",
                    "№", "Группа", "ГПА", "Зач. кн."));
            writer.newLine();

            writer.write("-".repeat(50));
            writer.newLine();

            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                writer.write(String.format("%-6d %-18s %-8.2f %-14s",
                        i + 1,
                        s.getGroupNumber(),
                        s.getGpa(),
                        s.getRecordBookId()));
                writer.newLine();
            }

            writer.newLine();
        }
    }
}
