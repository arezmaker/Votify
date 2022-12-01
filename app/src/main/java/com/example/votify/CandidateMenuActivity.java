package com.example.votify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;


public class CandidateMenuActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_menu);

    }
    public void popup1(View v)
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
                Intent i=new Intent(this,CandidateProfileActivity.class);
                SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("cCNIC",sharedPreferences.getString("cCNIC",""));
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
    public void gotoCastVote1(View view){
        Intent i=new Intent(this, CandidateTypeofVote.class);
        SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("cCNIC",sharedPreferences.getString("cCNIC",""));
        editor.apply();
        startActivity(i);
    }
    public void goToFeedback1(View view)
    {
        Intent intent=getIntent();
        Intent i=new Intent(CandidateMenuActivity.this, CandidateFeedbackActivity.class);
        SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("cCNIC",sharedPreferences.getString("cCNIC",""));
        editor.apply();
        //i.putExtra("CNIC1",intent.getStringExtra("CNIC"));
        startActivity(i);
    }

    public void goToReviewStatus1(View view)
    {
        Intent intent=getIntent();
        Intent i=new Intent(CandidateMenuActivity.this,CandidateReviewStatusActivity.class);
//        i.putExtra("CNIC1",intent.getStringExtra("CNIC"));
        SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("cCNIC",sharedPreferences.getString("cCNIC",""));
        editor.apply();
        startActivity(i);
    }

//    public void goToViewResult1(View view)
//    {
//        Intent i=new Intent(CandidateMenuActivity.this,ViewResult.class);
//        startActivity(i);
//    }

    public void goToUpdateInfo1(View view)
    {
        Intent intent=getIntent();
        Intent i=new Intent(CandidateMenuActivity.this,CandidateUpdateInfoActivity.class);
        SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("cCNIC",sharedPreferences.getString("cCNIC",""));
        editor.apply();
        //i.putExtra("CNIC1",intent.getStringExtra("CNIC"));
        startActivity(i);
    }
}