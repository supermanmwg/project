package com.weather.mwg.services;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

import com.weather.mwg.aidl.WeatherRequest;
import com.weather.mwg.aidl.WeatherResults;
import com.weather.mwg.aidl.WeatherData;
import com.weather.mwg.retrofit.PM2_5Item;
import com.weather.mwg.retrofit.PM2_5ServiceProxy;
import com.weather.mwg.retrofit.WeatherDataCurrent;
import com.weather.mwg.retrofit.WeatherDataForeCast;
import com.weather.mwg.retrofit.WeatherWebServiceProxy;
import com.weather.mwg.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class WeatherServiceAsync extends LifecycleLoggingService{
	public static final int CITY_NOT_FOUND = 0;
	public static final int NET_ERROR = 1;
	public static final int CITY_NOT_FOUND_NET_ERROR = 2;
	public static final int PM2_5_ERROR = 3;
	
	private WeatherWebServiceProxy mWeatherWebServiceProxy;
	private PM2_5ServiceProxy mPM2_5ServiceProxy;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "weather Service Async is onCreated");
		 mWeatherWebServiceProxy =
	                new RestAdapter.Builder()
	                .setEndpoint(WeatherWebServiceProxy.sWeather_Service_URL_Retro)
	                .build()
	                .create(WeatherWebServiceProxy.class);
		 mPM2_5ServiceProxy = 
				 	new RestAdapter.Builder()
		 			.setEndpoint(PM2_5ServiceProxy.sPM2_5_Service_URL_Retro)
		 			.build()
		 			.create(PM2_5ServiceProxy.class);
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
				public void getCurrentWeather(String location,String metric, long cnt,String lang,
						WeatherResults results) throws RemoteException {
					WeatherDataCurrent mWeatherDataCurrent;
					WeatherDataForeCast mForCastList;
					try {
						mWeatherDataCurrent = mWeatherWebServiceProxy.getWeatherData(location, metric,lang);
						mForCastList = mWeatherWebServiceProxy.getWeatherData(location, metric, cnt,lang);
					} catch(RetrofitError e) {
						results.sendErrors(CITY_NOT_FOUND_NET_ERROR);
						return ;
					}
					List<WeatherData> mList;
					if(null != mWeatherDataCurrent && null != mForCastList) {
						mList = Utils.genList(mWeatherDataCurrent, mForCastList, cnt);
						Log.v(TAG, "current and forcast is ok");
						Log.v(TAG, "mlist is " + mList.size());
					} else {
						results.sendErrors(CITY_NOT_FOUND);
						return;
					}
					for ( int i = 0; i < mList.size(); i++) {
						WeatherData mWeatherData = mList.get(i);
						Log.v(TAG, "name is " + mWeatherData.getmName());
						Log.v(TAG, "max temp is " + mWeatherData.getmTempMax());
						Log.v(TAG, "min temp is " + mWeatherData.getmTempMin());
						Log.v(TAG, "data is " + mWeatherData.getmDate());
						Log.v(TAG, "description is " + mWeatherData.getmDescription());
					}
					results.sendResult(mList);
				}

				@Override
				public void getLocation(String location,
						WeatherResults results) throws RemoteException {
					Log.d(TAG, "Weather Serive start to locate");
	
					String cityName = Utils.getLocationName(location);
					if(null != cityName) {
						results.sendLocationName(cityName);
					} else {
						results.sendErrors(NET_ERROR);
					}
				}

				@Override
				public void getPM2_5(String cityName, WeatherResults results)
						throws RemoteException {
					try {
						Log.d(TAG, "PM2.5 city name is " + cityName);
						List<PM2_5Item> mPm2_5List = mPM2_5ServiceProxy.getPM2_5(cityName, "5j1znBVAsnSf5xQyNQyq");
						int average = 0;
						int sum = 0;
						int count = 0;
						for(int i = 0; i < mPm2_5List.size(); i++) {
							Log.d(TAG, "" + i + "PM2.5 si " + mPm2_5List.get(i).getaqi());
							sum += mPm2_5List.get(i).getaqi();
							if( mPm2_5List.get(i).getaqi() > 0) {
								count ++;
							}
						}
						if(count > 0) {
							average = sum /count;
						}
						results.sendPM2_5(average);
					} catch(RetrofitError e) {
						Log.d(TAG, e.getMessage());
						
						results.sendErrors(PM2_5_ERROR);
						return ;
					}
					
				}
	};

	public void onDestroy() {
		mWeatherWebServiceProxy = null;

	};
}
