package com.fuzz.simpleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * List only the images in the IMAGE tab.
 * 
 */
public class ImageOnlyAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] Ids;
    private final int rowResourceId;

    public ImageOnlyAdapter(Context context, int textViewResourceId, String[] objects) {

        super(context, textViewResourceId, objects);

        this.context = context;
        this.Ids = objects;
        this.rowResourceId = textViewResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(rowResourceId, parent, false);
        
    	ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
    	
		// Use the imageHM HashMap for images
    	imageView.setImageBitmap( ((MainActivity)context).imageHM.get(position) );

        return rowView;

    }

    
    
}