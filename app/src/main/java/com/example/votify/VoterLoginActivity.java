package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class VoterLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_login);
    }

    public void goToMenuActivity(View view){
        Intent i=new Intent(this,MenuActivity.class);
        startActivity(i);
    }

    public void goToVoterSignUpActivity(View view){
        Intent i=new Intent(this,VoterSignUpActivity.class);
        startActivity(i);
    }
}