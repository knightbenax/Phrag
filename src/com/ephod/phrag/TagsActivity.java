package com.ephod.phrag;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class TagsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tags);
		setActionBarFont();
		
		ListView listView = (ListView)findViewById(R.id.edittagslistview);
		EditTagListAdapter mAdapter = new EditTagListAdapter(this);
		mAdapter.resetItems();
		listView.setAdapter(mAdapter);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.action_addtag:
				//showAboutPage();
				showAddNewTag();
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}				
	}
	
	public void showAddNewTag(){
		Intent intent = new Intent(this, NewTagActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tags, menu);
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
				
		((TextView)v.findViewById(R.id.title)).setText("Task Tags");
		actionBar.setCustomView(v);		
	}
		
	@Override
	public void onBackPressed(){
		showHomePage();
		finish();
	}
	
	public void showHomePage(){
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

}
