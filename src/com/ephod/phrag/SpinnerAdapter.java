package com.ephod.phrag;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SpinnerAdapter extends ArrayAdapter<String> {



	private final LayoutInflater mInflater;
	Activity parents;
	String[] items;
	
	ArrayAdapter<String> adapter;
	
	public SpinnerAdapter(Context context, int textViewResourceId, String[] objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);
		//adapter = new ArrayAdapter<String>(context, R.layout.singleitem);
		//adapter.setDropDownViewResource(R.layout.dropdownsingleitem);
		this.items = objects;
	}
	
	//Activity eventsFeed;
		
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return adapter.getItem(position);
	}

	/*@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}*/

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.singleitem, parent, false);
        }

        CustomTextView textView = (CustomTextView) convertView.findViewById(R.id.itemtitle);
        textView.setText( items[position]);

        return convertView;
    }

    // This is the function that should return the view for each item in spinner
    // This would normally be the same view as the passive state with optional 
    // customizations, e.g. for selected items
    /*@Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dropdownsingleitem, parent, false);
        }
        CustomEditText textView = (CustomEditText) convertView.findViewById(R.id.itemtitle);
        textView.setText(items[position]);

        // Do additional customization here

        return convertView;
    }*/

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	} 

}
