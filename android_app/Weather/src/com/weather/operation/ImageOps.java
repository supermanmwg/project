package com.weather.operation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.weather.R;
import com.weather.activities.AddCityActivity;
import com.weather.activities.MainActivity;
import com.weather.aidl.WeatherData;
import com.weather.customview.WeatherFragment;
import com.weather.provider.cache.WeatherTimeoutCache;

import android.content.Intent;
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
	
	private FragmentPagerAdapter mAdapter;
	
	//for home 
	private ImageView mAlterCity;
	
	private WeatherTimeoutCache mCache;
	private UniqueOps mUniqueOps;
	
	public ImageOps(MainActivity activity) {
		mActivity = new WeakReference<MainActivity>(activity);
		mCache = new WeatherTimeoutCache(activity);
		mUniqueOps = new UniqueOps(activity);
		initView();
		initDatas();
		mViewPager.setAdapter(mAdapter);
		initEvent();
	}

	private void initView()
	{
		mViewPager = (ViewPager)mActivity.get().findViewById(R.id.id_viewpager);
		
		mAlterCity = (ImageView) mActivity.get().findViewById(R.id.change_city);
		mAlterCity.setOnClickListener(mActivity.get());
		
		TextView one = (TextView)mActivity.get().findViewById(R.id.one);
		one.setOnClickListener(mActivity.get());
		one.setAlpha(1f);
		
		TextView two = (TextView)mActivity.get().findViewById(R.id.two);
		two.setOnClickListener(mActivity.get());
		two.setAlpha(0.5f);
		
		TextView three = (TextView)mActivity.get().findViewById(R.id.three);
		three.setOnClickListener(mActivity.get());
		three.setAlpha(0.5f);
		
		mTabIndicators.add(one);
		mTabIndicators.add(two);
		mTabIndicators.add(three);	
	}
	
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
		
		mAdapter = new FragmentPagerAdapter(mActivity.get().getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				return 3;
			}

			@Override
			public Fragment getItem(int pos)
			{
				Log.d(TAG, "pos is " + pos);
				 switch(pos) {
		            case 0: return WeatherFragment.newInstance(pos);
		            case 1: return WeatherFragment.newInstance(pos);
		            case 2: return WeatherFragment.newInstance(pos);
		            default: return WeatherFragment.newInstance(-1);
				 }
			}
		};
	}
	

	@SuppressWarnings("deprecation")
	private void initEvent()
	{

		mViewPager.setOnPageChangeListener(mActivity.get());

	}
	
	public void changeTab(int position) {
		resetOtherTabs();
		TextView t = mTabIndicators.get(position);
		t.setAlpha(1);
	}

	public void updateData(final List<WeatherData> mWeatherList) {
		if(null != mWeatherList && 3 == mWeatherList.size()) {
			mCache.remove(mWeatherList.get(0).getmName());
			mCache.put(mWeatherList);
			
			mUniqueOps.SetDisplayName(mWeatherList.get(0).getmName());
			Log.w(TAG, "get display name is " + mUniqueOps.getDisplayName());
			mActivity.get().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					initDatas();
					mViewPager.setAdapter(mAdapter);
					initEvent();
				}
			});
			

		}
	}
}
