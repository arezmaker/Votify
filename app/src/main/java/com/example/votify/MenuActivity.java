package com.example.votify;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

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
        Intent intent=getIntent();
        switch (item.getItemId())
        {
            case R.id.profile:
                Intent i=new Intent(this,ProfileActivity.class);
                i.putExtra("CNIC1",intent.getStringExtra("CNIC"));
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
        Intent intent=getIntent();
        Intent i=new Intent(this, TypeofVoteActivity.class);
        i.putExtra("CNIC1",intent.getStringExtra("CNIC"));
        startActivity(i);
    }

    public void goToFeedback(View view)
    {
        Intent i=new Intent(MenuActivity.this,FeedbackActivity.class);
        startActivity(i);
    }

    public void goToReviewStatus(View view)
    {
        Intent i=new Intent(MenuActivity.this,ReviewStatusActivity.class);
        startActivity(i);
    }
}