package com.example.votify;

import static android.widget.Toast.LENGTH_SHORT;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import com.example.votify.model.VotingArea;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        String cnic=String.valueOf(ed_cnic.getText());

        EditText add=findViewById(R.id.advt_editTextTextPostalAddress);
        String address=String.valueOf(add.getText());

        LatLng coord=getLocationFromAddress(this,address);

        String Stion="";
        ArrayList<VotingArea> va=new ArrayList<>();

        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                String query = "Select * from VotingAreas" ;
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next())
                {
                    float lat=resultSet.getFloat("Lat");
                    float lon=resultSet.getFloat("Long");
                    String station=resultSet.getString("Station");
                    va.add(new VotingArea(lat,lon,station));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(VoterSignUpActivity.this,"error", LENGTH_SHORT).show();
        }

        double minDistance=Float.MAX_VALUE;
        for (int i=0;i<va.size();i++)
        {
            double dis=distance(coord.latitude,coord.longitude,va.get(i).getLat(),va.get(i).getLon());
            if (dis<minDistance)
            {
                minDistance=dis;
                Stion=va.get(i).getStation();
            }
        }

        Toast.makeText(VoterSignUpActivity.this,Stion, LENGTH_SHORT).show();

        //Button b_register=findViewById(R.id.vs_button17);

        Voter voter=new Voter();
        voter.setName(name);
        voter.setBirth_date(bdate);
        voter.setExpire_date(edate);
        voter.setCnic(cnic);
        voter.setAddress(address);
        voter.setVotingArea(Stion);

        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                String query = "INSERT INTO Voter(name,CNIC,expiryDate,birthDate,Address,votingArea,typeofVote,castVote,Status) VALUES ('" + voter.getName()  + "','"+ voter.getCnic()+ "','" + voter.getExpire_date()  + "','" + voter.getBirth_date()  + "','"+ voter.getAddress()+ "','"+voter.getVotingArea()+"','national',0,'Active') " ;
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

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}