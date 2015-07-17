package com.weather.mwg.operation.weatherOpsImpl;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.weather.mwg.aidl.WeatherRequest;
import com.weather.mwg.aidl.WeatherResults;
import com.weather.mwg.activities.MainActivity;
import com.weather.mwg.aidl.WeatherData;
import com.weather.mwg.operation.ImageOps;
import com.weather.mwg.operation.LangOps;
import com.weather.mwg.operation.LocationOps;
import com.weather.mwg.operation.LocationResults;
import com.weather.mwg.operation.UniqueOps;
import com.weather.mwg.operation.WeatherOps;
import com.weather.mwg.operation.langOpsImpl.CnLangOpsImpl;
import com.weather.mwg.operation.langOpsImpl.EnLangOpsImpl;
import com.weather.mwg.retrofit.WeatherWebServiceProxy;
import com.weather.mwg.services.WeatherServiceAsync;
import com.weather.mwg.utils.GenericServiceConnection;

public class WeatherOpsImpl implements WeatherOps,LocationResults {
	/**
     * Debugging tag used by the Android logger.
     */
	private final String TAG = getClass().getSimpleName();

	/**
     * Used to enable garbage collection.
     */
	private WeakReference<MainActivity> mActivity;
	
	/**
	 * Used to enable UI operation
	 */
	private ImageOps mImageOps;
	
    /**
     * This GenericServiceConnection is used to receive results after
     * binding to the WeatherServiceAsync Service using bindService().
     */
	private GenericServiceConnection<WeatherRequest> mServiceConnection;
	
	/**
	 * Used to set the simple shared data
	 */
	private UniqueOps mUniqueOps;
	
	/**
	 * Used to enble locating operation
	 */
	private LocationOps mLocationOps;

	/**
	 * Used to enable the language operation (EN or CN)
	 */
	private LangOps mLangOps;

	 /**
     * Constructor initializes the fields.
     */
	public WeatherOpsImpl(MainActivity activity) {
		mActivity = new WeakReference<MainActivity>(activity);
		mImageOps = new ImageOps(mActivity.get());
		mServiceConnection = new GenericServiceConnection<>(
				WeatherRequest.class, activity);
		mUniqueOps = new UniqueOps(activity);
		mLocationOps = new LocationOps(this,activity);
		if (MainActivity.CN == activity.getLanguage()) {
			mLangOps = new CnLangOpsImpl();
		} else {
			mLangOps = new EnLangOpsImpl();
		}
	}

	/**
	 * Initiate the service binding protocol.
	 */
	@Override
	public void bindService() {
        // Launch the Weather Bound Services if they aren't already
        // running via a call to bindService(), which binds this
        // activity to the WeatherAsyncService if they aren't already
        // bound.
		Log.d(TAG, "bind Service!");
		if (null == mServiceConnection.getInterface()) {
			mActivity
					.get()
					.getApplicationContext()
					.bindService(WeatherServiceAsync.makeIntent(mActivity.get()),
							mServiceConnection, Context.BIND_AUTO_CREATE);
		} else {
			Log.d(TAG, "ServiceConnection was not bound!");
		}
	}
	
	/**
	 * Initiate the service unbinding protocol.
	 */
	@Override
	public void unbindService() {
		Log.d(TAG, "unbind Service!");
		// Unbind the Weather Async Service if it is connected.
		if (null != mServiceConnection.getInterface()) {
			mActivity.get().getApplicationContext()
					.unbindService(mServiceConnection);
		}
	}

	/**
	 * Set the Tab indicator's alpha to 1 (opacity)
	 */
	public void changeTab(int position) {
		mImageOps.changeTab(position);
	}

	/**
	 * Tab click event
	 */
	public void clickTab(View v) {
		mImageOps.clickTab(v);

	}
	
	/**
	 * Locate the city .
	 */
	@Override
	public void onLocation() {
		mLocationOps.onLocation();
	}
	
	@Override
	public void getLocation(Location mLocation) {
		final WeatherRequest mWeatherRequest = mServiceConnection
				.getInterface();

		if (null != mWeatherRequest) {
			try {
				if(null == mLocation) {
					Toast(mLangOps.getGPSNotFound());
				} else {
					String lltude = mLocation.getLatitude() + "," +mLocation.getLongitude();
					Log.d(TAG, "location is " + lltude);
					mWeatherRequest.getLocation(lltude, mWeatherResults);
				}
			} catch (RemoteException e) {
				Log.e(TAG, "RemoteException:" + e.getMessage());
			}
		} else {
			Log.d(TAG, "on loacate :mWeatherRequest was not bound");
		}
		
	}

