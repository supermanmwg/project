package com.weather.operation;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.weather.activities.MainActivity;
import com.weather.aidl.WeatherData;
import com.weather.aidl.WeatherRequest;
import com.weather.aidl.WeatherResults;
import com.weather.provider.cache.WeatherTimeoutCache;
import com.weather.retrofit.WeatherWebServiceProxy;
import com.weather.services.LifecycleLoggingService;
import com.weather.services.WeatherServiceAsync;
import com.weather.utils.GenericServiceConnection;

public class WeatherOpsImpl implements WeatherOps {

	private final String TAG = getClass().getSimpleName();

	private WeakReference<MainActivity> mActivity;
	private ImageOps mImageOps;
	private GenericServiceConnection<WeatherRequest> mServiceConnection;
	private LifecycleLoggingService mService;


	public WeatherOpsImpl(MainActivity activity) {
		mActivity = new WeakReference<MainActivity>(activity);
		mImageOps = new ImageOps(mActivity.get());
		mServiceConnection = new GenericServiceConnection<>(
				WeatherRequest.class);
		mService = new WeatherServiceAsync();
	}

	@Override
	public void bindService() {

		if (null == mServiceConnection.getInterface()) {
			mActivity
					.get()
					.getApplicationContext()
					.bindService(mService.makeIntent(mActivity.get()),
							mServiceConnection, Context.BIND_AUTO_CREATE);
			Log.d(TAG, "bind Service!");
		} else {
			Log.d(TAG, "mServiceConnection is not null!");
		}
	}

	@Override
	public void unbindService() {
		if (null != mServiceConnection.getInterface()) {
			mActivity.get().getApplicationContext()
					.unbindService(mServiceConnection);
			Log.d(TAG, "unbind Service!");
		}

	}

	public void changeTab(int position) {
		mImageOps.changeTab(position);
	}

	public void clickTab(View v) {
		mImageOps.clickTab(v);

	}

	@Override
	public void onLocation(String name) {

		final WeatherRequest mWeatherRequest = mServiceConnection
				.getInterface();

		if (null != mWeatherRequest) {
			try {
				Log.d(TAG, "before haha");
				mWeatherRequest.getCurrentWeather(name,
						WeatherWebServiceProxy.Celsius, 3,"zh_cn" ,mWeatherResults);
				Log.d(TAG, "after haha");
			} catch (RemoteException e) {
				Log.e(TAG, "RemoteException:" + e.getMessage());
			}
		} else {
			Log.d(TAG, "mWeatherRequest was null");
		}
	}

	private final Handler mDisHandler = new Handler();

	private final WeatherResults.Stub mWeatherResults = new WeatherResults.Stub() {

		/**
		 * This method is invoked by WeatherServiceAsync to return the results
		 * back to the MainActivity
		 */
		@Override
		public void sendErrors() throws RemoteException {
			mDisHandler.post(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(mActivity.get(), "the city is not found",
							Toast.LENGTH_SHORT).show();
				}
			});

		}

		@Override
		public void sendResult(List<WeatherData> results)
				throws RemoteException {
			mImageOps.updateData(results);

		}
	};

	@Override
	public void onConfigurationChange(MainActivity activity) {
		// TODO Auto-generated method stub

	}

}
