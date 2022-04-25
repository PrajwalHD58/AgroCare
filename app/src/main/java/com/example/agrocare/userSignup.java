package com.example.agrocare;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userSignup extends AppCompatActivity {

    Button isSignup2;
    TextView islogin2;
    TextInputEditText fullname_id, username_signup, phonenum_id, email_id, password_signup;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);


        setTitle("Sign Up Page");
        isSignup2 = findViewById(R.id.register);
        islogin2 = findViewById(R.id.sign_in);

        fullname_id = (TextInputEditText) findViewById(R.id.user_full_name);
        username_signup = (TextInputEditText) findViewById(R.id.user_name);
        password_signup = (TextInputEditText) findViewById(R.id.password_editText);
        phonenum_id = (TextInputEditText) findViewById(R.id.user_phone_number);
        email_id = (TextInputEditText) findViewById(R.id.email);

        islogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        isSignup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup(v);
            }

        });
    }


    public void login(View view) {
        Intent intent = new Intent(getApplicationContext(), userLogin.class);
        startActivity(intent);
        finish();
    }

    public void signup(View view) {
        String fn = fullname_id.getText().toString();
        String pn = phonenum_id.getText().toString();
        String user = username_signup.getText().toString();
        String pass = password_signup.getText().toString();
        String em = email_id.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!fn.isEmpty()) {
            fullname_id.setError(null);

            if (!user.isEmpty()) {

                username_signup.setError(null);

                if (!pn.isEmpty()) {

                    phonenum_id.setError(null);

                    if (!em.isEmpty()) {

                        email_id.setError(null);

                        if (!pass.isEmpty()) {

                            password_signup.setError(null);


                            if (em.matches(emailPattern)) {
                                email_id.setError(null);

                                firebaseDatabase = FirebaseDatabase.getInstance();
                                reference = firebaseDatabase.getReference("UserData");


                                String fn_s = fullname_id.getText().toString();
                                String pn_s = phonenum_id.getText().toString();
                                String user_s = username_signup.getText().toString();
                                String pass_s = password_signup.getText().toString();
                                String em_s = email_id.getText().toString();

                                usersdata ud = new usersdata(fn_s, user_s, pass_s, pn_s, em_s);

                                reference.child(user_s).setValue(ud);

                                Toast t = Toast.makeText(getApplicationContext(), "Data Saved,Please Login to Continue!!", Toast.LENGTH_LONG);
                                t.show();

                                Intent intent = new Intent(getApplicationContext(), userLogin.class);
                                startActivity(intent);
                                finish();
                            } else {
                                email_id.setError("Please Enter Valid email2");
                            }
                        } else {
                            password_signup.setError("Please Enter Valid Password");
                        }
                    } else {
                        email_id.setError("Please Enter Valid email");
                    }
                } else {

                    phonenum_id.setError("Please Enter Valid phone number");
                }
            } else {
                username_signup.setError("Please Enter Valid Username");
            }
        } else {
            fullname_id.setError("Please Enter Valid Fullname");
        }


    }
}




