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

        EasySplashScreen config = new EasySplashScreen(screen1.this)
                .withFullScreen()
                .withTargetActivity(userLogin.class)
                .withSplashTimeOut(3000)
                .withBackgroundResource(android.R.color.white)
                .withHeaderText("AgroCare")
                .withFooterText("Developed By- Prajwal")
                .withBeforeLogoText("AgroCare")
                .withLogo(R.drawable.ic_plant)
                .withAfterLogoText("For Smart Monitoring of Agriculture!");


        //set your own animations
        myCustomTextViewAnimation(config.getFooterTextView());

        //customize all TextViews

        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);

        //create the view
        View easySplashScreenView = config.create();

        setContentView(easySplashScreenView);



    }

    private void myCustomTextViewAnimation(TextView tv){
        Animation animation=new TranslateAnimation(0,0,480,0);
        animation.setDuration(1200);
        tv.startAnimation(animation);
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