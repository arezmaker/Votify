package com.example.votify;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.votify.model.Candidate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CandidateListActivity extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    ListView ListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

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

        ArrayList<Candidate> Av=new ArrayList<>();

        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from Candidate");
                while (resultSet.next())
                {
                    String name=resultSet.getString("name");
                    String cnic=resultSet.getString("CNIC");
                    Av.add(new Candidate(name,cnic,null,null,null,resultSet.getString("party"),0,null,0,null,0,null));
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

        ListView = (ListView) findViewById(R.id.listCandidate);
        CandidateListAdapter adap=new CandidateListAdapter(this,0,Av);
        ListView.setAdapter(adap);
    }

    public void goToAddCandidate(View view)
    {
        Intent i=new Intent(CandidateListActivity.this,AddCandidate.class);
        startActivity(i);
    }




    public class CandidateListAdapter extends ArrayAdapter<Candidate> {

        public CandidateListAdapter(@NonNull Context context, int resource, @NonNull List<Candidate> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Candidate c = getItem(position);

            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.candidate_list_custom_cell, parent, false);
            }
            EditText VName = convertView.findViewById(R.id.cl_name);
            VName.setText(c.getName());

            EditText cnic = convertView.findViewById(R.id.cl_editTextNumberSigned5);
            cnic.setText(c.getCNIC());

            EditText pty=convertView.findViewById(R.id.cl_Party);
            pty.setText(c.getParty());

            return convertView;
        }
    }
}