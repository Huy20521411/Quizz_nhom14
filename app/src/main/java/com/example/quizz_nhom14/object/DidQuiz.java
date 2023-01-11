package com.example.quizz_nhom14.object;

public class DidQuiz {
    private Quiz quiz;//Quiz user đó làm
    private User user;//User làm Quiz
    private int result;//Số câu trả lời đúng
    private String Time;//Thời điểm làm Quiz
    public DidQuiz() {
    }

    public DidQuiz( User user,Quiz quiz, int result,String Time) {
        this.quiz = quiz;
        this.user = user;
        this.result = result;
        this.Time=Time;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
