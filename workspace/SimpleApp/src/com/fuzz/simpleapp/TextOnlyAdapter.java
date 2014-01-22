package com.fuzz.simpleapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import com.fuzz.simpleapp.MainActivity.SectionFragment;

public class TextOnlyAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] Ids;
    private final int rowResourceId;

    public TextOnlyAdapter(Context context, int textViewResourceId, String[] objects) {

        super(context, textViewResourceId, objects);

        this.context = context;
        this.Ids = objects;
        this.rowResourceId = textViewResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        int id = Integer.parseInt(Ids[position]);
//        return Model.GetbyId(id).Name;
    	
    	
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(rowResourceId, parent, false);
        
        rowView = textData(rowView, position);        	
        
        return rowView;

    }


    private View textData(View rowView, int position) {
        
        // TextView textView = (TextView) rowView.findViewById(R.id.textView);
        TextView textView = new TextView(context);

        int id = Integer.parseInt(Ids[position]);

        textView.setText(Model.GetbyId(id).Name);

        return (View)textView;
    }

}