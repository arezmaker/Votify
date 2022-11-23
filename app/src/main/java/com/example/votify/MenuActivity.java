package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void goToFeedback(View view)
    {
        Intent i=new Intent(MenuActivity.this,FeedbackActivity.class);
        startActivity(i);
    }

    public void goToReviewStatus(View view)
    {
        Intent i=new Intent(MenuActivity.this,ReviewStatusActivity.class);
        startActivity(i);
    }
}