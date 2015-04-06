package com.example.alarmtest;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private static final long INITIAL_ALARM_DELAY = 1000 * 3;
	private AlarmManager mAlarmManager;
	private PendingIntent mPendingIntent;
	private Button alarmButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(this, SecondActivity.class);
		mPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
	
		alarmButton = (Button) findViewById(R.id.alarm_button);
		alarmButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alarm_button:
			mAlarmManager.set(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis() + INITIAL_ALARM_DELAY,
					mPendingIntent);
		}
		
	}
}
