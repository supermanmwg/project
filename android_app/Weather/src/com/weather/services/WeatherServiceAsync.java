package com.weather.services;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

import com.weather.aidl.WeatherData;
import com.weather.aidl.WeatherRequest;
import com.weather.aidl.WeatherResults;
import com.weather.lang.Chinese;
import com.weather.retrofit.WeatherDataCurrent;
import com.weather.retrofit.WeatherDataForeCast;
import com.weather.retrofit.WeatherDataForeCast.City;
import com.weather.retrofit.WeatherWebServiceProxy;
import com.weather.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class WeatherServiceAsync extends LifecycleLoggingService{
	
	private WeatherWebServiceProxy mWeatherWebServiceProxy;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "weather Service Async is onCreated");
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
				public void getCurrentWeather(String location,String metric, long cnt,String lang,
						WeatherResults results) throws RemoteException {
					WeatherDataCurrent mWeatherDataCurrent;
					WeatherDataForeCast mForCastList;
					try {
						mWeatherDataCurrent = mWeatherWebServiceProxy.getWeatherData(location, metric,lang);
						mForCastList = mWeatherWebServiceProxy.getWeatherData(location, metric, cnt,lang);
					} catch(RetrofitError e) {
						results.sendErrors("  " +Chinese.CITY_NOT_FOUND + "\n»ò" + Chinese.NET_ERROR);
						return ;
					}
					List<WeatherData> mList;
					if(null != mWeatherDataCurrent && null != mForCastList) {
						mList = Utils.genList(mWeatherDataCurrent, mForCastList, cnt);
						Log.v(TAG, "current and forcast is ok");
						Log.v(TAG, "mlist is " + mList.size());
					} else {
						results.sendErrors(Chinese.CITY_NOT_FOUND);
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
						results.sendErrors(Chinese.NET_ERROR);
					}
				}
	};

	public void onDestroy() {
		mWeatherWebServiceProxy = null;

	};
}
