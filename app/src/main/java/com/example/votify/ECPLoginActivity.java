package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ECPLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecplogin);

    }

    public void goToECPMenu(View view){
        Intent i=new Intent(ECPLoginActivity.this,ECPMenuActivity.class);
        startActivity(i);
    }
}