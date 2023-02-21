package com.example.quizz_nhom14.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.object.User;

public class ProfileFragment extends Fragment {
    ImageView ava;
    TextView name,gender,phone,role,email;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_myprofile, container,false);
        User u=new User(false,1,"Ngo Quoc Khoi","0901960571","Khoi123@gmail.com");
        name=v.findViewById(R.id.tv_Fullname);
        gender=v.findViewById(R.id.tv_Gender);
        phone=v.findViewById(R.id.tv_Phone);
        role=v.findViewById(R.id.tv_Role);
        email=v.findViewById(R.id.tv_Mail);
        name.setText(" "+u.getFullname());
        if(u.getGender()==1) gender.setText(" Nam");
        else gender.setText( "Nữ");
        phone.setText(u.getPhone());
        if(u.isClasssify()) role.setText(" Giảng viên");
        else role.setText(" Sinh viên");
        email.setText(" "+u.getEmail());
        phone.setText(" "+u.getPhone());
        return v;
    }
}
