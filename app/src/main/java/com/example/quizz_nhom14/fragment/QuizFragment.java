package com.example.quizz_nhom14.fragment;

import static android.view.View.GONE;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.activity.MainActivity;
import com.example.quizz_nhom14.activity.PlayActivity;
import com.example.quizz_nhom14.activity.ViewProgressActivity;
import com.example.quizz_nhom14.adapterclass.QuizAdapter;
import com.example.quizz_nhom14.object.JoinQuiz;
import com.example.quizz_nhom14.object.Question;
import com.example.quizz_nhom14.object.Quiz;
import com.example.quizz_nhom14.object.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class QuizFragment extends Fragment {
    ArrayList<JoinQuiz> ListJoin;
    ArrayList<Quiz> ListQuiz;
    QuizAdapter adapter;
    ListView listView;
    Button btnProgress;
    int countQuiz=0;
    TextView title;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.quizs_layout,container,false);
        listView=view.findViewById(R.id.lv);
        progressBar=view.findViewById(R.id.progress1);
        ListJoin=new ArrayList<>();
        ListQuiz=new ArrayList<>();
        User user=new User(1,"Khoi 2");
//        Quiz quiz =new Quiz("Game Vui","Python",10,"Hồng Nghĩa");
//        Question question=new Question("Cau1","q","w","e","r",3,1);
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

        myRef.child("Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    countQuiz= (int) snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        title=view.findViewById(R.id.tv_Title);
//        title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                quiz.setID(countQuiz+1);
//                myRef.child("Question/"+(countQuiz+1)).setValue(question);
//            }
//        });
        CountDownTimer begin1=new CountDownTimer(2000,500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                for (int i=0;i<ListJoin.size();i++){
                    if(ListJoin.get(i).getUser().getUserID()== user.getUserID()){
                        ListQuiz.add(ListJoin.get(i).getQuiz());
                    }
                }
                adapter = new QuizAdapter(view.getContext(), ListQuiz);
                listView.setAdapter(adapter);
                progressBar.setVisibility(GONE);
            }
        }.start();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog=new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_confirm);
                Button btnXN=dialog.findViewById(R.id.btnXN);
                Button btnHuy=dialog.findViewById(R.id.btnHuy);
                dialog.show();
                btnXN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent=new Intent(view.getContext(), PlayActivity.class);
                        intent.putExtra("quiz",ListQuiz.get(position));
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
        return view;
    }

}


