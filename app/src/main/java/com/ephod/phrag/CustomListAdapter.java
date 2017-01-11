package com.ephod.phrag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ephod.phrag.CustomTextView;

public class CustomListAdapter extends BaseAdapter {

	private Context mContext;
	String[] items;
	
	public CustomListAdapter(Context context, String[] items){
		//super();
		mContext = context;
		this.items =items;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		View contextView = view;//re-use an existing row, if one is supplied
		if (contextView == null){//otherwise create a new one
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			contextView = inflater.inflate(R.layout.listview_each_item, null);
		}
		CustomTextView container = (CustomTextView)contextView.findViewById(R.id.tasksbody);
		container.setText(items[position]);
		return view;
	}

	public void remove(int position) {
		// TODO Auto-generated method stub
		
	}

	public void insert(int position, String item) {
		// TODO Auto-generated method stub
		
	}


}
