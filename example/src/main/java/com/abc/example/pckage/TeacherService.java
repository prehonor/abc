package com.abc.example.pckage;

public class TeacherService {
    private StudentService studentService;
    private String name;
    private int limitNum;

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void inventStudentParent(){
        studentService.callDaddy();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }
}
