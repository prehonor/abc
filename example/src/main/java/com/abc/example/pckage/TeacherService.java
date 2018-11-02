package com.abc.example.pckage;

public class TeacherService {
    private StudentService studentService;

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void inventStudentParent(){
        studentService.callDaddy();
    }
}
