package com.example.clientstragety;

import java.lang.ref.WeakReference;

import com.example.activities.DisplayWeatherActivity;
import com.example.aidl.WeatherData;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class WeatherContext {

	// TAG for Debug
	private final String TAG = getClass().getSimpleName();

	// Intent transaction symbol
	public static final String WEATHRE_DATA = "Weather Data";

	// Activity object for UI operations
	private WeakReference<Activity> mActivity;

	public WeatherContext(Activity activity) {
		mActivity = new WeakReference<Activity>(activity);
	}

	public void toastError() {
		Toast.makeText(mActivity.get(), "The city wasn't found!",
				Toast.LENGTH_SHORT).show();
	}

	public void startDisplayActivity(WeatherData result) {
		if (null != result) {
			Intent intent = new Intent(mActivity.get(),
					DisplayWeatherActivity.class);
			intent.putExtra(WEATHRE_DATA, result);
			mActivity.get().startActivity(intent);
		} else {
			toastError();
		}

	}
}
