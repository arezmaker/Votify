package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToVoterLoginActivity(View view){
        Intent i=new Intent(this,VoterLoginActivity.class);
        startActivity(i);
    }

    public void goToECPLoginActivity(View view){
        Intent i=new Intent(this,ECPLoginActivity.class);
        startActivity(i);
    }

    public void goToCandidateLoginActivity(View view){
        Intent i=new Intent(this,CandidateLoginActivity.class);
        startActivity(i);
    }

    public void goToAdminLoginActivity(View view){
        Intent i=new Intent(this,AdminLoginActivity.class);
        startActivity(i);
    }
}