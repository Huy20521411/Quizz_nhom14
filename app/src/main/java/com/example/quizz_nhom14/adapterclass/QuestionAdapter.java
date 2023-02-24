package com.example.quizz_nhom14.adapterclass;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizz_nhom14.activity.CreateQuizsActivity;
import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.object.Question;
import com.example.quizz_nhom14.object.Quiz;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;
import java.util.Calendar;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView tv_NumOfQues;
        private TextView tv_AnswerA;
        private TextView tv_AnswerB;
        private TextView tv_AnswerC;
        private TextView tv_AnswerD;
        private TextView A;
        private TextView B;
        private TextView C;
        private TextView D;
        private Button btn_Update;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_NumOfQues = itemView.findViewById(R.id.tv_Number_of_Question);
            tv_AnswerA = itemView.findViewById(R.id.tv_AnswerA);
            tv_AnswerB = itemView.findViewById(R.id.tv_AnswerB);
            tv_AnswerC = itemView.findViewById(R.id.tv_AnswerC);
            tv_AnswerD = itemView.findViewById(R.id.tv_AnswerD);

            A = itemView.findViewById(R.id.A);
            B = itemView.findViewById(R.id.B);
            C = itemView.findViewById(R.id.C);
            D = itemView.findViewById(R.id.D);
            btn_Update = itemView.findViewById(R.id.btn_update);
        }
    }
    private Context mContext;
    private ArrayList<Question> dsQuestion;
    public QuestionAdapter(Context mContext, ArrayList<Question> dsQuestion)
    {
        this.dsQuestion =dsQuestion;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View quizzView = inflater.inflate(R.layout.question_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(quizzView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = dsQuestion.get(position);
        int position1 = position +1;
        holder.tv_NumOfQues.setText("Câu thứ " + position1 + ": " + question.getName());
        holder.tv_AnswerA.setText(question.getAnswerA());
        holder.tv_AnswerB.setText(question.getAnswerB());
        holder.tv_AnswerC.setText(question.getAnswerC());
        holder.tv_AnswerD.setText(question.getAnswerD());



        if(question.getCorrectAnswer()==1)
        {
            holder.A.setTextColor(Color.parseColor("#F44336"));
            holder.B.setTextColor(Color.parseColor("#FF000000"));
            holder.C.setTextColor(Color.parseColor("#FF000000"));
            holder.D.setTextColor(Color.parseColor("#FF000000"));
        } else if(question.getCorrectAnswer()==2)
        {
            holder.B.setTextColor(Color.parseColor("#F44336"));
            holder.A.setTextColor(Color.parseColor("#FF000000"));
            holder.C.setTextColor(Color.parseColor("#FF000000"));
            holder.D.setTextColor(Color.parseColor("#FF000000"));

        }
        else if(question.getCorrectAnswer()==3)
        {
            holder.C.setTextColor(Color.parseColor("#F44336"));
            holder.B.setTextColor(Color.parseColor("#FF000000"));
            holder.A.setTextColor(Color.parseColor("#FF000000"));
            holder.D.setTextColor(Color.parseColor("#FF000000"));

        }
        else
        {
            holder.D.setTextColor(Color.parseColor("#F44336"));
            holder.B.setTextColor(Color.parseColor("#FF000000"));
            holder.C.setTextColor(Color.parseColor("#FF000000"));
            holder.A.setTextColor(Color.parseColor("#FF000000"));

        }


        holder.btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_quesion);
                Window window = dialog.getWindow();
                if(window == null)
                    return;

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAtributes = window.getAttributes();
                windowAtributes.gravity = Gravity.CENTER;
                window.setAttributes(windowAtributes);

                dialog.setCancelable(false);

                Button btn_exit = dialog.findViewById(R.id.btn_exit_question_dialog);
                Button btn_accept = dialog.findViewById(R.id.btn_accept_question_dialog);
                EditText et_content_ques = dialog.findViewById(R.id.et_content_Question);
                EditText  et_content_A= dialog.findViewById(R.id.et_content_A);
                EditText  et_content_B= dialog.findViewById(R.id.et_content_B);
                EditText  et_content_C= dialog.findViewById(R.id.et_content_C);
                EditText  et_content_D= dialog.findViewById(R.id.et_content_D);
                RadioButton rd_A = dialog.findViewById(R.id.rd_A);
                RadioButton rd_B = dialog.findViewById(R.id.rd_B);
                RadioButton rd_C = dialog.findViewById(R.id.rd_C);
                RadioButton rd_D = dialog.findViewById(R.id.rd_D);

                et_content_ques.setText(question.getName());
                et_content_A.setText(question.getAnswerA());
                et_content_B.setText(question.getAnswerB());
                et_content_C.setText(question.getAnswerC());
                et_content_D.setText(question.getAnswerD());


                if(question.getCorrectAnswer()==1)
                {
                    rd_A.setChecked(true);
                } else if (question.getCorrectAnswer()==2)
                {
                    rd_B.setChecked(true);
                } else if (question.getCorrectAnswer()==3)
                {
                    rd_C.setChecked(true);
                }else if (question.getCorrectAnswer()==4)
                {
                    rd_D.setChecked(true);
                }

                btn_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(rd_A.isChecked())
                        {
                            question.setCorrectAnswer(1);

                        } else if (rd_B.isChecked())
                        {
                            question.setCorrectAnswer(2);
                        } else if (rd_C.isChecked())
                        {
                            question.setCorrectAnswer(3);
                        }else
                        {
                            question.setCorrectAnswer(4);
                        }

                        Question updatequestion = new Question(
                                question.getQuestionID(),
                                et_content_ques.getText().toString(),
                                et_content_A.getText().toString(),
                                et_content_B.getText().toString(),
                                et_content_C.getText().toString(),
                                et_content_D.getText().toString(),
                                question.getCorrectAnswer(),
                                question.getQuizID());

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();
                        myRef.child("Question/"+question.getQuestionID()).setValue(updatequestion);

                        dialog.dismiss();

                    }

                });


                btn_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return dsQuestion.size();
    }
}
