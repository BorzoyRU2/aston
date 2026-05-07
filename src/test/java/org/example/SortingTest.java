package org.example;

import org.example.model.Student;
import org.example.sort.*;

import java.util.ArrayList;

public class SortingTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== SortingTest ===");

        SortStrategy[] strategies = {
                new BubbleSortStrategy(),
                new SelectionSortStrategy(),
                new InsertionSortStrategy(),
                new MergeSortStrategy(),
                new QuickSortStrategy()
        };

        for (SortStrategy strategy : strategies) {
            testSortByGpa(strategy);
            testSortByGroupNumber(strategy);
            testSortByRecordBookId(strategy);
            testSortEmptyList(strategy);
            testSortSingleElement(strategy);
            testSortAlreadySorted(strategy);
        }

        System.out.println("\nРезультат: " + passed + " пройдено, " + failed + " провалено.");
    }

    private static void testSortByGpa(SortStrategy strategy) {
        ArrayList<Student> list = makeStudents(
                s("A", 50.0, "100001"),
                s("B", 10.0, "100002"),
                s("C", 90.0, "100003"),
                s("D", 30.0, "100004")
        );

        strategy.sort(list, "gpa");

        boolean ok = list.get(0).getGpa() <= list.get(1).getGpa()
                  && list.get(1).getGpa() <= list.get(2).getGpa()
                  && list.get(2).getGpa() <= list.get(3).getGpa();

        assertTrue(strategy.getName() + " sortByGpa", ok);
    }

    private static void testSortByGroupNumber(SortStrategy strategy) {
        ArrayList<Student> list = makeStudents(
                s("KI-23", 50.0, "100001"),
                s("IS-21", 60.0, "100002"),
                s("PI-22", 70.0, "100003"),
                s("KI-21", 80.0, "100004")
        );

        strategy.sort(list, "groupNumber");

        boolean ok = true;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getGroupNumber().compareToIgnoreCase(list.get(i + 1).getGroupNumber()) > 0) {
                ok = false;
                break;
            }
        }

        assertTrue(strategy.getName() + " sortByGroupNumber", ok);
    }

    private static void testSortByRecordBookId(SortStrategy strategy) {
        ArrayList<Student> list = makeStudents(
                s("A", 50.0, "500000"),
                s("B", 60.0, "100000"),
                s("C", 70.0, "800000"),
                s("D", 80.0, "200000")
        );

        strategy.sort(list, "recordBookId");

        boolean ok = true;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getRecordBookId().compareTo(list.get(i + 1).getRecordBookId()) > 0) {
                ok = false;
                break;
            }
        }

        assertTrue(strategy.getName() + " sortByRecordBookId", ok);
    }

    private static void testSortEmptyList(SortStrategy strategy) {
        ArrayList<Student> list = new ArrayList<>();
        try {
            strategy.sort(list, "gpa");
            assertTrue(strategy.getName() + " sortEmptyList (нет исключения)", true);
        } catch (Exception e) {
            assertTrue(strategy.getName() + " sortEmptyList (нет исключения)", false);
        }
    }

    private static void testSortSingleElement(SortStrategy strategy) {
        ArrayList<Student> list = makeStudents(s("KI-23", 75.0, "123456"));
        strategy.sort(list, "gpa");
        assertEquals(strategy.getName() + " sortSingleElement: gpa", 75.0, list.get(0).getGpa());
    }

    private static void testSortAlreadySorted(SortStrategy strategy) {
        ArrayList<Student> list = makeStudents(
                s("A", 10.0, "100001"),
                s("B", 20.0, "100002"),
                s("C", 30.0, "100003")
        );

        strategy.sort(list, "gpa");

        boolean ok = list.get(0).getGpa() == 10.0
                  && list.get(1).getGpa() == 20.0
                  && list.get(2).getGpa() == 30.0;

        assertTrue(strategy.getName() + " sortAlreadySorted", ok);
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

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.out.println("[FAIL] " + testName);
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
}
