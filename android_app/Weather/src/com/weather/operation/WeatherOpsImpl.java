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
import com.weather.utils.Utils;

public class WeatherOpsImpl implements WeatherOps {

	private final String TAG = getClass().getSimpleName();

	private WeakReference<MainActivity> mActivity;
	private ImageOps mImageOps;
	private GenericServiceConnection<WeatherRequest> mServiceConnection;
	private LifecycleLoggingService mService;
	private UniqueOps mUniqueOps;


	public WeatherOpsImpl(MainActivity activity) {
		mActivity = new WeakReference<MainActivity>(activity);
		mImageOps = new ImageOps(mActivity.get());
		mServiceConnection = new GenericServiceConnection<>(
				WeatherRequest.class);
		mService = new WeatherServiceAsync();
		mUniqueOps = new UniqueOps(activity);
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
	public void onUpdate(String name) {

		final WeatherRequest mWeatherRequest = mServiceConnection
				.getInterface();

		if (null != mWeatherRequest) {
			try {
				Log.d(TAG, "before haha");
				Log.d(TAG, "city name is " + name);
				mWeatherRequest.getCurrentWeather(name,
						WeatherWebServiceProxy.Celsius, 3,"zh_cn" ,mWeatherResults);
				Log.d(TAG, "after haha");
				mUniqueOps.SetName(UniqueOps.DISPLAY_NAME, name);
			} catch (RemoteException e) {
				Log.e(TAG, "RemoteException:" + e.getMessage());
			}
		} else {
			Log.d(TAG, "mWeatherRequest was null");
		}
	}

	@Override
	public void onLocation() {
		final WeatherRequest mWeatherRequest = mServiceConnection
				.getInterface();
		
		if(null != mWeatherRequest) {
			try {
				Log.d(TAG, "weather request start to locate");
				String location = "36.41709826,116.945041";
				mWeatherRequest.getLocation(location, mWeatherResults);
			} catch (RemoteException e) {
				Log.e(TAG, "RemoteException:" + e.getMessage());
			}
		} else {
			Log.d(TAG, "mWeatherRequest was null");
		}
		
	}
	
	@Override
	public String getName(String type) {
		return mUniqueOps.getName(type);
	}

	@Override
	public void SetName(String type, String name) {
		mUniqueOps.SetName(type, name);
	}

	@Override
	public Set<String> getList(String type) {
		return mUniqueOps.getList(type);
	}
	
	@Override
	public void setListName(String type, Set<String> nameSet) {
		mUniqueOps.setListName(type, nameSet);
	}


	
	private final Handler mDisHandler = new Handler();

	private final WeatherResults.Stub mWeatherResults = new WeatherResults.Stub() {

		/**
		 * This method is invoked by WeatherServiceAsync to return the results
		 * back to the MainActivity
		 */
		@Override
		public void sendErrors(final String error) throws RemoteException {
			mDisHandler.post(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(mActivity.get(), error,
							Toast.LENGTH_SHORT).show();
				}
			});

		}

		@Override
		public void sendResult(List<WeatherData> results)
				throws RemoteException {
			mImageOps.updateData(results);

		}

		@Override
		public void sendLocationName(String name) throws RemoteException {
			onUpdate(name);
		}
	};

	@Override
	public void onConfigurationChange(MainActivity activity) {
		// TODO Auto-generated method stub

	}


}
