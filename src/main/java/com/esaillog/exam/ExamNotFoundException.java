package com.esaillog.exam;

public class ExamNotFoundException extends RuntimeException {
    public ExamNotFoundException(String id) {
        super("Exam not found with id: " + id);
    }
}
