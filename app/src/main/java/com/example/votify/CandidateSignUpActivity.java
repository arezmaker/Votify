package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CandidateSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_sign_up);
    }

    public void goToCandidateLoginActivity(View view){
        Intent i=new Intent(this,CandidateLoginActivity.class);
        startActivity(i);
    }
}