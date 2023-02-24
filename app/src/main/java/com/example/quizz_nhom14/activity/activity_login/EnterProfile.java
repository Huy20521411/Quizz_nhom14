package com.example.quizz_nhom14.activity.activity_login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.activity.MainActivity;
import com.example.quizz_nhom14.object.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EnterProfile extends AppCompatActivity {
    int newuID;
    ArrayList<User> users = new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_profile);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                newuID= (int) (snapshot.getChildrenCount()+1);
                for (DataSnapshot s : snapshot.getChildren()) {
                    users.add(s.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
            }
        });
        EditText mFullnameEditText = findViewById(R.id.fullname_edit_text);
        EditText mPhoneEditText = findViewById(R.id.phone_edit_text);
        mPhoneEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditText mEmailEditText = findViewById(R.id.email_edit_text);
        RadioButton mGvRadioButton = findViewById(R.id.gv_radio_button);
        mGvRadioButton.setChecked(true);
        RadioButton mMaleRadioButton = findViewById(R.id.male_radio_button);
        mMaleRadioButton.setChecked(true);
        Button mSubmitButton = findViewById(R.id.submit_button);
        Intent intent=getIntent();
        mEmailEditText.setText(intent.getStringExtra("email"));
        mEmailEditText.setEnabled(false);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean classify = mGvRadioButton.isChecked();
                int gender = mMaleRadioButton.isChecked() ? 1 : 2;
                String fullname = mFullnameEditText.getText().toString().trim();
                String phone = mPhoneEditText.getText().toString().trim();
                String email = mEmailEditText.getText().toString().trim();
                if(fullname.equals("")||email.equals("")||phone.equals(""))
                    Toast.makeText(EnterProfile.this, "Please enter full information", Toast.LENGTH_LONG).show();
                else{
                    User user = new User(intent.getStringExtra("Uid"),newuID,classify, gender, fullname, phone, email);
                    //Gui user len realtime database
                    myRef.child("User/"+newuID).setValue(user);
                    Intent intent=new Intent(EnterProfile.this, MainActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}