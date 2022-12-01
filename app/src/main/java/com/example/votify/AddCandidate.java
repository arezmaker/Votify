package com.example.votify;

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

import com.example.votify.model.Candidate;
import com.example.votify.model.VotingArea;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddCandidate extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_candidate);

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
    public void AddNewCandidate(View view) {
        EditText ed_party=findViewById(R.id.cs_Party);
        String party=String.valueOf(ed_party.getText());
        if (party.length()==0)
        {
            Toast.makeText(AddCandidate.this,"Invalid party",Toast.LENGTH_SHORT).show();
            return;
        }

        EditText ed_name=findViewById(R.id.cs_editTextTextPersonName3);
        String name=String.valueOf(ed_name.getText());
        if (name.length()==0)
        {
            Toast.makeText(AddCandidate.this,"Invalid name",Toast.LENGTH_SHORT).show();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String currdate=formatter.format(LocalDate.now());
        LocalDate present=LocalDate.parse(currdate,formatter);

        EditText ed_bdate=findViewById(R.id.cs_editTextDate3);
        LocalDate bdate;
        try {
            bdate = LocalDate.parse(String.valueOf(ed_bdate.getText()), formatter);
        } catch (Exception e) {
            Toast.makeText(AddCandidate.this,"Invalid Date Format",Toast.LENGTH_SHORT).show();
            return;
        }

        Period period = Period.between(bdate, present);
        int years=period.getYears();

        if (years < 18)
        {
            Toast.makeText(AddCandidate.this,"Age less than 18",Toast.LENGTH_SHORT).show();
            return;
        }

        //Date bdate=new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(ed_bdate.getText()));
        //Date bdate=new SimpleDateFormat("dd/MM/yyyy").parse("24/07/2000");

        EditText ed_edate=findViewById(R.id.cs_editTextDate2);
        LocalDate edate;
        try {
            edate=LocalDate.parse(String.valueOf(ed_edate.getText()),formatter);
        } catch (Exception e) {
            Toast.makeText(AddCandidate.this,"Invalid Date Format",Toast.LENGTH_SHORT).show();
            return;
        }

        //Date edate=new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(ed_edate.getText()));

        EditText ed_cnic=findViewById(R.id.cs_editTextNumberSigned5);
        String cnic=String.valueOf(ed_cnic.getText());
        if(cnic.length()==13)
        {
            if (Long.parseLong(cnic)>0000000000000)
            {

            }
            else
            {
                Toast.makeText(AddCandidate.this,"Invalid CNIC",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else
        {
            Toast.makeText(AddCandidate.this,"Invalid CNIC",Toast.LENGTH_SHORT).show();
            return;
        }

        EditText add=findViewById(R.id.adct_editTextTextPostalAddress);
        String address=String.valueOf(add.getText());
        if(address.length()==0)
        {
            Toast.makeText(AddCandidate.this,"Invalid address",Toast.LENGTH_SHORT).show();
            return;
        }

        LatLng coord;
        try {
            coord=getLocationFromAddress(this,address);
        } catch (Exception e) {
            Toast.makeText(AddCandidate.this,"Invalid Address",Toast.LENGTH_SHORT).show();
            return;
        }
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
            Toast.makeText(AddCandidate.this,"error", Toast.LENGTH_SHORT).show();
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

        Toast.makeText(AddCandidate.this,Stion, Toast.LENGTH_SHORT).show();

        //Button b_register=findViewById(R.id.vs_button17);

        Candidate candidate=new Candidate();
        candidate.setName(name);
        candidate.setBirthDate(bdate);
        candidate.setExpiryDate(edate);
        candidate.setCNIC(cnic);
        candidate.setAddress(address);
        candidate.setVotingArea(Stion);
        candidate.setParty(party);

        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                String query = "INSERT INTO Candidate(name,CNIC,expiryDate,birthDate,Address,votingArea,party,totalVote,typeofVote,castVote,Status) VALUES ('" + candidate.getName()  + "','"+ candidate.getCNIC()+ "','" + candidate.getExpiryDate()  + "','" + candidate.getBirthDate()  + "','"+ candidate.getAddress()+ "','"+candidate.getVotingArea()+"','"+candidate.getParty()+"',0,'national',0,'Active') " ;
                statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(AddCandidate.this,"error", Toast.LENGTH_SHORT).show();
        }


        Intent i=new Intent(AddCandidate.this, CandidateListActivity.class);
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