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

    public void goToCandidateList(View view){
        Intent i=new Intent(ECPMenuActivity.this,CandidateListActivity.class);
        startActivity(i);
    }

    public void goToCreateElection(View view){
        Intent i= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            i = new Intent(ECPMenuActivity.this, CreateElection.class);
        }
        startActivity(i);
    }

}