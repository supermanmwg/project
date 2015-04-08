package com.example.healthalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	private static final long INITIAL_ALARM_DELAY = 10 * 1000;
	private AlarmManager mAlarmManager;
	private PendingIntent mPendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(this, SecondActivity.class);
		mPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mAlarmManager.set(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + INITIAL_ALARM_DELAY,
				mPendingIntent);
	}

}
