package com.ephod.phrag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

class ProgressArcView extends View {
	 
	Paint p;
	Paint textp;
	RectF rectF;
	Typeface tf;
	public float arcAngle = 230;
	public float arcPercent = 71;
	public String finishedValue = "";
	public boolean animated = false;
	int framesPerSecond = 60;
	long animationDuration = 500;
	long startTime = System.currentTimeMillis();

	// CONSTRUCTOR
	public ProgressArcView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		p = new Paint();
		textp = new Paint();
		int value = this.getWidth();
		rectF = new RectF(10, 10, value, value);
		if(!isInEditMode()){
			tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Omnes.ttf");
		}
		
		setFocusable(true);
	}
	
	public ProgressArcView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    init();
	}

	public ProgressArcView(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	    init();
	}


	@SuppressLint("DrawAllocation") @Override
	protected void onDraw(Canvas canvas) {
		//canvas.drawColor(Color.CYAN);
		// smooths
		if (finishedValue == "yes"){
			
			int value = this.getWidth() - 5;
			rectF = new RectF(5, 5, value, value);
			textp.setStyle(Paint.Style.FILL);
			textp.setColor(Color.parseColor("#197b30"));
			textp.setAntiAlias(true);
			
			p.setAntiAlias(true);
			p.setStyle(Paint.Style.STROKE); 
			p.setStrokeWidth(0.7f);
			p.setAlpha(0x80);
			p.setColor(Color.parseColor("#000000"));
			canvas.drawArc(rectF, 0, 360, false, p);
			
			p.setStrokeWidth(8);
			p.setAlpha(0x255);
			p.setColor(Color.parseColor("#197b30"));
			
			arcAngle = 360;
			canvas.drawArc(rectF, 270, arcAngle, false, p);
			int x = 0, y = 0;
			
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.checktag);
			
			int xPos = (canvas.getWidth() / 2) - (bitmap.getWidth() / 2);
			int yPos = (int) ((canvas.getHeight() / 2) - (bitmap.getHeight() / 2)); 
						
			canvas.drawBitmap(bitmap, xPos, yPos, textp);
			
		} else {
			int value = this.getWidth() - 5;
			rectF = new RectF(5, 5, value, value);
			textp.setStyle(Paint.Style.FILL);
			textp.setColor(Color.parseColor("#000000"));
			textp.setTypeface(tf);
			textp.setTextSize(20);
			textp.setTextAlign(Align.CENTER);
			p.setAntiAlias(true);
			p.setStyle(Paint.Style.STROKE); 
			p.setStrokeWidth(0.7f);
			p.setAlpha(0x80);
			p.setColor(Color.parseColor("#000000"));
			canvas.drawArc(rectF, 0, 360, false, p);
			
			p.setStrokeWidth(8);
			p.setAlpha(0x255);
			p.setColor(Color.parseColor("#e85c65"));
			
			arcAngle = (arcPercent/100) * 360;
			canvas.drawArc(rectF, 270, arcAngle, false, p);
			int x = 0, y = 0;
			
			int xPos = (canvas.getWidth() / 2);
			int yPos = (int) ((canvas.getHeight() / 2) - ((textp.descent() + textp.ascent()) / 2)) ; 
			 //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.
			canvas.drawText(String.valueOf((int)arcPercent) + "%", xPos, yPos + 1, textp);
		} 
		
		if(animated && (finishedValue != "yes")){
			
			if(arcPercent < 100){
				
				long elaspedTime = System.currentTimeMillis() - startTime;
				
				int value = this.getWidth() - 5;
				rectF = new RectF(5, 5, value, value);
				textp.setStyle(Paint.Style.FILL);
				textp.setColor(Color.parseColor("#000000"));
				textp.setTypeface(tf);
				textp.setTextSize(20);
				textp.setTextAlign(Align.CENTER);
				p.setAntiAlias(true);
				p.setStyle(Paint.Style.STROKE); 
				p.setStrokeWidth(0.7f);
				p.setAlpha(0x80);
				p.setColor(Color.parseColor("#000000"));
				canvas.drawArc(rectF, 0, 360, false, p);
				
				p.setStrokeWidth(8);
				p.setAlpha(0x255);
				p.setColor(Color.parseColor("#e85c65"));
				
				
				arcPercent = arcPercent + 1;
				arcAngle = (arcPercent/100) * 360;
				canvas.drawArc(rectF, 270, arcAngle, false, p);
				int x = 0, y = 0;
				
				int xPos = (canvas.getWidth() / 2);
				int yPos = (int) ((canvas.getHeight() / 2) - ((textp.descent() + textp.ascent()) / 2)) ; 
				 //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.
				canvas.drawText(String.valueOf((int)arcPercent) + "%", xPos, yPos + 1, textp);
				
				if (elaspedTime < animationDuration){}
					this.postInvalidateDelayed(animationDuration/framesPerSecond);
				
			} else if (arcPercent == 100){
				
				//this.invalidate();
				
				//after the animation to hundred has occured. Now draw the finished circle and tick
				int value = this.getWidth() - 5;
				rectF = new RectF(5, 5, value, value);
				
				//we would draw over the current percent that is there, this time in white colorr
				textp.setStyle(Paint.Style.FILL);
				textp.setColor(Color.parseColor("#ffffff"));
				textp.setTypeface(tf);
				textp.setTextSize(20);
				textp.setTextAlign(Align.CENTER);
				
				int xPos = (canvas.getWidth() / 2);
				int yPos = (int) ((canvas.getHeight() / 2) - ((textp.descent() + textp.ascent()) / 2)) ; 
				 //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.
				//canvas.drawText(String.valueOf((int)arcPercent) + "%", xPos, yPos + 1, textp);
				canvas.drawARGB(255, 255, 255, 255);
				
				p.setAntiAlias(true);
				p.setStyle(Paint.Style.STROKE); 
				p.setStrokeWidth(0.7f);
				p.setAlpha(0x80);
				p.setColor(Color.parseColor("#000000"));
				canvas.drawArc(rectF, 0, 360, false, p);
				
				p.setStrokeWidth(8);
				p.setAlpha(0x255);
				p.setColor(Color.parseColor("#197b30"));
				
				arcAngle = 360;
				canvas.drawArc(rectF, 270, arcAngle, false, p);
				int x = 0, y = 0;
				
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.checktag);
				
				xPos = (canvas.getWidth() / 2) - (bitmap.getWidth() / 2);
				yPos = (int) ((canvas.getHeight() / 2) - (bitmap.getHeight() / 2)); 
				
				canvas.drawBitmap(bitmap, xPos, yPos, textp);
				
			}
		}
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}

}
