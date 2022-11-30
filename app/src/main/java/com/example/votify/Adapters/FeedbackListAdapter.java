package com.example.votify.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.votify.R;
import com.example.votify.model.Feedback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FeedbackListAdapter extends ArrayAdapter<Feedback> {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    ArrayList<Feedback> objects;

    public FeedbackListAdapter(@NonNull Context context, int resource,  @NonNull ArrayList<Feedback> objects) {
        super(context, resource, objects);
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ActivityCompat.requestPermissions((Activity) getContext(),new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

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

        Feedback f = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.feedback_list_custom_cell, parent, false);
        }
        EditText sub = convertView.findViewById(R.id.FeedbackSubject);
        sub.setText(f.getSubject());

        EditText msg = convertView.findViewById(R.id.FeedbackPreview);
        msg.setText(f.getMessage());

        ImageButton rem= convertView.findViewById(R.id.imageButton_remove);
        rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Deleted",Toast.LENGTH_LONG).show();
                if (connection!=null){
                    Statement statement = null;
                    try {
                        statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("delete from Feedback where id="+objects.get(position).getId()+"");

//                        ((Activity) getContext()).recreate();
//                        objects.remove(position);
//                        this.recreate();
//                        Toast.makeText(getContext(), "Deleted",Toast.LENGTH_SHORT).show();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
//                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
