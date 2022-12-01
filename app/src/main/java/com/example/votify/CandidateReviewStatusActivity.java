package com.example.votify;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CandidateReviewStatusActivity extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    EditText c;
    EditText s;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_status);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        c=findViewById(R.id.ed_cnic);
        s=findViewById(R.id.ed_stat);

        if (connection!=null){
            Statement statement = null;
            try {
                SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select CNIC, Status from Candidate where CNIC='"+sharedPreferences.getString("cCNIC","") +"'");
                while (resultSet.next())
                {
                    c.setText(resultSet.getString("CNIC"));
                    s.setText(resultSet.getString("Status"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(CandidateReviewStatusActivity.this,"null",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void changeStatus(View view)
    {
       String status_var=String.valueOf(s.getText());
       String cnic=String.valueOf(c.getText());
       if(status_var!="Active")
       {
           s.setText("Active");
       }
        if (connection!=null){
            Statement statement = null;
            try {

                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("update Candidate set [Status]='Active' where CNIC='"+cnic+"' ");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Intent i =new Intent(CandidateReviewStatusActivity.this,CandidateMenuActivity.class);
        Toast.makeText(CandidateReviewStatusActivity.this, "Status Changed",Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
}