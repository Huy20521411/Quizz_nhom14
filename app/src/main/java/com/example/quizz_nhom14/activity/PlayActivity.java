package com.example.quizz_nhom14.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
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
    ArrayList<Question> listQues;
    int count=0;
    int result=0;
    int countDidQuiz=0;
    int CountClickBtnFinish=0;
    DidQuiz dq=null;
    User user=new User(2,"Khoi 1");
    Quiz quiz=new Quiz("Mobile Layout","Mobile",3,"Nghi");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        this.setTitle(quiz.getName());

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
                Question Q=listQues.get(count);
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
                    ln[listQues.get(count).getCorrectAnswer()-1].setBackground(getResources().getDrawable(R.drawable.ans_backgroundgreen));
                    if(finalI!=listQues.get(count).getCorrectAnswer()-1){
                        ln[finalI].setBackground(getResources().getDrawable(R.drawable.ans_backgroundred));
                    }
                    else{
                        result++;
                    }
                    btnNext.setVisibility(VISIBLE);
                    setDisableAns();
                    countDownTimer.cancel();
                    if(count+1==listQues.size()){
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
            BeginClock.setVisibility(GONE);
            setQuestion(listQues.get(count));
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