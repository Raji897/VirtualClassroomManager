package com.edtech.classroom;

import java.util.HashSet;
import java.util.Set;

public class Classroom {
    private final String name;
    private final Set<String> students = new HashSet<>();
    public Classroom(String name) { this.name = name; }
    public String getName() { return name; }
    public Set<String> getStudents() { return students; }
    public boolean addStudent(String studentId) { return students.add(studentId); }
    public boolean removeStudent(String studentId) { return students.remove(studentId); }
}