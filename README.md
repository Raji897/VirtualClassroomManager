# VirtualClassroomManager

## How to Run in VS Code

1. Install Java Extension Pack.
2. Place files as above.
3. Open folder in VS Code.
4. Right-click `VirtualClassroomApp.java` → Run Java.
   - Or:
     ```
     cd src/main/java
     javac com/edtech/app/VirtualClassroomApp.java
     java com.edtech.app.VirtualClassroomApp
     ```

## Valid Inputs

- Class/Student names: at least 2 chars, letters/numbers/underscore/dash
- Assignment details: not empty

## Invalid Inputs

- Names too short, or with invalid symbols
- Referring to non-existent classrooms/students/assignments
- Assignment details empty

## Sample Session

**Valid:**
```
add_classroom Math101
[✔] Classroom 'Math101' created.

add_student Alice Math101
[✔] Student 'Alice' enrolled in 'Math101'.

schedule_assignment Math101 HW1
[✔] Assignment 'HW1' scheduled for 'Math101'.

submit_assignment Alice Math101 HW1
Assignment submitted by Student Alice in Math101.

assignment_status Math101 HW1
Submissions for HW1 in Math101:
 - Alice

classroom_details Math101
Classroom: Math101
Number of students: 1
Number of assignments: 1

list_all_students
All students in all classrooms:
 - Alice (Class: Math101)

search_student Alice
Student Alice is enrolled in classroom: Math101

undo
Last action undone.

remove_assignment Math101 HW1
[✔] Assignment 'HW1' removed from 'Math101'.

history
Command History:
1. add_classroom Math101
2. add_student Alice Math101
3. schedule_assignment Math101 HW1
...

repeat 2
Repeating: add_student Alice Math101
[✖] Student already enrolled.

statistics Math101
Statistics for Math101:
Total Students: 1
Total Assignments: 0

exit
Thank you for using Virtual Classroom Manager! 👋
```

**Invalid:**
```
add_classroom A
[✖] Invalid classroom name. Usage: add_classroom [ClassName]

add_student Bob Math999
[✖] Classroom does not exist.

schedule_assignment Math101 
[✖] Usage: schedule_assignment [ClassName] [AssignmentDetail]

submit_assignment Alice Physics HW2
[✖] Classroom does not exist.

remove_student Bob Math101
[✖] Student not found in classroom.
```
