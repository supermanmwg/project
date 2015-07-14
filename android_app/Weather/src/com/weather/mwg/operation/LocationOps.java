package com.weather.mwg.operation;

import java.lang.ref.WeakReference;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class LocationOps {
	private  final String TAG = getClass().getSimpleName();
	
	private static final long ONE_MIN = 1000 * 60;
	private static final long TWO_MIN = ONE_MIN * 2;
	private static final long FIVE_MIN = ONE_MIN * 5;
	private static final long MEASURE_TIME = 1000 * 30;
	private static final long POLLING_FREQ = 1000 * 10;
	private static final float MIN_ACCURACY = 25.0f;
	private static final float MIN_LAST_READ_ACCURACY = 500.0f;
	private static final float MIN_DISTANCE = 10.0f;
	
	private WeakReference<Activity> mActivity;
	
	// Reference to the LocationManager and LocationListener
	private LocationManager mLocationManager;
	
	// Current best location estimate
	private Location mBestReading;

	
	public LocationOps(Activity activity) {
		mActivity= new WeakReference<Activity>(activity);
	}

	public String  onLocation() {
		// Acquire reference to the LocationManager
		if (null == (mLocationManager = (LocationManager)mActivity.get().getSystemService(Context.LOCATION_SERVICE)))
				return null;
		// Get best last location measurement
		mBestReading = bestLastKnownLocation(MIN_LAST_READ_ACCURACY, FIVE_MIN);
		if(null != mBestReading) {
			return mBestReading.getLatitude() + "," + mBestReading.getLongitude();
		} else {
			Log.d(TAG, "best regarding is null");
			return null;
		}
		
	}
	
	private Location bestLastKnownLocation(float minAccuracy, long maxAge) {

		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;
		long bestAge = Long.MIN_VALUE;

		while(true) {
			List<String> matchingProviders = mLocationManager.getAllProviders();

			for (String provider : matchingProviders) {

				Location location = mLocationManager.getLastKnownLocation(provider);

				if (location != null) {

					float accuracy = location.getAccuracy();
					long time = location.getTime();

					if (accuracy < bestAccuracy) {

						bestResult = location;
						bestAccuracy = accuracy;
						bestAge = time;

					}
				}
			}

			return bestResult;
		}
	}

}
