package com.ephod.phrag;

import java.util.Calendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.TextView;
import com.ephod.phrag.EnhancedListView;
import com.ephod.phrag.EnhancedListView.SwipeDirection;

public class HomeActivity extends AppCompatActivity {
	
	//private EnhancedListAdapter mAdapter;
    //private EnhancedListView mListView;
    
    private EnhancedListAdapter mAdapter;
    public SwipeListView mListView;
    ColorDrawable topbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_home);
		
		mAdapter = new EnhancedListAdapter(this);
		mListView = (SwipeListView)findViewById(R.id.new_lv_list);
		
        mAdapter.resetItems();
        mListView.setAdapter(mAdapter);
        //mListView.
		//setActionBarFont();
		//setUpSwiping();
        setNewActionBarFont();

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
            //getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getTheme().resolveAttribute(android.R.attr.windowContentOverlay, new TypedValue(), false);

			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			// enable status bar tint
			tintManager.setStatusBarTintEnabled(true);
			topbar = new ColorDrawable(Color.parseColor("#197b30"));
			tintManager.setStatusBarTintDrawable(topbar);
        }


		//startService(new Intent(this, NotificationService.class));
		setRepeatingAlarm();
	}

    public void setNewActionBarFont(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.new_toolbar);
        toolbar.setTitle("Phrag");
        //toolbar.setElevation(4);
        //toolbar.set
        setSupportActionBar(toolbar);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	public void setRepeatingAlarm(){
		Intent myIntent = new Intent(this, MyAlarmReceiver.class);
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 103, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.HOUR, 2);
		
		long interval = 7200000;
		
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.action_about:
				showAboutPage();
				return true;
			case R.id.action_addtask:
				showAddTaskPage();
				finish();
				return true;
			case R.id.action_tags:
				showTags();
				finish();
			default:
				return super.onOptionsItemSelected(item);
		}				
	}
	

	public void showTags(){
		Intent intent = new Intent(this, TagsActivity.class);
		startActivity(intent);
	}
	
	public void showAboutPage(){
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
	
	public void showAddTaskPage(){
		Intent intent = new Intent(this, NewTaskActivity.class);
		startActivity(intent);
	}
	
	public void setActionBarFont(){
		//SpannableString s = new SpannableString("Phrag");		
		//s.setSpan(new TypefaceSpan("Omnes.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		ActionBar actionBar = getActionBar();
		//actionBar.setTitle(s);
		
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		
		LayoutInflater inflator =(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.titleview, null);
		
		
		((TextView)v.findViewById(R.id.title)).setText("Phrag");
		actionBar.setCustomView(v);
		
		
	}
	
	String[] items = {"If you enable Swipe To Dismiss functionality the user will be able to press down on a list item", "Before you enable Swipe To Dismiss you need to set up an" , "Before you enable Swipe To Dismiss you need to set up an"};
	
	
	
	/*public void setUpSwiping(){		
        
        mListView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
        	//play sound
        	
            /**
             * This method will be called when the user swiped a way or deleted it via
             * {@link de.timroes.android.listview.EnhancedListView#delete(int)}.
             *
             * @param listView The {@link EnhancedListView} the item has been deleted from.
             * @param position The position of the item to delete from your adapter.
             * @return An {@link de.timroes.android.listview.EnhancedListView.Undoable}, if you want
             *      to give the user the possibility to undo the deletion.
             *
        	
        	
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {

                final String item = (String) ((Task) mAdapter.getItem(position)).content; 
                final String id = (String) ((Task) mAdapter.getItem(position)).id;
                final String date = (String) ((Task) mAdapter.getItem(position)).date;
                final String tag = (String) ((Task) mAdapter.getItem(position)).tag;
                mAdapter.remove(position);
                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        mAdapter.insert(position, item, id, date, tag);
                    }
                    
                    @Override 
                    public String getTitle() {
    			        return "Task Phraged!";// + item.getFullName(); // Plz, use the resource system :)
    			    }
                };
            }
        });
        
        

        // Show toast message on click and long click on list items.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "You need to freaking do this task, so you can Phrag it. My friend, get to work!!", Toast.LENGTH_LONG).show();
            }
        });
        
        
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            	
            	
                //Toast.makeText(getApplicationContext(), "You need to freaking do this task, so you can Phrag it. My friend, get to work!!", Toast.LENGTH_LONG).show(); //Long clicked on item " + mAdapter.getItem(position)
                return true;
            }
        });
        
        mListView.setUndoHideDelay(3);
        mListView.enableSwipeToDismiss();
		mListView.setSwipeDirection(SwipeDirection.BOTH);
		//mListView.setSmoothScrollbarEnabled(true);
        
	}*/

}
