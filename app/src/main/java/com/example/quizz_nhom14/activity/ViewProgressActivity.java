package com.example.quizz_nhom14.activity;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.adapterclass.DidSameQuizAdapter;
import com.example.quizz_nhom14.adapterclass.QuizAdapter;
import com.example.quizz_nhom14.object.DidQuiz;
import com.example.quizz_nhom14.object.DidSameQuizs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProgressActivity extends AppCompatActivity {
    TextView test;
    ListView lvDidSameQuiz;
    ArrayList<DidQuiz> ListDidQuiz;
    ArrayList<DidSameQuizs> ListDidSameQuiz;
    DidSameQuizAdapter didSameQuizAdapter;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progress);

        test=findViewById(R.id.tv_Title);
        lvDidSameQuiz=findViewById(R.id.lvdidsame);
        ListDidQuiz=new ArrayList<>();
        ListDidSameQuiz=new ArrayList<>();
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
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewProgressActivity.this, ""+ListDidQuiz.size(), Toast.LENGTH_LONG).show();
            }
        });
        CountDownTimer begin=new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                sort(ListDidQuiz,0,ListDidQuiz.size()-1);
//                for(int i=0;i<ListDidQuiz.size();i++){
//                    Toast.makeText(ViewProgressActivity.this, ""+ListDidQuiz.get(i).getQuiz().getID(), Toast.LENGTH_SHORT).show();
//                }
                ListDidSameQuiz.get(count).list.add(ListDidQuiz.get(0));
                Toast.makeText(ViewProgressActivity.this, ""+ListDidSameQuiz.get(count).list.get(0).getResult(), Toast.LENGTH_SHORT).show();
//                for(int i=1;i<ListDidQuiz.size();i++){
//                    if(ListDidQuiz.get(i).getQuiz().getID()==ListDidQuiz.get(i-1).getQuiz().getID()){
//                        ListDidSameQuiz.get(count).list.add(ListDidQuiz.get(i));
//                    }
//                    else{
//                        count++;
//                        ListDidSameQuiz.get(count).list.add(ListDidQuiz.get(i));
//                    }
//                }
            }
        }.start();
        didSameQuizAdapter=new DidSameQuizAdapter(ViewProgressActivity.this,ListDidSameQuiz);
        lvDidSameQuiz.setAdapter(didSameQuizAdapter);
    }
    int partition(ArrayList<DidQuiz> arr, int low, int high) {
        int pivot = arr.get(high).getQuiz().getID();
        int i = (low - 1);
        for (int j = low; j < high; j++) {

            if (arr.get(j).getQuiz().getID() < pivot) {
                i++;

                DidQuiz temp = arr.get(i);
                arr.set(i,arr.get(j));
                arr.set(j,temp);
            }
        }
        DidQuiz temp = arr.get(i+1);
        arr.set(i+1,arr.get(high));
        arr.set(high,temp);

        return i + 1;
    }

    void sort(ArrayList<DidQuiz> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }
}