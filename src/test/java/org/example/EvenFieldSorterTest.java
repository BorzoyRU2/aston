package org.example;

import org.example.model.Student;
import org.example.sort.BubbleSortStrategy;
import org.example.sort.EvenFieldSorter;

import java.util.ArrayList;

public class EvenFieldSorterTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== EvenFieldSorterTest ===");

        testEvenGpasSortedOddStayInPlace();
        testAllOddNoChange();
        testAllEvenGetSorted();
        testEmptyList();

        System.out.println("\nРезультат: " + passed + " пройдено, " + failed + " провалено.");
    }

    /**
     * gpa: 10(чёт), 15(нечёт), 6(чёт), 9(нечёт), 4(чёт)
     * Чётные на позициях 0,2,4 → значения [10,6,4] → после сортировки [4,6,10]
     * Нечётные на позициях 1,3 → остаются [15,9]
     * Итог: [4, 15, 6, 9, 10]
     */
    private static void testEvenGpasSortedOddStayInPlace() {
        ArrayList<Student> list = makeStudents(
                s("A", 10.0, "100001"),
                s("B", 15.0, "100002"),
                s("C",  6.0, "100003"),
                s("D",  9.0, "100004"),
                s("E",  4.0, "100005")
        );

        new EvenFieldSorter().sortEvenOnly(list, "gpa", new BubbleSortStrategy());

        double[] expected = {4.0, 15.0, 6.0, 9.0, 10.0};
        boolean ok = true;
        for (int i = 0; i < expected.length; i++) {
            if (Double.compare(list.get(i).getGpa(), expected[i]) != 0) {
                ok = false;
                break;
            }
        }

        assertTrue("testEvenGpasSortedOddStayInPlace", ok);
    }

    private static void testAllOddNoChange() {
        ArrayList<Student> list = makeStudents(
                s("A", 11.0, "100001"),
                s("B", 33.0, "100002"),
                s("C",  7.0, "100003")
        );

        new EvenFieldSorter().sortEvenOnly(list, "gpa", new BubbleSortStrategy());

        // все нечётные — порядок не меняется
        assertTrue("testAllOddNoChange: pos0=11", Double.compare(list.get(0).getGpa(), 11.0) == 0);
        assertTrue("testAllOddNoChange: pos1=33", Double.compare(list.get(1).getGpa(), 33.0) == 0);
        assertTrue("testAllOddNoChange: pos2=7",  Double.compare(list.get(2).getGpa(),  7.0) == 0);
    }

    private static void testAllEvenGetSorted() {
        ArrayList<Student> list = makeStudents(
                s("A", 40.0, "100001"),
                s("B", 20.0, "100002"),
                s("C", 60.0, "100003")
        );

        new EvenFieldSorter().sortEvenOnly(list, "gpa", new BubbleSortStrategy());

        assertTrue("testAllEvenGetSorted: pos0=20", Double.compare(list.get(0).getGpa(), 20.0) == 0);
        assertTrue("testAllEvenGetSorted: pos1=40", Double.compare(list.get(1).getGpa(), 40.0) == 0);
        assertTrue("testAllEvenGetSorted: pos2=60", Double.compare(list.get(2).getGpa(), 60.0) == 0);
    }

    private static void testEmptyList() {
        ArrayList<Student> list = new ArrayList<>();
        try {
            new EvenFieldSorter().sortEvenOnly(list, "gpa", new BubbleSortStrategy());
            assertTrue("testEmptyList (нет исключения)", true);
        } catch (Exception e) {
            assertTrue("testEmptyList (нет исключения)", false);
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
