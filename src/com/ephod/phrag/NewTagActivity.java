package com.ephod.phrag;

import com.ephod.phrag.ColorPickerDialog.OnColorSelectedListener;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NewTagActivity extends Activity {

	TasksDatabaseHelper taskNigga = new TasksDatabaseHelper(this); 
	String mainColor = "#000000";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_tag);
		
		setActionBarFont();
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.action_accept:
				addTag();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}				
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_tag, menu);
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
				
		
		((TextView)v.findViewById(R.id.title)).setText("New Tag");
		actionBar.setCustomView(v);		
	}
	
	@Override
	public void onBackPressed(){
		showTagsPage();
		finish();
	}
	
	public void showColorDialog(View v){
		ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, Color.parseColor(mainColor), new OnColorSelectedListener() {

	        @Override
	        public void onColorSelected(int color) {
	            // do action
	        	String hexColor = String.format("#%06X", (0xFFFFFF & color));
	        	mainColor = hexColor;
	        	
	        	ImageButton tagButton = (ImageButton)findViewById(R.id.choosetagcolorbutton);
				tagButton.setImageResource(R.drawable.ic_action_eyedrop_set);
	        }

	    });
		
		colorPickerDialog.show();
	}
	
	public void showTagsPage(){
		Intent intent = new Intent(this, TagsActivity.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}
	
	public void addTag(){
		EditText taskContent = (EditText)findViewById(R.id.phragdetails);
		if (taskContent.getText().length() > 0 ){ //means that the user as added something
			String contents = taskContent.getText().toString();
			taskNigga.PutTagInTheDb(contents, mainColor);
			Toast.makeText(getApplicationContext(), "Tag created :)", Toast.LENGTH_SHORT).show();
			showTagsPage();
			finish();
		} else {
			Toast.makeText(getApplicationContext(), "You haven't created a new tag :(", Toast.LENGTH_SHORT).show();
		}
	}
	

}
