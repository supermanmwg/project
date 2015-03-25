package com.example.fragmenttest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.telephony.SignalStrength;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuoteFragment extends Fragment {
	
	private static final String TAG="MainActivity";
	private TextView quoteText = null;
	private int signal = 0;
	
	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG,getClass().getSimpleName()+" onAttach()");
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG,getClass().getSimpleName()+" onCreate()");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG,getClass().getSimpleName()+" onCreateView()");
		return inflater.inflate(R.layout.quote_fragment,
				container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG,getClass().getSimpleName()+" onActivityCreated()");
		super.onActivityCreated(savedInstanceState);
		
		quoteText = (TextView)getActivity().findViewById(R.id.quoteView);
		quoteText.setText("haha2");
		quoteText.setTextSize(32);
	}
	@Override
	public void onStart() {
		Log.i(TAG,getClass().getSimpleName()+" onStart()");
		super.onStart();
	}
	
	@Override
	public void onResume() {
		Log.i(TAG,getClass().getSimpleName()+" onResume()");
		super.onResume();
	}
	
	@Override
	public void onPause() {
		Log.i(TAG,getClass().getSimpleName()+" onPause()");
		super.onPause();
	}
	
	@Override
	public void onStop() {
		Log.i(TAG,getClass().getSimpleName()+" onStop()");
		super.onStop();
	}
	
	@Override
	public void onDestroyView() {
		Log.i(TAG,getClass().getSimpleName()+" onDestroyView()");
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		Log.i(TAG,getClass().getSimpleName()+" onDestroy()");
		super.onDestroy();
	}
	
	@Override
	public void onDetach() {
		Log.i(TAG,getClass().getSimpleName()+" onDetach()");
		super.onDetach();
	}
	
	public void changeText() {
		if(quoteText != null) {
			if(signal == 0) {
				quoteText.setText("haha, I am 2");
				signal = 1;
			} else {
				quoteText.setText("Haha,I am 1");
				signal = 0;
			}
		}
	}
}
