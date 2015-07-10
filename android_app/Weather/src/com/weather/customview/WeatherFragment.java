package com.weather.customview;

import java.lang.ref.WeakReference;
import java.util.List;

import com.weather.R;
import com.weather.aidl.WeatherData;
import com.weather.operation.UniqueOps;
import com.weather.operation.WeatherOps;
import com.weather.provider.cache.WeatherTimeoutCache;
import com.weather.utils.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherFragment  extends BaseFragment{

	public final String TAG = getClass().getSimpleName();
	
	private static final String POS = "position";
	private WeakReference<Activity> mActivity;
	private WeatherTimeoutCache mCache;
	private UniqueOps mUniqueOps;
	
	private WeatherData mWeatherData;
	private View v;
	
	//for fragment ui
	private TextView cityNameTView;
	private TextView tempTextView;
	private TextView desTextView;
	private TextView datTextView;
	private TextView windTextView;
	private TextView humTextView;
	
	private String cityName;
	
	private int pos;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = new WeakReference<Activity>(activity);
		mUniqueOps = new UniqueOps(activity);
		mCache = new WeatherTimeoutCache(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v =  inflater.inflate(R.layout.fragment,container, false);
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getArguments() != null) {
			pos = getArguments().getInt(POS);
		}
		
	}
	
	  public static WeatherFragment newInstance(int pos) {

		  WeatherFragment f = new WeatherFragment();
		  if(-1 != pos) {
			  Bundle b = new Bundle();
			  b.putInt(POS, pos);
			  f.setArguments(b);
		  }
	      return f;
	    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}
	
	@Override
	public void onStart() {
		super.onStart();
		cityName = mUniqueOps.getDisplayName();
		Log.d(TAG, "city name is " + cityName);
		if(null != cityName) {
			//
			List<WeatherData> list = mCache.get(cityName);
			if(null != list) {
				Log.e(TAG," update list  size is " + list.size() + "pos is " + pos);
				mWeatherData = mCache.get(cityName).get(pos);
				updateDisplayFragment(mWeatherData);
			} else {
				Log.e(TAG, "update list  size is 0");
			}
		}
	}

	public void updateDisplayFragment(WeatherData mData) {
		initFragmentUI();
		setFragmentData(mData);
	}
	
	private void setFragmentData(WeatherData mData) {
		cityNameTView.setText(mData.getmName());
		tempTextView.setText("" + (long)mData.getmTempMin() + "¡ã/" + (long)mData.getmTempMax() + "¡ã");
		desTextView.setText(mData.getmDescription());
		datTextView.setText("" + Utils.TimeStampToDate(mData.getmDate()+1, null));
		windTextView.setText("·çÁ¦£º" + mData.getmSpeed()); 
		humTextView.setText("Êª¶È£º" + mData.getmHumidity() + "%");
		
	}

	private void initFragmentUI() {
		cityNameTView = (TextView)mActivity.get().findViewById(R.id.city_name);
		tempTextView = (TextView)v.findViewById(R.id.temperature);
		desTextView = (TextView)v.findViewById(R.id.description);
		datTextView = (TextView)v.findViewById(R.id.date);
		windTextView = (TextView)v.findViewById(R.id.wind);
		humTextView = (TextView)v.findViewById(R.id.humidity);
	}
	
}
