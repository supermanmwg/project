package com.example.fragmenttest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TitleFragment extends Fragment implements OnClickListener {
	
	private static final String TAG="MainActivity";
	
	private TextView title_text = null;
	private Button changeButton = null;
	
	private TitleListener mTitleListener = null;
	
	public interface TitleListener {
		public void titleOnListener();
	}
	
	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG,getClass().getSimpleName()+" onAttach()");
		mTitleListener = (TitleListener) activity;
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
		
		return inflater.inflate(R.layout.title_fragment, container,false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG,getClass().getSimpleName()+" onActivityCreated()");
		super.onActivityCreated(savedInstanceState);
		
		title_text = (TextView)getActivity().findViewById(R.id.title_text);
		title_text.setText("haha");
		
		changeButton = (Button)getActivity().findViewById(R.id.change_quote);
		changeButton.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.change_quote:
			mTitleListener.titleOnListener();
			break;
		default:
			break;
		}
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

}
