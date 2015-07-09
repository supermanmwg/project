package com.weather.customview;

import java.lang.ref.WeakReference;

import com.weather.R;
import com.weather.aidl.WeatherData;
import com.weather.operation.WeatherOps;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherFragment  extends Fragment{

	public final String TAG = getClass().getSimpleName();
	private WeakReference<Activity> mActivity;
	
	private WeatherData mWeatherData;
	private View v;
	
	//for fragment ui
	private TextView cityNameTView;
	private TextView tempTextView;
	private TextView desTextView;
	private TextView datTextView;
	private TextView windTextView;
	private TextView humTextView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = new WeakReference<Activity>(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v =  inflater.inflate(R.layout.fragment,container, false);
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(null != getArguments()) {
			mWeatherData = getArguments().getParcelable(WeatherOps.WEATHRE_DATA);
			if(mWeatherData != null) {

			}
		} 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if(mWeatherData != null) {
			Log.d(TAG, "getArgument is not null");
			Log.d(TAG, "description is " + mWeatherData.getmDescription());
		/*		Log.d(TAG, "name is " + mWeatherData.getmName());
			Log.d(TAG, "max temp is " + mWeatherData.getmTempMax());
			Log.d(TAG, "min temp is " + mWeatherData.getmTempMin());
			Log.d(TAG, "data is " + mWeatherData.getmDate());
			Log.d(TAG, "description is " + mWeatherData.getmDescription());*/
			updateDisplayFragment(mWeatherData);	
		} else {
			Log.d(TAG, "getArguments is null");
		}
	}

	public void updateDisplayFragment(WeatherData mData) {
		initFragmentUI();
		setFragmentData(mData);
	}
	
	private void setFragmentData(WeatherData mData) {
		cityNameTView.setText(mData.getmName());
		tempTextView.setText("" + (long)mData.getmTempMin() + "бу/" + (long)mData.getmTempMax() + "бу");
		Log.d(TAG, "1 description is " + mWeatherData.getmDescription());
		Log.d(TAG, "2 description is " + mData.getmDescription());
		desTextView.setText(mData.getmDescription());
		datTextView.setText("" + mData.getmDate());
		windTextView.setText("" + mData.getmSpeed()); 
		humTextView.setText("" + mData.getmHumidity() + "%");
		
	}

	private void initFragmentUI() {
		cityNameTView = (TextView)mActivity.get().findViewById(R.id.city_name);
		tempTextView = (TextView)mActivity.get().findViewById(R.id.temperature);
		desTextView = (TextView)mActivity.get().findViewById(R.id.description);
		datTextView = (TextView)mActivity.get().findViewById(R.id.date);
		windTextView = (TextView)mActivity.get().findViewById(R.id.wind);
		humTextView = (TextView)mActivity.get().findViewById(R.id.humidity);
	}
	
}
