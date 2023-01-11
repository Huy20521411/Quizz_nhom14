package com.example.quizz_nhom14.object;

public class Quiz {
    private int ID=-1;
    private String Name;//Tên Quiz
    private String Sub;//Tên môn học
    private int numof_questions;//Số câu hỏi
    private String Teacher;//Tên giáo viên

    public Quiz() {
    }

    public Quiz(String name, String sub, int numof_questions, String teacher) {
        this.ID = ID;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
