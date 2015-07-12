package com.weather.operation;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.weather.R;
import com.weather.activities.MainActivity;
import com.weather.aidl.WeatherData;
import com.weather.aidl.WeatherRequest;
import com.weather.aidl.WeatherResults;
import com.weather.lang.Chinese;
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
	private UniqueOps mUniqueOps;
	private LocationOps mLocationOps;


	public WeatherOpsImpl(MainActivity activity) {
		mActivity = new WeakReference<MainActivity>(activity);
		mImageOps = new ImageOps(mActivity.get());
		mServiceConnection = new GenericServiceConnection<>(
				WeatherRequest.class, activity);
		mService = new WeatherServiceAsync();
		mUniqueOps = new UniqueOps(activity);
		mLocationOps = new LocationOps(activity);
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
				String acronyName = null;
				if(name.contains("ÊÐ")) {
					
					String[] arrays  = name.split("ÊÐ");
					acronyName = arrays[0];
				} else {
					acronyName = name;
				}
				Log.d(TAG, "acrony name is " + acronyName);
				mWeatherRequest.getPM2_5(acronyName, mWeatherResults);
				mUniqueOps.SetName(UniqueOps.DISPLAY_NAME, name);
			} catch (RemoteException e) {
				Log.e(TAG, "RemoteException:" + e.getMessage());
			}
		} else {
			Log.d(TAG, "on update :mWeatherRequest was null");
		}
	}

	@Override
	public void onLocation() {
		final WeatherRequest mWeatherRequest = mServiceConnection
				.getInterface();
		
		if(null != mWeatherRequest) {
			try {
				Log.d(TAG, "weather request start to locate");
				//String location = "36.41709826,116.945041";
				String location = mLocationOps.onLocation();
				Log.d(TAG, "get location is " + location);
				mWeatherRequest.getLocation(location, mWeatherResults);
			} catch (RemoteException e) {
				Log.e(TAG, "RemoteException:" + e.getMessage());
			}
		} else {
			Log.d(TAG, "on loacate :mWeatherRequest was null");
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
					if(error.equals(Chinese.PM2_5_ERROR)) {
						mUniqueOps.SetValue(UniqueOps.AQI, 0);
					} else {
						Toast(error);
					}
				}
			});

		}

		@Override
		public void sendResult(List<WeatherData> results)
				throws RemoteException {
			mImageOps.updateData(results);
			if(1 == locateSign) {
				locateSign = 0;
			} else {
				Toast(Chinese.REFRESH_SUCCESS);
			}
		}

		private int locateSign = 0;
		@Override
		public void sendLocationName(String name) throws RemoteException {
			onUpdate(name);
			Toast(Chinese.LOCATE_SUCCESS);
			locateSign = 1;
		}

		@Override
		public void sendPM2_5(int value) throws RemoteException {
			Log.d(TAG, "PM2_5 is " + value);
			mUniqueOps.SetValue(UniqueOps.AQI, value);
			
		}
	};

	@Override
	public void onConfigurationChange(MainActivity activity) {
		// TODO Auto-generated method stub

	}

	
	public void Toast(final String message) {
		mDisHandler.post(new Runnable() {
			
			@Override
			public void run() {
				  Toast toast = new Toast(mActivity.get());

			        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 100);
			        toast.setDuration(Toast.LENGTH_SHORT);
			        TextView t =new TextView(mActivity.get());
			        t.setBackgroundColor(Color.parseColor("#3971AD"));
			        t.setTextColor(Color.parseColor("#ffffff"));
			        t.setTextSize(20);
			        t.setText(message);
			        toast.setView(t);
			        toast.show();
				
			}
		});
		
	}

}
