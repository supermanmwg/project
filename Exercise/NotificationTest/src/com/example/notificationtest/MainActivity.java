package com.example.notificationtest;

import com.example.notificationtest.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends Activity implements OnClickListener {

	//Notification ID
	private static final int MY_NOTIFICATION_ID = 1;
	private Button notifyButton;
	private final CharSequence contentText = "You've Been Notified!";

	// Notification Action Elements
	private Intent mNotificationIntent;
	private PendingIntent mContentIntent;

	RemoteViews mContentView = new RemoteViews(
			"com.example.notificationtest", R.layout.custom_notifiy);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mNotificationIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		mContentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
				mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

		notifyButton = (Button) findViewById(R.id.notify_button);
		notifyButton.setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.notify_button:
			mContentView.setTextViewText(R.id.notify_text, contentText + "haha,lol..");
			Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext())
			.setTicker("haha,Ticker!")
			.setSmallIcon(android.R.drawable.stat_sys_warning)
			.setAutoCancel(true)
			.setContentIntent(mContentIntent)
			.setContent(mContentView);
			
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(MY_NOTIFICATION_ID,notificationBuilder.build());
			break;

		default:
			break;
		}
	}
}
