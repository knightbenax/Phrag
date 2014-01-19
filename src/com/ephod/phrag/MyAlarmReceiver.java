package com.ephod.phrag;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class MyAlarmReceiver extends BroadcastReceiver {

	int taskscount = 0;
	private int mId = 101856;
	TasksDatabaseHelper helper;
	private List<String> mItems = new ArrayList<String>();
	private List<String> mItemsId = new ArrayList<String>();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//check for boot complete event and start the service
		//Intent service = new Intent(context, NotificationService.class);
		//context.startService(service);
		helper = new TasksDatabaseHelper(context);
		taskscount = helper.getTaskCount(); //Get the number of tasks available
		helper.getAllTasks(mItems, mItemsId);
		showNotifications(context);
	}
	
	
	public void showNotifications(Context context){
		String content;
		
		if (taskscount > 1){
			content = String.valueOf(taskscount) + " tasks to phrag";
		} else {
			content = String.valueOf(taskscount) + " task to phrag";
		}
		
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.ic_notification)
		        .setContentTitle("Phrag")
		        //.setVibrate(new long[]{300, 300})
		        .setDefaults(Notification.DEFAULT_VIBRATE)
		        .setOnlyAlertOnce(true)
		        .setAutoCancel(true)
		        .setContentText(content);
		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle(mBuilder);
		
		//String[] tasks = new String[taskscount];
		inboxStyle.setBigContentTitle("Tasks To Phrag");
		inboxStyle.setSummaryText("Keep Calm And Phrag Stuff");
		//add tasks into the big view
		for (int i=0; i < taskscount - 1; i++){
			inboxStyle.addLine((CharSequence) mItems.get(i)); //add the individual tasks to the big view
		}
		
		mBuilder.setStyle(inboxStyle);
		
		//Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(context, HomeActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(HomeActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(mId, mBuilder.build());
	}
	
	
	

}
