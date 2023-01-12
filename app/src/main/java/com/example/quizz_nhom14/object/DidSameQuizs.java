package com.example.quizz_nhom14.object;

import java.util.ArrayList;

public class DidSameQuizs {
    public ArrayList<DidQuiz> list=new ArrayList<>();

    private int getCountDidQuiz(){
        return this.list.size();
    }

    public long getAverage() {
        int S=0,A;
        for(int i=0;i<this.list.size();i++){
            S+=this.list.get(i).getResult();
        }
        A=(int)S/list.size();
        return A;
    }

}
