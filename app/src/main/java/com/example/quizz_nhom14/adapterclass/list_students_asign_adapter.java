package com.example.quizz_nhom14.adapterclass;

import android.app.job.JobInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.object.JoinQuiz;
import com.example.quizz_nhom14.object.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class list_students_asign_adapter extends BaseAdapter {

    final ArrayList<JoinQuiz> joinQuizs;

    public list_students_asign_adapter(ArrayList<JoinQuiz> joinQuizs) {
        this.joinQuizs = joinQuizs;
    }

    @Override
    public int getCount() {
        return joinQuizs.size();
    }

    @Override
    public Object getItem(int position) {
        return joinQuizs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.student_added_item, null);
        } else viewProduct = convertView;

        JoinQuiz joinQuiz = (JoinQuiz) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.tv_id)).setText(String.format("ID = %d", joinQuiz.getUser().getUserID()));
        ((TextView) viewProduct.findViewById(R.id.tv_name)).setText(String.format("Tên SP : %s", joinQuiz.getUser().getFullname()));

        return viewProduct;
    }
}
