package com.example.quizz_nhom14.adapterclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quizz_nhom14.R;

import java.util.ArrayList;

public class AnswerAdapter extends BaseAdapter {
    ArrayList<String> list;
    Context context;

    public AnswerAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
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
//        View row=inflater.inflate(R.layout.answer_item,null);
//        TextView Choice=row.findViewById(R.id.tvChoice);
//        TextView Ans=row.findViewById(R.id.tvAns);
//        switch (position){
//            case 0:{
//                Choice.setText("A");
//                break;
//            }
//            case 1:{
//                Choice.setText("B");
//                break;
//            }case 2:{
//                Choice.setText("C");
//                break;
//            }
//            case 3:{
//                Choice.setText("D");
//                break;
//            }
//        }
//        Ans.setText(list.get(position));
        return convertView;
    }
}
