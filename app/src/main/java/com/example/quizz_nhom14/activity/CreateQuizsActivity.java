package com.example.quizz_nhom14.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.adapterclass.QuestionAdapter;
import com.example.quizz_nhom14.adapterclass.list_students_asign_adapter;
import com.example.quizz_nhom14.object.JoinQuiz;
import com.example.quizz_nhom14.object.Question;
import com.example.quizz_nhom14.object.Quiz;
import com.example.quizz_nhom14.object.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateQuizsActivity extends AppCompatActivity {

    RecyclerView rv_question;
    QuestionAdapter adapter;
    ArrayList<Question> dsQuestion;
    Button btn_AddQuestion;
    Button btn_Asign;
    Question question;

    Button btn_acept_create_quiz;
    EditText et_name_quiz;
    EditText et_name_sub;

    Quiz quiz;

    User user;

    ArrayList<User> users;

    int newquizid;

    int countSTT;

    int questionidnew;
    int newjoinquizid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quizs);

        dsQuestion = new ArrayList<>();
        rv_question = findViewById(R.id.rv_question);
        btn_AddQuestion = findViewById(R.id.fab_addquestion);
        btn_acept_create_quiz = findViewById(R.id.btn_acept_create_quiz);
        et_name_quiz = findViewById(R.id.et_name_quiz);
        et_name_sub = findViewById(R.id.et_name_sub);

        adapter = new QuestionAdapter(this, dsQuestion);

        rv_question.setAdapter(adapter);
        rv_question.setLayoutManager(new LinearLayoutManager(this));

        findViewByIds();

        Intent intent = getIntent();
        Window window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));
        quiz = (Quiz)intent.getSerializableExtra("quizz");
        User gv= (User) intent.getSerializableExtra("user");
        btn_AddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_question_dialog();
            }
        });

        btn_Asign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               open_asign_dialog();
            }
        });

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        if(quiz !=null)
        {

            myRef.child("Question").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dsQuestion.clear();
                    for(DataSnapshot sn: snapshot.getChildren())
                    {
                        int idquiz = sn.child("quizID").getValue(int.class);
                        if(idquiz == quiz.getID())
                        {
                            dsQuestion.add(sn.getValue(Question.class));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            et_name_quiz.setText(quiz.getName());
            et_name_sub.setText(quiz.getSub());
            btn_acept_create_quiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Quiz q = new Quiz( quiz.getID(),
                            et_name_quiz.getText().toString(),
                            et_name_sub.getText().toString(),
                            dsQuestion.size(),
                            gv.getFullname(),
                            gv.getUserID()
                    );

                    myRef.child("Quiz/"+quiz.getID()).setValue(q);
                    finish();
                }
            });
        }
        else {

            btn_Asign.setVisibility(View.GONE);

            myRef.child("Quiz").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    newquizid = (int)snapshot.getChildrenCount()+1;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

//            Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
            btn_acept_create_quiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Quiz q = new Quiz( newquizid,
                            et_name_quiz.getText().toString(),
                            et_name_sub.getText().toString(),
                            dsQuestion.size(),
                            gv.getFullname(),
                            gv.getUserID()
                    );

                    myRef.child("Quiz/"+newquizid).setValue(q);
                    finish();
                }
            });

        }

        myRef.child("Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionidnew = (int)snapshot.getChildrenCount()+1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        users = new ArrayList<>();
        myRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for(DataSnapshot sn: snapshot.getChildren())
                {
                    users.add(sn.getValue(User.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void open_question_dialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_quesion);
        Window window = dialog.getWindow();
        if(window == null)
            return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAtributes);

        dialog.setCancelable(false);

        Button btn_exit = dialog.findViewById(R.id.btn_exit_question_dialog);
        Button btn_accept = dialog.findViewById(R.id.btn_accept_question_dialog);
        EditText et_content_Question = dialog.findViewById(R.id.et_content_Question);
        EditText et_content_A = dialog.findViewById(R.id.et_content_A);
        EditText et_content_B = dialog.findViewById(R.id.et_content_B);
        EditText et_content_C = dialog.findViewById(R.id.et_content_C);
        EditText et_content_D = dialog.findViewById(R.id.et_content_D);
        RadioButton rd_A =dialog.findViewById(R.id.rd_A);
        RadioButton rd_B =dialog.findViewById(R.id.rd_B);
        RadioButton rd_C =dialog.findViewById(R.id.rd_C);
        RadioButton rd_D =dialog.findViewById(R.id.rd_D);


        //button thời gian
            // set thời gian mặc định 30s

        //kt rd

        Intent intent = getIntent();
        int quizID = intent.getIntExtra("quizid",0);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        myRef.child("Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countSTT = (int)snapshot.getChildrenCount()+1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkrd;
                if(rd_A.isChecked() ==true)
                {
                    checkrd =1;
                } else if(rd_B.isChecked() ==true) {
                    checkrd =2;
                }
                else if(rd_C.isChecked() ==true) {
                    checkrd =3;
                }
                else checkrd =4;


                if(quizID == 0)
                {
                    question = new Question(questionidnew,
                            et_content_Question.getText().toString(),
                            et_content_A.getText().toString(),
                            et_content_B.getText().toString(),
                            et_content_C.getText().toString(),
                            et_content_D.getText().toString(),
                            checkrd,
                            quiz.getID());
                }
                else question = new Question(
                        questionidnew,
                        et_content_Question.getText().toString(),
                        et_content_A.getText().toString(),
                        et_content_B.getText().toString(),
                        et_content_C.getText().toString(),
                        et_content_D.getText().toString(),
                        checkrd,
                        quizID);
                dsQuestion.add(question);

                myRef.child("Question/"+countSTT).setValue(question);
                dialog.dismiss();

            }
        });



        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void open_asign_dialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_asign);
        Window window = dialog.getWindow();
        if(window == null)
            return;

        ListView lv_listAdded = dialog.findViewById(R.id.lv_listAdded);
        ArrayList<JoinQuiz> joinQuizs = new ArrayList<JoinQuiz>();
        ArrayList<JoinQuiz> jqs = new ArrayList<JoinQuiz>();

