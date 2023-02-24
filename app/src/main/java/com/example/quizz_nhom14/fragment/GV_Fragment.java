package com.example.quizz_nhom14.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.activity.CreateQuizsActivity;
import com.example.quizz_nhom14.activity.MainActivity;
import com.example.quizz_nhom14.adapterclass.QuizzsAdapter;
import com.example.quizz_nhom14.object.Quiz;
import com.example.quizz_nhom14.object.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GV_Fragment extends Fragment {
    int countQuizid;
    FloatingActionButton btnaddquiz;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity mainActivity;
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView;
        ArrayList<Quiz> Quizzss,Quizzss1;

        mainActivity= (MainActivity) getActivity();
        User user = mainActivity.getUser();

        final RecyclerView rv_quizz= v.findViewById(R.id.rv_dsQuizz);
        btnaddquiz=v.findViewById(R.id.fab);
        btnaddquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(v.getContext(), CreateQuizsActivity.class);
                intent.putExtra("quizid",countQuizid);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        Quizzss = new ArrayList<Quiz>();
        Quizzss1 = new ArrayList<Quiz>();
        QuizzsAdapter adapter;
        adapter=new QuizzsAdapter(mainActivity,Quizzss);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Quiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countQuizid = (int)snapshot.getChildrenCount()+1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("Quiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Quizzss1.clear();
                Quizzss.clear();
                for(DataSnapshot sn : snapshot.getChildren())
                {
                    Quizzss1.add(sn.getValue(Quiz.class));
                }
                for(Quiz q:Quizzss1){
                    if(q.getTeacherID()==user.getUserID()){
                        Quizzss.add(q);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rv_quizz.setAdapter(adapter);
        rv_quizz.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

}
