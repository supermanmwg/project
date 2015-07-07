package com.weather.customview;

import java.lang.ref.WeakReference;

import com.weather.R;
import com.weather.activities.MainActivity;
import com.weather.operation.ImageOps;
import com.weather.retrofit.WeatherDataForeCast;

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
	
	private WeatherDataForeCast mWeatherData;
	
	private int id;
	
	private TextView detailTView;
	private View v;

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
			id = getArguments().getInt(ImageOps.ID);			
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		detailTView = (TextView) v.findViewById(R.id.wind);
		
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
}
