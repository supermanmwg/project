package com.example.operations;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.activities.MainActivity;
import com.example.clientstragety.WeatherClient;
import com.example.clientstragety.WeatherClientAsync;
import com.example.clientstragety.WeatherClientSync;
import com.example.clientstragety.WeatherContext;
import com.example.services.WeatherServiceSync;
import com.example.R;

import android.content.Context;
import android.util.Log;
import android.view.View;

/**
 * This class implements all the weather-related operations defined in the
 * 
 * @author Administrator
 *
 */
public class WeatherOpsImpl implements WeatherOps {

	/**
	 * Debugging tag used by the Android logger
	 */
	protected final String TAG = getClass().getSimpleName();

	/**
	 * Used to enable garbage collection.
	 */
	protected WeakReference<MainActivity> mActivity;

	/**
	 * 
	 */
	private WeatherContext mWeatherContext;

	/**
	 * 
	 */
	private HashMap<Integer, WeatherClient> serviceConnections = new HashMap<Integer, WeatherClient>();


	/**
	 * Constructor initializes the fields.
	 */
	public WeatherOpsImpl(MainActivity activity) {
		// Initialize the WeakReference.
		mActivity = new WeakReference<MainActivity>(activity);
		mWeatherContext = new WeatherContext(activity);

		serviceConnections.put(R.id.syncService, new WeatherClientSync(
				mWeatherContext));
		serviceConnections.put(R.id.asyncService, new WeatherClientAsync(
				mWeatherContext));

	}

	@Override
	public void onConfigurationChange(MainActivity activity) {
		// TODO Auto-generated method stub

	}

	/**
	 * Initiate the service binding protocol.
	 */
	@Override
	public void bindService() {
		// Launch the Weather BoundService if they aren't already
		// running via a call to bindService, which binds ths
		// activity to the WeatherService if they aren't already
		// bound.
		List<Integer> keySet = new ArrayList<Integer>(
				serviceConnections.keySet());
		for (Integer key : keySet) {
			WeatherClient mWeatherClient = serviceConnections.get(key);
			Log.d(TAG, "service connection key is " + key);
			if (null == mWeatherClient.mServiceConnection.getInterface()) {
				mActivity
						.get()
						.getApplicationContext()
						.bindService(
								mWeatherClient.mService.makeIntent(mActivity.get()),
								mWeatherClient.mServiceConnection,
								Context.BIND_AUTO_CREATE);
			}
		}
	}

	/**
	 * Initiate the service unbinding protocol.
	 */
	@Override
	public void unbindService() {
		if (mActivity.get().isChangingConfigurations())
			Log.d(TAG,
					"just a configuration change - unbindService() not called");
		else {
			Log.d(TAG, "calling unbindService()");
			
			List<Integer> keySet = new ArrayList<Integer>(
					serviceConnections.keySet());
			for (Integer key : keySet) {
				WeatherClient mWeatherClient = serviceConnections.get(key);
				if (null != mWeatherClient.mServiceConnection.getInterface()) {
					Log.d(TAG, "service connection key is " + key);
					mActivity
							.get()
							.getApplicationContext()
							.unbindService(mWeatherClient.mServiceConnection);
				}
			}
		}
	}

	@Override
	public void expandWeahter(String cityName, View v) {
		Integer id = v.getId();
		WeatherClient mClient = serviceConnections.get(id);
		Log.d(TAG, "weather client is " + mClient.getClass() );
		Log.d(TAG, "1 weather  client connection is " + mClient.mServiceConnection.getInterface().getClass());
		if(null != mClient)
			mClient.expandWeather(cityName);
	}


}
