package com.example.votify;

import android.Manifest;
import android.content.Intent;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminLoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_admin_login);

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
    public void login(View view){
        EditText a_username=findViewById(R.id.a_editTextTextPersonName2);
        char[] username=new char[30];
        if (String.valueOf(a_username.getText()).length()==0)
        {
            Toast.makeText(AdminLoginActivity.this,"Invalid username",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            username = String.valueOf(a_username.getText()).toCharArray();
        }

        EditText a_pass=findViewById(R.id.a_editTextTextPassword2);
        char[] pass=new char[15];
        if (String.valueOf(a_pass.getText()).length()==0)
        {
            Toast.makeText(AdminLoginActivity.this,"Invalid password",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            pass = String.valueOf(a_pass.getText()).toCharArray();
        }

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        EditText v_date=findViewById(R.id.v_editTextDate);
//        LocalDate edate=LocalDate.parse(String.valueOf(v_date.getText()),formatter);

        //ECP ecp=new ECP();


        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                String query = "Select * from [Admin] where username='"+String.valueOf(username)+"' and pass='"+String.valueOf(pass)+"'";
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()){
//                    SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
//                    SharedPreferences.Editor editor=sharedPreferences.edit();
//                    editor.putString("vCNIC",String.valueOf(cnic));
//                    editor.apply();
                    Intent i=new Intent(this,AdminMenuActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(AdminLoginActivity.this,"invalid login",Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

//    public void login(View view){
//        Intent i=new Intent(ECPLoginActivity.this,VoterSignUpActivity.class);
//        startActivity(i);
//    }
}