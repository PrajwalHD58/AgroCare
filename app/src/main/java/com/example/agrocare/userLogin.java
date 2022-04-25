package com.example.agrocare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agrocare.splashscreen.screen1;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class userLogin extends AppCompatActivity {

    private static final String TAG = "UserLogin";
    Button islogin;
    TextView isSignup;
    TextInputEditText username_id, username_pass;
    CheckBox isCheck;
    private static usersdata user_data;
    private ProgressBar progressBar;
    private static String username;
    private String s1 = "username", s3 = "isCheck";
    private static final String PREF = "PREFS";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        setTitle("Login Page");

        islogin = findViewById(R.id.get_otp);
        isSignup = findViewById(R.id.sign_up);
        isCheck = findViewById(R.id.check);
        username_id = (TextInputEditText) findViewById(R.id.username_editText);
        username_pass = (TextInputEditText) findViewById(R.id.password_editText);
        progressBar = findViewById(R.id.progressBar);


        islogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_Id = username_id.getText().toString();
                String password_Id = username_pass.getText().toString();

                if (!username_Id.isEmpty()) {
                    username_id.setError(null);

                    if (!password_Id.isEmpty()) {
                        username_pass.setError(null);


                        String username_Id_curr = username_id.getText().toString();
                        String password_Id_curr = username_pass.getText().toString();

                        FirebaseDatabase firebaseDatabase;
                        DatabaseReference reference;

                        firebaseDatabase = FirebaseDatabase.getInstance();
                        reference = firebaseDatabase.getReference("UserData");

                        progressBar.setVisibility(View.VISIBLE);
                        islogin.setVisibility(View.GONE);
                        Query check_user = reference.orderByChild("username").equalTo(username_Id_curr);

                        check_user.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    username_id.setError(null);


                                    String password_check = dataSnapshot.child(username_Id_curr).child("password").getValue(String.class);
                                    if (password_check.equals(password_Id_curr)) {
                                        username_pass.setError(null);
                                        username = username_Id_curr;
                                        Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                                        if (isCheck.isChecked()) {
                                            SharedPreferences sharedPreferences = getSharedPreferences(PREF, MODE_PRIVATE);
                                            SharedPreferences.Editor edit = sharedPreferences.edit();
                                            edit.putString(s1, username);
                                            edit.putBoolean(s3, true);
                                            edit.commit();

                                        }
                                        screen1.setUsername(username);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        username_pass.setError("Wrong Password!");
                                    }
                                } else {
                                    username_id.setError("Invalid Username");
                                }
                                progressBar.setVisibility(View.GONE);
                                islogin.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                progressBar.setVisibility(View.GONE);
                                islogin.setVisibility(View.VISIBLE);

                            }
                        });

                    } else {
                        username_pass.setError("Please Enter Valid Password");
                    }
                } else {
                    username_id.setError("Please Enter Valid Username");
                }
            }

        });

        isSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), userSignup.class);
                startActivity(intent);
            }
        });

    }


}

