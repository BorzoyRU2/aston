package org.example;

import org.example.model.Student;
import org.example.writer.ResultFileWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ResultFileWriterTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== ResultFileWriterTest ===");

        testFileCreatedAndContainsData();
        testAppendMode();

        System.out.println("\nРезультат: " + passed + " пройдено, " + failed + " провалено.");
    }

    private static void testFileCreatedAndContainsData() {
        File tempFile = new File("test_output.txt");
        tempFile.delete();

        ArrayList<Student> list = makeStudents(
                s("KI-23", 87.5, "123456"),
                s("PI-21", 55.0, "654321")
        );

        try {
            new ResultFileWriter().writeResults(list, "Тестовый заголовок", tempFile.getPath());

            String content = readFile(tempFile);

            assertTrue("testFileCreatedAndContainsData: файл создан", tempFile.exists());
            assertTrue("testFileCreatedAndContainsData: содержит заголовок", content.contains("Тестовый заголовок"));
            assertTrue("testFileCreatedAndContainsData: содержит KI-23", content.contains("KI-23"));
            assertTrue("testFileCreatedAndContainsData: содержит PI-21", content.contains("PI-21"));

        } catch (IOException e) {
            System.out.println("[FAIL] testFileCreatedAndContainsData — исключение: " + e.getMessage());
            failed++;
        } finally {
            tempFile.delete();
        }
    }

    private static void testAppendMode() {
        File tempFile = new File("test_append.txt");
        tempFile.delete();

        ArrayList<Student> list = makeStudents(s("KI-23", 80.0, "111111"));

        try {
            ResultFileWriter writer = new ResultFileWriter();
            writer.writeResults(list, "Первая запись", tempFile.getPath());
            writer.writeResults(list, "Вторая запись", tempFile.getPath());

            String content = readFile(tempFile);

            assertTrue("testAppendMode: содержит первую запись", content.contains("Первая запись"));
            assertTrue("testAppendMode: содержит вторую запись", content.contains("Вторая запись"));

        } catch (IOException e) {
            System.out.println("[FAIL] testAppendMode — исключение: " + e.getMessage());
            failed++;
        } finally {
            tempFile.delete();
        }
    }

    // --- вспомогательные ---

    private static String readFile(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    private static Student s(String group, double gpa, String id) {
        return new Student.Builder()
                .groupNumber(group)
                .gpa(gpa)
                .recordBookId(id)
                .build();
    }

    private static ArrayList<Student> makeStudents(Student... students) {
        ArrayList<Student> list = new ArrayList<>();
        for (Student st : students) {
            list.add(st);
        }
        return list;
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.out.println("[FAIL] " + testName);
            failed++;
        }
    }
}
