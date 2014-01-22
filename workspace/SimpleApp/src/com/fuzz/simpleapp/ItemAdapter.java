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

public class ItemAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] Ids;
    private final int rowResourceId;
    private int viewType;

    public ItemAdapter(Context context, int textViewResourceId, String[] objects, int viewType) {

        super(context, textViewResourceId, objects);

        this.context = context;
        this.Ids = objects;
        this.rowResourceId = textViewResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(rowResourceId, parent, false);
        
        if(viewType == SectionFragment.ALL_ITEMS) {
            rowView = allData(rowView, position);        	
        }
        else if(viewType == SectionFragment.TEXT_ONLY) {
            rowView = textData(rowView, position);        	
        }
        
        return rowView;

    }

    
    private View allData(View rowView, int position) {
        
    	ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        TextView textView = (TextView) rowView.findViewById(R.id.textView);

        int id = Integer.parseInt(Ids[position]);
        String imageFile = Model.GetbyId(id).IconFile;

        textView.setText(Model.GetbyId(id).Name);
        // get input stream
        InputStream ims = null;
        try {
            ims = context.getAssets().open(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load image as Drawable
        Drawable d = Drawable.createFromStream(ims, null);
        // set image to ImageView
        imageView.setImageDrawable(d);

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