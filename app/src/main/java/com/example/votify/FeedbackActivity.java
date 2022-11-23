package com.example.votify;

import static android.widget.Toast.LENGTH_SHORT;

import android.Manifest;
import android.content.Intent;
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
import java.sql.SQLException;
import java.sql.Statement;

public class FeedbackActivity extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    EditText subject;
    EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

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


    public void goToMenuActivityFromFeedback(View view){
        subject=findViewById(R.id.feedback_editTextSubject);
        String sub=String.valueOf(subject.getText());
        Toast.makeText(FeedbackActivity.this,sub, LENGTH_SHORT).show();

        message=findViewById(R.id.feedback_editTextTextMultiLine);
        String msg=String.valueOf(message.getText());
        Toast.makeText(FeedbackActivity.this,msg, LENGTH_SHORT).show();

        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                String query = "INSERT INTO Feedback VALUES ('" + sub  + "','"+ msg+ "') " ;
                statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(FeedbackActivity.this,"error", LENGTH_SHORT).show();
        }

        Intent i=new Intent(FeedbackActivity.this,MenuActivity.class);
        startActivity(i);
    }
}