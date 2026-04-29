package org.example.model;


public class Student {

    private final String groupNumber;
    private final double gpa;
    private final String recordBookId;

    // Приватный конструктор
    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.gpa = builder.gpa;
        this.recordBookId = builder.recordBookId;
    }

    // Геттеры
    public String getGroupNumber() {
        return groupNumber;
    }

    public double getGpa() {
        return gpa;
    }

    public String getRecordBookId() {
        return recordBookId;
    }

    // toString()
    @Override
    public String toString() {
        return "Student{" +
                "groupNumber='" + groupNumber + '\'' +
                ", gpa=" + gpa +
                ", recordBookId='" + recordBookId + '\'' +
                '}';
    }

    // ─────────────────────────────
    // Builder
    // ─────────────────────────────

    public static class Builder {
        private String groupNumber;
        private double gpa;
        private String recordBookId;

        public Builder groupNumber(String groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public Builder gpa(double gpa) {
            this.gpa = gpa;
            return this;
        }

        public Builder recordBookId(String recordBookId) {
            this.recordBookId = recordBookId;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
