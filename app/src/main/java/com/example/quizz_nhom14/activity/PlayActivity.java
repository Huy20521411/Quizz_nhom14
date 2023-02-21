package com.example.quizz_nhom14.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.object.DidQuiz;
import com.example.quizz_nhom14.object.Question;
import com.example.quizz_nhom14.object.Quiz;
import com.example.quizz_nhom14.object.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {
    LinearLayout[] ln=new LinearLayout[4];
    LinearLayout questionfield;
    TextView Clock,BeginClock,nameQues,countQues;
    TextView[] ans=new TextView[4];
    Button btnNext,btnFinish;
    ArrayList<Question> listQues,listQuestion;
    int count=0;
    int result=0;
    int countDidQuiz=0;
    int CountClickBtnFinish=0;
    DidQuiz dq=null;

    User user=new User(2,"Khoi 1");
    Quiz quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.play));
        window.setNavigationBarColor(this.getResources().getColor(R.color.play));
        setContentView(R.layout.activity_play);

        Intent intent=getIntent();
        Quiz q=(Quiz) intent.getSerializableExtra("quiz");
        this.setTitle(q.getName());
        quiz=q;

        BeginClock=findViewById(R.id.tv_BeginClock);
        Clock=findViewById(R.id.tvTime);
        countQues=findViewById(R.id.tv_CountQues);
        nameQues=findViewById(R.id.tv_NameQues);
        questionfield=findViewById(R.id.ln_Questionfield);
        ln[0]=findViewById(R.id.lnansA);
        ln[1]=findViewById(R.id.lnansB);
        ln[2]=findViewById(R.id.lnansC);
        ln[3]=findViewById(R.id.lnansD);
        ans[0]=findViewById(R.id.tvAnsA);
        ans[1]=findViewById(R.id.tvAnsB);
        ans[2]=findViewById(R.id.tvAnsC);
        ans[3]=findViewById(R.id.tvAnsD);
        btnNext=findViewById(R.id.btnNext);
        btnFinish=findViewById(R.id.btnFinish);

        listQues=new ArrayList<>();
        listQuestion=new ArrayList<>();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s:snapshot.getChildren()){
                    listQues.add(s.getValue(Question.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("DidQuiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    countDidQuiz= (int) snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Bắt sự kiện nhấn nút Next
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Question Q=listQuestion.get(count);
                setQuestion(Q);
                setEnableAns();
            }
        });

        //Bắt sự kiện nhấn chọn Answer
        for (int i=0;i<4;i++){
            int finalI = i;
            ln[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ln[listQuestion.get(count).getCorrectAnswer()-1].setBackground(getResources().getDrawable(R.drawable.ans_backgroundgreen));
                    if(finalI!=listQuestion.get(count).getCorrectAnswer()-1){
                        ln[finalI].setBackground(getResources().getDrawable(R.drawable.ans_backgroundred));
                    }
                    else{
                        result++;
                    }
                    btnNext.setVisibility(VISIBLE);
                    setDisableAns();
                    countDownTimer.cancel();
                    if(count+1==listQuestion.size()){
                        btnNext.setVisibility(GONE);
                        btnFinish.setVisibility(VISIBLE);
                    }
                }
            });
        }

        //Bắt sự kiện ấn nút Finish
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideItem();
                btnFinish.setVisibility(GONE);
                DateTimeFormatter dtf = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                }
                LocalDateTime now = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    now = LocalDateTime.now();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dq=new DidQuiz(user,quiz,result,dtf.format(now));
                }
                if (CountClickBtnFinish==0){
                    myRef.child("DidQuiz/"+(countDidQuiz+1)).setValue(dq);
                }
                else{
                    finish();
                }
                HideItem();
                btnFinish.setVisibility(VISIBLE);
                btnFinish.setText("Back to Home");
                CountClickBtnFinish++;
            }
        });
        HideItem();
        begin.start();
    }

    //Đồng hồ đếm ngược thời gian bắt đầu câu 1
    CountDownTimer begin=new CountDownTimer(5000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            BeginClock.setText(String.valueOf(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            for(int i=0;i<listQues.size();i++){
                if(quiz.getID()==listQues.get(i).getQuizID()) listQuestion.add(listQues.get(i));
            }
            BeginClock.setVisibility(GONE);
            setQuestion(listQuestion.get(count));
            VisibleItem();

        }
    };

    //Đồng hồ đếm thời gian mỗi câu hỏi
    CountDownTimer countDownTimer=new CountDownTimer(10000, 1000) {

        public void onTick(long millisUntilFinished) {
            Clock.setText("" + millisUntilFinished / 1000);
        }

        public void onFinish() {
            Clock.setText("Time Up!");
            btnNext.setVisibility(VISIBLE);
            setDisableAns();
            if(count+1==listQuestion.size()){
                btnNext.setVisibility(GONE);
                btnFinish.setVisibility(VISIBLE);
            }
        }
    };
    private void setQuestion(Question Q){
        btnNext.setVisibility(GONE);
        nameQues.setText(Q.getName());
        countQues.setText("Question "+(count+1)+" of 2");
        ans[0].setText(Q.getAnswerA());
        ans[1].setText(Q.getAnswerB());
        ans[2].setText(Q.getAnswerC());
        ans[3].setText(Q.getAnswerD());
        ln[0].setBackground(getResources().getDrawable(R.drawable.ans_background));
        ln[1].setBackground(getResources().getDrawable(R.drawable.ans_background));
        ln[2].setBackground(getResources().getDrawable(R.drawable.ans_background));
        ln[3].setBackground(getResources().getDrawable(R.drawable.ans_background));
        countDownTimer.cancel();
        countDownTimer.start();
    }
    private void HideItem(){
        Clock.setVisibility(GONE);
        questionfield.setVisibility(GONE);
        for(int i=0;i<4;i++){
            ln[i].setVisibility(GONE);
        }
        for(int i=0;i<4;i++){
            ans[i].setVisibility(GONE);
        }
        btnNext.setVisibility(GONE);
        btnFinish.setVisibility(GONE);
    }
    private void VisibleItem(){
        Clock.setVisibility(VISIBLE);
        questionfield.setVisibility(VISIBLE);
        for(int i=0;i<4;i++){
            ln[i].setVisibility(VISIBLE);
        }
        for(int i=0;i<4;i++){
            ans[i].setVisibility(VISIBLE);
        }
    }

    private void setDisableAns(){
        for (int i=0;i<4;i++){
            ln[i].setEnabled(false);
        }
    }
    private void setEnableAns(){
        for (int i=0;i<4;i++){
            ln[i].setEnabled(true);
        }
    }
}