package com.weather.services;
import java.util.List;
import retrofit.RestAdapter;
import com.weather.aidl.WeatherData;
import com.weather.aidl.WeatherRequest;
import com.weather.aidl.WeatherResults;
import com.weather.retrofit.WeatherDataCurrent;
import com.weather.retrofit.WeatherDataForeCast;
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
				public void getCurrentWeather(String location,String metric, long cnt,
						WeatherResults results) throws RemoteException {
					WeatherDataCurrent mWeatherDataCurrent = mWeatherWebServiceProxy.getWeatherData(location, metric);
					WeatherDataForeCast mForCastList = mWeatherWebServiceProxy.getWeatherData(location, metric, 5);
					List<WeatherData> mList;
					if(null != mWeatherDataCurrent && null != mForCastList) {
						mList = Utils.genList(mWeatherDataCurrent, mForCastList, cnt);
						Log.d(TAG, "current and forcast is ok");
						Log.d(TAG, "mlist is " + mList.size());
					} else {
						Log.d(TAG, "current or forcast is null");
						results.sendErrors();
						return;
					}
					for ( int i = 0; i < mList.size(); i++) {
						WeatherData mWeatherData = mList.get(i);
						Log.d(TAG, "name is " + mWeatherData.getmName());
						Log.d(TAG, "max temp is " + mWeatherData.getmTempMax());
						Log.d(TAG, "min temp is " + mWeatherData.getmTempMin());
						Log.d(TAG, "data is " + mWeatherData.getmDate());
						Log.d(TAG, "description is " + mWeatherData.getmDescription());
					}
					results.sendResult(mList);
				}
	};

	public void onDestroy() {
		mWeatherWebServiceProxy = null;
	};
}
