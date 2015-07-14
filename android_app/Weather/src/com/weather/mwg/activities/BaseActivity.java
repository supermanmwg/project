package com.weather.mwg.activities;

import com.weather.mwg.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class BaseActivity extends FragmentActivity {

	/**
	 * Debugging tag used by the Android logger.
	 */
	public final String TAG = getClass().getSimpleName();

	/**
	 * For English user
	 */
	public static final int EN = 0;

	/**
	 * For Chinese user
	 */
	public static final int CN = 1;

	/**
	 * For data transfer
	 */
	public static final String DATA = "data";

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

	/**
	 * Get the System language
	 */
	public int getLanguage() {
		if (getResources().getString(R.string.app_name).equals("Éµ¹ÏÌìÆø"))
			return CN;
		else
			return EN;
	}

}
