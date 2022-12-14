package com.example.votify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }
    public void popup(View v)
    {
        PopupMenu popupMenu=new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.profile:
                Intent i=new Intent(this,ProfileActivity.class);
                SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("vCNIC",sharedPreferences.getString("vCNIC",""));
                editor.apply();
                startActivity(i);
                return true;
            case R.id.logout:
                Intent i1=new Intent(this,MainActivity.class);
                startActivity(i1);
                return true;
            default:
                return false;

        }
    }

    public void gotoCastVote(View view){
        Intent i=new Intent(this, TypeofVoteActivity.class);
        SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("vCNIC",sharedPreferences.getString("vCNIC",""));
        editor.apply();
        startActivity(i);
    }

    public void goToFeedback(View view)
    {
        Intent i=new Intent(MenuActivity.this,FeedbackActivity.class);
        SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("vCNIC",sharedPreferences.getString("vCNIC",""));
        editor.apply();
        startActivity(i);
    }

    public void goToReviewStatus(View view)
    {
        Intent i=new Intent(MenuActivity.this,ReviewStatusActivity.class);
        SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("vCNIC",sharedPreferences.getString("vCNIC",""));
        editor.apply();
        startActivity(i);
    }

    public void goToViewResult(View view)
    {
        Intent i=new Intent(MenuActivity.this,ViewResult.class);
        startActivity(i);
    }

    public void goToUpdateInfo(View view)
    {
        Intent i=new Intent(MenuActivity.this,UpdateInfoActivity.class);
        SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("vCNIC",sharedPreferences.getString("vCNIC",""));
        editor.apply();
        startActivity(i);
    }
}