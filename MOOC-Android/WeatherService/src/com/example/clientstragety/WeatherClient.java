package com.example.clientstragety;

import com.example.services.LifecycleLoggingService;
import com.example.utils.GenericServiceConnection;

public abstract class WeatherClient<T extends android.os.IInterface> {
	public	GenericServiceConnection<T> mServiceConnection;
	public  LifecycleLoggingService mService;
	public  abstract void expandWeather(String cityName);
	protected WeatherContext mWeatherContext;

}
