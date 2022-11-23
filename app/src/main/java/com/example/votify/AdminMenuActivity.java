package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }


    public void goToReviewFeedback(View view)
    {
        Intent i=new Intent(AdminMenuActivity.this,AdminReviewFeedback.class);
        startActivity(i);
    }
}