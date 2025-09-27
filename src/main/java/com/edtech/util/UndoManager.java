package com.edtech.util;

import java.util.Stack;

public class UndoManager {
    private final Stack<Runnable> undoStack = new Stack<>();
    public void registerUndo(Runnable undoAction) { undoStack.push(undoAction); }
    public void undo() {
        if (!undoStack.isEmpty()) {
            undoStack.pop().run();
            System.out.println("Last action undone.");
        } else {
            System.out.println("No actions to undo.");
        }
    }
}