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

import com.example.quizz_nhom14.Data.Time;
import com.example.quizz_nhom14.adapterclass.QuestionAdapter;
import com.example.quizz_nhom14.adapterclass.list_students_asign_adapter;
import com.example.quizz_nhom14.object.Question;
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

    int countSTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quizs);

        dsQuestion = new ArrayList<>();
        rv_question = findViewById(R.id.rv_question);
        btn_AddQuestion = findViewById(R.id.fab_addquestion);


        adapter = new QuestionAdapter(this, dsQuestion);

        rv_question.setAdapter(adapter);
        rv_question.setLayoutManager(new LinearLayoutManager(this));

        findViewByIds();

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

        Time timeselected = new Time(0,30);

        //button thời gian
            // set thời gian mặc định 30s
            btn_time.setText( 0 +":" + 30);
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

                        btn_time.setText(minute + ":" + second);
                        timeselected.setMinute(minute);
                        timeselected.setSecond(second);
                    }
                },minute,second,true);
                timePickerDialog.show();
            }
        });


        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //kt rd
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

                Intent intent = getIntent();
                int quizID = intent.getIntExtra("quizid",0);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();


                myRef.child("Question").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                         countSTT = (int)snapshot.getChildrenCount();

                        snapshot.child("quizID").getValue();
                        question = new Question(countSTT+1,
                                et_content_Question.getText().toString(),
                                et_content_A.getText().toString(),
                                et_content_B.getText().toString(),
                                et_content_C.getText().toString(),
                                et_content_D.getText().toString(),
                                checkrd,
                                quizID,
                                timeselected);

                        dsQuestion.add(question);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                myRef.child("Question").child(String.valueOf(countSTT+1)).setValue(question);

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
        ArrayList<User> user = new ArrayList<User>();
        user.add(new User(true,1,"Pham Chan Huy","huy123","123"));
        user.add(new User(true,1,"Pham Chan Huy2","huy12334","123"));
        user.add(new User(true,1,"Pham Chan Huy3","huy12354","123"));

        list_students_asign_adapter adapter = new list_students_asign_adapter(user);
        lv_listAdded.setAdapter(adapter);

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAtributes);

        dialog.setCancelable(false);

        Button btn_exit_diaglog_asign = dialog.findViewById(R.id.btn_exit_asign_dialog);

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