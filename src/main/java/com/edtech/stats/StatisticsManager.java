package com.edtech.stats;

import com.edtech.classroom.ClassroomManager;
import com.edtech.classroom.Classroom;
import com.edtech.assignment.Assignment;
import com.edtech.assignment.AssignmentManager;

import java.util.List;

public class StatisticsManager {
    public static void showStatistics(String className) {
        Classroom classroom = ClassroomManager.getInstance().getClassroom(className);
        if (classroom == null) {
            System.out.println("Classroom does not exist.");
            return;
        }
        List<Assignment> assignments = AssignmentManager.getInstance().getAssignments(className);
        int totalStudents = classroom.getStudents().size();
        int totalAssignments = assignments.size();

        System.out.printf("Statistics for %s:%n", className);
        System.out.printf("Total Students: %d%n", totalStudents);
        System.out.printf("Total Assignments: %d%n", totalAssignments);
    }
}