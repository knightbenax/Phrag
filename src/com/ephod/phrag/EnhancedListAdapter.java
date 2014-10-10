package com.ephod.phrag;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class EnhancedListAdapter extends BaseAdapter {

	Activity bleh;
	
	TasksDatabaseHelper taskNigga; 
	//TagsDatabaseHelper tagNigga; 
	
	public EnhancedListAdapter(Activity bleh){
		this.bleh = bleh;
		taskNigga = new TasksDatabaseHelper(bleh);
		//tagNigga = new TagsDatabaseHelper(bleh);
	}
	
	private List<Task> mItems = new LinkedList<Task>();
	private List<Tag> mTags = new LinkedList<Tag>();

    void resetItems() {
        mItems.clear();
        mTags.clear();
        mTags = taskNigga.getAllTags();
        
        if (mTags.isEmpty()){
        	taskNigga.PutTagInTheDb("Chore", "#5c1212");
        	taskNigga.PutTagInTheDb("Side Projects", "#0a5aad");
        }    
        
        mItems = taskNigga.getAllTasks();
        if (mItems.isEmpty()){
        	Calendar today = Calendar.getInstance();
        	String DateAsString  = today.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + ", " + String.valueOf(today.get(Calendar.DAY_OF_MONTH)) + " " + today.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " + String.valueOf(today.get(Calendar.YEAR)); 
        	taskNigga.PutStuffInTheDb("This is a test task. Swipe the task left to reveal more", DateAsString, "Chore", "1");
        }
        
        //AlertDialog.Builder alert = new AlertDialog.Builder(bleh);
	    //alert.setTitle(taskNigga.getTaskCount());
	    //alert.show();
        //Log.d("Mushy II", String.valueOf(taskNigga.getTaskCount()));
        mItems = taskNigga.getAllTasks();
        //notifyDataSetChanged();
    }

    public void remove(int position) {
    	String TaskId = mItems.get(position).id;
        mItems.remove(position);//remove the physical view from the list  
        notifyDataSetChanged();
        taskNigga.deleteTask(TaskId);
        //delete the task by id here
        
    }

    public void insert(int position, String item, String id, String date, String tag, String finished) {
    	Task task = new Task(id, item, date, tag, finished);
        mItems.add(position, task);
        taskNigga.PutStuffInTheDb(item, date, tag, finished);// add the item back, the one that was deleted
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
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
            /*final Animation animFadeinout = AnimationUtils.loadAnimation(bleh, R.anim.fadeout);
            
            final TransitionDrawable transition = (TransitionDrawable) convertView.findViewById(R.id.listviewoga).getBackground();
            final LinearLayout listviewsecondoga = (LinearLayout)convertView.findViewById(R.id.listviewsecondoga);
            
            convertView.findViewById(R.id.listviewoga).setOnLongClickListener(new OnLongClickListener(){

				@Override
				public boolean onLongClick(View arg0) {
					// TODO Auto-generated method stub
					
					listviewsecondoga.startAnimation(animFadeinout);
				
					//listviewsecondoga.setAlpha(0.0f);
			        transition.startTransition(300);
			        
					return false;
				}
            	
            });*/
            
            holder.mTextView = (CustomTextView) convertView.findViewById(R.id.tasksbody);
            holder.mTextDate = (CustomTextView) convertView.findViewById(R.id.tasksdate);
            holder.mTagColor = (LinearLayout) convertView.findViewById(R.id.tagcolor);
            holder.mArcView = (ProgressArcView) convertView.findViewById(R.id.taskpercentage);
            holder.deleteButton = (ImageButton)convertView.findViewById(R.id.deletebutton);
            holder.markButton = (ImageButton)convertView.findViewById(R.id.markbutton);

            final View Viewu = convertView;
            
            holder.deleteButton.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					HomeActivity newbleh = (HomeActivity)bleh;
					View childView = newbleh.mListView.getChildAt(position - newbleh.mListView.getFirstVisiblePosition());				
					newbleh.mListView.touchListener.myGenerateDismissAnimate(Viewu, true, true, position, EnhancedListAdapter.this, childView);
				}
            	
            });
            
            holder.markButton.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
										// TODO Auto-generated method stub
					float arcPercent = holder.mArcView.arcPercent;
					//while(arcPercent < 100){
						//holder.mArcView.arcPercent = holder.mArcView.arcPercent + 0.02f;
						//arcPercent = holder.mArcView.arcPercent;
						holder.mArcView.animated = true;
						holder.mArcView.invalidate();
					//}
					//for(float i = arcPercent; i < 100; i++){
						taskNigga.updateSingleTaskFinished(mItems.get(position).id, "0"); //update the finishing in the database
					//}
						//AlertDialog.Builder alert = new AlertDialog.Builder(bleh);
					    //alert.setTitle("Guy");
					    //alert.show();
				}
            	
            });
            
            convertView.setTag(holder);
            //convertView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); //reset the height of the list iew item after it had gotten used
    	//} else {
            //holder = (ViewHolder) convertView.getTag();
        //}

        holder.position = position;
        holder.mTextView.setText(mItems.get(position).content);
        holder.mTextDate.setText(mItems.get(position).date);
        if (mItems.get(position).finished.equals("0")){
        	holder.mArcView.finishedValue = "yes";
        	holder.markButton.setVisibility(View.GONE);
        }
        String currentTag = mItems.get(position).tag;
        String currentTagColor = "";
        currentTagColor = taskNigga.getSingleTagColor(currentTag);
        //Log.d("Mushy II", "Color" + taskNigga.getTagCount() + currentTag);
        holder.mTagColor.setBackgroundColor(Color.parseColor(currentTagColor));

        return convertView;
    }

    private class ViewHolder {
    	CustomTextView mTextView;
    	CustomTextView mTextDate;
    	ImageButton deleteButton;
    	ImageButton markButton;
    	LinearLayout mTagColor;
    	ProgressArcView mArcView;
    	int position;
    }
	
}
