package com.example.quizz_nhom14.adapterclass;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.activity.CreateQuizsActivity;
import com.example.quizz_nhom14.activity.MainActivity;
import com.example.quizz_nhom14.object.Quiz;
import com.example.quizz_nhom14.object.User;

import java.util.ArrayList;

public class QuizzsAdapter extends RecyclerView.Adapter<QuizzsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn_detail_button_quiz;
        TextView tv_quizzname;
        TextView tv_subname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_detail_button_quiz = itemView.findViewById(R.id.btn_detail_button_quiz);
            tv_subname = itemView.findViewById(R.id.tv_subname);
            tv_quizzname = itemView.findViewById(R.id.tv_quizzname);
        }
    }
    private MainActivity mainActivity;
    private ArrayList<Quiz> dsQuizzs;
    public QuizzsAdapter(MainActivity mainActivity, ArrayList<Quiz> dsQuizzs)
    {
        this.dsQuizzs =dsQuizzs;
        this.mainActivity=mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View quizzView = inflater.inflate(R.layout.quizzs_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(quizzView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quiz quiz = dsQuizzs.get(position);

        holder.tv_quizzname.setText(quiz.getName());
        holder.tv_subname.setText(quiz.getSub());

        holder.btn_detail_button_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mở csdl của bộ quizz này
                User user= mainActivity.getUser();
                Intent intent = new Intent(mainActivity, CreateQuizsActivity.class);
                intent.putExtra("quizz",quiz);
                intent.putExtra("user",user);
                mainActivity.startActivity(intent);
            }

        });

    }
    @Override
    public int getItemCount() {
        return dsQuizzs.size();
    }
}
