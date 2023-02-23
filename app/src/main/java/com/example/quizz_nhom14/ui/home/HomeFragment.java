package com.example.quizz_nhom14.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizz_nhom14.MainActivity;
import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.adapterclass.QuizzsAdapter;
import com.example.quizz_nhom14.databinding.FragmentHomeBinding;
import com.example.quizz_nhom14.object.Quiz;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    QuizzsAdapter adapter;
    ArrayList<Quiz> Quizzss;

    long numof_quiz;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        final  RecyclerView rv_quizz= binding.rvDsQuizz;

        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        Quizzss = new ArrayList<Quiz>();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();



        myRef.child("Quiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numof_quiz = snapshot.getChildrenCount();
                Quizzss.clear();
                for(DataSnapshot sn : snapshot.getChildren())
                {
                   Quizzss.add(sn.getValue(Quiz.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter= new QuizzsAdapter(getActivity(), Quizzss);

        rv_quizz.setAdapter(adapter);
        rv_quizz.setLayoutManager(new LinearLayoutManager(getActivity()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}