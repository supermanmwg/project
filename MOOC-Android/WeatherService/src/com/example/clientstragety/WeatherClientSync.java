package com.example.clientstragety;

import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidl.WeatherCall;
import com.example.aidl.WeatherData;
import com.example.services.WeatherServiceSync;
import com.example.utils.GenericServiceConnection;

public class WeatherClientSync extends WeatherClient<WeatherCall> {

	private final String TAG = getClass().getSimpleName();

	public WeatherClientSync(WeatherContext weatherContext) {
		mWeatherContext = weatherContext;
		mServiceConnection = new GenericServiceConnection<>(WeatherCall.class);
		mService = new WeatherServiceSync();
	}

	@Override
	public void expandWeather(String cityName) {

		final WeatherCall weatherCall = mServiceConnection.getInterface();

		if (null != weatherCall) {
			new AsyncTask<String, Void, WeatherData>() {

				private String cityName;

				@Override
				protected WeatherData doInBackground(String... params) {
					// TODO Auto-generated method stub
					cityName = params[0];
					try {
						return weatherCall.getCurrentWeather(cityName);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					return null;
				}

				@Override
				protected void onPostExecute(WeatherData result) {
					if (null != result) {
						mWeatherContext.startDisplayActivity(result);
					}
				}
			}.execute(cityName);
		} else {
			Log.d(TAG, "mWeatherCall was null.");
		}
	}

}
