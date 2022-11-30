package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ECPMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecpmenu);
    }

    public void goToECPPartyList(View view){
        Intent i=new Intent(ECPMenuActivity.this,ECPPartyList.class);
        startActivity(i);
    }

    public void goToVoterList(View view){
        Intent i=new Intent(ECPMenuActivity.this,VoterList.class);
        startActivity(i);
    }
}