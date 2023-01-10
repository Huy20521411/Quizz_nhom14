package com.example.quizz_nhom14.object;

public class JoinQuiz {
    private Quiz quiz;
    private User user;

    public JoinQuiz() {
    }

    public JoinQuiz(Quiz quiz, User user) {
        this.quiz = quiz;
        this.user = user;
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
}
