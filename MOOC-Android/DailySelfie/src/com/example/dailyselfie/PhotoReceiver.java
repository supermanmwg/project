package com.example.dailyselfie;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class PhotoReceiver extends BroadcastReceiver {
	
	private static final int MY_NOTIFICATION_ID = 1;
	private static final String TAG = "PhotoReceiver";
	private Intent mNotificationIntent;
	private PendingIntent mContentIntent;
	private final CharSequence contentTitle = "Daily Selfie";
	private final CharSequence contentText = "Time for another selfie!";
	private final Uri soundURI = Uri
			.parse("android.resource://com.example.dailyselfie/"
					+ R.raw.alarm_rooster);

	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		mNotificationIntent = new Intent(context, MainActivity.class);
		mContentIntent = PendingIntent.getActivity(context, 0,
				mNotificationIntent, Intent.FLAG_ACTIVITY_CLEAR_TASK);
		
		Notification.Builder notificationBuilder = new Notification.Builder(
				context).setAutoCancel(true).setContentTitle(contentTitle)
				.setContentText(contentText).setSound(soundURI).setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(mContentIntent);
		
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// Pass the Notification to the NotificationManager:
				mNotificationManager.notify(MY_NOTIFICATION_ID,
						notificationBuilder.build());
		Log.i(TAG,"Sending Notification  is success");
	}
}
