package com.ephod.phrag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class EnhancedListAdapter extends BaseAdapter {

	Activity bleh;
	
	TasksDatabaseHelper taskNigga; 
	
	public EnhancedListAdapter(Activity bleh){
		this.bleh = bleh;
		taskNigga = new TasksDatabaseHelper(bleh);
	}
	
	private List<String> mItems = new ArrayList<String>();
	private List<String> mItemsId = new ArrayList<String>();

    void resetItems() {
        mItems.clear();
        mItemsId.clear();
        taskNigga.getAllTasks(mItems, mItemsId);
        if (mItems.isEmpty()){
        	//for(int i = 1; i <= 20; i++) {
                //mItems.add("Test item in the yard containing two rows of data or what not" + i);
                //mItemsId.add(String.valueOf(i));
            //}
        	Calendar today = Calendar.getInstance();
        	String DateAsString  = today.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + " " + today.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " +today.getDisplayName(Calendar.YEAR, Calendar.SHORT, Locale.US);
        	taskNigga.PutStuffInTheDb("FUCK Damisi. This is a test task. Phrag it by swiping the task left or right", DateAsString);
        	taskNigga.getAllTasks(mItems, mItemsId);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mItems.remove(position);//remove the physical view from the list
        String TaskId  = mItemsId.get(position);
        mItemsId.remove(position);
        
        taskNigga.deleteTask(TaskId);
        //delete the task by id here
        notifyDataSetChanged();
    }

    public void insert(int position, String item, String id) {
        mItems.add(position, item);
        mItemsId.add(position, id);
        Calendar today = Calendar.getInstance();
        String DateAsString  = today.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + " " + today.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " +today.getDisplayName(Calendar.YEAR, Calendar.SHORT, Locale.US);
        taskNigga.PutStuffInTheDb(item, DateAsString);// add the item back, the one that was deleted
        //add the item back to the database
        notifyDataSetChanged();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mItems.size();
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
        return mItems.get(position);
    }
    
    public String getId(int position) { //get the id of the Task that was deleted
        return mItemsId.get(position);
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
            convertView = bleh.getLayoutInflater().inflate(R.layout.listview_each_item, parent, false);
            // Clicking the delete icon, will read the position of the item stored in
            // the tag and delete it from the list. So we don't need to generate a new
            // onClickListener every time the content of this view changes.
            final View origView = convertView;
            /*convertView.findViewById(R.id.action_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListView.delete(((ViewHolder)origView.getTag()).position);
                }
            });*/

            holder = new ViewHolder();
            assert convertView != null;
            holder.mTextView = (CustomTextView) convertView.findViewById(R.id.tasksbody);

            convertView.setTag(holder);
            //convertView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); //reset the height of the list iew item after it had gotten used
    	//} else {
            //holder = (ViewHolder) convertView.getTag();
        //}

        holder.position = position;
        holder.mTextView.setText(mItems.get(position));

        return convertView;
    }

    private class ViewHolder {
    	CustomTextView mTextView;
        int position;
    }
	
}
