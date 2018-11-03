package com.abc.example.pckage;

public class TeacherService {
    private StudentService studentService;
    private String name;
    private int limitNum;
    private boolean yesOrNo;

    private float averageYear;
    private double averageSalary;

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

    public void setYesOrNo(boolean yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    public void setAverageYear(float averageYear) {
        this.averageYear = averageYear;
    }

    public void setAverageSalary(double averageSalary) {
        this.averageSalary = averageSalary;
    }

}
