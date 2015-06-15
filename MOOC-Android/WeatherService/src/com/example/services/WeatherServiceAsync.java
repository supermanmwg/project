package com.example.services;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.activities.MainActivity;
import com.example.aidl.WeatherData;
import com.example.aidl.WeatherRequest;
import com.example.aidl.WeatherResults;
import com.example.utils.UtilsGUI;
import com.example.utils.UtilsNet;

public class WeatherServiceAsync extends LifecycleLoggingService {

	/**
	 * Factory method that makes an Intent used to start the WeatherServiceAsync
	 * when passed to bindService().
	 * 
	 * @param context
	 *            The context of the calling component.
	 */
	public Intent makeIntent(Context context) {
		return new Intent(context, WeatherServiceAsync.class);
	}
	
	 /**
     * Called when a client (e.g., MainActivity) calls
     * bindService() with the proper Intent.  Returns the
     * implementation of WeatherRequest, which is implicitly cast as
     * an IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mWeatherRequestImpl;
    }
	

	/**
	 * The concrete implementation of the AIDL Interface WeatherRequest, which
	 * extends the Stub class that implements WeatherRequest, thereby allowing
	 * Android to handle class across process boundaries. This method runs in a
	 * seperate Thread as part of the Android Binder framework.
	 * 
	 * This implementation plays the role of Invoker in Broker Pattern.
	 */
	private final WeatherRequest.Stub mWeatherRequestImpl = 
			new WeatherRequest.Stub() {
			/**
			 * Implement the AIDL WeatherRequest getCurrentWeather() method, which
			 * forwards to  UtilsNet getResults() to obtain the results from Weather 
			 * Web service and then sends the results back to the Activity via a
			 * callback.
			 */
				
				@Override
				public void getCurrentWeather(String cityName, WeatherResults results)
						throws RemoteException {
					// Call the Weather Web service to get the list of
					// possible expansions of the designated weather
					
					WeatherData weatherResults = 
							dataTimeCache.getData(cityName);
					if (null == weatherResults) {
						weatherResults = UtilsNet.getResults(cityName);
						if(null != weatherResults) {
							final String iconPath = getIconPath(ICON_URL + weatherResults.getmIconID() + ".png");
							weatherResults.setmIconID(iconPath);
							dataTimeCache.cacheData(cityName, weatherResults);
						}
						Log.d(TAG, "Data from the update Internet");
					} else {
						Log.d(TAG, "Data from the cache");
					}

					if(null != weatherResults) {
						Log.d(TAG, ""
								+ "results for weather: "
								+ cityName);
						//Invoke a one-way callback to send list of 
						// weather expansions back to the MainActivity.
						results.sendResults(weatherResults);
					} else {
						results.sendErrors();
					}
					
				}
			};
			protected String getIconPath(String url) {
				return UtilsGUI.downloadImage(this, url);
			}

}
