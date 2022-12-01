package com.example.votify;

import static android.widget.Toast.LENGTH_SHORT;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CandidateTypeofVote extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    Spinner spinner;
    String[] type={"Select Type","National","Provincial"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeofvote);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, type);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    if (connection != null) {
                        Statement statement = null;
                        try {
                            SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                            statement = connection.createStatement();
                            String query1 = "Select * from Candidate where CNIC='" + sharedPreferences.getString("cCNIC","") + "' and typeofVote = 'national'";
                            ResultSet resultSet = statement.executeQuery(query1);
                            if (resultSet.next()) {
                                Intent i1 = new Intent(CandidateTypeofVote.this, CandidateCastVote.class);
                                SharedPreferences sharedPreferences1=getSharedPreferences("CNIC",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences1.edit();
                                editor.putString("cCNIC",sharedPreferences1.getString("cCNIC",""));
                                editor.apply();
                                startActivity(i1);
                            } else {
                                Toast.makeText(CandidateTypeofVote.this, "Wrong Type of Vote Selected", LENGTH_SHORT).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (i == 2) {
                    if (connection != null) {
                        Statement statement = null;
                        try {
                            SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                            statement = connection.createStatement();
                            String query1 = "Select * from Candidate where CNIC='" + sharedPreferences.getString("cCNIC","") + "' and typeofVote = 'provincial'";
                            ResultSet resultSet = statement.executeQuery(query1);
                            if (resultSet.next()) {
                                Intent i1 = new Intent(CandidateTypeofVote.this, CandidateCastVote.class);
                                SharedPreferences sharedPreferences1=getSharedPreferences("CNIC",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences1.edit();
                                editor.putString("cCNIC",sharedPreferences1.getString("cCNIC",""));
                                editor.apply();
                                startActivity(i1);
                            } else {
                                Toast.makeText(CandidateTypeofVote.this, "Wrong Type of Vote Selected", LENGTH_SHORT).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}