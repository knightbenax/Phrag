package com.ephod.phrag;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomEditText extends EditText {

	public CustomEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	
	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		if(!isInEditMode()){ //this is to prevent, the Viewer from trying to render the XML view
			Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Omnes.ttf");
			setTypeface(tf);
		}
	}
	
}
