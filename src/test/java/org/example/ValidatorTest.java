package org.example;

import org.example.validation.StudentValidator;

public class ValidatorTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== ValidatorTest ===");

        testValidGroupNumber();
        testEmptyGroupNumberThrows();
        testValidGpa();
        testGpaBelowZeroThrows();
        testGpaAbove100Throws();
        testValidRecordBookId();
        testRecordBookIdWithLettersThrows();
        testEmptyRecordBookIdThrows();
        testValidCsvLine();
        testCsvLineWithWrongFieldCountThrows();
        testCsvLineWithInvalidGpaThrows();

        System.out.println("\nРезультат: " + passed + " пройдено, " + failed + " провалено.");
    }

    private static void testValidGroupNumber() {
        assertNoException("testValidGroupNumber",
                () -> StudentValidator.validateGroupNumber("KI-23"));
    }

    private static void testEmptyGroupNumberThrows() {
        assertThrows("testEmptyGroupNumberThrows",
                () -> StudentValidator.validateGroupNumber(""));
    }

    private static void testValidGpa() {
        assertNoException("testValidGpa",
                () -> StudentValidator.validateGpa(75.5));
    }

    private static void testGpaBelowZeroThrows() {
        assertThrows("testGpaBelowZeroThrows",
                () -> StudentValidator.validateGpa(-1.0));
    }

    private static void testGpaAbove100Throws() {
        assertThrows("testGpaAbove100Throws",
                () -> StudentValidator.validateGpa(100.1));
    }

    private static void testValidRecordBookId() {
        assertNoException("testValidRecordBookId",
                () -> StudentValidator.validateRecordBookId("123456"));
    }

    private static void testRecordBookIdWithLettersThrows() {
        assertThrows("testRecordBookIdWithLettersThrows",
                () -> StudentValidator.validateRecordBookId("12AB56"));
    }

    private static void testEmptyRecordBookIdThrows() {
        assertThrows("testEmptyRecordBookIdThrows",
                () -> StudentValidator.validateRecordBookId(""));
    }

    private static void testValidCsvLine() {
        assertNoException("testValidCsvLine",
                () -> StudentValidator.validateCsvLine("KI-23,87.5,123456"));
    }

    private static void testCsvLineWithWrongFieldCountThrows() {
        assertThrows("testCsvLineWithWrongFieldCountThrows",
                () -> StudentValidator.validateCsvLine("KI-23,87.5"));
    }

    private static void testCsvLineWithInvalidGpaThrows() {
        assertThrows("testCsvLineWithInvalidGpaThrows",
                () -> StudentValidator.validateCsvLine("KI-23,abc,123456"));
    }

    // --- вспомогательные ---

    private interface Runnable {
        void run() throws Exception;
    }

    private static void assertNoException(String testName, Runnable action) {
        try {
            action.run();
            System.out.println("[PASS] " + testName);
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] " + testName + " — неожиданное исключение: " + e.getMessage());
            failed++;
        }
    }

    private static void assertThrows(String testName, Runnable action) {
        try {
            action.run();
            System.out.println("[FAIL] " + testName + " — ожидалось исключение, но его нет");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("[PASS] " + testName);
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] " + testName + " — неожиданный тип исключения: " + e.getClass().getSimpleName());
            failed++;
        }
    }
}
