package com.ephod.phrag;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewTaskActivity extends AppCompatActivity {

	TasksDatabaseHelper taskNigga = new TasksDatabaseHelper(this); 
	private String DateValue;
	private String TimeValue;
	private String TagValue;
	TagListAdapter tagListAdapter;
	ListView taglist;
	LinearLayout view;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_task);

		
		String[] ampm = new String[]{"AM", "PM"};
		
		Calendar today = Calendar.getInstance();
		TimeValue  = String.valueOf(today.get(Calendar.HOUR)) + ":" + String.valueOf(today.get(Calendar.MINUTE)) + " " + ampm[today.get(Calendar.AM_PM)];
		DateValue  = today.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + ", " + String.valueOf(today.get(Calendar.DAY_OF_MONTH)) + " " + today.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " + String.valueOf(today.get(Calendar.YEAR)); 
	    TagValue = "Chore";
	    
		tagListAdapter = new TagListAdapter(this);
		tagListAdapter.resetItems();

		createToolbar();
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.action_check:
				addTask();
				return true;	
			default:
				return super.onOptionsItemSelected(item);
		}				
	}
	
	
	
	public void setDateValue(String value){
		DateValue = value;
		ImageButton dateButton = (ImageButton)findViewById(R.id.choosedatebutton);
		dateButton.setImageResource(R.drawable.ic_action_date_set);
	}
	
	public void setTimeValue(String value){
		TimeValue = value;
		ImageButton timeButton = (ImageButton)findViewById(R.id.choosetimebutton);
		timeButton.setImageResource(R.drawable.ic_action_alarms_set);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_task, menu);
		return true;
	}
	
	public void showDateDialog(View v){
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void showTimeDialog(View v){
		DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	AlertDialog dialog = null;
	
	public void showTagDialog(View v){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		

		builder.setTitle("Choose Task Tag");
		//builder.setIcon(R.drawable.ic_action_alarms_icon);

		
		view = (LinearLayout)View.inflate(this, R.layout.taglistlayout, null);

		taglist = (ListView)view.findViewById(R.id.taglists);
		taglist.setAdapter(tagListAdapter);
		
		taglist.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				TagValue = tagListAdapter.mTags.get(position).name;
				ImageButton tagButton = (ImageButton)findViewById(R.id.choosetagbutton);
				tagButton.setImageResource(R.drawable.ic_action_labels_set);
				dialog.dismiss();
			}
			
		});
		/*Spinner spinner = (Spinner)view.findViewById(R.id.datespinner);
		Spinner timespinner = (Spinner)view.findViewById(R.id.timespinner);

		final SpinnerAdapter list = new SpinnerAdapter(this, R.layout.singleitem, categories);
		spinner.setAdapter(list);
		
		final SpinnerAdapter timelist = new SpinnerAdapter(this, R.layout.singleitem, timecategories);
		timespinner.setAdapter(timelist);
		
		spinner.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				//showDateDialog();
				return false;
			}
		});
		
		spinner.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				//showDateDialog();
				return false;
			}
		});
		
		
		timespinner.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				//showTimeDialog();
				return false;
			}
		});
		
		timespinner.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				//showTimeDialog();
				return false;
			}
		});*/

		
		builder.setView(view);
		/*builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});*/
		
		//builder.setMessage("Balls");
		dialog = builder.create();
		dialog.show();
	}

	Toolbar mToolbar;

	private void createToolbar() {
		Bundle bundle = getIntent().getExtras();

		mToolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(mToolbar);

		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(false);

		LayoutInflater inflator =(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.titleview, null);

		((TextView)v.findViewById(R.id.title)).setText("New Task");

        /*person = (CircularImageView) v.findViewById(R.id.person);

        person.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Drawer.isDrawerOpen(GravityCompat.END))
                            Drawer.closeDrawer(GravityCompat.START);
                        else
                            Drawer.openDrawer(GravityCompat.START);
                    }
                }
        );*/


		android.support.v7.app.ActionBar.LayoutParams layoutParams = new android.support.v7.app.ActionBar.LayoutParams(android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);

		getSupportActionBar().setCustomView(v, layoutParams);


	}

	
	@Override
	public void onBackPressed(){
		showHomePage();
		finish();
	}
	
	public void showHomePage(){
		Intent intent = new Intent(this, HomeActivity.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}
	
	public void addTask(){
		EditText taskContent = (EditText)findViewById(R.id.phragdetails);
		if (taskContent.getText().length() > 0 ){ //means that the user as added something
			String contents = taskContent.getText().toString();
			String finalDateValue = DateValue + " - " + TimeValue;
			taskNigga.PutStuffInTheDb(contents, finalDateValue, TagValue, "1");
			Toast.makeText(getApplicationContext(), "Task added to be phraged", Toast.LENGTH_SHORT).show();
			showHomePage();
			finish();
		} else {
			Toast.makeText(getApplicationContext(), "No task added to be phraged :(", Toast.LENGTH_SHORT).show();
		}
	}
	

}
