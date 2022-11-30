package com.example.votify;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ViewResult extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    ArrayList<String> xAxisLabel;

//    ArrayList<Party> p;

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);

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

        barChart = findViewById(R.id.idBarChart);

        // calling method to get bar entries.
        getBarEntries();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Election Result");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);
//        XAxis xAxis = barChart.getXAxis();
//        barChart.getXAxis().setCenterAxisLabels(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
    }

    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();
//        p=new ArrayList<>();
        xAxisLabel = new ArrayList<>();

        if (connection!=null){
            Statement statement = null;
            float i=0;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from Party");

                while (resultSet.next())
                {
                    String name=resultSet.getString("name");
                    int tvote=resultSet.getInt("totalVote");
//                    p.add(new Party(name,"",0,tvote,""));
                    barEntriesArrayList.add(new BarEntry(i, tvote));
                    xAxisLabel.add(name);
                    i++;
                }
//                if (resultSet.next()){
//                    Intent i=new Intent(VoterLoginActivity.this,MenuActivity.class);
//                    startActivity(i);
//                }
//                else{
//                    Toast.makeText(VoterLoginActivity.this,"invalid login",Toast.LENGTH_SHORT).show();
//                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
//        barEntriesArrayList.add(new BarEntry(1f, 4));
//        barEntriesArrayList.add(new BarEntry(2f, 6));
//        barEntriesArrayList.add(new BarEntry(3f, 8));
//        barEntriesArrayList.add(new BarEntry(4f, 2));
//        barEntriesArrayList.add(new BarEntry(5f, 4));
//        barEntriesArrayList.add(new BarEntry(6f, 1));
    }
}