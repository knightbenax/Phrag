package com.ephod.phrag;

import java.util.LinkedList;
import java.util.List;

import com.ephod.phrag.ColorPickerDialog.OnColorSelectedListener;
import com.ephod.phrag.WackColorPickerDialog.OnColorChangedListener;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class EditTagListAdapter extends BaseAdapter implements WackColorPickerDialog.OnColorChangedListener {

	Activity bleh;
	
	TasksDatabaseHelper taskNigga; 
	//TagsDatabaseHelper tagNigga; 
	
	public EditTagListAdapter(Activity bleh){
		this.bleh = bleh;
		taskNigga = new TasksDatabaseHelper(bleh);
		//tagNigga = new TagsDatabaseHelper(bleh);
	}
	
	public List<Tag> mTags = new LinkedList<Tag>();

    void resetItems() {
        mTags.clear();
        mTags = taskNigga.getAllTags();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mTags.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return mTags.get(position);
    }
    
    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
    * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    
    private EditTagListAdapter getColorChangedListener(){
		return this;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        //if(convertView == null) {
            convertView = bleh.getLayoutInflater().inflate(R.layout.edittaglistview_each_item, parent, false);

            final View origView = convertView;
    
            holder = new ViewHolder();
            assert convertView != null;
            holder.mEditText = (CustomEditText) convertView.findViewById(R.id.tagname);
            holder.mTagColor = (LinearLayout) convertView.findViewById(R.id.tagcolor);
            convertView.setTag(holder);
            //convertView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); //reset the height of the list iew item after it had gotten used
    	//} else {
            //holder = (ViewHolder) convertView.getTag();
        //}
            


        holder.position = position;
        final String previoustagname = mTags.get(position).name;
        holder.mEditText.setText(previoustagname);
        final String currentTagColor = mTags.get(position).color;
        holder.mTagColor.setBackgroundColor(Color.parseColor(currentTagColor));

        holder.mTagColor.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//WackColorPickerDialog newPicker = new WackColorPickerDialog(bleh, getColorChangedListener(), Color.parseColor(currentTagColor));
				//newPicker.show();
				ColorPickerDialog colorPickerDialog = new ColorPickerDialog(bleh, Color.parseColor(currentTagColor), new OnColorSelectedListener() {

			        @Override
			        public void onColorSelected(int color) {
			            // do action
			        	String hexColor = String.format("#%06X", (0xFFFFFF & color));
			        	taskNigga.updateSingleTag(mTags.get(position).id, mTags.get(position).name, hexColor);
			        	holder.mTagColor.setBackgroundColor(Color.parseColor(hexColor));
			        	//Log.d("ColorPicker", String.valueOf(hexColor));
			        }

			    });
				
				colorPickerDialog.show();
			}
        	
        });
        
		holder.mEditText.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId,	KeyEvent event) {
				boolean handled = false;
				if(actionId == EditorInfo.IME_ACTION_DONE){
					InputMethodManager inputManager = (InputMethodManager) bleh.getSystemService(bleh.INPUT_METHOD_SERVICE); 
					inputManager.hideSoftInputFromWindow(bleh.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); 
					holder.mEditText.clearFocus();
					String newtag =  holder.mEditText.getText().toString();
					taskNigga.updateTaskTags(previoustagname, newtag);
					taskNigga.updateSingleTag(mTags.get(position).id, newtag, mTags.get(position).color);
					handled = true;
								}
				return handled;
			}
			
		});

        
        
        return convertView;
    }

    private class ViewHolder {
    	CustomEditText mEditText;
    	LinearLayout mTagColor;
    	int position;
    }

	@Override
	public void colorChanged(int color) {
		// TODO Auto-generated method stub
		
	}
	
}
