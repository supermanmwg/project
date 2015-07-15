package com.weather.mwg.operation;

import java.lang.ref.WeakReference;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class LocationOps {
	/**
	 * Debugging tag used by the Android logger.
	 */
	public final String TAG = getClass().getSimpleName();

	/**
	 * Used to enable garbage collection.
	 */
	private WeakReference<Activity> mActivity;

	/**
	 * Reference to the LocationManager and LocationListener
	 */
	private LocationManager mLocationManager;

	/**
	 * Current best location estimate
	 */
	private Location mBestReading;

	/**
	 * Constructor
	 */
	public LocationOps(Activity activity) {
		mActivity = new WeakReference<Activity>(activity);
	}
	
	/**
	 * Get the city's location (longitude and latitude)
	 * @return
	 */
	public String onLocation() {
		// Acquire reference to the LocationManager
		if (null == (mLocationManager = (LocationManager) mActivity.get()
				.getSystemService(Context.LOCATION_SERVICE)))
			return null;
		
		// Get best last location measurement
		mBestReading = bestLastKnownLocation();
		if (null != mBestReading) {
			return mBestReading.getLatitude() + ","
					+ mBestReading.getLongitude();
		} else {
			Log.d(TAG, "best regarding is null");
			return null;
		}
	}

	/**
	 * Select the best accurate location
	 * @param minAccuracy
	 * @param maxAge
	 * @return
	 */
	private Location bestLastKnownLocation() {

		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;

		while (true) {
			List<String> matchingProviders = mLocationManager.getAllProviders();

			for (String provider : matchingProviders) {

				Location location = mLocationManager
						.getLastKnownLocation(provider);

				if (location != null) {

					float accuracy = location.getAccuracy();

					if (accuracy < bestAccuracy) {
						bestResult = location;
						bestAccuracy = accuracy;
					}
				}
			}

			return bestResult;
		}
	}
}
