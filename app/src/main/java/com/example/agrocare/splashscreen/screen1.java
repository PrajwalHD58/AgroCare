package com.example.agrocare.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.agrocare.MainActivity;
import com.example.agrocare.R;
import com.example.agrocare.userLogin;

import gr.net.maroulis.library.EasySplashScreen;

public class screen1 extends AppCompatActivity {

    private String s1 = "username", s3 = "isCheck";
    private static final String PREF = "PREFS";
    private static String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);

        if (loadData()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        Button btn=findViewById(R.id.next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),userLogin.class);
                startActivity(intent);
                finish();
            }
        });




    }



    private Boolean loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF, MODE_PRIVATE);
        username = sharedPreferences.getString(s1, "");
        Boolean temp = sharedPreferences.getBoolean(s3, false);
        return temp;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String userName) {
        username = userName;
    }
}