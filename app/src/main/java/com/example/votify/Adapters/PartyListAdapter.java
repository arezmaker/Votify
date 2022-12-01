package com.example.votify.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.votify.R;
import com.example.votify.model.Party;

import java.util.List;

public class PartyListAdapter extends ArrayAdapter<Party> {

    public PartyListAdapter(@NonNull Context context, int resource, @NonNull List<Party> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Party p = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.party_list_custom_cell, parent, false);
        }
        EditText PName = convertView.findViewById(R.id.partyName);
        PName.setText(p.getName());

        EditText Chairname = convertView.findViewById(R.id.chairmanName);
        Chairname.setText(p.getChairman());

        ImageView imgsymbol = convertView.findViewById(R.id.imageView16);

        String sym=p.getSymbol();
        byte[] decodeString = Base64.decode(sym, Base64.DEFAULT);
        Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString,
                0, decodeString.length);
        imgsymbol.setImageBitmap(decodebitmap);



        return convertView;
    }
}