package com.fuzz.simpleapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.fuzz.simpleapp.MainActivity.AllDataIndex;

/**
 * List text and images in the TEXT/IMAGE tab
 *
 */
public class AllItemsAdapter extends ArrayAdapter<AllDataIndex> {

    private final Context context;
    private final ArrayList<AllDataIndex> ids;
    private final int rowResourceId;

    public AllItemsAdapter(Context context, int textViewResourceId, ArrayList<AllDataIndex> objects) {

        super(context, textViewResourceId, objects);

        this.context = context;
        this.ids = objects;
        this.rowResourceId = textViewResourceId;

        if(GlobalSettings.allItemsAdapter) Log.d("AllItemsAdapter", "constructor num objects: "+ objects.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(rowResourceId, parent, false);
        
        TextView textView;

        AllDataIndex allDataIndex = ids.get(position);
        
        if( allDataIndex.type.equals("text")) {
        	// If text then use the TextView item
        	// Use the textList ArrayList for text
            textView = (TextView) rowView.findViewById(R.id.textView);
            textView.setText(((MainActivity)context).textList.get(allDataIndex.i));
		}
    	else if( allDataIndex.type.equals("image")) {
    		// If image use the ImageView item
    		// Use the imageHM HashMap for images
    		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
    		imageView.setImageBitmap(((MainActivity)context).imageHM.get(allDataIndex.i));
    	}

        return rowView;
    }

    
}