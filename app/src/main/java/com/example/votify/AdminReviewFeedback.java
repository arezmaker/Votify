package com.example.votify;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.votify.Adapters.FeedbackListAdapter;
import com.example.votify.model.Feedback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminReviewFeedback extends AppCompatActivity {

    private static String ip = "10.0.2.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Votify";
    private static String username = "test";
    private static String password = "test1234";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

    private Connection connection = null;

    ListView fListView;

    FeedbackListAdapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_review_feedback);

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

        ArrayList<Feedback> f=new ArrayList<>();

        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from Feedback");
                while (resultSet.next())
                {
                    int id=resultSet.getInt( "id");
                    String sub=resultSet.getString("Subject");
                    String msg=resultSet.getString("Message");
                    f.add(new Feedback(id,sub,msg));
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

        fListView = (ListView) findViewById(R.id.listReviewFeedback);
        adap=new FeedbackListAdapter(this,0,f);
        fListView.setAdapter(adap);

//        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//        ImageButton IMG =fListView.findViewById(R.id.imageButton_remove);
//        IMG.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                adap.notifyDataSetChanged();
//            }
//        });

//        fListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                adap.notifyDataSetChanged();
//            }
//        });s


    }

//    public void onDustbin(View view){
//        adap.notifyDataSetChanged();
//        this.recreate();
//    }


//    public class FeedbackAdapter extends ArrayAdapter<Feedback> {
//
//        ArrayList<Feedback> objects;
//
//        public FeedbackAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Feedback> objects) {
//            super(context, resource, objects);
//            this.objects=objects;
//
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//            Feedback f = getItem(position);
//
//            if (convertView == null){
//                convertView = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.feedback_list_custom_cell, parent, false);
//            }
//            EditText sub = convertView.findViewById(R.id.FeedbackSubject);
//            sub.setText(f.getSubject());
//
//            EditText msg = convertView.findViewById(R.id.FeedbackPreview);
//            msg.setText(f.getMessage());
//
//            Button rem=(Button) convertView.findViewById(R.id.imageButton_remove);
//            rem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getContext(), objects.get(position).getSubject(),Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            return convertView;
//        }
//    }
}