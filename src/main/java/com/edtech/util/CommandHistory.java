package com.edtech.util;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private final List<String> history = new ArrayList<>();
    public void add(String command) { history.add(command); }
    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No commands executed yet.");
            return;
        }
        System.out.println("Command History:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }
    public String get(int index) {
        if (index >= 0 && index < history.size()) {
            return history.get(index);
        }
        return null;
    }
}