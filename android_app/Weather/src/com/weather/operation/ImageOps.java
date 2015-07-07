package com.weather.operation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.weather.R;
import com.weather.activities.AddCityActivity;
import com.weather.activities.MainActivity;
import com.weather.customview.WeatherFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageOps {
	
	public static final String ID = "id";
	private final String TAG = getClass().getSimpleName();

	private WeakReference<MainActivity> mActivity;
	
	private ViewPager mViewPager;

	private List<TextView> mTabIndicators = new ArrayList<TextView>();
	
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	
	//for home 
	private ImageView mHomeView;
	
	public ImageOps(MainActivity activity) {
		mActivity = new WeakReference<MainActivity>(activity);
		initView();
		initDatas();
		mViewPager.setAdapter(mAdapter);
		initEvent();
	}

	private void initView()
	{
		mViewPager = (ViewPager)mActivity.get().findViewById(R.id.id_viewpager);
		TextView one = (TextView)mActivity.get().findViewById(R.id.one);
		mTabIndicators.add(one);
		TextView two = (TextView)mActivity.get().findViewById(R.id.two);
		mTabIndicators.add(two);
		TextView three = (TextView)mActivity.get().findViewById(R.id.three);
		mTabIndicators.add(three);
		TextView four = (TextView)mActivity.get().findViewById(R.id.four);
		mTabIndicators.add(four);
		TextView five = (TextView)mActivity.get().findViewById(R.id.five);
		mTabIndicators.add(five);
		
		
		one.setOnClickListener(mActivity.get());
		two.setOnClickListener(mActivity.get());
		three.setOnClickListener(mActivity.get());
		four.setOnClickListener(mActivity.get());
		five.setOnClickListener(mActivity.get());
		one.setAlpha(1f);
		two.setAlpha(0.5f);
		three.setAlpha(0.5f);
		four.setAlpha(0.5f);
		five.setAlpha(0.5f);
		
		mHomeView = (ImageView) mActivity.get().findViewById(R.id.change_city);
		mHomeView.setOnClickListener(mActivity.get());

	}
	
	/**
	 * 点击Tab按钮
	 * 
	 * @param v
	 */
	public void clickTab(View v)
	{

		switch (v.getId())
		{
		case R.id.one:
			resetOtherTabs();
			mTabIndicators.get(0).setAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.two:
			resetOtherTabs();
			mTabIndicators.get(1).setAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.three:
			resetOtherTabs();
			mTabIndicators.get(2).setAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.four:
			resetOtherTabs();
			mTabIndicators.get(3).setAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;
		case R.id.five:
			resetOtherTabs();
			Log.d(TAG, "get 4");
			mTabIndicators.get(4).setAlpha(1.0f);
			mViewPager.setCurrentItem(4, false);
			break;
		case R.id.change_city:
			Log.d(TAG, "change city");
			Intent intent = new Intent(mActivity.get(), AddCityActivity.class);
			mActivity.get().startActivity(intent);
			break;
			
		}
	}
	
	private void resetOtherTabs()
	{
		for (int i = 0; i < mTabIndicators.size(); i++)
		{
			mTabIndicators.get(i).setAlpha(0.5f);
		}
	}

	
	private void initDatas()
	{
		for (int i= 0; i< 5; i++)
		{
			WeatherFragment tabFragment = new WeatherFragment();
			Bundle bundle = new Bundle();
			bundle.putInt(ID, i);
			tabFragment.setArguments(bundle);
			mTabs.add(tabFragment);
		}

		mAdapter = new FragmentPagerAdapter(mActivity.get().getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int position)
			{
				return mTabs.get(position);
			}
		};
	}
	

	private void initEvent()
	{

		mViewPager.setOnPageChangeListener(mActivity.get());

	}
	
	public void changeTab(int position) {
		resetOtherTabs();
		TextView t = mTabIndicators.get(position);
		t.setAlpha(1);
	}
	

}
