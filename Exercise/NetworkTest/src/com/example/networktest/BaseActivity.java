package com.example.networktest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends Activity implements HttpInterface {
	
	private static Context mContext;
	private static View mView;
	private static BaseActivity mActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		mView = getWindow().getDecorView().findViewById(android.R.id.content);
		mActivity = (BaseActivity)this;
	}
	
	public static Context getmContext() {
		return mContext;
	}
	
	public static View getmView() {
		return mView;
	}

	public static BaseActivity getmActivity() {
		return mActivity;
	}

	@Override
	public void setAdapter(List<String> result) {		
	}
	
}
