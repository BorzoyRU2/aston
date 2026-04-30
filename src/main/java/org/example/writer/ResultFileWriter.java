package org.example.writer;

import org.example.model.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ResultFileWriter {

    public void writeResults(List<Student> students, String title, String path) throws IOException {

        try (FileWriter writer = new FileWriter(path, true)) {

            writer.write("==== " + title + " ====\n");
            writer.write("Время: " + LocalDateTime.now() + "\n");

            for (Student s : students) {
                writer.write(s.toString() + "\n");
            }

            writer.write("\n");
        }
    }
}
