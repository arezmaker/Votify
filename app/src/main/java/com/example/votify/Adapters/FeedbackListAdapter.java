package com.example.votify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.votify.R;
import com.example.votify.model.Feedback;

import java.util.List;

public class FeedbackListAdapter extends ArrayAdapter<Feedback> {

    public FeedbackListAdapter(@NonNull Context context, int resource,  @NonNull List<Feedback> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Feedback f = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.feedback_list_custom_cell, parent, false);
        }
        EditText sub = convertView.findViewById(R.id.FeedbackSubject);
        sub.setText(f.getSubject());

        EditText msg = convertView.findViewById(R.id.FeedbackPreview);
        msg.setText(f.getMessage());

        return convertView;
    }
}
