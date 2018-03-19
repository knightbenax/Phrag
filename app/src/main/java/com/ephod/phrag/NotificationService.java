package com.ephod.phrag;


import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

@SuppressLint("NewApi") public class NotificationService extends Service {

	/*Want this to show the notifications for the number of tasks we still have to Phrag*/
	
	private Handler mHandler = new Handler();
	private static final int ONE_HOUR =  60000; //3600000;
	PowerManager pm;
	PowerManager.WakeLock wakelock;
	int taskscount = 0;
	private int mId = 101856;
	TasksDatabaseHelper helper = new TasksDatabaseHelper(this);
	private List<Task> mItems = new LinkedList<Task>();
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startid){
		//Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
		//mHandler.postDelayed(periodRunner, ONE_HOUR);
		
		//run the showing of the task instead
		taskscount = helper.getTaskCount(); //Get the number of tasks available
		mItems = helper.getAllTasks();
		
		if (taskscount > 0){
			showNotifications(); //if we have at least one task. Show th is
		}
		return Service.START_STICKY;
	}
	
	
	@Override
	public void onCreate(){
		super.onCreate();
		//Toast.makeText(getApplicationContext(), "Service Created", Toast.LENGTH_SHORT).show();
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	}
	
	
	private Runnable periodRunner = new Runnable(){
		
		public void run(){
			try{
				//Toast.makeText(getApplicationContext(), "Service Ran", Toast.LENGTH_SHORT).show();
				wakelock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Phrag");
				wakelock.acquire();
				//show notification here
				taskscount = helper.getTaskCount(); //Get the number of tasks available
				mItems = helper.getAllTasks();
				
				if (taskscount > 0){
					showNotifications(); //if we have at least one task. Show this	
				}
				
				mHandler.postDelayed(periodRunner, ONE_HOUR);
			} finally {
				wakelock.release();
			}
		}
		
	};
	
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	
	@Override
	public void onStart(Intent intent, int startId){
		super.onCreate();
	}
	
	
	public void showNotifications(){
		String content;
		
		if (taskscount > 1){
			content = String.valueOf(taskscount) + " tasks to phrag";
		} else {
			content = String.valueOf(taskscount) + " task to phrag";
		}
		
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_notification)
		        .setContentTitle("Phrag")
		        //.setVibrate(new long[]{300, 300})
		        .setDefaults(Notification.DEFAULT_VIBRATE)
		        .setOnlyAlertOnce(true)
		        .setAutoCancel(true)
		        .setContentText(content);
		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle(mBuilder);
		
		//String[] tasks = new String[taskscount];
		inboxStyle.setBigContentTitle("All Tasks To Phrag");
		inboxStyle.setSummaryText("Keep Calm And Phrag Stuff");
		//add tasks into the big view
		for (int i=0; i < taskscount - 1; i++){
			inboxStyle.addLine((CharSequence) mItems.get(i)); //add the individual tasks to the big view
		}
		
		mBuilder.setStyle(inboxStyle);
		
		//Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, HomeActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(HomeActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(mId, mBuilder.build());
	}

}
