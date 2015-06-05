package com.example.operations;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.example.activities.DisplayWeatherActivity;
import com.example.activities.MainActivity;
import com.example.aidl.WeatherCall;
import com.example.aidl.WeatherData;
import com.example.aidl.WeatherRequest;
import com.example.aidl.WeatherResults;
import com.example.services.WeatherServiceAsync;
import com.example.services.WeatherServiceSync;
import com.example.utils.GenericServiceConnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * This class implements all the weather-related operations defined in the
 * 
 * @author Administrator
 *
 */
public class WeatherOpsImpl implements WeatherOps {

	/**
	 * Debugging tag used by the Android logger
	 */
	protected final String TAG = getClass().getSimpleName();

	/**
	 * Used to enable garbage collection.
	 */
	protected WeakReference<MainActivity> mActivity;

	/**
	 * This GenericServiceConnection is used to receive results after binding to
	 * the WeatherServiceSync Service using bindService().
	 */
	private GenericServiceConnection<WeatherCall> mServiceConnectionSync;

	/**
	 * This GenericServiceConnection is used to receive results after binding to
	 * the WeatherServiceAsync Service using bindService().
	 */
	private GenericServiceConnection<WeatherRequest> mServiceConnectionAsync;

	/**
	 * This Handler is used to post Runnable to the UI from the mWeatherResult
	 * callback method to avoid a dependency on the Activity, which may be
	 * destroyed in the UI Thread during a runtime configuration change.
	 */
	private final Handler mDisHandler = new Handler();

	/**
	 * The implementation of the WeatherResults AIDL Interface, which will be
	 * passed to the Weather web servcie using the
	 * WeatherRequest.getCurrentWeather() method.
	 * 
	 * This implementation of WeatherResults.Stub plays the role of Invoker in
	 * the Broker Pattern since it dispatches the upcall to SendResults().
	 */

	private final WeatherResults.Stub mWeatherResults = new WeatherResults.Stub() {

		/**
		 * This method is invoked by WeatherServiceAsync to return the results
		 * back to the MainActivity
		 */
		@Override
		public void sendResults(final List<WeatherData> results)
				throws RemoteException {
			// Since the Android Binder framework dispathces ths method
			// int a background Thread we need to explicitly post a runnable
			// contanning
			// the results to an intent,and change to DisplayWeatherActivity. We
			// use the
			// MDisplayHander to avoid a dependency on the Activity , which may
			// be destoryed
			// int the UI Thread during a runtime configuration change.
			mDisHandler.post(new Runnable() {

				@Override
				public void run() {
					Intent intent = new Intent(mActivity.get(),
							DisplayWeatherActivity.class);
					intent.putParcelableArrayListExtra(WEATHRE_DATA,
							(ArrayList<? extends Parcelable>) results);
					mActivity.get().startActivity(intent);
				}
			});
		}

		@Override
		public void sendErrors() throws RemoteException {
			Toast.makeText(mActivity.get(), "The city wasn't found!", Toast.LENGTH_SHORT).show();
			
		}
	};

	/**
	 * Constructor initializes the fields.
	 */
	public WeatherOpsImpl(MainActivity activity) {
		// Initialize the WeakReference.
		mActivity = new WeakReference<MainActivity>(activity);

		// Initialize the GenericServiceConnection objects.
		mServiceConnectionSync = new GenericServiceConnection<WeatherCall>(
				WeatherCall.class);
		mServiceConnectionAsync = new GenericServiceConnection<WeatherRequest>(
				WeatherRequest.class);
	}

	@Override
	public void onConfigurationChange(MainActivity activity) {
		// TODO Auto-generated method stub

	}

	/**
	 * Initiate the service binding protocol.
	 */
	@Override
	public void bindService() {
		// Launch the Weather BoundService if they aren't already
		// running via a call to bindService, which binds ths
		// activity to the WeatherService if they aren't already
		// bound.
		if (null == mServiceConnectionSync.getInterface())
			mActivity.get().getApplicationContext().bindService(
							WeatherServiceSync.makeIntent(mActivity.get()),
							mServiceConnectionSync, 
							Context.BIND_AUTO_CREATE);
		
		if (null == mServiceConnectionAsync.getInterface())
			mActivity.get().getApplicationContext().bindService(
							WeatherServiceAsync.makeIntent(mActivity.get()),
							mServiceConnectionAsync, 
							Context.BIND_AUTO_CREATE);

	}
	
	/**
	 * Initiate the service unbinding protocol.
	 */
	@Override
	public void unbindService() {
        if (mActivity.get().isChangingConfigurations()) 
            Log.d(TAG,
                  "just a configuration change - unbindService() not called");
        else {
            Log.d(TAG,
                  "calling unbindService()");

            // Unbind the Async Service if it is connected.
            if (mServiceConnectionAsync.getInterface() != null)
                mActivity.get().getApplicationContext().unbindService
                    (mServiceConnectionAsync);

            // Unbind the Sync Service if it is connected.
            if (mServiceConnectionSync.getInterface() != null)
                mActivity.get().getApplicationContext().unbindService
                    (mServiceConnectionSync);
        }
	}

	/**
	 * Initiate the synchronous weather service lookup when the user presses the
	 * "Get Weather Sync" button
	 */
	@Override
	public void expandWeatherSync(String cityName) {
		final WeatherCall weatherCall = 
				mServiceConnectionSync.getInterface();
		
		if(null != weatherCall) {
			new AsyncTask<String, Void, List<WeatherData>>() {

				private String cityName;
				@Override
				protected List<WeatherData> doInBackground(String... params) {
					// TODO Auto-generated method stub
					cityName = params[0];
					try {
						return weatherCall.getCurrentWeather(cityName);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					return null;
				}
				
				@Override
				protected void onPostExecute(List<WeatherData> result) {
					if(null != result && result.size() > 0) {
						Intent intent = new Intent(mActivity.get(), DisplayWeatherActivity.class);
						intent.putParcelableArrayListExtra(WEATHRE_DATA, (ArrayList<? extends Parcelable>) result);
						mActivity.get().startActivity(intent);
					} else {
						Toast.makeText(mActivity.get(), "The city wasn't found!", Toast.LENGTH_SHORT).show();
					}
				}
			}.execute(cityName);
		} else {
			Log.d(TAG, "mWeatherCall was null.");
		}

	}

	@Override
	public void expandWeatherAsync(String cityName) {
		final WeatherRequest mWeatherRequest =
				mServiceConnectionAsync.getInterface();
		
		if(null != mWeatherRequest) {
			try {
				mWeatherRequest.getCurrentWeather(cityName, mWeatherResults);
			} catch(RemoteException e){
                Log.e(TAG,
                        "RemoteException:" 
                        + e.getMessage());
			}
		} else {
			Log.d(TAG, "mWeatherRequest was null");
		}
	}
}
