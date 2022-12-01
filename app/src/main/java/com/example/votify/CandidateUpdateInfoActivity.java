package com.example.votify;

import static android.widget.Toast.LENGTH_SHORT;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.votify.model.VotingArea;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CandidateUpdateInfoActivity extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    EditText n;
    EditText c;
    EditText cA;
    EditText nA;

    Intent i;
    String name;
    String CNIC;
    String Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        i=getIntent();

        n=findViewById(R.id.upif_name);

        c=findViewById(R.id.upif_editTextNumberSigned2);

        cA=findViewById(R.id.upif_editTextTextPostalAddress);

        nA=findViewById(R.id.upif_editTextTextPostalAddress2);

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
                SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                statement = connection.createStatement();
                String query = "Select * from Candidate where CNIC ='" + sharedPreferences.getString("cCNIC","") + "'";
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()){
                    name=resultSet.getString("name");
                    n.setText(name);

                    CNIC=resultSet.getString("CNIC");
                    c.setText(CNIC);

                    Address=resultSet.getString("Address");
                    cA.setText(Address);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateInfo(View view)
    {
        String NewAdd=String.valueOf(nA.getText());
        if (NewAdd.length()==0)
        {
            Toast.makeText(CandidateUpdateInfoActivity.this,"Enter New Address",Toast.LENGTH_SHORT).show();
            return;
        }

        LatLng coord;
        try {
            coord=getLocationFromAddress(this,NewAdd);
        } catch (Exception e) {
            Toast.makeText(CandidateUpdateInfoActivity.this,"Invalid Address",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(CandidateUpdateInfoActivity.this,"error", LENGTH_SHORT).show();
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

        Toast.makeText(CandidateUpdateInfoActivity.this,Stion, LENGTH_SHORT).show();

        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                String query = "Update Candidate set Address='"+NewAdd+"',votingArea='"+Stion+"' where CNIC='"+CNIC+"'";
                Toast.makeText(CandidateUpdateInfoActivity.this,CNIC,Toast.LENGTH_SHORT).show();
                statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                Intent in=new Intent(CandidateUpdateInfoActivity.this,CandidateMenuActivity.class);
                SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("cCNIC",sharedPreferences.getString("cCNIC",""));
                editor.apply();
                //in.putExtra("CNIC",i.getStringExtra("CNIC1"));
                startActivity(in);
            }
            finally {

            }
        }
        else {
            Toast.makeText(CandidateUpdateInfoActivity.this,"error", LENGTH_SHORT).show();
        }

//        Toast.makeText(UpdateInfoActivity.this,String.valueOf(coord.latitude),Toast.LENGTH_SHORT).show();
//        Toast.makeText(UpdateInfoActivity.this,String.valueOf(coord.longitude),Toast.LENGTH_SHORT).show();
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