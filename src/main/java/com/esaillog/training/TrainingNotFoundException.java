package com.esaillog.training;

public class TrainingNotFoundException extends Exception{
    public TrainingNotFoundException(String id) {
        super("Training not found with id: " + id);
    }
}
