package com.example.quizz_nhom14.object;

import java.io.Serializable;
import java.util.ArrayList;

public class DidSameQuizs implements Serializable {
    public ArrayList<DidQuiz> list=new ArrayList<>();

    private int getCountDidQuiz(){
        return this.list.size();
    }

    public DidSameQuizs(ArrayList<DidQuiz> list) {
        this.list = list;
    }
    public DidSameQuizs() {}

    public float getAverage() {
        float S=0f,A;
        for(int i=0;i<this.list.size();i++){
            S+=(((float)this.list.get(i).getResult()/(float)list.get(i).getQuiz().getNumof_questions())/ (float)list.size())*100;
        }
        return S;
    }

}
