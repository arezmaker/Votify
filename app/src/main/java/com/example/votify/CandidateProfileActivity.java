package com.example.votify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CandidateProfileActivity extends AppCompatActivity {
    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "test";
    private static String username = "test";
    private static String password = "123";
    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;

    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView1 = (TextView) findViewById(R.id.textView3);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView4);
        TextView textView4 = (TextView) findViewById(R.id.textView5);
        TextView textView5 = (TextView) findViewById(R.id.textView6);
        Intent i = getIntent();
        //Toast.makeText(ProfileActivity.this, i.getStringExtra("CNIC1"), LENGTH_SHORT).show();
        if (connection != null) {
            Statement statement = null;
            try {
                SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                statement = connection.createStatement();
                String query = "Select * from Candidate where CNIC ='" +sharedPreferences.getString("cCNIC","") + "'";
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    textView.setText(rs.getString(9));
                    textView1.setText(rs.getString(3));
                    textView2.setText(rs.getString(1));
                    textView3.setText(rs.getString(4));
                    textView4.setText(rs.getString(5));
                    textView5.setText(rs.getString(2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}