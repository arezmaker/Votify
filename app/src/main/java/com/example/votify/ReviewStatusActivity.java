package com.example.votify;

import android.Manifest;
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

public class ReviewStatusActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_status);

        c=findViewById(R.id.rf_editTextNumberSigned);
        s=findViewById(R.id.editTextTextPersonName);

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

        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select CNIC,Status from voter where CNIC='12345'");
                c.setText(resultSet.getString("CNIC"));
                s.setText(resultSet.getString("Status"));

            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(ReviewStatusActivity.this,"null",Toast.LENGTH_SHORT).show();
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
                ResultSet resultSet = statement.executeQuery("update voter set [Status]='Inactive' where CNIC='"+cnic+"' ");



            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}