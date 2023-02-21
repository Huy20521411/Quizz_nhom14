package com.example.quizz_nhom14.adapterclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.object.DidQuiz;

import java.util.ArrayList;

public class DidQuizAdapter extends BaseAdapter {
    Context context;
    ArrayList<DidQuiz> listdid;

    public DidQuizAdapter(Context context, ArrayList<DidQuiz> list) {
        this.context = context;
        this.listdid = list;
    }

    @Override
    public int getCount() {
        return listdid.size();
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
        View row=inflater.inflate(R.layout.item_didquiz,null);
        TextView lan=row.findViewById(R.id.tvLan);
        TextView time=row.findViewById(R.id.tvTime);
        TextView result=row.findViewById(R.id.tvResult);
        lan.setText("Lần làm: "+ ((position+1)));
        time.setText("Thời gian làm quiz: "+listdid.get(position).getTime());
        result.setText("Số câu trả lời đúng: "+listdid.get(position).getResult());
        return row;
    }
}
