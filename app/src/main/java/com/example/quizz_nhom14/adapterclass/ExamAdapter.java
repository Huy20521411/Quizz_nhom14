package com.example.quizz_nhom14;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExamAdapter extends BaseAdapter {
    Context context;
    ArrayList<Exam> list;

    public ExamAdapter(Context context, ArrayList<Exam> list) {
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
        View row=inflater.inflate(R.layout.exam_item,null);
        Exam exam=list.get(position);
        TextView tvName=row.findViewById(R.id.tvExamName);
        TextView tvSub=row.findViewById(R.id.tvExamSub);
        TextView tvNum=row.findViewById(R.id.tvExamNumques);
        TextView tvTeacher=row.findViewById(R.id.tvTeacher);
        tvName.setText(exam.getName());
        tvSub.setText("Môn học: "+exam.getSub());
        tvNum.setText("Số câu hỏi: "+exam.getNumof_questions());
        tvTeacher.setText("Giảng viên: "+exam.getTeacher());
        return row;
    }
}
