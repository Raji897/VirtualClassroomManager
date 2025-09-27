package com.edtech.classroom;

import com.edtech.util.Logger;
import com.edtech.assignment.AssignmentManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class ClassroomManager {
    private static final ClassroomManager instance = new ClassroomManager();
    private final Map<String, Classroom> classrooms = new HashMap<>();
    private final Logger logger = Logger.getInstance();

    private ClassroomManager() {}

    public static ClassroomManager getInstance() { return instance; }

    public void addClassroom(String name) {
        if (classrooms.containsKey(name)) {
            System.out.println("Classroom already exists.");
            logger.logWarn("Duplicate classroom: " + name);
            return;
        }
        classrooms.put(name, new Classroom(name));
        System.out.println("Classroom " + name + " has been created.");
        logger.logInfo("Classroom created: " + name);
    }

    public void removeClassroom(String name) {
        if (classrooms.remove(name) != null) {
            System.out.println("Classroom " + name + " has been removed.");
            logger.logInfo("Classroom removed: " + name);
        } else {
            System.out.println("Classroom does not exist.");
            logger.logWarn("Tried to remove non-existent classroom: " + name);
        }
    }

    public Classroom getClassroom(String name) { return classrooms.get(name); }
    public void listClassrooms() {
        if (classrooms.isEmpty()) {
            System.out.println("No classrooms available.");
            return;
        }
        System.out.println("Classrooms:");
        for (String name : classrooms.keySet()) System.out.println(" - " + name);
    }
    public Collection<Classroom> getAllClassrooms() { return classrooms.values(); }
    public void showClassroomDetails(String name) {
        Classroom c = getClassroom(name);
        if (c == null) {
            System.out.println("Classroom does not exist.");
            return;
        }
        int numStudents = c.getStudents().size();
        int numAssignments = AssignmentManager.getInstance().getAssignments(name).size();
        System.out.println("Classroom: " + name);
        System.out.println("Number of students: " + numStudents);
        System.out.println("Number of assignments: " + numAssignments);
    }
}