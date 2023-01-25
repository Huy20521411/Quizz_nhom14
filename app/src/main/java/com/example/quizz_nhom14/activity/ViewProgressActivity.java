package com.example.quizz_nhom14.activity;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

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
                for(int i=0;i<ListDidSameQuiz.size();i++){
                    Toast.makeText(ViewProgressActivity.this, "a", Toast.LENGTH_SHORT).show();
                    for(int j=0;j<ListDidSameQuiz.get(i).list.size();j++){
                        Toast.makeText(ViewProgressActivity.this, ""+ListDidSameQuiz.get(i).list.get(j).getQuiz().getID(), Toast.LENGTH_SHORT).show();
                    }
                }
//                   Toast.makeText(ViewProgressActivity.this, ""+ListDidQuiz.size(), Toast.LENGTH_SHORT).show();
            }
        });
        CountDownTimer begin=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                sort(ListDidQuiz,0,ListDidQuiz.size()-1);
                if(ListDidQuiz.size()!=0){
                    ListDidSameQuiz.add(new DidSameQuizs());
                    if(ListDidQuiz.size()>=2){
                        for(int i=1;i<ListDidQuiz.size();i++){
                            if (ListDidQuiz.get(i).getQuiz().getID() != ListDidQuiz.get(i - 1).getQuiz().getID()){
                                ListDidSameQuiz.add(new DidSameQuizs());
                            }
                        }
                        for(int i=1;i<ListDidQuiz.size();i++){
                            if (ListDidQuiz.get(i).getQuiz().getID() == ListDidQuiz.get(i - 1).getQuiz().getID()) {
                                ListDidSameQuiz.get(count).list.add(ListDidQuiz.get(i));
                            }
                            else{
                                count++;
                                ListDidSameQuiz.get(count).list.add(ListDidQuiz.get(i));
                            }
                        }
                    }
                }
//                if(ListDidQuiz.size()>=2) {
//                    for (int i = 1; i < ListDidQuiz.size(); i++) {
//                        if (ListDidQuiz.get(i).getQuiz().getID() != ListDidQuiz.get(i - 1).getQuiz().getID() || i == ListDidQuiz.size() - 1) {
//                            ArrayList<DidQuiz> smallListDidQuiz = new ArrayList<>();
//                            for (int j = x; j < i; j++) {
//                                smallListDidQuiz.add(ListDidQuiz.get(j));
//                                if (i == ListDidQuiz.size() - 1) {
//                                    smallListDidQuiz.add(ListDidQuiz.get(i));
//                                }
//                            }
//                            DidSameQuizs dsq = new DidSameQuizs(smallListDidQuiz);
//                            ListDidSameQuiz.add(dsq);
//                            x = i;
//                        }
//                    }
//                }
                didSameQuizAdapter=new DidSameQuizAdapter(ViewProgressActivity.this,ListDidSameQuiz);
                lvDidSameQuiz.setAdapter(didSameQuizAdapter);
            }
        }.start();
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#3765DA"));
        actionBar.setBackgroundDrawable(colorDrawable);
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