package com.example.quizz_nhom14;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> list;
    ArrayList<ArrayList<String>> list1;
    AnswerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        list=new ArrayList<>();
        list.add("Khoi1");
        list.add("Khoi2");
        list.add("Khoi3");
        list.add("Khoi4");
        adapter=new AnswerAdapter(list,PlayActivity.this);
        listView=findViewById(R.id.lvAnswers);
        listView.setAdapter(adapter);
    }
}