package com.example.services;

import com.example.aidl.WeatherData;
import com.example.utils.DataTimeCache;
import com.example.utils.UtilsGUI;

import android.R.string;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.text.StaticLayout;
import android.util.Log;

/**
 * This abstract class extends the Service class and overrides life cycle
 * callbacks for logging various life cycle events.
 */
public abstract class LifecycleLoggingService extends Service {
	/**
	 * Debugging tag used by the Android logger.
	 */
	protected final String TAG = getClass().getSimpleName();
	protected final String ICON_URL = "http://openweathermap.org/img/w/";

	public static DataTimeCache<String, WeatherData> dataTimeCache =
			new DataTimeCache<String, WeatherData>(10 * 1000);

	private final Handler mServiceHandler = new Handler();
	/**
	 * Hook method called when the Service is created.
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		// Service is being created anew.
		Log.d(TAG, "onCreate() - service created a new one");
	}

	/**
	 * Hook method called to deliver an intent sent via startService().
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand() - intent received");
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * Factory method that's invoked when a client calls bindService().
	 */
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind() - client has invoked bindService()");

		return null;
	}

	/**
	 * Factory method that's invoked when a client calls bindService().
	 */
	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "onUnbind() - client has invoked unbindService()");
		return super.onUnbind(intent);
	}

	/**
	 * Hook method called when the last client unbinds from the Service.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() - service is being shut down");
	}
	
	public abstract  Intent makeIntent(Context context);
			
}
