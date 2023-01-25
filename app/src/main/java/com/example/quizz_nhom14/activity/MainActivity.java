package com.example.quizz_nhom14.activity;

import static android.view.View.GONE;
import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.quizz_nhom14.object.JoinQuiz;
import com.example.quizz_nhom14.object.Quiz;
import com.example.quizz_nhom14.adapterclass.QuizAdapter;
import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.object.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<JoinQuiz> ListJoin;
    ArrayList<Quiz> ListQuiz;
    QuizAdapter adapter;
    ListView listView;
    Button btnProgress;
    ProgressBar Progress3s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//         getSupportActionBar().hide();
        listView=findViewById(R.id.lv);
        Progress3s=findViewById(R.id.progress);
        btnProgress=findViewById(R.id.btnProgress);
        ListJoin=new ArrayList<>();
        ListQuiz=new ArrayList<>();

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#3765DA"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        User user=new User(1,"Khoi 2");
        Quiz quiz =new Quiz("History of 2023","Phát triển ứng dụng di động",16,"Trần Hồng Nghi");
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("JoinQuiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren()){
                    ListJoin.add(s.getValue(JoinQuiz.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        CountDownTimer begin=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                for (int i=0;i<ListJoin.size();i++){
                    if(ListJoin.get(i).getUser().getUserID()== user.getUserID()){
                        ListQuiz.add(ListJoin.get(i).getQuiz());
                        Progress3s.setVisibility(GONE);
                    }
                }
                adapter = new QuizAdapter(MainActivity.this, ListQuiz);
                listView.setAdapter(adapter);
            }
        }.start();

        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ViewProgressActivity.class);
                startActivity(intent);
            }
        });


//        myRef.child("JoinQuiz/3").setValue(new JoinQuiz(quiz,user));
//        myRef.child("DidQuiz").push().setValue(new DidQuiz(quiz,user,4));
//        myRef.child("Quiz").push().setValue(quiz);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_confirm);
                Button btnXN=dialog.findViewById(R.id.btnXN);
                Button btnHuy=dialog.findViewById(R.id.btnHuy);
                dialog.show();
                btnXN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,PlayActivity.class);
                        startActivity(intent);
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}