package com.example.dailyselfie;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private AlarmManager mAlarmManager;
	private Intent mNotificationIntent;
	private PendingIntent mNotificationReceivePendingIntent;
	private static final long INITIAL_ALARM_DELAY = 2 * 1000L;

	private List<Photo> photoList = new ArrayList<Photo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// for list
		initPhoto();
		PhotoAdapter adapter = new PhotoAdapter(getApplicationContext(),
				R.layout.photo_item, photoList);
		ListView mListView = (ListView) findViewById(R.id.list_view);
		mListView.setAdapter(adapter);
		
		// for alarm
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mNotificationIntent = new Intent(this, PhotoReceiver.class);
		mNotificationReceivePendingIntent = PendingIntent.getBroadcast(this, 0,
				mNotificationIntent, 0);

	}

	// Test
	private void initPhoto() {
		Photo haha = new Photo("haha");
		photoList.add(haha);
		photoList.add(haha);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + INITIAL_ALARM_DELAY,
				INITIAL_ALARM_DELAY, mNotificationReceivePendingIntent);
		Log.i(TAG, "alarm repeat is set");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.photo_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.photo_button:
			Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File f = null;

			try {

			} catch (Exception e) {
				// TODO: handle exception
			}
			startActivityForResult(takePhotoIntent, 0);
			return true;

		default:
			return false;
		}
	}

}
