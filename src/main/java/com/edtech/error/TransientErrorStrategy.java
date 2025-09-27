package com.edtech.error;

public class TransientErrorStrategy {
    public void handle(Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
        System.out.println("Please try again or contact support if the issue persists.");
    }
}