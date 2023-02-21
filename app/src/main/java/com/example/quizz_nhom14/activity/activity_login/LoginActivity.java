package com.example.quizz_nhom14.activity.activity_login;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.quizz_nhom14.R;
import com.example.quizz_nhom14.activity.MainActivity;
import com.example.quizz_nhom14.object.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    private TextView forgotPasswordTextView;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        emailEditText = findViewById(R.id.email_field);
        passwordEditText = findViewById(R.id.password_field);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        forgotPasswordTextView = findViewById(R.id.forgot_password_text);

        // Set click listeners for buttons
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                Toast.makeText(LoginActivity.this, "1111",
                        Toast.LENGTH_SHORT).show();
                //
                if (currentUser != null) {
                    boolean isEmailVerified = currentUser.isEmailVerified();
//                    if (!isEmailVerified) {
//                        Toast.makeText(LoginActivity.this, "Tài khoản chưa được kích hoạt qua gmail.",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        //add to firebase realtime database
//                        Toast.makeText(LoginActivity.this, "Login success",
//                                Toast.LENGTH_SHORT).show();
//                        //
                        loginUser();


//                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "current user null",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required.");
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            boolean isEmailVerified = currentUser.isEmailVerified();

                            //Login và xác thực thành công
                            if (isEmailVerified) {
//                                 Kiểm tra đã có dữ liệu tài khoản trong database chưa

                                ArrayList<User> users = new ArrayList<User>();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("message");
                                myRef.child("User").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {

                                        for (DataSnapshot s : snapshot.getChildren()) {
                                            users.add(s.getValue(User.class));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                                        Toast.makeText(LoginActivity.this, "Đọc thông tin thất bại",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });

                                for (int i = 0; i < users.size(); i++) {
                                    //Nếu đã có thông tin trong database
                                    // Login successful, go to main activity
                                    if (currentUser.getUid() == users.get(i).getUserID()) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                    }
                                }
                                //Tài khoản đã xác minh nhưng chưa có thông tin trong database
                                //Tạo Dialog cho người dùng nhập thông tin vào

                                Dialog dialog = new Dialog(LoginActivity.this);
                                dialog.setContentView(R.layout.dialog_user_input);
                                EditText mFullnameEditText = dialog.findViewById(R.id.fullname_edit_text);
                                EditText mPhoneEditText = dialog.findViewById(R.id.phone_edit_text);
                                EditText mEmailEditText = dialog.findViewById(R.id.email_edit_text);
                                RadioGroup mClassifyRadioGroup = dialog.findViewById(R.id.classify_radio_group);
                                RadioButton mGvRadioButton = dialog.findViewById(R.id.gv_radio_button);
                                RadioButton mSvRadioButton = dialog.findViewById(R.id.sv_radio_button);
                                RadioGroup mGenderRadioGroup = dialog.findViewById(R.id.gender_radio_group);
                                RadioButton mMaleRadioButton = dialog.findViewById(R.id.male_radio_button);
                                RadioButton mFemaleRadioButton = dialog.findViewById(R.id.female_radio_button);
                                Button mSubmitButton = dialog.findViewById(R.id.submit_button);

                                mEmailEditText.setText(currentUser.getEmail());
                                mEmailEditText.setEnabled(false);
                                mSubmitButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        boolean classify = mGvRadioButton.isChecked();
                                        int gender = mMaleRadioButton.isChecked() ? 1 : 2;
                                        String fullname = mFullnameEditText.getText().toString();
                                        String phone = mPhoneEditText.getText().toString();
                                        String email = mEmailEditText.getText().toString();
                                        String username = "";
                                        String password = "";
                                        User user = new User(classify, gender, fullname, phone, email);
                                        myRef.setValue(user);
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Tài khoản chưa được kích hoạt qua gmail.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}





