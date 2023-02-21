package com.example.quizz_nhom14.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.adapterclass.DidQuizAdapter;
import com.example.quizz_nhom14.object.DidQuiz;
import com.example.quizz_nhom14.object.DidSameQuizs;
import com.example.quizz_nhom14.object.Quiz;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class ViewDidQuiz extends AppCompatActivity {
    ListView lvDidQuiz;
    ArrayList<DidQuiz> l,ListDidQuiz;
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_did_quiz);

        Intent intent=getIntent();
        Quiz q=(Quiz) intent.getSerializableExtra("dsq");

        mDrawerLayout=findViewById(R.id.drawer_layout1);
        lvDidQuiz=findViewById(R.id.lvdid);

        ListDidQuiz=new ArrayList<>();
        l=new ArrayList<>();

        toolbar=findViewById(R.id.toolbar1);
        toolbar.setTitle(q.getName());
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar
                ,R.string.nav_draw_start,R.string.nav_drawe_close);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("DidQuiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren()){
                    l.add(s.getValue(DidQuiz.class));
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
                for(int i=0;i<l.size();i++){
                    if(q.getID()==l.get(i).getQuiz().getID()){
                        ListDidQuiz.add(l.get(i));

                    }
                }
                DidQuizAdapter dqa=new DidQuizAdapter(ViewDidQuiz.this,ListDidQuiz);
                lvDidQuiz.setAdapter(dqa);
            }
        }.start();
    }
}