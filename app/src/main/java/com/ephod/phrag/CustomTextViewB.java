package com.ephod.phrag;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextViewB extends TextView {

	public CustomTextViewB(Context context) {
		
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	
	public CustomTextViewB(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public CustomTextViewB(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		if(!isInEditMode()){ //this is to prevent, the Viewer from trying to render the XML view
			Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OmnesSemibold.ttf");
			setTypeface(tf);
		}
	}
}
