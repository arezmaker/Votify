package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CandidateLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_login);
    }

    public void goToMenuActivity(View view){
        Intent i=new Intent(this,MenuActivity.class);
        startActivity(i);
    }

    public void goToCandidateSignUpActivity(View view){
        Intent i=new Intent(this,CandidateSignUpActivity.class);
        startActivity(i);
    }
}