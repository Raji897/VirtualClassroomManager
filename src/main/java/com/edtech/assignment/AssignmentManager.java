package com.edtech.assignment;

import com.edtech.classroom.ClassroomManager;
import com.edtech.classroom.Classroom;
import com.edtech.util.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class AssignmentManager {
    private static final AssignmentManager instance = new AssignmentManager();
    private final Map<String, Map<String, Assignment>> assignments = new HashMap<>();
    private final Logger logger = Logger.getInstance();
    private AssignmentManager() {}

    public static AssignmentManager getInstance() { return instance; }

    public void scheduleAssignment(String className, String details) {
        Classroom classroom = ClassroomManager.getInstance().getClassroom(className);
        if (classroom == null) {
            System.out.println("Classroom does not exist.");
            logger.logWarn("Tried to schedule assignment for non-existent classroom: " + className);
            return;
        }
        assignments.computeIfAbsent(className, k -> new HashMap<>());
        Map<String, Assignment> classAssignments = assignments.get(className);
        if (classAssignments.containsKey(details)) {
            System.out.println("Assignment already scheduled for this classroom.");
            logger.logWarn("Duplicate assignment scheduled: " + details + " for " + className);
            return;
        }
        classAssignments.put(details, new Assignment(details));
        System.out.println("Assignment for " + className + " has been scheduled.");
        logger.logInfo("Assignment scheduled: " + details + " for " + className);
    }

    public void removeAssignment(String className, String details) {
        Map<String, Assignment> classAssignments = assignments.get(className);
        if (classAssignments != null && classAssignments.remove(details) != null) {
            System.out.println("Assignment removed.");
            logger.logInfo("Assignment removed: " + details + " from " + className);
        } else {
            System.out.println("Assignment not found.");
            logger.logWarn("Tried to remove non-existent assignment: " + details + " from " + className);
        }
    }

    public void listAssignments(String className) {
        Map<String, Assignment> classAssignments = assignments.get(className);
        if (classAssignments == null || classAssignments.isEmpty()) {
            System.out.println("No assignments scheduled for " + className + ".");
            return;
        }
        System.out.println("Assignments for " + className + ":");
        for (String details : classAssignments.keySet()) {
            System.out.println(" - " + details);
        }
    }

    public void submitAssignment(String studentId, String className, String details) {
        Classroom classroom = ClassroomManager.getInstance().getClassroom(className);
        if (classroom == null) {
            System.out.println("Classroom does not exist.");
            return;
        }
        if (!classroom.getStudents().contains(studentId)) {
            System.out.println("Student not enrolled in classroom.");
            return;
        }
        Map<String, Assignment> classAssignments = assignments.get(className);
        if (classAssignments == null || !classAssignments.containsKey(details)) {
            System.out.println("Assignment not found in classroom.");
            return;
        }
        Assignment assignment = classAssignments.get(details);
        if (assignment.submit(studentId)) {
            System.out.println("Assignment submitted by Student " + studentId + " in " + className + ".");
            logger.logInfo("Assignment submitted: " + details + " by " + studentId + " in " + className);
        } else {
            System.out.println("Student already submitted this assignment.");
            logger.logWarn("Duplicate submission: " + details + " by " + studentId + " in " + className);
        }
    }

    public void assignmentSubmissionStatus(String className, String assignmentDetail) {
        Map<String, Assignment> classAssignments = assignments.get(className);
        if (classAssignments == null || !classAssignments.containsKey(assignmentDetail)) {
            System.out.println("Assignment not found.");
            return;
        }
        Assignment assignment = classAssignments.get(assignmentDetail);
        if (assignment.getSubmissions().isEmpty()) {
            System.out.println("No submissions yet for " + assignmentDetail + " in " + className + ".");
        } else {
            System.out.println("Submissions for " + assignmentDetail + " in " + className + ":");
            for (String studentId : assignment.getSubmissions()) {
                System.out.println(" - " + studentId);
            }
        }
    }

    public List<Assignment> getAssignments(String className) {
        Map<String, Assignment> classAssignments = assignments.get(className);
        if (classAssignments == null) return new ArrayList<>();
        return new ArrayList<>(classAssignments.values());
    }
}