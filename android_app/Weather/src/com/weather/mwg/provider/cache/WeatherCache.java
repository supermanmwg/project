package com.weather.mwg.provider.cache;

import java.util.ArrayList;
import java.util.List;
import com.weather.mwg.aidl.WeatherData;
import com.weather.mwg.provider.WeatherContract;
import com.weather.mwg.provider.WeatherContract.WeatherValuesEntry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Timeout cache that uses a content provider to store data
 */
public class WeatherCache implements Cache<String, List<WeatherData>> {
	/**
	 * LogCat tag.
	 */
	public final static String TAG = WeatherCache.class.getSimpleName();

	/**
	 * Context used to access the contentResolver.
	 */
	private Context mContext;

	/**
	 * Constructor
	 */
	public WeatherCache(Context context) {
		// Store the context.
		mContext = context;
	}

	/**
	 * Helper method that creates a content values object that can be inserted
	 * into the db's WeatherValuesEntry table from a given WeatherData object.
	 */
	private List<ContentValues> makeWeatherDataContentValues(
			List<WeatherData> wd) {
		List<ContentValues> contentList = new ArrayList<>();
		for (int i = 0; i < wd.size(); i++) {
			ContentValues cvs = new ContentValues();
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_NAME, wd.get(i)
					.getmName());
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_DATE, wd.get(i)
					.getmDate());
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_COUNTRY, wd
					.get(i).getmCountry());
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_TEMP, wd.get(i)
					.getmTemp());
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_TEMP_MAX,
					wd.get(i).getmTempMax());
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_TEMP_MIN,
					wd.get(i).getmTempMin());
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_HUMIDITY,
					wd.get(i).getmHumidity());
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_PRESSURE,
					wd.get(i).getmPressure());
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_SPEED, wd.get(i)
					.getmSpeed());
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_DEG, wd.get(i)
					.getmDeg());
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_ICON, wd.get(i)
					.getmIconID());
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_DESCRIPTION, wd
					.get(i).getmDescription());
			contentList.add(cvs);
		}
		return contentList;
	}

	/**
	 * Place the WeatherData object into the cache. It assumes that a get()
	 * method has already attempted to find this object's location in the cache,
	 * and returned null.
	 */
	@Override
	public void put(List<WeatherData> obj) {
		putImpl(obj);
	}

	/**
	 * Helper method that places a WeatherData object into the database.
	 */
	private void putImpl(List<WeatherData> wd) {
		// Enter the main WeatherData into the WeatherValues table.
		List<ContentValues> weatheList = makeWeatherDataContentValues(wd);
		ContentValues[] contentArray = new ContentValues[weatheList.size()];
		weatheList.toArray(contentArray);
		mContext.getContentResolver().bulkInsert(
				WeatherContract.WeatherValuesEntry.WEATHER_VALUES_CONTENT_URI,
				contentArray);
	}

	/**
	 * Attempts to retrieve the given key's corresponding WeatherData object. If
	 * the key doesn't exist.
	 */
	@Override
	public List<WeatherData> get(final String location) {
		// Attempt to retrieve the location's data from the content
		// provider.
		try (Cursor wdCursor = mContext.getContentResolver().query(
				WeatherValuesEntry.WEATHER_VALUES_CONTENT_URI, null,
				WeatherValuesEntry.COLUMN_NAME + " = ?",
				new String[] { location }, null)) {
			// Check that the cursor isn't null and contains an item.
			if (wdCursor != null && wdCursor.moveToFirst()) {
				// Convert the contents of the cursor into a
				// WeatherData object.
				return getWeatherDataFromCursor(wdCursor);
			} else

				// Query was empty or returned null.
				return null;
		}
	}

	/**
	 * Constructor using a cursor returned by the WeatherProvider. This cursor
	 * must contain all the data for the object - i.e., it must contain a row
	 * for each Weather object corresponding to the Weather object.
	 */
	private List<WeatherData> getWeatherDataFromCursor(Cursor data) {
		if (data == null || !data.moveToFirst())
			return null;
		else {
			List<WeatherData> weatherList = new ArrayList<>();
			do {
				final String name = data.getString(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_NAME));
				final long date = data.getLong(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_DATE));
				final String country = data.getString(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_COUNTRY));
				final double temp = data.getDouble(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_TEMP));
				final double tempMax = data.getDouble(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_TEMP_MAX));
				final double tempMin = data.getDouble(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_TEMP_MIN));
				final long humidity = data.getLong(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_HUMIDITY));
				final double pressure = data.getDouble(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_PRESSURE));
				final double speed = data.getDouble(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_SPEED));
				final double degree = data.getDouble(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_DEG));
				final String icon = data.getString(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_ICON));
				final String description = data.getString(data
						.getColumnIndex(WeatherValuesEntry.COLUMN_DESCRIPTION));
				WeatherData weatherData = new WeatherData(speed, degree, temp,
						tempMax, tempMin, humidity, pressure, date, country,
						name, icon, description);
				weatherList.add(weatherData);
			} while (data.moveToNext());

			// Return a WeatherData list.
			return weatherList;
		}
	}

	/**
	 * Delete the Weather Values and Weather Conditions associated with a
	 * location.
	 */
	@Override
	public void remove(String location) {
		// Delete expired entries from the WeatherValues table.
		mContext.getContentResolver().delete(
				WeatherValuesEntry.WEATHER_VALUES_CONTENT_URI,
				WeatherValuesEntry.COLUMN_NAME + " = ?",
				new String[] { location });
	}

	/**
	 * Return the current number of WeatherData objects in Database.
	 * 
	 * @return size
	 */
	@Override
	public int size() {
		// Query the data for all rows of the Weather Values table.
		try (Cursor cursor = mContext.getContentResolver().query(
				WeatherContract.WeatherValuesEntry.WEATHER_VALUES_CONTENT_URI,
				new String[] { WeatherValuesEntry._ID }, null, null, null)) {
			// Return the number of rows in the table, which is equivlent
			// to the number of objects
			return cursor.getCount();
		}
	}
}
