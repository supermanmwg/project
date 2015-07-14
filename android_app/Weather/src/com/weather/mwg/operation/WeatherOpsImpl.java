package com.weather.mwg.operation;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;

import android.R.array;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.nfc.cardemulation.OffHostApduService;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.weather.mwg.R;
import com.weather.mwg.aidl.WeatherRequest;
import com.weather.mwg.aidl.WeatherResults;
import com.weather.mwg.activities.MainActivity;
import com.weather.mwg.aidl.WeatherData;
import com.weather.mwg.retrofit.WeatherWebServiceProxy;
import com.weather.mwg.services.LifecycleLoggingService;
import com.weather.mwg.services.WeatherServiceAsync;
import com.weather.mwg.utils.GenericServiceConnection;


public class WeatherOpsImpl implements WeatherOps {

	private final String TAG = getClass().getSimpleName();

	private WeakReference<MainActivity> mActivity;
	private ImageOps mImageOps;
	private GenericServiceConnection<WeatherRequest> mServiceConnection;
	private LifecycleLoggingService mService;
	private UniqueOps mUniqueOps;
	private LocationOps mLocationOps;
	private LangOps mLangOps;


	public WeatherOpsImpl(MainActivity activity) {
		mActivity = new WeakReference<MainActivity>(activity);
		mImageOps = new ImageOps(mActivity.get());
		mServiceConnection = new GenericServiceConnection<>(
				WeatherRequest.class, activity);
		mService = new WeatherServiceAsync();
		mUniqueOps = new UniqueOps(activity);
		mLocationOps = new LocationOps(activity);
		if(MainActivity.CN == activity.getLanguage()) {
			mLangOps = new CnLangOpsImpl();
		} else {
			mLangOps = new EnLangOpsImpl();
		}
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
	public void onUpdate(String name,int tag) {

		final WeatherRequest mWeatherRequest = mServiceConnection
				.getInterface();

		if (null != mWeatherRequest) {
			try {
				Log.d(TAG, "before haha");
				Log.d(TAG, "city name is " + name);
				String acronyName = null;
				if(name.contains("ÊÐ")) {
					
					String[] arrays  = name.split("ÊÐ");
					acronyName = arrays[0];
					if(arrays.length >= 2)
						name = arrays[1].split(",")[0];
				} else {
					acronyName = name;
				}
				Log.d(TAG, "acrony name is " + acronyName);
				mUniqueOps.SetName(UniqueOps.PM25, acronyName);
				mWeatherRequest.getPM2_5(acronyName, mWeatherResults);
				String[] arrays = name.split("ÊÐ");
				for (int i = 0; i < arrays.length; i++) {
					Log.d(TAG, "arrays " + i + arrays[i]);
				}
				if(WeatherOps.ADD == tag || WeatherOps.LOCATE == tag)
					mUniqueOps.SetName(UniqueOps.DISPLAY_NAME, name);
				mWeatherRequest.getCurrentWeather(mUniqueOps.getName(UniqueOps.DISPLAY_NAME),
						WeatherWebServiceProxy.Celsius, 3, mLangOps.getWeatherLang() ,mWeatherResults);
				Log.d(TAG, "after haha");

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
				mWeatherRequest.getLocation(location,mWeatherResults);
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
		public void sendErrors(final int error) throws RemoteException {
			mDisHandler.post(new Runnable() {

				@Override
				public void run() {
					switch (error) {
					case WeatherServiceAsync.PM2_5_ERROR:
						mUniqueOps.SetValue(UniqueOps.AQI, 0);
						break;
					case WeatherServiceAsync.CITY_NOT_FOUND:
						Toast(mLangOps.getCityNotFound());
						break;
					case WeatherServiceAsync.NET_ERROR:
						Toast(mLangOps.getNetError());
						break;
					case WeatherServiceAsync.CITY_NOT_FOUND_NET_ERROR:
						Toast("  " + mLangOps.getCityNotFound() + "\n" + mLangOps.getOr() + mLangOps.getNetError());
						break;
					default:
						break;
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
				Toast(mLangOps.getRefreshSuccess());
			}
		}

		private int locateSign = 0;
		@Override
		public void sendLocationName(String name) throws RemoteException {
			onUpdate(name,WeatherOps.LOCATE);
			Toast(mLangOps.getLocateSuccess());
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
