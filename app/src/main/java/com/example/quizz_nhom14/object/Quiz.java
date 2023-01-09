package com.example.quizz_nhom14.object;

import java.util.Date;

public class Exam {
    private String Name;
    private String Sub;
    private int numof_questions;
    private String Teacher;

    public Exam() {
    }

    public Exam(String name, String sub, int numof_questions, String teacher) {
        Name = name;
        Sub = sub;
        this.numof_questions = numof_questions;
        Teacher = teacher;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSub() {
        return Sub;
    }

    public void setSub(String sub) {
        Sub = sub;
    }

    public int getNumof_questions() {
        return numof_questions;
    }

    public void setNumof_questions(int numof_questions) {
        this.numof_questions = numof_questions;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }
}