	/**
	 * Update the weather info
	 * @param string
	 * @param tag :
	 * 				(1) ADD : for altering city (need to modify the display city);  
	 * 				(2) LOCATE : for locating city (need to refresh the display city);  
	 * 				(3) UPDATE : for updating the present display city weather info. 
	 */
	@Override
	public void onUpdate(String name, int tag) {

		final WeatherRequest mWeatherRequest = mServiceConnection
				.getInterface();

		if (null != mWeatherRequest) {
			if(null == name) {
				Toast(mLangOps.getCityNotFound());
				return ;
			}
			try {
				//name format : (1)議議庈議議庈ㄛ(2)議議庈議議⑹			    
				String pm25City = null;
				if (name.contains("庈")) {
					String[] arrays = name.split("庈");
					pm25City = arrays[0];
					if (arrays.length >= 2)
						name = arrays[1].split(",")[0];
				} else {
					pm25City = name;
				}
				
				//Set the pm2.5 city name
				mUniqueOps.SetName(UniqueOps.PM25_CITY, pm25City);
				
				//get city's pm2.5 data if it has
			//	mWeatherRequest.getPM2_5(pm25City, mWeatherResults);
				
				//set the display city name
				if (WeatherOps.ADD == tag || WeatherOps.LOCATE == tag)
					mUniqueOps.SetName(UniqueOps.DISPLAY_NAME, name);
				
				//get The display city weather info
				mWeatherRequest.getCurrentWeather(
						mUniqueOps.getName(UniqueOps.DISPLAY_NAME),
						WeatherWebServiceProxy.Celsius, 3,
						mLangOps.getWeatherLang(), mWeatherResults);
				
				
			} catch (RemoteException e) {
				Log.e(TAG, "RemoteException:" + e.getMessage());
			}
		} else {
			Log.d(TAG, "on update :mWeatherRequest was null");
		}
	}

	/**
	 * Get simple share data 
	 * @param type
	 * @return
	 */
	@Override
	public String getName(String type) {
		return mUniqueOps.getName(type);
	}

	/**
	 * Set simple share data of the type
	 * @param type
	 * @param name
	 */
	@Override
	public void SetName(String type, String name) {
		mUniqueOps.SetName(type, name);
	}
	
    /**
     * This Handler is used to post Runnables to the UI from the
     * mWeatherResults callback methods to avoid a dependency on the
     * Activity, which may be destroyed in the UI Thread during a
     * runtime configuration change.
     */
	private final Handler mDisHandler = new Handler();
	
	/**
     * The implementation of the WeatherResults AIDL Interface, which
     * will be passed to the Weather Async service using the
     * WeatherRequest methods.
     */
	private final WeatherResults.Stub mWeatherResults = new WeatherResults.Stub() {

		/**
		 * This method is invoked by WeatherServiceAsync to return the errors
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
						Toast("  " + mLangOps.getCityNotFound() + "\n"
								+ mLangOps.getOr() + mLangOps.getNetError());
						break;
					default:
						break;
					}
				}
			});

		}
		
		/**
		 * This method is invoked by WeatherServiceAsync to return the results
		 * back to the MainActivity
		 */
		@Override
		public void sendResult(List<WeatherData> results)
				throws RemoteException {
			mImageOps.updateData(results);
			if (1 == locateSign) {
				locateSign = 0;
				Toast(mLangOps.getLocateSuccess());
			} else {
				Toast(mLangOps.getRefreshSuccess());
			}
		}

		/**
		 *  1: Use locating;  0: Just refresh
		 */
		private int locateSign = 0;
		
		/**
		 * This method is invoked by WeatherServiceAsync to return the location name
		 * back to the MainActivity
		 */
		@Override
		public void sendLocationName(String name) throws RemoteException {
			
			onUpdate(name, WeatherOps.LOCATE);
			
			locateSign = 1;
		}
		
		/**
		 * This method is invoked by WeatherServiceAsync to return the pm2.5 info
		 * back to the MainActivity
		 */
		@Override
		public void sendPM2_5(int value) throws RemoteException {
			mUniqueOps.SetValue(UniqueOps.AQI, value);
		}
	};

	/**
	 * Custom Toast
	 * @param message:
	 * 				Toast message
	 */
	public void Toast(final String message) {
		mDisHandler.post(new Runnable() {

			@Override
			public void run() {
				Toast toast = new Toast(mActivity.get());

				toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0,
						100);
				toast.setDuration(Toast.LENGTH_SHORT);
				TextView t = new TextView(mActivity.get());
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