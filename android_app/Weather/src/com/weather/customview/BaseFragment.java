package com.weather.customview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;


public class BaseFragment extends Fragment {

	private final String TAG = getClass().getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG,"onCreate is on");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG,"onActivityCreated is on");
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG,"onStart is on");
		
	}
	
	
}
