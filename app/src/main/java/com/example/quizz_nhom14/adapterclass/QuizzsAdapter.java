package com.example.quizz_nhom14.adapterclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.object.Quiz;
import com.example.quizz_nhom14.ui.gallery.GalleryFragment;

import java.util.ArrayList;

public class QuizzsAdapter extends RecyclerView.Adapter<QuizzsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
    private  Context mContext;
    private ArrayList<Quiz> dsQuizzs;
    public QuizzsAdapter(Context mContext, ArrayList<Quiz> dsQuizzs)
    {
        this.dsQuizzs =dsQuizzs;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View quizzView = inflater.inflate(R.layout.quizzs_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(quizzView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quiz employee = dsQuizzs.get(position);

    }
    @Override
    public int getItemCount() {
        return dsQuizzs.size();
    }
}
