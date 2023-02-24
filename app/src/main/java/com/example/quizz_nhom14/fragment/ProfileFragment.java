package com.example.quizz_nhom14.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.activity.MainActivity;
import com.example.quizz_nhom14.activity.activity_login.LoginActivity;
import com.example.quizz_nhom14.object.User;

public class ProfileFragment extends Fragment {
    ImageView ava;
    TextView name,gender,phone,role,email;
    Button btnLogout;
    MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_myprofile, container,false);
        mainActivity=(MainActivity)getActivity();
        User u=mainActivity.getUser();
        name=v.findViewById(R.id.tv_Fullname);
        gender=v.findViewById(R.id.tv_Gender);
        phone=v.findViewById(R.id.tv_Phone);
        role=v.findViewById(R.id.tv_Role);
        email=v.findViewById(R.id.tv_Mail);
        btnLogout=v.findViewById(R.id.btn_Logout);
        name.setText(" "+u.getFullname());
        if(u.getGender()==1) gender.setText(" Nam");
        else gender.setText( " Nữ");
        phone.setText(u.getPhone());
        if(u.isClasssify()) role.setText(" Giảng viên");
        else role.setText(" Sinh viên");
        email.setText(" "+u.getEmail());
        phone.setText(" "+u.getPhone());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_confirm_logout);
                TextView huy,ok;
                huy=dialog.findViewById(R.id.dialog_huy);
                ok=dialog.findViewById(R.id.dialog_ok);
                dialog.show();
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(mainActivity, LoginActivity.class));
                        mainActivity.finish();
                    }
                });
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        return v;
    }
}
