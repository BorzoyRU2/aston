package org.example;

import org.example.model.Student;
import org.example.search.StudentSearcher;

import java.util.ArrayList;

public class StudentSearcherTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== StudentSearcherTest ===");

        testCountByGroupNumber();
        testCountByGpa();
        testCountByRecordBookId();
        testCountReturnsZeroWhenNotFound();
        testCountOnEmptyList();
        testCountWithSingleThread();
        testCountWithManyThreads();

        System.out.println("\nРезультат: " + passed + " пройдено, " + failed + " провалено.");
    }

    private static void testCountByGroupNumber() {
        ArrayList<Student> list = makeStudents(
                s("KI-23", 80.0, "100001"),
                s("PI-21", 70.0, "100002"),
                s("KI-23", 60.0, "100003"),
                s("IS-22", 50.0, "100004")
        );

        try {
            int count = new StudentSearcher(2).countOccurrences(list, "groupNumber", "KI-23");
            assertEquals("testCountByGroupNumber", 2, count);
        } catch (InterruptedException e) {
            System.out.println("[FAIL] testCountByGroupNumber — прерван");
            failed++;
        }
    }

    private static void testCountByGpa() {
        ArrayList<Student> list = makeStudents(
                s("A", 55.0, "100001"),
                s("B", 55.0, "100002"),
                s("C", 70.0, "100003")
        );

        try {
            int count = new StudentSearcher(2).countOccurrences(list, "gpa", "55.0");
            assertEquals("testCountByGpa", 2, count);
        } catch (InterruptedException e) {
            System.out.println("[FAIL] testCountByGpa — прерван");
            failed++;
        }
    }

    private static void testCountByRecordBookId() {
        ArrayList<Student> list = makeStudents(
                s("A", 80.0, "123456"),
                s("B", 90.0, "654321"),
                s("C", 70.0, "123456")
        );

        try {
            int count = new StudentSearcher(2).countOccurrences(list, "recordBookId", "123456");
            assertEquals("testCountByRecordBookId", 2, count);
        } catch (InterruptedException e) {
            System.out.println("[FAIL] testCountByRecordBookId — прерван");
            failed++;
        }
    }

    private static void testCountReturnsZeroWhenNotFound() {
        ArrayList<Student> list = makeStudents(
                s("KI-23", 80.0, "100001"),
                s("PI-21", 70.0, "100002")
        );

        try {
            int count = new StudentSearcher(2).countOccurrences(list, "groupNumber", "IS-99");
            assertEquals("testCountReturnsZeroWhenNotFound", 0, count);
        } catch (InterruptedException e) {
            System.out.println("[FAIL] testCountReturnsZeroWhenNotFound — прерван");
            failed++;
        }
    }

    private static void testCountOnEmptyList() {
        try {
            int count = new StudentSearcher(2).countOccurrences(new ArrayList<>(), "groupNumber", "KI-23");
            assertEquals("testCountOnEmptyList", 0, count);
        } catch (InterruptedException e) {
            System.out.println("[FAIL] testCountOnEmptyList — прерван");
            failed++;
        }
    }

    private static void testCountWithSingleThread() {
        ArrayList<Student> list = makeStudents(
                s("KI-23", 80.0, "100001"),
                s("KI-23", 90.0, "100002"),
                s("PI-21", 70.0, "100003")
        );

        try {
            int count = new StudentSearcher(1).countOccurrences(list, "groupNumber", "KI-23");
            assertEquals("testCountWithSingleThread", 2, count);
        } catch (InterruptedException e) {
            System.out.println("[FAIL] testCountWithSingleThread — прерван");
            failed++;
        }
    }

    private static void testCountWithManyThreads() {
        // потоков больше, чем элементов — не должно быть ошибок
        ArrayList<Student> list = makeStudents(
                s("KI-23", 80.0, "100001"),
                s("KI-23", 90.0, "100002")
        );

        try {
            int count = new StudentSearcher(10).countOccurrences(list, "groupNumber", "KI-23");
            assertEquals("testCountWithManyThreads", 2, count);
        } catch (InterruptedException e) {
            System.out.println("[FAIL] testCountWithManyThreads — прерван");
            failed++;
        }
    }

    // --- вспомогательные ---

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

    private static void assertEquals(String testName, int expected, int actual) {
        if (expected == actual) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.out.println("[FAIL] " + testName + " | ожидалось: " + expected + ", получено: " + actual);
            failed++;
        }
    }
}
