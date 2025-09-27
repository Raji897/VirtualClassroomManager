package com.edtech.app;

import com.edtech.classroom.*;
import com.edtech.student.*;
import com.edtech.assignment.*;
import com.edtech.util.*;
import com.edtech.error.*;
import com.edtech.stats.StatisticsManager;

import java.util.Scanner;

public class VirtualClassroomApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClassroomManager classroomManager = ClassroomManager.getInstance();
    private static final StudentManager studentManager = StudentManager.getInstance();
    private static final AssignmentManager assignmentManager = AssignmentManager.getInstance();
    private static final Logger logger = Logger.getInstance();
    private static final ErrorHandler errorHandler = new ErrorHandler();
    private static final CommandHistory commandHistory = new CommandHistory();
    private static final UndoManager undoManager = new UndoManager();

    public static void main(String[] args) {
        logger.logInfo("Application started.");
        showWelcome();

        boolean exit = false;
        while (!exit) {
            try {
                showMenu();
                String input = scanner.nextLine().trim();
                commandHistory.add(input);
                exit = handleInput(input);
            } catch (Exception e) {
                errorHandler.handleTransientError(e);
            }
        }
        logger.logInfo("Application exited.");
        scanner.close();
    }

    private static void showWelcome() {
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│        VIRTUAL CLASSROOM MANAGER v2.0       │");
        System.out.println("└─────────────────────────────────────────────┘");
        System.out.println("Welcome! 🚀");
        System.out.println("Type 'help' to see available commands.");
        System.out.println("Type 'exit' to quit the application.\n");
    }

    private static void showMenu() {
        System.out.println("\n═══════════════════════ MAIN MENU ═══════════════════════");
        System.out.println("| 1. add_classroom [ClassName]          | Create a new classroom");
        System.out.println("| 2. list_classrooms                    | Show all classrooms");
        System.out.println("| 3. remove_classroom [ClassName]       | Delete a classroom");
        System.out.println("| 4. add_student [StudentID] [ClassName]| Enroll student");
        System.out.println("| 5. list_students [ClassName]          | List classroom students");
        System.out.println("| 6. remove_student [StudentID] [Class] | Remove student");
        System.out.println("| 7. list_all_students                  | Show all students");
        System.out.println("| 8. schedule_assignment [Class] [Detail]| Create assignment");
        System.out.println("| 9. list_assignments [ClassName]       | Show assignments");
        System.out.println("| 10. remove_assignment [Class] [Detail]| Delete assignment");
        System.out.println("| 11. submit_assignment [ID] [Class] [Detail]| Submit assignment");
        System.out.println("| 12. assignment_status [Class] [Detail]| Who submitted?");
        System.out.println("| 13. search_student [StudentID]        | Find student");
        System.out.println("| 14. classroom_details [ClassName]     | Class summary");
        System.out.println("| 15. undo                              | Undo last action");
        System.out.println("| 16. history                           | Command history");
        System.out.println("| 17. repeat [n]                        | Repeat command #n");
        System.out.println("| 18. statistics [ClassName]            | Stats");
        System.out.println("| help                                  | Show menu");
        System.out.println("| exit                                  | Quit");
        System.out.println("══════════════════════════════════════════════════════════");
        System.out.print("🌟 Enter command: ");
    }

    private static void printSuccess(String message) {
        System.out.println("[✔] " + message);
    }

    private static void printError(String message) {
        System.out.println("[✖] " + message);
    }

    private static boolean handleInput(String input) {
        String[] parts = input.split("\\s+", 2);
        String cmd = parts[0].toLowerCase();

        try {
            switch (cmd) {
                case "add_classroom":
                    if (parts.length > 1 && InputValidator.isValidName(parts[1])) {
                        undoManager.registerUndo(() -> classroomManager.removeClassroom(parts[1]));
                        classroomManager.addClassroom(parts[1]);
                        printSuccess("Classroom '" + parts[1] + "' created.");
                    } else printError("Invalid classroom name. Usage: add_classroom [ClassName]");
                    break;
                case "list_classrooms":
                    classroomManager.listClassrooms();
                    break;
                case "remove_classroom":
                    if (parts.length > 1 && InputValidator.isValidName(parts[1])) {
                        Classroom removed = classroomManager.getClassroom(parts[1]);
                        undoManager.registerUndo(() -> { if (removed != null) classroomManager.addClassroom(parts[1]); });
                        classroomManager.removeClassroom(parts[1]);
                        printSuccess("Classroom '" + parts[1] + "' removed.");
                    } else printError("Usage: remove_classroom [ClassName]");
                    break;
                case "add_student":
                    String[] stParts = parts.length > 1 ? parts[1].split("\\s+", 2) : new String[0];
                    if (stParts.length == 2 && InputValidator.isValidId(stParts[0]) && InputValidator.isValidName(stParts[1])) {
                        undoManager.registerUndo(() -> studentManager.removeStudent(stParts[0], stParts[1]));
                        studentManager.addStudent(stParts[0], stParts[1]);
                        printSuccess("Student '" + stParts[0] + "' enrolled in '" + stParts[1] + "'.");
                    } else printError("Usage: add_student [StudentID] [ClassName]");
                    break;
                case "list_students":
                    if (parts.length > 1 && InputValidator.isValidName(parts[1])) studentManager.listStudents(parts[1]);
                    else printError("Usage: list_students [ClassName]");
                    break;
                case "remove_student":
                    String[] rmParts = parts.length > 1 ? parts[1].split("\\s+", 2) : new String[0];
                    if (rmParts.length == 2 && InputValidator.isValidId(rmParts[0]) && InputValidator.isValidName(rmParts[1])) {
                        undoManager.registerUndo(() -> studentManager.addStudent(rmParts[0], rmParts[1]));
                        studentManager.removeStudent(rmParts[0], rmParts[1]);
                        printSuccess("Student '" + rmParts[0] + "' removed from '" + rmParts[1] + "'.");
                    } else printError("Usage: remove_student [StudentID] [ClassName]");
                    break;
                case "list_all_students":
                    studentManager.listAllStudents();
                    break;
                case "schedule_assignment":
                    String[] saParts = parts.length > 1 ? parts[1].split("\\s+", 2) : new String[0];
                    if (saParts.length == 2 && InputValidator.isValidName(saParts[0]) && InputValidator.isValidAssignmentDetail(saParts[1])) {
                        undoManager.registerUndo(() -> assignmentManager.removeAssignment(saParts[0], saParts[1]));
                        assignmentManager.scheduleAssignment(saParts[0], saParts[1]);
                        printSuccess("Assignment '" + saParts[1] + "' scheduled for '" + saParts[0] + "'.");
                    } else printError("Usage: schedule_assignment [ClassName] [AssignmentDetail]");
                    break;
                case "list_assignments":
                    if (parts.length > 1 && InputValidator.isValidName(parts[1])) assignmentManager.listAssignments(parts[1]);
                    else printError("Usage: list_assignments [ClassName]");
                    break;
                case "remove_assignment":
                    String[] raParts = parts.length > 1 ? parts[1].split("\\s+", 2) : new String[0];
                    if (raParts.length == 2 && InputValidator.isValidName(raParts[0]) && InputValidator.isValidAssignmentDetail(raParts[1])) {
                        undoManager.registerUndo(() -> assignmentManager.scheduleAssignment(raParts[0], raParts[1]));
                        assignmentManager.removeAssignment(raParts[0], raParts[1]);
                        printSuccess("Assignment '" + raParts[1] + "' removed from '" + raParts[0] + "'.");
                    } else printError("Usage: remove_assignment [ClassName] [AssignmentDetail]");
                    break;
                case "submit_assignment":
                    String[] subParts = parts.length > 1 ? parts[1].split("\\s+", 3) : new String[0];
                    if (subParts.length == 3 && InputValidator.isValidId(subParts[0]) && InputValidator.isValidName(subParts[1]) && InputValidator.isValidAssignmentDetail(subParts[2]))
                        assignmentManager.submitAssignment(subParts[0], subParts[1], subParts[2]);
                    else printError("Usage: submit_assignment [StudentID] [ClassName] [AssignmentDetail]");
                    break;
                case "assignment_status":
                    String[] asParts = parts.length > 1 ? parts[1].split("\\s+", 2) : new String[0];
                    if (asParts.length == 2 && InputValidator.isValidName(asParts[0]) && InputValidator.isValidAssignmentDetail(asParts[1]))
                        assignmentManager.assignmentSubmissionStatus(asParts[0], asParts[1]);
                    else printError("Usage: assignment_status [ClassName] [AssignmentDetail]");
                    break;
                case "search_student":
                    if (parts.length > 1 && InputValidator.isValidId(parts[1])) studentManager.searchStudent(parts[1]);
                    else printError("Usage: search_student [StudentID]");
                    break;
                case "classroom_details":
                    if (parts.length > 1 && InputValidator.isValidName(parts[1])) classroomManager.showClassroomDetails(parts[1]);
                    else printError("Usage: classroom_details [ClassName]");
                    break;
                case "undo":
                    undoManager.undo();
                    break;
                case "history":
                    commandHistory.showHistory();
                    break;
                case "repeat":
                    if (parts.length > 1) {
                        try {
                            int n = Integer.parseInt(parts[1]);
                            String prevCmd = commandHistory.get(n - 1);
                            if (prevCmd != null) {
                                System.out.println("Repeating: " + prevCmd);
                                handleInput(prevCmd);
                            } else {
                                printError("No such command in history.");
                            }
                        } catch (NumberFormatException ex) {
                            printError("Usage: repeat [n]");
                        }
                    } else {
                        printError("Usage: repeat [n]");
                    }
                    break;
                case "statistics":
                    if (parts.length > 1 && InputValidator.isValidName(parts[1])) StatisticsManager.showStatistics(parts[1]);
                    else printError("Usage: statistics [ClassName]");
                    break;
                case "help":
                    showMenu();
                    break;
                case "exit":
                    System.out.println("\nThank you for using Virtual Classroom Manager! 👋\n");
                    return true;
                default:
                    printError("Unknown command. Type 'help' for menu.");
            }
        } catch (Exception e) {
            errorHandler.handleTransientError(e);
        }
        return false;
    }
}