package com.example.fragmenttest;

import com.example.fragmenttest.TitleFragment.TitleListener;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements TitleListener{
	
	private final static String TAG = "MainActivity";
	private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
	
	//for title and Quote string
	public static String[] title = null;
	public static String[] quote = null;
	
	//for framelayout
	private FrameLayout mTitleFrame = null;
	private FrameLayout mQuoteFrame = null;
	
	//for fragment manager
	private FragmentManager mFragmentManager =null;
	
	//for fragment 
	public static TitleFragment mTitleFragment = new TitleFragment();
	public static QuoteFragment mQuoteFragment = new QuoteFragment();
	
	//for title button
	private Button changeButton = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG,getClass().getSimpleName()+" onCreate()");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		title = getResources().getStringArray(R.array.title_string);
		quote = getResources().getStringArray(R.array.quote_string);
		
		setContentView(R.layout.main);
		Log.i(TAG,getClass().getSimpleName()+" setContentView finished");
		
		mTitleFrame = (FrameLayout)findViewById(R.id.title);
		mQuoteFrame = (FrameLayout)findViewById(R.id.quote);
		
		mFragmentManager = getFragmentManager();
		FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
		mFragmentTransaction.add(R.id.title, mTitleFragment);	
		mFragmentTransaction.commit();
		

		mFragmentManager
		.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
			public void onBackStackChanged() {
				setLayout();
			}
		});

	}
	
	private void setLayout() {
		
		// Determine whether the QuoteFragment has been added
		if (!mQuoteFragment.isAdded()) {
			
			// Make the TitleFragment occupy the entire layout 
			mTitleFrame.setLayoutParams(new LinearLayout.LayoutParams(
					MATCH_PARENT, MATCH_PARENT));
			mQuoteFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
					MATCH_PARENT));
		} else {

			// Make the TitleLayout take 1/3 of the layout's width
			mTitleFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
					MATCH_PARENT, 1f));
			
			// Make the QuoteLayout take 2/3's of the layout's width
			mQuoteFrame.setLayoutParams(new LinearLayout.LayoutParams(0,
					MATCH_PARENT, 2f));
		}
	}
	
	@Override
	public void titleOnListener() {
		if(!mQuoteFragment.isAdded()) {
			FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
			mFragmentTransaction.add(R.id.quote,mQuoteFragment);
			mFragmentTransaction.addToBackStack(null);
			mFragmentTransaction.commit();
			mFragmentManager.executePendingTransactions();
		}
		mQuoteFragment.changeText();
	}
	
	@Override
	protected void onStart() {
		Log.i(TAG,getClass().getSimpleName()+" onStart()");
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		Log.i(TAG,getClass().getSimpleName()+" onResume()");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.i(TAG,getClass().getSimpleName()+" onPause()");
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		Log.i(TAG,getClass().getSimpleName()+" onStop()");
		super.onStop();
	}
	
	@Override
	protected void onRestart() {
		Log.i(TAG,getClass().getSimpleName()+" onRestart()");
		super.onRestart();
	}
	
	@Override
	protected void onDestroy() {
		Log.i(TAG,getClass().getSimpleName()+" onDestroy()");
		super.onDestroy();
	}



}
