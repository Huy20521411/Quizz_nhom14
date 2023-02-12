package com.example.quizz_nhom14.adapterclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.object.DidQuiz;
import com.example.quizz_nhom14.object.DidSameQuizs;

import java.util.ArrayList;

public class DidSameQuizAdapter extends BaseAdapter {
    Context context;
    ArrayList<DidSameQuizs> listdidsame;

    public DidSameQuizAdapter(Context context, ArrayList<DidSameQuizs> list) {
        this.context = context;
        this.listdidsame = list;
    }

    @Override
    public int getCount() {
        return listdidsame.size();
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
        View row=inflater.inflate(R.layout.didsamequiz_item,null);
        ArrayList<DidQuiz> list=new ArrayList<>();
        list=this.listdidsame.get(position).list;
        TextView namequiz=row.findViewById(R.id.tvDidQuizName);
        TextView subname=row.findViewById(R.id.tvDidSubName);
        TextView didnum=row.findViewById(R.id.tvNumDid);
        TextView teacher=row.findViewById(R.id.tvTeacher1);
        ProgressBar progressBar=row.findViewById(R.id.progress1);
        namequiz.setText(list.get(0).getQuiz().getName());
        subname.setText(list.get(0).getQuiz().getSub());
        didnum.setText("Số lần làm quiz: "+list.size());
        teacher.setText(list.get(list.size()-1).getQuiz().getTeacher()+" ");
        progressBar.setProgress((int)(this.listdidsame.get(position).getAverage()));
        return row;
    }
}
