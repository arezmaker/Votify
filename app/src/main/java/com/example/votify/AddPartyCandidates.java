package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AddPartyCandidates extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_party_candidates);
    }

    public void goToAddCandidateInfo(View view){
        Intent i=new Intent(AddPartyCandidates.this,AddCandidateInfo.class);
        startActivity(i);
    }

    public void goToECPPartyList(View view){
        Intent i=new Intent(AddPartyCandidates.this,ECPPartyList.class);
        startActivity(i);
    }

}