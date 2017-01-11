package com.ephod.phrag;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {

	private final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";
	private final String EXTERNAL_AVALIABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equalsIgnoreCase(BOOT_ACTION) || intent.getAction().equalsIgnoreCase(EXTERNAL_AVALIABLE)){
			//check for boot complete event and start the alarm instead
			//Intent service = new Intent(context, NotificationService.class);
			//context.startService(service);
			setRepeatingAlarm(context);
		}
	}
	
	
	public void setRepeatingAlarm(Context context){
		Intent myIntent = new Intent(context, MyAlarmReceiver.class);
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 103, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.MINUTE, 30);
		
		long interval = 7200000;
		
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
	}

}
