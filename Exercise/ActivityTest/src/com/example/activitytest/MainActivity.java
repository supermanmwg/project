package com.example.activitytest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "ActivityTest";
	private static final int SECONDACTIVITY = 1;
	private Button goToSecond = null;

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.main);

		goToSecond = (Button) findViewById(R.id.activity_2);
		goToSecond.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// hidden Intent should add <action> & <category> in
				// AndroidManifest.xml
				Intent intent = new Intent(
						"com.example.activitytest.SecondActivity2");
				startActivityForResult(intent, SECONDACTIVITY, null);
			}
		});
	}

	// when activity is back ,we can differ any different activity by
	// requestCode & resultCode and get Activity data by intent;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SECONDACTIVITY && resultCode == RESULT_OK) {
			int num;
			num = data.getIntExtra("haha", -1);
			if (num != -1) {
				Toast.makeText(getApplicationContext(), "Get num is " + num,
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Log.i(TAG, "req=" + requestCode + " res=" + resultCode);
		}
	};

	// on configure changed ,we can get newConfig and do some jobs
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// if(newConfig.orientation==ActivityInfo.SCREEN_ORIENTATION_USER)
		
		if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			Log.i(TAG, "on configure portrait");
			Toast.makeText(getApplicationContext(),
					"hello I am portrait! " + newConfig.orientation,
					Toast.LENGTH_SHORT).show();
		} else {
			Log.i(TAG, "on configure land " + newConfig.orientation);
			Toast.makeText(getApplicationContext(), "hello I am land!",
					Toast.LENGTH_SHORT).show();
		}
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "onRestart");
	}
}
