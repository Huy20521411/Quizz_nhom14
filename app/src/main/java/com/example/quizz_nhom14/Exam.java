package com.example.quizz_nhom14;

import java.util.Date;

public class QuestionGroup {
    private String Name;
    private Date Deadline;
    private int numof_Questions;

    public QuestionGroup(String name, Date deadline, int numof_Questions) {
        Name = name;
        Deadline = deadline;
        this.numof_Questions = numof_Questions;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getDeadline() {
        return Deadline;
    }

    public void setDeadline(Date deadline) {
        Deadline = deadline;
    }

    public int getNumof_Questions() {
        return numof_Questions;
    }

    public void setNumof_Questions(int numof_Questions) {
        this.numof_Questions = numof_Questions;
    }
}
