package com.weather.mwg.operation;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class LocationOps implements LocationListener{
	/**
	 * Debugging tag used by the Android logger.
	 */
	public final String TAG = getClass().getSimpleName();
	
	/**
	 * Time and length accuracy
	 */
	private static final long ONE_MIN = 1000 * 60;
	private static final long TWO_MIN = ONE_MIN * 2;
	private static final long FIVE_MIN = ONE_MIN * 5;
	private static final long MEASURE_TIME = 1000 * 30;
	private static final long POLLING_FREQ = 1000 * 10;
	private static final float MIN_ACCURACY = 1000.0f;
	private static final float MIN_LAST_READ_ACCURACY = 1000.0f;
	private static final float MIN_DISTANCE = 1000.0f;

	/**
	 * Used to enable garbage collection.
	 */
	private WeakReference<LocationResults> mResult;
	private WeakReference<Activity> mActivity;
	
	private LocationListener mLocationListener;

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
	public LocationOps(LocationResults mLocationResults,Activity activity) {
		mResult = new WeakReference<LocationResults>(mLocationResults);
		mActivity = new WeakReference<Activity>(activity);
		mLocationListener = this;
	}
	
	/**
	 * Get the city's location (longitude and latitude)
	 * @return
	 */
	public void onLocation() {
		// Acquire reference to the LocationManager
		if (null == (mLocationManager = (LocationManager) mActivity.get()
				.getSystemService(Context.LOCATION_SERVICE)))
			return ;
		
		detectGPS();
		
		LocationUpdate();
		
		bestLastKnownLocation(MIN_LAST_READ_ACCURACY, FIVE_MIN);
	}

	public void detectGPS() {
        // Getting GPS status
       boolean isGPSEnabled = mLocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
       
       // Getting network status
       boolean isNetworkEnabled = mLocationManager
               .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if(!isGPSEnabled && !isNetworkEnabled) {
        	Log.d(TAG, "Need to open the GPS");
        	showSettingsAlert();
        }
	}
	
	  /**
     * Function to show settings alert dialog.
     * On pressing the Settings button it will launch Settings Options.
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity.get());

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mActivity.get().startActivity(intent);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


	
	public void LocationUpdate() {
		// Determine whether initial reading is
		// "good enough". If not, register for
		// further location updates

		if (null == mBestReading
				|| mBestReading.getAccuracy() > MIN_LAST_READ_ACCURACY
				|| mBestReading.getTime() < System.currentTimeMillis()
						- TWO_MIN) {
			Log.d(TAG, "update the listener");

			// Register for network location updates
			if (null != mLocationManager
					.getProvider(LocationManager.NETWORK_PROVIDER)) {
				mLocationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, POLLING_FREQ,
						MIN_DISTANCE, this);
			}

			// Register for GPS location updates
			if (null != mLocationManager
					.getProvider(LocationManager.GPS_PROVIDER)) {
				mLocationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, POLLING_FREQ,
						MIN_DISTANCE, this);
			}
			
			// Schedule a runnable to unregister location listeners
			Executors.newScheduledThreadPool(1).schedule(new Runnable() {

				@Override
				public void run() {

					Log.i(TAG, "location updates cancelled");

					mLocationManager.removeUpdates(mLocationListener);

				}
			}, MEASURE_TIME, TimeUnit.MILLISECONDS);
		}
	}
	/**
	 * Select the best accurate location
	 * @param minAccuracy
	 * @param maxAge
	 * @return
	 */
	private void bestLastKnownLocation(float minAccuracy, long maxAge) {

		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;
		long bestAge = Long.MIN_VALUE;

	    Log.d("GPS Enabled", "GPS Enabled");
     
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

		// Return best reading or null
		if (bestAccuracy > minAccuracy
				|| (System.currentTimeMillis() - bestAge) > maxAge) {
			return;
		} else {
			Log.d(TAG, "Get location ," + bestResult.getLatitude() + "," + bestResult.getLongitude());
			mResult.get().getLocation(bestResult);
			mLocationManager.removeUpdates(this);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		
		// Determine whether new location is better than current best
		// estimate

		if (null == mBestReading
				|| location.getAccuracy() < mBestReading.getAccuracy()) {

			// Update best estimate
			mBestReading = location;

			if (mBestReading.getAccuracy() < MIN_ACCURACY) {
				Log.d(TAG, "Get location ," + mBestReading.getLatitude() + "," + mBestReading.getLongitude());
				mResult.get().getLocation(mBestReading);
				mLocationManager.removeUpdates(this);
			}

		}
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

}
