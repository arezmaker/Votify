package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class VoterSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_sign_up);
    }

    public void goToVoterLoginActivity(View view){
        Intent i=new Intent(VoterSignUpActivity.this,VoterLoginActivity.class);
        startActivity(i);
    }
}