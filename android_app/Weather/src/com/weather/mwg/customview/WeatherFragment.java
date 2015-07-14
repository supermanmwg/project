package com.weather.mwg.customview;

import java.lang.ref.WeakReference;
import java.util.List;
import com.weather.mwg.R;
import com.weather.mwg.activities.MainActivity;
import com.weather.mwg.aidl.WeatherData;
import com.weather.mwg.operation.CnLangOpsImpl;
import com.weather.mwg.operation.EnLangOpsImpl;
import com.weather.mwg.operation.LangOps;
import com.weather.mwg.operation.UniqueOps;
import com.weather.mwg.provider.cache.WeatherTimeoutCache;
import com.weather.mwg.utils.Utils;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherFragment extends BaseFragment {

	/**
	 * Used to enable garbage collection.
	 */
	private WeakReference<MainActivity> mActivity;

	/**
	 * Used get weather data from data provider
	 */
	private WeatherTimeoutCache mCache;

	/**
	 * Used to set the simple shared data
	 */
	private UniqueOps mUniqueOps;

	/**
	 * Used to get the language operation (EN or CN)
	 */
	private LangOps mLangOps;

	/**
	 * Used to save weather data from mCache
	 */
	private WeatherData mWeatherData;

	/**
	 * Used to set the fragment view
	 */
	private View v;

	/**
	 * Fragment UI
	 */
	private TextView cityNameTView;
	private TextView tempTextView;
	private TextView desTextView;
	private TextView datTextView;
	private TextView windTextView;
	private TextView humTextView;
	private TextView pm25TextView;

	private String cityName;

	/**
	 * Fragment number
	 */
	private int pos;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = new WeakReference<MainActivity>((MainActivity) activity);
		mUniqueOps = new UniqueOps(activity);
		mCache = new WeatherTimeoutCache(activity);
		if (MainActivity.CN == mActivity.get().getLanguage()) {
			mLangOps = new CnLangOpsImpl();
		} else {
			mLangOps = new EnLangOpsImpl();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment, container, false);
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			pos = getArguments().getInt(POS);
		}
	}

	/**
	 * Weather Fragment Factory method
	 * 
	 * @param pos
	 *            :fragment number
	 * @return
	 */
	public static WeatherFragment newInstance(int pos) {

		WeatherFragment f = new WeatherFragment();
		if (-1 != pos) {
			Bundle b = new Bundle();
			b.putInt(POS, pos);
			f.setArguments(b);
		}
		return f;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		cityName = mUniqueOps.getName(UniqueOps.SQL_NAME);

		if (null != cityName) {
			Log.d(TAG, "city name is " + cityName);
			List<WeatherData> list = mCache.get(cityName);
			if (null != list) {
				mWeatherData = mCache.get(cityName).get(pos);
				updateDisplayFragment(mWeatherData);
			} else {
				Log.e(TAG, "update list  size is 0");
			}
		}
	}

	/**
	 * Update the fragment
	 * 
	 * @param mData
	 *            :weather data (used to init fragment UI's text)
	 */
	public void updateDisplayFragment(WeatherData mData) {
		initFragmentUI();
		setFragmentData(mData);
	}

	/**
	 * Set the Fragment text from mData
	 * 
	 * @param mData
	 *            ::weather data (used to init fragment UI's text)
	 */
	private void setFragmentData(WeatherData mData) {
		String cityName = mLangOps.getCityName(mUniqueOps
				.getName(UniqueOps.DISPLAY_NAME));

		String temp = "" + (long) mData.getmTempMin() + "°/"
				+ (long) mData.getmTempMax() + "°";

		String desc = mData.getmDescription();

		// need to be careful
		String date = ""
				+ Utils.TimeStampToDate(mData.getmDate() + 1,
						mLangOps.getDateFormat());

		String wind = mLangOps.convertWindDetails(mData.getmSpeed()) + "\n"
				+ mLangOps.convertSpeed(mData.getmSpeed())
				+ mLangOps.getWindLevelString();

		String hum = mLangOps.getHumidString() + mData.getmHumidity() + "%";

		cityNameTView.setText(cityName);
		tempTextView.setText(temp);
		desTextView.setText(desc);
		datTextView.setText(date);
		windTextView.setText(wind);
		humTextView.setText(hum);
		pm25TextView.setText("");
		if (0 == pos) {
			int aqi = mUniqueOps.getValue(UniqueOps.AQI);
			Log.d(TAG, "fragment Aqi is " + aqi);
			if (0 != aqi) {
				String pm25 = aqi + " " + mLangOps.genWeatherConditon(aqi)
						+ "  ";
				pm25TextView.setText(pm25);
			}
		}
	}

	/**
	 * Init fragment UI
	 */
	private void initFragmentUI() {
		cityNameTView = (TextView) mActivity.get().findViewById(R.id.city_name);
		tempTextView = (TextView) v.findViewById(R.id.temperature);
		desTextView = (TextView) v.findViewById(R.id.description);
		datTextView = (TextView) v.findViewById(R.id.date);
		windTextView = (TextView) v.findViewById(R.id.wind);
		humTextView = (TextView) v.findViewById(R.id.humidity);
		pm25TextView = (TextView) v.findViewById(R.id.pm2_5);
	}
}