package com.edtech.assignment;

import java.util.HashSet;
import java.util.Set;

public class Assignment {
    private final String details;
    private final Set<String> submissions = new HashSet<>();
    public Assignment(String details) { this.details = details; }
    public String getDetails() { return details; }
    public Set<String> getSubmissions() { return submissions; }
    public boolean submit(String studentId) { return submissions.add(studentId); }
}