//////////////////
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        list_students_asign_adapter adapter = new list_students_asign_adapter(joinQuizs);
        lv_listAdded.setAdapter(adapter);

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAtributes);

        dialog.setCancelable(false);

        Button btn_exit_diaglog_asign = dialog.findViewById(R.id.btn_exit_asign_dialog);
        FloatingActionButton btn_add_asign = dialog.findViewById(R.id.btn_add_asign);
        EditText et_search_id = dialog.findViewById(R.id.et_search_id);
        et_search_id.setInputType(InputType.TYPE_CLASS_NUMBER);

        //keo ve
        myRef.child("JoinQuiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                joinQuizs.clear();
                for(DataSnapshot sn: snapshot.getChildren())
                {
                    jqs.add(sn.getValue(JoinQuiz.class));
                }
                for(JoinQuiz j:jqs){
                    if (j.getQuiz().getID()==quiz.getID()) joinQuizs.add(j);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //tim user

            myRef.child("JoinQuiz").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    newjoinquizid = (int) snapshot.getChildrenCount() + 1;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        //them
        btn_add_asign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int iduser = Integer.valueOf(et_search_id.getText().toString());

                int dem1 = 0;
                int dem2 = 0;

                for (JoinQuiz jq : joinQuizs) {
                    if (jq.getUser().getUserID() == iduser)
                        dem1++;
                }
                if (dem1 != 0)
                    Toast.makeText(getApplicationContext(), "Ban đã thêm học viên này rồi", Toast.LENGTH_SHORT).show();
                else {
                    for (User u : users) {
                        if (iduser == u.getUserID()) {
                            user=u;
                            dem2++;
                        }
                    }
                    if(dem2==0){
                        Toast.makeText(CreateQuizsActivity.this, "User này không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //them
                        JoinQuiz joinQuiz = new JoinQuiz(quiz, user);

                        //push

                        myRef.child("JoinQuiz/" + newjoinquizid).setValue(joinQuiz);
                        joinQuizs.add(joinQuiz);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        btn_exit_diaglog_asign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    void findViewByIds()
    {
        btn_Asign = findViewById(R.id.btn_asign);
    }
}