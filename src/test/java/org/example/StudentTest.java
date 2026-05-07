package org.example;

import org.example.model.Student;

public class StudentTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== StudentTest ===");

        testBuilderCreatesCorrectFields();
        testBuilderDefaultGpa();
        testToStringContainsAllFields();

        System.out.println("\nРезультат: " + passed + " пройдено, " + failed + " провалено.");
    }

    private static void testBuilderCreatesCorrectFields() {
        Student student = new Student.Builder()
                .groupNumber("KI-23")
                .gpa(87.5)
                .recordBookId("123456")
                .build();

        assertEquals("testBuilderCreatesCorrectFields: groupNumber", "KI-23", student.getGroupNumber());
        assertEquals("testBuilderCreatesCorrectFields: gpa", 87.5, student.getGpa());
        assertEquals("testBuilderCreatesCorrectFields: recordBookId", "123456", student.getRecordBookId());
    }

    private static void testBuilderDefaultGpa() {
        Student student = new Student.Builder()
                .groupNumber("PI-21")
                .recordBookId("999999")
                .build();

        assertEquals("testBuilderDefaultGpa: gpa должен быть 0.0", 0.0, student.getGpa());
    }

    private static void testToStringContainsAllFields() {
        Student student = new Student.Builder()
                .groupNumber("IS-23")
                .gpa(55.0)
                .recordBookId("777777")
                .build();

        String str = student.toString();
        assertTrue("testToStringContainsAllFields: содержит groupNumber", str.contains("IS-23"));
        assertTrue("testToStringContainsAllFields: содержит gpa", str.contains("55.0"));
        assertTrue("testToStringContainsAllFields: содержит recordBookId", str.contains("777777"));
    }

    // --- вспомогательные методы ---

    private static void assertEquals(String testName, String expected, String actual) {
        if (expected.equals(actual)) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.out.println("[FAIL] " + testName + " | ожидалось: " + expected + ", получено: " + actual);
            failed++;
        }
    }

    private static void assertEquals(String testName, double expected, double actual) {
        if (Double.compare(expected, actual) == 0) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.out.println("[FAIL] " + testName + " | ожидалось: " + expected + ", получено: " + actual);
            failed++;
        }
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
