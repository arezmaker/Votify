package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView ecplogo;
    private ImageView votlogo;
    private static int splashTimeOut=5000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ecplogo=(ImageView)findViewById(R.id.imageView4);
        votlogo=(ImageView)findViewById(R.id.imageView5);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },splashTimeOut);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        Animation myanim2 = AnimationUtils.loadAnimation(this,R.anim.splash_anim2);
        ecplogo.startAnimation(myanim);
        votlogo.startAnimation(myanim2);
    }
}