package com.edtech.student;

import com.edtech.classroom.ClassroomManager;
import com.edtech.classroom.Classroom;
import com.edtech.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private static final StudentManager instance = new StudentManager();
    private final Logger logger = Logger.getInstance();
    private StudentManager() {}

    public static StudentManager getInstance() { return instance; }

    public void addStudent(String studentId, String className) {
        Classroom classroom = ClassroomManager.getInstance().getClassroom(className);
        if (classroom == null) {
            System.out.println("Classroom does not exist.");
            logger.logWarn("Tried to enroll student in non-existent classroom: " + className);
            return;
        }
        if (!classroom.addStudent(studentId)) {
            System.out.println("Student already enrolled.");
            logger.logWarn("Duplicate student enrollment: " + studentId + " in " + className);
            return;
        }
        System.out.println("Student " + studentId + " has been enrolled in " + className + ".");
        logger.logInfo("Student enrolled: " + studentId + " in " + className);
    }

    public void removeStudent(String studentId, String className) {
        Classroom classroom = ClassroomManager.getInstance().getClassroom(className);
        if (classroom == null) {
            System.out.println("Classroom does not exist.");
            return;
        }
        if (classroom.removeStudent(studentId)) {
            System.out.println("Student " + studentId + " removed from " + className + ".");
            logger.logInfo("Student removed: " + studentId + " from " + className);
        } else {
            System.out.println("Student not found in classroom.");
            logger.logWarn("Tried to remove non-existent student: " + studentId + " from " + className);
        }
    }

    public void listStudents(String className) {
        Classroom classroom = ClassroomManager.getInstance().getClassroom(className);
        if (classroom == null) {
            System.out.println("Classroom does not exist.");
            return;
        }
        if (classroom.getStudents().isEmpty()) {
            System.out.println("No students enrolled in " + className + ".");
            return;
        }
        System.out.println("Students in " + className + ":");
        for (String studentId : classroom.getStudents()) {
            System.out.println(" - " + studentId);
        }
    }

    public void listAllStudents() {
        System.out.println("All students in all classrooms:");
        boolean found = false;
        for (Classroom classroom : ClassroomManager.getInstance().getAllClassrooms()) {
            for (String studentId : classroom.getStudents()) {
                System.out.println(" - " + studentId + " (Class: " + classroom.getName() + ")");
                found = true;
            }
        }
        if (!found) System.out.println("No students enrolled yet.");
    }

    public void searchStudent(String studentId) {
        boolean found = false;
        for (Classroom classroom : ClassroomManager.getInstance().getAllClassrooms()) {
            if (classroom.getStudents().contains(studentId)) {
                System.out.println("Student " + studentId + " is enrolled in classroom: " + classroom.getName());
                found = true;
            }
        }
        if (!found) System.out.println("Student " + studentId + " not found in any classroom.");
    }

    public List<String> getStudentIds(String className) {
        Classroom classroom = ClassroomManager.getInstance().getClassroom(className);
        if (classroom == null) return new ArrayList<>();
        return new ArrayList<>(classroom.getStudents());
    }
}