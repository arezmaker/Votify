package com.example.votify;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.votify.Adapters.PartyListAdapter;
import com.example.votify.model.Party;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ECPPartyList extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;


    ListView mListView;
    //String[] mListArray = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecpparty_list);

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

        ArrayList<Party> p=new ArrayList<>();

        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from Party");
                while (resultSet.next())
                {
                    String name=resultSet.getString("name");
                    String Chairman=resultSet.getString("Chairman");
                    String Symbol=resultSet.getString("symbol");
                    p.add(new Party(name,Symbol,0,0,Chairman));
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


        mListView = (ListView) findViewById(R.id.listParty);
        PartyListAdapter adap=new PartyListAdapter(this,0,p);
        mListView.setAdapter(adap);
    }

    public void goToAddParty(View view){
        Intent i=new Intent(ECPPartyList.this,AddParty.class);
        startActivity(i);
    }

}

