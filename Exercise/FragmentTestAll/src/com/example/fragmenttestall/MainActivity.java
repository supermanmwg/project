package com.example.fragmenttestall;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity implements MainFragment.onButtonLister{

	public static final String IMAGE_ID = "Image ID";
	private FragmentManager mFragmentManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		mFragmentManager = getFragmentManager();
		FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
		mFragmentTransaction.add(R.id.container, new MainFragment());
		mFragmentTransaction.commit();
	}

	@Override
	public void notifyMain(int id) {
		SecondFragment mFragment = new SecondFragment();
		Bundle args = new Bundle();
		args.putInt(IMAGE_ID, id);
		mFragment.setArguments(args);
		FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
		mFragmentTransaction.replace(R.id.container,mFragment);
		mFragmentTransaction.addToBackStack(null);
		mFragmentTransaction.commit();
		mFragmentManager.executePendingTransactions();
	}
}
