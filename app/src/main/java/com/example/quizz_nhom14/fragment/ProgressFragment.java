package com.example.quizz_nhom14.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.activity.ViewDidQuiz;
import com.example.quizz_nhom14.activity.ViewProgressActivity;
import com.example.quizz_nhom14.adapterclass.DidSameQuizAdapter;
import com.example.quizz_nhom14.object.DidQuiz;
import com.example.quizz_nhom14.object.DidSameQuizs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ProgressFragment extends Fragment {
    TextView test;
    ListView lvDidSameQuiz;
    ArrayList<DidQuiz> ListDidQuiz;
    ArrayList<DidSameQuizs> ListDidSameQuiz;
    DidSameQuizAdapter didSameQuizAdapter;
    ProgressBar progressBar;
    int count=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.progress_layout,container,false);

        progressBar=v.findViewById(R.id.progress1);
//        test=v.findViewById(R.id.tv_Title);
        lvDidSameQuiz=v.findViewById(R.id.lvdidsame);
        ListDidQuiz=new ArrayList<>();
        ListDidSameQuiz=new ArrayList<>();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("DidQuiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s:snapshot.getChildren()){
                    ListDidQuiz.add(s.getValue(DidQuiz.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LocalDateTime myDateObj = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    myDateObj = LocalDateTime.now();
//                }
//                DateTimeFormatter myFormatObj = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//                }
//
//                String formattedDate = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    formattedDate = myDateObj.format(myFormatObj);
//                }
//                Toast.makeText(v.getContext(), formattedDate, Toast.LENGTH_SHORT).show();
//            }
//        });

        CountDownTimer begin=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                sort(ListDidQuiz,0,ListDidQuiz.size()-1);
                if(ListDidQuiz.size()!=0){
                    ListDidSameQuiz.add(new DidSameQuizs());
                    if(ListDidQuiz.size()>=2){
                        for(int i=1;i<ListDidQuiz.size();i++){
                            if (ListDidQuiz.get(i).getQuiz().getID() != ListDidQuiz.get(i - 1).getQuiz().getID()){
                                ListDidSameQuiz.add(new DidSameQuizs());
                            }
                        }
                        for(int i=1;i<ListDidQuiz.size();i++){
                            if (ListDidQuiz.get(i).getQuiz().getID() == ListDidQuiz.get(i - 1).getQuiz().getID()) {
                                ListDidSameQuiz.get(count).list.add(ListDidQuiz.get(i));
                            }
                            else{
                                count++;
                                ListDidSameQuiz.get(count).list.add(ListDidQuiz.get(i));
                            }
                        }
                    }
                }
                didSameQuizAdapter=new DidSameQuizAdapter(v.getContext(),ListDidSameQuiz);
                lvDidSameQuiz.setAdapter(didSameQuizAdapter);
            }
        }.start();

        lvDidSameQuiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent a=new Intent(v.getContext(), ViewDidQuiz.class);
                startActivity(a);
            }
        });

        return v;
    }
    int partition(ArrayList<DidQuiz> arr, int low, int high) {
        int pivot = arr.get(high).getQuiz().getID();
        int i = (low - 1);
        for (int j = low; j < high; j++) {

            if (arr.get(j).getQuiz().getID() < pivot) {
                i++;

                DidQuiz temp = arr.get(i);
                arr.set(i,arr.get(j));
                arr.set(j,temp);
            }
        }
        DidQuiz temp = arr.get(i+1);
        arr.set(i+1,arr.get(high));
        arr.set(high,temp);

        return i + 1;
    }

    void sort(ArrayList<DidQuiz> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }
}
