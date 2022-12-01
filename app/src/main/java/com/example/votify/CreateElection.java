package com.example.votify;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CreateElection extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;

    private Connection connection = null;

    CalendarView cv;

    final String[] d = new String[1];
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final LocalDate[] dte = new LocalDate[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_election);

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

        cv=findViewById(R.id.cal);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                i1++;
                if(i2<10 && i1>=10)
                {
                    d[0] =i+"-"+i1 +"-0"+i2;
                }
                else if (i2>=10 && i1<10)
                {
                    d[0] =i+"-0"+i1 +"-"+i2;
                }
                else if (i2<10 && i1<10)
                {
                    d[0] =i+"-0"+i1 +"-0"+i2;
                }
                else {
                    d[0] = i + "-" + i1 + "-" + i2;
                }
                dte[0] =LocalDate.parse(d[0],formatter);
                Toast.makeText(CreateElection.this,d[0],Toast.LENGTH_SHORT).show();
            }});
    }

    public void SubmitElection(View v){

        String currdate=formatter.format(LocalDate.now());
        LocalDate present=LocalDate.parse(currdate,formatter);

        Period period = Period.between(present,dte[0]);
        int years=period.getYears();

        if (years <= 0)
        {
            if(period.getMonths()<=0)
            {
                if(period.getDays()<0)
                {
                    Toast.makeText(CreateElection.this,"Invalid Date Selected",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        if (connection!=null){
            Statement statement = null;
            Statement statement1 = null;
            try {
                statement = connection.createStatement();
                String query="Select * from Election where election='"+ dte[0] +"'";
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()){
                    Toast.makeText(CreateElection.this,"Election Already exist",Toast.LENGTH_SHORT).show();
                }
                else{
                    statement1 = connection.createStatement();
                    String query1="Insert into Election values " + "('"+ dte[0] +"','')";
                    statement1.executeQuery(query1);
                    try {
                        Toast.makeText(CreateElection.this,"Election created successfully",Toast.LENGTH_SHORT).show();
                    }
                    finally {

                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Intent i=new Intent(CreateElection.this,ECPMenuActivity.class);
        startActivity(i);
    }

}