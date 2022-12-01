package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AddCandidateInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_candidate_info);
    }

    public void goToAddPartyCandidates(View view){
        Intent i=new Intent(AddCandidateInfo.this,AddPartyCandidates.class);
        startActivity(i);
    }

}