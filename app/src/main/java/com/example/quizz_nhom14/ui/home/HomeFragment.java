package com.example.quizz_nhom14.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.adapterclass.QuizzsAdapter;
import com.example.quizz_nhom14.databinding.FragmentHomeBinding;
import com.example.quizz_nhom14.object.Quiz;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    QuizzsAdapter adapter;
    ArrayList<Quiz> Quizzss;

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
        Quizzss.add(new Quiz("hayhoc","mmt",3,"huy"));
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