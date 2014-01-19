package com.ephod.phrag;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewTaskActivity extends Activity {

	TasksDatabaseHelper taskNigga = new TasksDatabaseHelper(this); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_task);
		setActionBarFont();
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
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_task, menu);
		return true;
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
				
		((TextView)v.findViewById(R.id.title)).setText("New Task");
		actionBar.setCustomView(v);		
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
		EditText taskContent = (EditText)findViewById(R.id.editText1);
		if (taskContent.getText().length() > 0 ){ //means that the user as added something
			Calendar today = Calendar.getInstance();
			String contents = taskContent.getText().toString();
			String DateAsString  = today.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US) + " " + today.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " " +today.getDisplayName(Calendar.YEAR, Calendar.SHORT, Locale.US);
			taskNigga.PutStuffInTheDb(contents, DateAsString);
			Toast.makeText(getApplicationContext(), "Task added to be phraged", Toast.LENGTH_SHORT).show();
			showHomePage();
			finish();
		} else {
			Toast.makeText(getApplicationContext(), "No task added to be phraged :(", Toast.LENGTH_SHORT).show();
		}
	}
	

}
