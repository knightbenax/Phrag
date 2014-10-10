package com.ephod.phrag;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class TagListAdapter extends BaseAdapter {

	Activity bleh;
	
	TasksDatabaseHelper taskNigga; 
	//TagsDatabaseHelper tagNigga; 
	
	public TagListAdapter(Activity bleh){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //if(convertView == null) {
            convertView = bleh.getLayoutInflater().inflate(R.layout.taglistview_each_item, parent, false);

            final View origView = convertView;
    
            holder = new ViewHolder();
            assert convertView != null;
            holder.mTextView = (CustomTextView) convertView.findViewById(R.id.tagname);
            holder.mTagColor = (LinearLayout) convertView.findViewById(R.id.tagcolor);
            convertView.setTag(holder);
            //convertView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); //reset the height of the list iew item after it had gotten used
    	//} else {
            //holder = (ViewHolder) convertView.getTag();
        //}

        holder.position = position;
        holder.mTextView.setText(mTags.get(position).name);
        String currentTagColor = mTags.get(position).color;
        holder.mTagColor.setBackgroundColor(Color.parseColor(currentTagColor));

        return convertView;
    }

    private class ViewHolder {
    	CustomTextView mTextView;
    	LinearLayout mTagColor;
    	int position;
    }
	
}
