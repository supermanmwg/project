package com.example.services;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.activities.MainActivity;
import com.example.aidl.WeatherCall;
import com.example.aidl.WeatherData;
import com.example.utils.UtilsGUI;
import com.example.utils.UtilsNet;

public class WeatherServiceSync extends LifecycleLoggingService{

	public static Intent makeIntent(Context context) {
		return new Intent(context, WeatherServiceSync.class);
	}
	
	/**
	 * Called when a client (e.g., MainActivity) calls bindService with the proper Intent. 
	 * Return the implementation of WeatherCall, which is implicity cast as an Ibinder.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return mWeatherCallImpl;
	}
	
	/**
	 * The concrete implementation of the AIDL Interface WeatherCall
	 * which extends the Stub class that implement WeatherCall, thereby
	 * allowing Android to handle calls across process
	 * boundaries. This method runs in a seperate Thread as part of
	 * the Android Binder framework.
	 * 
	 * This implementation plays the role of Invoker in the Broker Pattern.
	 */
	private final WeatherCall.Stub mWeatherCallImpl = 
			new WeatherCall.Stub() {
				
				@Override
				public List<WeatherData> getCurrentWeather(String cityName)
						throws RemoteException {
					// Call Weather Web service to get the list of
					// possible expansions of designated weather.
					final List<WeatherData> weatherResults = 
							UtilsNet.getResults(cityName);
					
					if(null != weatherResults) {
						Log.d(TAG, ""
								+ weatherResults.size()
								+ "results for weather: "
								+ cityName);
						
						//Return the list of weather expansions back to the
						//MainActivity.
						return weatherResults;
					} else {
						//Create a zero-sized weatherResults object to 
						//indicate to the caller that the weather had no
						//expansions.
						Log.d(TAG, "The City is not found!");
						return new ArrayList<WeatherData>();
					}
				}
			};
	
}
