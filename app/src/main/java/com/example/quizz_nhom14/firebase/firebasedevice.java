package com.example.quizz_nhom14.firebase;

import com.example.quizz_nhom14.object.DidQuiz;
import com.example.quizz_nhom14.object.DidSameQuizs;
import com.example.quizz_nhom14.object.JoinQuiz;
import com.example.quizz_nhom14.object.Question;
import com.example.quizz_nhom14.object.Quiz;
import com.example.quizz_nhom14.object.User;

public class firebasedevice {

    User user;
    Quiz quiz;
    Question question;
    JoinQuiz joinQuiz;
    DidQuiz didQuiz;
    DidSameQuizs didSameQuizs;

    public firebasedevice(User user, Quiz quiz, Question question, JoinQuiz joinQuiz, DidQuiz didQuiz, DidSameQuizs didSameQuizs) {
        this.user = user;
        this.quiz = quiz;
        this.question = question;
        this.joinQuiz = joinQuiz;
        this.didQuiz = didQuiz;
        this.didSameQuizs = didSameQuizs;
    }
}

