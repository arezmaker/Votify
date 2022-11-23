package com.example.votify;

import static android.widget.Toast.LENGTH_SHORT;

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

import com.example.votify.model.Voter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VoterSignUpActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_voter_sign_up);

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
    public void goToVoterLoginActivity(View view) {
        EditText ed_name=findViewById(R.id.vs_editTextTextPersonName3);
        String name=String.valueOf(ed_name.getText());
        //Toast.makeText(VoterSignUpActivity.this,name,Toast.LENGTH_SHORT).show();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        EditText ed_bdate=findViewById(R.id.vs_editTextDate3);
        LocalDate bdate=LocalDate.parse(String.valueOf(ed_bdate.getText()),formatter);
        //Date bdate=new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(ed_bdate.getText()));
        //Date bdate=new SimpleDateFormat("dd/MM/yyyy").parse("24/07/2000");

        EditText ed_edate=findViewById(R.id.vs_editTextDate2);
        LocalDate edate=LocalDate.parse(String.valueOf(ed_edate.getText()),formatter);

        //Date edate=new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(ed_edate.getText()));

        EditText ed_cnic=findViewById(R.id.vs_editTextNumberSigned5);
        char[] cnic=new char[15];
        cnic=String.valueOf(ed_cnic.getText()).toCharArray();

        //Button b_register=findViewById(R.id.vs_button17);

        Voter voter=new Voter();
        voter.setName(name);
        voter.setBirth_date(bdate);
        voter.setExpire_date(edate);
        voter.setCnic(cnic);

        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                String query = "INSERT INTO Voter(name,cnic,votingArea,expirydate,birthdate,typeOfVote) VALUES ('" + voter.getName()  + "','"+ String.valueOf(voter.getCnic())+ "','"+""+"','" + voter.getExpire_date()  + "','" + voter.getBirth_date()  + "','"+""+"') " ;
                statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(VoterSignUpActivity.this,"error", LENGTH_SHORT).show();
        }


        Intent i=new Intent(VoterSignUpActivity.this,VoterLoginActivity.class);
        startActivity(i);
    }
}