package com.example.quizz_nhom14.activity.activity_login;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
    ArrayList<User> users = new ArrayList<User>();
    private FirebaseAuth firebaseAuth;
    int newuID=0;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
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
        forgotPasswordTextView.setPaintFlags(forgotPasswordTextView.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
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
                Toast.makeText(LoginActivity.this, "Đọc thông tin thất bại",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listeners for buttons
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
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

                                for (int i = 0; i <= users.size(); i++) {
                                    //Nếu đã có thông tin trong database
                                    // Login successful, go to main activity
                                    if (i<users.size()) {
                                        if(currentUser.getUid().equals(users.get(i).getUID())){
                                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                            intent.putExtra("user",users.get(i));
                                            startActivity(intent);
                                            finish();
                                            break;
                                        }
                                    }
                                    else {
                                        //Tài khoản đã xác minh nhưng chưa có thông tin trong database
                                        //Tạo Dialog cho người dùng nhập thông tin vào
                                        Intent in=new Intent(LoginActivity.this,EnterProfile.class);
                                        in.putExtra("email",currentUser.getEmail());
                                        in.putExtra("Uid",currentUser.getUid());
                                        startActivity(in);
                                        finish();
                                    }
                                }

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





