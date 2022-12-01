package com.example.votify;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.votify.model.Candidate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CandidateLoginActivity extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_login);

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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void goToMenuActivity(View view){
        EditText v_cnic=findViewById(R.id.c_editTextNumberSigned);
        char[] cnic=new char[15];
        if(String.valueOf(v_cnic.getText()).length()==13)
        {
            if (Long.parseLong(String.valueOf(v_cnic.getText()))>0000000000000)
            {
                cnic=String.valueOf(v_cnic.getText()).toCharArray();
            }
            else
            {
                Toast.makeText(CandidateLoginActivity.this,"Invalid CNIC",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else
        {
            Toast.makeText(CandidateLoginActivity.this,"Invalid CNIC",Toast.LENGTH_SHORT).show();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        EditText v_date=findViewById(R.id.c_editTextDate);
        LocalDate edate;
        try {
            edate = LocalDate.parse(String.valueOf(v_date.getText()), formatter);
        } catch (Exception e) {
            Toast.makeText(CandidateLoginActivity.this,"Invalid Date Format",Toast.LENGTH_SHORT).show();
            return;
        }

        //Toast.makeText(VoterLoginActivity.this,String.valueOf(edate),Toast.LENGTH_SHORT).show();
        Candidate candidate=new Candidate();


        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                String query = "Select * from Candidate where CNIC='"+String.valueOf(cnic)+"' and expiryDate='"+String.valueOf(edate)+"'";
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()){
                    SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("cCNIC",String.valueOf(cnic));
                    editor.apply();
                    Intent i=new Intent(this,CandidateMenuActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(CandidateLoginActivity.this,"invalid login",Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void goToCandidateSignUpActivity(View view){
        Intent i=new Intent(CandidateLoginActivity.this,CandidateSignUpActivity.class);
        startActivity(i);
    }
}