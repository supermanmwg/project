package com.example.clientstragety;

import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidl.WeatherData;
import com.example.aidl.WeatherRequest;
import com.example.aidl.WeatherResults;
import com.example.services.WeatherServiceAsync;
import com.example.utils.GenericServiceConnection;

public class WeatherClientAsync extends WeatherClient<WeatherRequest>{

	private final String TAG = getClass().getSimpleName();
	
	public WeatherClientAsync(WeatherContext weatherContext) {
		this.mWeatherContext = weatherContext;
		 mServiceConnection = new GenericServiceConnection<>(
					WeatherRequest.class);
		 mService = new WeatherServiceAsync();
	}
	
	/**
	 * This Handler is used to post Runnable to the UI from the mWeatherResult
	 * callback method to avoid a dependency on the Activity, which may be
	 * destroyed in the UI Thread during a runtime configuration change.
	 */
	private final Handler mDisHandler = new Handler();
	
	private final WeatherResults.Stub mWeatherResults = new WeatherResults.Stub() {

		/**
		 * This method is invoked by WeatherServiceAsync to return the results
		 * back to the MainActivity
		 */
		@Override
		public void sendResults(final WeatherData results)
				throws RemoteException {
			// Since the Android Binder framework dispathces ths method
			// int a background Thread we need to explicitly post a runnable
			// contanning the results to an intent,and change to DisplayWeatherActivity. 
			//We use the MDisplayHander to avoid a dependency on the Activity , which may
			// be destoryed int the UI Thread during a runtime configuration change.
			mDisHandler.post(new Runnable() {

				@Override
				public void run() {
					mWeatherContext.startDisplayActivity(results);
				}
			});
		}
		
		@Override
		public void sendErrors() throws RemoteException {
			mDisHandler.post(new Runnable() {

				@Override
				public void run() {
					mWeatherContext.toastError();
				}
			});

		}
	};

	
	@Override
	public void expandWeather(String cityName) {
		final WeatherRequest mWeatherRequest = mServiceConnection
				.getInterface();
		Log.d(TAG, "2 weather  client connection is " + mServiceConnection.getInterface().getClass());

		if (null != mWeatherRequest) {
			try {
				Log.d(TAG, "before haha");
				mWeatherRequest.getCurrentWeather(cityName, mWeatherResults);
				Log.d(TAG, "after haha");
			} catch (RemoteException e) {
				Log.e(TAG, "RemoteException:" + e.getMessage());
			}
		} else {
			Log.d(TAG, "mWeatherRequest was null");
		}
	}
	
	}


