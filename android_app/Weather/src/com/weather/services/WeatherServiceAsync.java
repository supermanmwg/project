package com.weather.services;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import retrofit.RestAdapter;
import retrofit.http.Query;

import com.weather.aidl.WeatherData;
import com.weather.aidl.WeatherRequest;
import com.weather.aidl.WeatherResults;
import com.weather.retrofit.WeatherDataCurrent;
import com.weather.retrofit.WeatherDataForeCast;
import com.weather.retrofit.WeatherWebServiceProxy;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class WeatherServiceAsync extends LifecycleLoggingService{

	private WeatherWebServiceProxy mWeatherWebServiceProxy;
	private List<WeatherData> mWeatherList = new ArrayList<WeatherData>();
	@Override
	public void onCreate() {
		super.onCreate();
		 mWeatherWebServiceProxy =
	                new RestAdapter.Builder()
	                .setEndpoint(WeatherWebServiceProxy.sWeather_Service_URL_Retro)
	                .build()
	                .create(WeatherWebServiceProxy.class);
	}
	@Override
	public Intent makeIntent(Context context) {
		return new Intent(context, WeatherServiceAsync.class);
	}

    @Override
    public IBinder onBind(Intent intent) {
        return mWeatherRequestImpl;
    }
	
	private final WeatherRequest.Stub mWeatherRequestImpl = 
			new WeatherRequest.Stub() {

				@Override
				public void getCurrentWeather(String location,String metric, long cnt,
						WeatherResults results) throws RemoteException {
					WeatherDataCurrent mWeatherDataCurrent = mWeatherWebServiceProxy.getWeatherData(location, metric);
					WeatherDataForeCast mFoCastList = mWeatherWebServiceProxy.getWeatherData(location, metric, 5);
					
				}
	};

	public void onDestroy() {
		mWeatherWebServiceProxy = null;
	};
}
