package com.weather.mwg.customview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

public class BaseFragment extends Fragment {

	/**
	 * Debugging tag used by the Android logger.
	 */
	public final String TAG = getClass().getSimpleName();

	/**
	 * Fragment position
	 */
	public static final String POS = "position";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate is on");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated is on");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.d(TAG, "onStart is on");
		super.onStart();
	}
}
