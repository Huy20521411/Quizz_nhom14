package com.example.quizz_nhom14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

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

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class CreateQuizsActivity extends AppCompatActivity {

    RecyclerView rv_question;
    QuestionAdapter adapter;
    ArrayList<Question> dsQuestion;
    FloatingActionButton btn_AddQuestion;
    Button btn_Asign;
    Question question;

    Button btn_acept_create_quiz;
    EditText et_name_quiz;
    EditText et_name_sub;

    Quiz quiz;

    User user;

    ArrayList<User> users;

    int time;
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

        quiz = (Quiz)intent.getSerializableExtra("quizz");

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


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

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

                    Quiz quiz = new Quiz( newquizid,
                            et_name_quiz.getText().toString(),
                            et_name_sub.getText().toString(),
                            4,
                            "huy"
                    );

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    myRef.child("Quiz/"+newquizid).setValue(quiz);
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
        Button btn_time = dialog.findViewById(R.id.btn_time_question);
        EditText et_content_Question = dialog.findViewById(R.id.et_content_Question);
        EditText et_content_A = dialog.findViewById(R.id.et_content_A);
        EditText et_content_B = dialog.findViewById(R.id.et_content_B);
        EditText et_content_C = dialog.findViewById(R.id.et_content_C);
        EditText et_content_D = dialog.findViewById(R.id.et_content_D);
        RadioButton rd_A =dialog.findViewById(R.id.rd_A);
        RadioButton rd_B =dialog.findViewById(R.id.rd_B);
        RadioButton rd_C =dialog.findViewById(R.id.rd_C);
        RadioButton rd_D =dialog.findViewById(R.id.rd_D);

        time = 30;

        //button thời gian
            // set thời gian mặc định 30s
            btn_time.setText( "30s");
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int second = calendar.get(Calendar.SECOND);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateQuizsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int minute, int second) {
                        ///sử dụng thêm biến lưu trữ thời gian


                        Toast.makeText(CreateQuizsActivity.this, String.format("Thoi gian da chon: %d:%d",minute,second), Toast.LENGTH_SHORT).show();

                        time = minute*60+second;

                        btn_time.setText(time+"s");

                    }
                },minute,second,true);
                timePickerDialog.show();
            }
        });

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
                    question = new Question(
                            et_content_Question.getText().toString(),
                            et_content_A.getText().toString(),
                            et_content_B.getText().toString(),
                            et_content_C.getText().toString(),
                            et_content_D.getText().toString(),
                            checkrd,
                            quiz.getID(),
                            time,
                            questionidnew);
                }
                else question = new Question(
                        et_content_Question.getText().toString(),
                        et_content_A.getText().toString(),
                        et_content_B.getText().toString(),
                        et_content_C.getText().toString(),
                        et_content_D.getText().toString(),
                        checkrd,
                        quizID,
                        time,
                        questionidnew);
                dsQuestion.add(question);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = database.getReference();

                myRef1.child("Question/"+countSTT).setValue(question);
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


        //keo ve
        if(quiz !=null) {
            myRef.child("JoinQuiz").addValueEventListener(new ValueEventListener() {

                //câp nhật ds sinh vien dc them
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    joinQuizs.clear();
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



        }
        else {

            //Them
            Toast.makeText(CreateQuizsActivity.this,"vô",Toast.LENGTH_LONG).show();
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


        }

        btn_add_asign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                //lay id ve va truy xuat user

                int userID = Integer.valueOf(et_search_id.getText().toString());

                //tim iduser
                for(User us: users)
                {
                    if(userID==us.getUserID())
                    {
                        user = us;
                    }
                }

                //getuser

                Quiz quiz = new Quiz( newquizid,
                        et_name_quiz.getText().toString(),
                        et_name_sub.getText().toString(),
                        4,
                        "huy"
                );

                myRef.child("Quiz/"+newquizid).setValue(quiz);

                JoinQuiz joinQuiz = new JoinQuiz( quiz, user);

                myRef.child("JoinQuiz").child(String.valueOf(newjoinquizid)).setValue(joinQuiz);
                joinQuizs.add(joinQuiz);
                adapter.notifyDataSetChanged();
            }
        });

            //them thẳng

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