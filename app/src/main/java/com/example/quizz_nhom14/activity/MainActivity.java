package com.example.quizz_nhom14.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quizz_nhom14.Exam;
import com.example.quizz_nhom14.ExamAdapter;
import com.example.quizz_nhom14.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Exam> arrayList;
    ExamAdapter adapter;
    ListView listView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.lv);
        arrayList=new ArrayList<>();
        Exam exam=new Exam("History of 2023","Phát triển ứng dụng di động",16,"Trần Hồng Nghi");
        arrayList.add(exam);
        arrayList.add(exam);
        arrayList.add(exam);
        arrayList.add(exam);
        arrayList.add(exam);
        arrayList.add(exam);
        arrayList.add(exam);
        arrayList.add(exam);
        adapter = new ExamAdapter(MainActivity.this, arrayList);
        listView.setAdapter(adapter);
        String s="khoi";
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                myRef.child("khoilatao").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot s:snapshot.getChildren()){
                            Exam exam1=s.getValue(Exam.class);
                            Toast.makeText(MainActivity.this, exam1.getTeacher(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}