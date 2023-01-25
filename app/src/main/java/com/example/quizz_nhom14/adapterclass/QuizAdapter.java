package com.example.quizz_nhom14.adapterclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizz_nhom14.object.Quiz;
import com.example.quizz_nhom14.R;

import java.util.ArrayList;

public class QuizAdapter extends BaseAdapter {
    Context context;
    ArrayList<Quiz> list;


    public QuizAdapter(Context context, ArrayList<Quiz> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.quiz_item,null);
        Quiz quiz =list.get(position);
        TextView tvName=row.findViewById(R.id.tvExamName);
        TextView tvSub=row.findViewById(R.id.tvExamSub);
        TextView tvNum=row.findViewById(R.id.tvExamNumques);
        TextView tvTeacher=row.findViewById(R.id.tvTeacher);
        tvName.setText(quiz.getName());
        tvSub.setText("Môn học: "+ quiz.getSub());
        tvNum.setText("Số câu hỏi: "+ quiz.getNumof_questions());
        tvTeacher.setText("Giảng viên: "+ quiz.getTeacher());

        return row;
    }
}
