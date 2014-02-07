package com.fuzz.simpleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TextOnlyAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] ids;
    private final int rowResourceId;

    public TextOnlyAdapter(Context context, int textViewResourceId, String[] objects) {

        super(context, textViewResourceId, objects);

        this.context = context;
        this.ids = objects;
        this.rowResourceId = textViewResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(rowResourceId, parent, false);
        
        TextView textView = (TextView) rowView.findViewById(R.id.textView);
    	
    	// Use the textList ArrayList for text
    	textView.setText( ((MainActivity)context).textList.get(position) );
        
        return rowView;

    }

    
}