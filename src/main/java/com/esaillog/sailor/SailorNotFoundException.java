package com.esaillog.sailor;

public class SailorNotFoundException extends Exception {
    public SailorNotFoundException(String id) {
        super("Sailor not found with id: " + id);
    }
}
