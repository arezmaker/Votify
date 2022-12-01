package com.example.votify;

import static android.widget.Toast.LENGTH_SHORT;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddParty extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    private ImageView imageView;
    private EditText P_name;
    private EditText C_name;

    Bitmap selectedImageBitmap = null;
    ByteArrayOutputStream byteArrayOutputStream=null;
    byte[] bytesImage=null;
    String encodedImage=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_party);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
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

        imageView = (ImageView) findViewById(R.id.imageView17);


    }

    public void selectSymGallery(View view) {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }



    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();

                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                            bytesImage = byteArrayOutputStream.toByteArray();
                            encodedImage = Base64.encodeToString(bytesImage, Base64.DEFAULT);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageView.setImageBitmap(selectedImageBitmap);
                    }
                }
            });

    public void goToAddPartyCandidates(View view){
        P_name=(EditText) findViewById(R.id.textView2);
        String Pname=String.valueOf(P_name.getText());
        if (Pname.length()==0)
        {
            Toast.makeText(AddParty.this,"Invalid name",Toast.LENGTH_SHORT).show();
            return;
        }

        C_name=(EditText) findViewById(R.id.textView3);
        String Cname=String.valueOf(C_name.getText());
        if (Cname.length()==0)
        {
            Toast.makeText(AddParty.this,"Invalid name",Toast.LENGTH_SHORT).show();
            return;
        }

        //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        //byte[] bytesImage = byteArrayOutputStream.toByteArray();

        Toast.makeText(AddParty.this,encodedImage, LENGTH_SHORT).show();


        int tcandid=0;
        int tvote=0;

        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                String query = "INSERT INTO Party(name,noOfCandidates,totalVote,Chairman,symbol) VALUES ('" + Pname  + "','"+ tcandid + "','" + tvote  + "','" + Cname  + "','"+encodedImage+"') " ;
                statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(AddParty.this,"error", LENGTH_SHORT).show();
        }

        Intent i=new Intent(AddParty.this,ECPPartyList.class);
        startActivity(i);
    }
}