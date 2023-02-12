package com.example.quizz_nhom14.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.adapterclass.DidQuizAdapter;
import com.example.quizz_nhom14.object.DidQuiz;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewDidQuiz extends AppCompatActivity {
    TextView QuizName;
    ListView lvDidQuiz;
    ArrayList<DidQuiz> ListDidQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_did_quiz);

        QuizName=findViewById(R.id.tv_QuizName);
        lvDidQuiz=findViewById(R.id.lvdid);

        ListDidQuiz=new ArrayList<>();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("DidQuiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren()){
                    ListDidQuiz.add(s.getValue(DidQuiz.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        CountDownTimer begin = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                DidQuizAdapter dqa=new DidQuizAdapter(ViewDidQuiz.this,ListDidQuiz);
                lvDidQuiz.setAdapter(dqa);
            }
        }.start();
    }
}