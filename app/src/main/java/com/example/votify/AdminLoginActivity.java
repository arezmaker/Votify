package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    }

    public void goToAdminMenu(View view)
    {
        Intent i=new Intent(AdminLoginActivity.this,AdminMenuActivity.class);
        startActivity(i);
    }
}