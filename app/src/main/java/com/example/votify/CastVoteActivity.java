package com.example.votify;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

public class CastVoteActivity extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;
    SimpleAdapter ad;
    ImageButton button;
    ListView listView;
    CandidateListAdapter adapter;
    Intent i;
    int castVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_castvote);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Candidate> c=new ArrayList<>();

        if (connection != null) {
            Statement statement = null;
            try {
                SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                statement = connection.createStatement();
                String query = "Select castVote from Voter where CNIC='" + sharedPreferences.getString("vCNIC","") + "'";
                ResultSet rs = statement.executeQuery(query);
                if(rs.next())
                {
                    castVote = rs.getInt("castVote");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(castVote == 1)
        {
            Toast.makeText(CastVoteActivity.this, "Vote has already been casted",Toast.LENGTH_SHORT).show();
            Intent i1 = new Intent(CastVoteActivity.this, MenuActivity.class);
            startActivity(i1);
        }

        if (connection != null) {
            Statement statement = null;
            try {
                SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                statement = connection.createStatement();
                String query = "Select * from Candidate c join Voter v on c.votingArea = v.votingArea join Party p on c.party=p.name where v.CNIC='" + sharedPreferences.getString("vCNIC","") + "' and v.castVote != 1";
                ResultSet rs = statement.executeQuery(query);

                while (rs.next()) {
                    String party = rs.getString("party");
                    String name = rs.getString("name");
                    String sym = rs.getString(27);
                    String CNIC=rs.getString("CNIC");
                    c.add((new Candidate(name, CNIC, "", null, null, party, 0, "", 0, sym,0,"")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        listView=(ListView) findViewById(R.id.listview);
        adapter=new CandidateListAdapter(CastVoteActivity.this,0,c);
        listView.setAdapter(adapter);
    }

    public class CandidateListAdapter extends ArrayAdapter<Candidate> {

        ArrayList<Candidate> objects;

        public CandidateListAdapter(@NonNull Context context, int resource,  @NonNull ArrayList<Candidate> objects) {
            super(context, resource, objects);
            this.objects=objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Candidate f = getItem(position);

            if (convertView == null){
                convertView = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.cast_vote_list_custom_cell, parent, false);
            }
            TextView sub = convertView.findViewById(R.id.partyName);
            sub.setText(f.getParty());

            TextView msg = convertView.findViewById(R.id.chairmanName);
            msg.setText(f.getName());

            ImageView imgsymbol = convertView.findViewById(R.id.sym);
            String sym=f.getSymbol();
            byte[] decodeString = Base64.decode(sym, Base64.DEFAULT);
            Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString,
                    0, decodeString.length);
            imgsymbol.setImageBitmap(decodebitmap);

            ImageButton rem= convertView.findViewById(R.id.imageButton2);
            rem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Vote has been casted",Toast.LENGTH_LONG).show();
                    String pty=objects.get(position).getParty();
                    if (connection!=null){
                        Statement statement = null;
                        Statement statement1 = null;
                        Statement statement2 = null;
                        try {
                            statement = connection.createStatement();
                            ResultSet resultSet1 = statement.executeQuery("Update Candidate set totalVote +=1 where CNIC="+objects.get(position).getCNIC()+"");

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            statement2 = connection.createStatement();
                            ResultSet resultSet1 = statement.executeQuery("Update Party set totalVote +=1 where name='"+pty+"'");

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            SharedPreferences sharedPreferences=getSharedPreferences("CNIC",MODE_PRIVATE);
                            statement1 = connection.createStatement();
                            ResultSet resultSet = statement1.executeQuery("Update Voter Set castVote=1 where CNIC='" + sharedPreferences.getString("vCNIC","") + "'");

                        }
                        catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            Intent i1 = new Intent(CastVoteActivity.this, MenuActivity.class);
                            startActivity(i1);
                        } finally {

                        }

                    }

                }
            });

            return convertView;
        }
    }
}