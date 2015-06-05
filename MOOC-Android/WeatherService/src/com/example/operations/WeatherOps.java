package com.example.operations;

import com.example.activities.MainActivity;

import android.app.Activity;
import android.view.View;

public interface WeatherOps {
	/**
	 * String for intent extra info.
	 */
	public static final String WEATHRE_DATA = "Weather Data";
	
	/**
	 * Initiate the service binding protocol.
	 */
	public void bindService();

	/**
	 * Initiate the service unbinding protocol.
	 */
	public void unbindService();

	/**
	 * Initiate the synchronous weather service lookup when the user presses the
	 * "Get Weather Sync" button
	 */
	public void expandWeatherSync(String cityName);

	/**
	 * Initiate the synchronous weather service lookup when the user presses the
	 * "Get Weather Async" button
	 */
	public void expandWeatherAsync(String cityName);

	/**
	 * Called after a runtime configuration change occurs to finish the
	 * initialization steps
	 */
	public void onConfigurationChange(MainActivity activity);
}
