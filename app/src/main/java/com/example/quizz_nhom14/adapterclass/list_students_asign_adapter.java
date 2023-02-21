package com.example.quizz_nhom14.adapterclass;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.object.User;

import java.util.ArrayList;

public class list_students_asign_adapter extends BaseAdapter {

    final ArrayList<User> listUser;

    public list_students_asign_adapter(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int position) {
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listUser.get(position).getUserID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.student_added_item, null);
        } else viewProduct = convertView;

        User user = (User) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.tv_id)).setText(String.format("ID = %d", user.getUserID()));
        ((TextView) viewProduct.findViewById(R.id.tv_name)).setText(String.format("Tên SP : %s", user.getFullname()));

        return viewProduct;
    }
}
