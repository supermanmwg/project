package com.weather.mwg.operation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import com.weather.mwg.R;
import com.weather.mwg.activities.MainActivity;
import com.weather.mwg.aidl.WeatherData;
import com.weather.mwg.customview.WeatherFragment;
import com.weather.mwg.provider.cache.WeatherCache;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ImageOps {
	/**
	 * Debugging tag used by the Android logger.
	 */
	public final String TAG = getClass().getSimpleName();

	/**
	 * Used to enable garbage collection.
	 */
	private WeakReference<MainActivity> mActivity;

	/**
	 * Viewpager Adapter
	 */
	private FragmentPagerAdapter mAdapter;

	/**
	 * Used to get weather data from data provider
	 */
	private WeatherCache mCache;

	/**
	 * Used to set the simple shared data
	 */
	private UniqueOps mUniqueOps;

	/**
	 * Used to scroll the view
	 */
	private ViewPager mViewPager;

	/**
	 * Tab indicators
	 */
	private List<TextView> mTabIndicators = new ArrayList<TextView>();

	/**
     * Constructor initializes the fields.
     */
	public ImageOps(MainActivity activity) {
		mActivity = new WeakReference<MainActivity>(activity);
		mCache = new WeatherCache(activity);
		mUniqueOps = new UniqueOps(activity);
		initView();
		initDatas();
		mViewPager.setAdapter(mAdapter);
		initEvent();
	}

	/**
	 * Initiate viewpager and tabindicators
	 */
	private void initView() {
		mViewPager = (ViewPager) mActivity.get()
				.findViewById(R.id.id_viewpager);

		TextView one = (TextView) mActivity.get().findViewById(R.id.one);
		one.setOnClickListener(mActivity.get());
		one.setAlpha(1f);

		TextView two = (TextView) mActivity.get().findViewById(R.id.two);
		two.setOnClickListener(mActivity.get());
		two.setAlpha(0.5f);

		TextView three = (TextView) mActivity.get().findViewById(R.id.three);
		three.setOnClickListener(mActivity.get());
		three.setAlpha(0.5f);

		mTabIndicators.add(one);
		mTabIndicators.add(two);
		mTabIndicators.add(three);
	}

	/**
	 * Tab click event
	 */
	public void clickTab(View v) {

		switch (v.getId()) {
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
		}
	}

	/**
	 * Set all the Tab indicators to half alpha
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicators.size(); i++) {
			mTabIndicators.get(i).setAlpha(0.5f);
		}
	}

	/**
	 * Set the viewpager adapter
	 */
	private void initDatas() {

		mAdapter = new FragmentPagerAdapter(mActivity.get()
				.getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return 3;
			}

			@Override
			public Fragment getItem(int pos) {
				Log.d(TAG, "pos is " + pos);
				switch (pos) {
				case 0:
					return WeatherFragment.newInstance(pos);
				case 1:
					return WeatherFragment.newInstance(pos);
				case 2:
					return WeatherFragment.newInstance(pos);
				default:
					return WeatherFragment.newInstance(-1);
				}
			}
		};
	}

	/**
	 * Set view pager onChange listener
	 */
	@SuppressWarnings("deprecation")
	private void initEvent() {

		mViewPager.setOnPageChangeListener(mActivity.get());

	}

	/**
	 * Set the Tab indicator's alpha to 1 (opacity)
	 */
	public void changeTab(int position) {
		resetOtherTabs();
		TextView t = mTabIndicators.get(position);
		t.setAlpha(1);
	}

	/**
	 * Update the weather fragment
	 * 
	 * @param mWeatherList
	 */
	public void updateData(final List<WeatherData> mWeatherList) {
		if (null != mWeatherList && 3 == mWeatherList.size()) {
			// For provider operation
			String sql_name = mWeatherList.get(0).getmName();

			// Update the data provider
			mCache.remove(sql_name);
			mCache.put(mWeatherList);

			// Set the provider's seach name
			mUniqueOps.SetName(UniqueOps.SQL_NAME, sql_name);

			mActivity.get().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// Reset the adapter and viewpager
					initDatas();
					mViewPager.setAdapter(mAdapter);
					initEvent();
				}
			});
		}
	}
}
