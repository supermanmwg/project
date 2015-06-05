package com.example.activities;

import com.example.R;
import com.example.operations.WeatherOps;
import com.example.operations.WeatherOpsImpl;
import com.example.utils.RetainedFragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class MainActivity extends LifecycleLoggingActivity {

	/**
	 * Used to retain the WeatherOps state between runtime configuration
	 * changes.
	 */
	protected final RetainedFragmentManager mRetainedFragmentManager = new RetainedFragmentManager(
			this.getFragmentManager(), TAG);

	/**
	 * Provides weather-related operations
	 */
	private WeatherOps mWeatherOps;

	// City name entered by the user
	protected EditText mCityEditText;

	/**
	 * Used to retain the WeatherOps state between runtime configuration
	 * changes.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);

		mCityEditText = (EditText) findViewById(R.id.cityName);
		handleConfigurationChanges();
	}

	/**
	 * Handle hardware reconfigurations, such as rotating the display.
	 */
	protected void handleConfigurationChanges() {
		// If this method returns true then this is the first time
		// the Activity has been created.
		if (mRetainedFragmentManager.firstTimeIn()) {
			Log.d(TAG, "First time onCreate() call");

			// Create the WeathermOps object one time. The "true" parameter
			// instructs WeatherOps to use the WeatherService
			mWeatherOps = new WeatherOpsImpl(this);

			// Store the WeatherOps into RetainedFragmentManager
			mRetainedFragmentManager.put(WEATHER_OPS_STATE, mWeatherOps);

			// Initiate the service binding protocal (which may be a no-op,
			// denpending on which type of Weather Service is used).
			// ---TODO
			mWeatherOps.bindService();

		} else {
			Log.d(TAG, "Not the first time!");
			// The RetainedFragmentManager was previously initialized,
			// which means that a runtime configuration changes occured.
			mWeatherOps = mRetainedFragmentManager.get(WEATHER_OPS_STATE);

			// This check shouldn't be necessary under normal circumtances,
			// but it's better to lose state than to crash.
			if (null == mWeatherOps) {
				// Create the WeathermOps object one time. The "true" parameter
				// instructs WeatherOps to use the WeatherService
				mWeatherOps = new WeatherOpsImpl(this);

				// Store the WeatherOps into RetainedFragmentManager
				mRetainedFragmentManager.put(WEATHER_OPS_STATE, mWeatherOps);

				// Initiate the service binding protocal (which may be a no-op,
				// denpending on which type of Weather Service is used).
				// --TODO
				mWeatherOps.bindService();

			} else {
				// Inform it that the runtime configuration change has
				// completed.
				mWeatherOps.onConfigurationChange(this);
			}
		}
	}

	/**
	 * Initiate the synchronous weather service when the user presses the
	 * "Get Weather Sync" button
	 */
	public void expandWeatherSync(View v) {
		final String cityName = mCityEditText.getText().toString();
		Log.d(TAG, "Sync button pressed  " + cityName);
		mWeatherOps.expandWeatherSync(cityName);
	}

	/**
	 * Initiate the synchronous weather service when the user presses the
	 * "Get Weather Async" button
	 */
	public void expandWeatherAsync(View v) {
		final String cityName = mCityEditText.getText().toString();
		Log.d(TAG, "Async button pressed  " + cityName);
		mWeatherOps.expandWeatherAsync(cityName);
	}

	@Override
	protected void onDestroy() {
		// Unbind from the Service
		mWeatherOps.unbindService();

		// Always call super class for necessary operations when an Activity is
		// destroyed
		super.onDestroy();
	}

}
