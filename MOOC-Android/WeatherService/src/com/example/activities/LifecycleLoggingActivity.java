package com.example.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class LifecycleLoggingActivity extends Activity {
	/**
	 * Debuggin tag used by the Android logger.
	 */
	protected final String TAG = getClass().getSimpleName();
	protected final String WEATHER_OPS_STATE = "WEATHER_OPS_STATE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		if (null != savedInstanceState) {
			Log.d(TAG, "onCreate(): activity recreated");
		} else {
			Log.d(TAG, "onCreate(): activity create a new one");
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart() = the activity is about yo become visible");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG,
				"onResume() - the activity has become visible(it is now \"resumed\")");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG,
				"onPause() - another activity is taking focus (this activity is about to be \"paused\")");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG,
				"onStop() - the activity is no longer visible (it is now \"stopped\")");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart() - the activity is about to be restarted()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() - the activity is about to be destroyed");
	}
}
