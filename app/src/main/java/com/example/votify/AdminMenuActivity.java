package com.example.votify;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminMenuActivity extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    String name="";
    int tvote=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

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

        if (connection!=null)
        {
            Statement statement=null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from Party where totalVote=(select max(totalVote) from Party)");
                if (resultSet.next())
                {
                    name=name+resultSet.getString("name");
                    tvote=resultSet.getInt("totalVote");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    public void goToReviewFeedback(View view)
    {
        Intent i=new Intent(AdminMenuActivity.this,AdminReviewFeedback.class);
        startActivity(i);
    }

    public void generateResult(View view)
    {
        String res=name+" won the elections by securing "+tvote+" votes";
        Toast.makeText(AdminMenuActivity.this,res,Toast.LENGTH_SHORT).show();

        if (connection!=null)
        {
            Statement statement=null;
            try {
                statement = connection.createStatement();
                statement.executeQuery("update Election set Result='"+res+"' where id=(select max(id) from Election)");
//                if (resultSet.next())
//                {
//                    name=name+resultSet.getString("name");
//                    tvote=resultSet.getInt("totalVote");
//                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}