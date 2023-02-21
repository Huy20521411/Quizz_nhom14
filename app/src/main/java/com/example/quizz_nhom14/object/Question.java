package com.example.quizz_nhom14.object;

import com.example.quizz_nhom14.Data.Time;

import java.util.Calendar;

public class Question {
    private int questionID;
    private String name;
    private String answerA,answerB,answerC,answerD;
    private int correctAnswer;
    private int quizID;
    private Time time_question;

    public Question() {
    }

    public Question(int questionID, String name, String answerA, String answerB, String answerC, String answerD, int correctAnswer, int quizID,Time time_question) {
        this.questionID = questionID;
        this.name = name;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
        this.quizID = quizID;
        this.time_question = time_question;
    }

    public Question(int questionID, String name, String answerA, String answerB, String answerC, String answerD, int correctAnswer, int quizID) {
        this.questionID = questionID;
        this.name = name;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
        this.quizID = quizID;
        this.time_question = new Time(0,30);
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public Time getTime_question() {
        return time_question;
    }

    public void setTime_question(Time time_question) {
        this.time_question = time_question;
    }
}
