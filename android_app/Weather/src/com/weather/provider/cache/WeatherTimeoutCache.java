package com.weather.provider.cache;

import java.util.ArrayList;
import java.util.List;
import com.weather.aidl.WeatherData;
import com.weather.provider.WeatherContract;
import com.weather.provider.WeatherContract.WeatherValuesEntry;
import android.app.AlarmManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.SystemClock;
import android.util.Log;

/**
 * Timeout cache that uses a content provider to store data and the Alarm
 * manager and a broadcast receiver to remove expired cache entries
 */
public class WeatherTimeoutCache implements
		TimeoutCache<String, List<WeatherData>> {
	/**
	 * LogCat tag.
	 */
	private final static String TAG = WeatherTimeoutCache.class.getSimpleName();

	/**
	 * Default cache timeout in to 25 seconds (in milliseconds).
	 */
	private static final long DEFAULT_TIMEOUT = Long.valueOf(25000L);

	/**
	 * Cache is cleaned up at regular intervals (i.e., twice a day) to remove
	 * expired WeatherData.
	 */
	public static final long CLEANUP_SCHEDULER_TIME_INTERVAL = AlarmManager.INTERVAL_HALF_DAY;

	/**
	 * AlarmManager provides access to the system alarm services. Used to
	 * schedule Cache cleanup at regular intervals to remove expired Weather
	 * Values.
	 */
	private AlarmManager mAlarmManager;

	/**
	 * The timeout for an instance of this class in milliseconds.
	 */
	private long mDefaultTimeout;

	/**
	 * Context used to access the contentResolver.
	 */
	private Context mContext;

	/**
	 * Constructor that sets the default timeout for the cache (in seconds).
	 */
	public WeatherTimeoutCache(Context context) {
		// Store the context.
		mContext = context;

		// Set the timeout in milliseconds.
		mDefaultTimeout = DEFAULT_TIMEOUT;

		// Get the AlarmManager system service.
		mAlarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		// If Cache Cleanup is not scheduled, then schedule it.
		scheduleCacheCleanup(context);
	}

	/**
	 * Helper method that creates a content values object that can be inserted
	 * into the db's WeatherValuesEntry table from a given WeatherData object.
	 */
	private List<ContentValues> makeWeatherDataContentValues(
			List<WeatherData> wd, long timeout) {
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
			cvs.put(WeatherContract.WeatherValuesEntry.COLUMN_EXPIRATION_TIME,
					System.currentTimeMillis() + timeout);
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
		putImpl(obj, mDefaultTimeout);
	}

	/**
	 * Places the WeatherData object into the cache with a user specified
	 * timeout.
	 */
	@Override
	public void put(List<WeatherData> obj, int timeout) {
		putImpl( obj,
		// Timeout must be expressed in milliseconds.
				timeout * 1000);
	}

	/**
	 * Helper method that places a WeatherData object into the database.
	 */
	private void putImpl(List<WeatherData> wd, long timeout) {
		// Enter the main WeatherData into the WeatherValues table.
		List<ContentValues> weatheList = makeWeatherDataContentValues(wd,
				timeout);
		ContentValues[] contentArray = new ContentValues[weatheList.size()];
		weatheList.toArray(contentArray);
		mContext.getContentResolver().bulkInsert(
				WeatherContract.WeatherValuesEntry.WEATHER_VALUES_CONTENT_URI,
				contentArray);
	}

	/**
	 * Attempts to retrieve the given key's corresponding WeatherData object. If
	 * the key doesn't exist or has timed out, null is returned.
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
				Log.d(TAG, "Cursor not null and has first item");
				if (wdCursor
						.getLong(wdCursor
								.getColumnIndex(WeatherContract.WeatherValuesEntry.COLUMN_EXPIRATION_TIME)) < System
						.currentTimeMillis()) {

					// Concurrently delete the stale data from the db
					// in a new thread.
					new Thread(new Runnable() {
						public void run() {
							remove(location);
						}
					}).start();

					return null;
				} else
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
								.getColumnIndex(WeatherValuesEntry.COLUMN_EXPIRATION_TIME));
				WeatherData weatherData =new  WeatherData(
									speed,
									degree, 
									temp,
									tempMax, 
									tempMin, 
									humidity, 
									pressure,
									date, 
									country, 
									name, 
									icon,
									description);
				weatherList.add(weatherData);
			} while (data.moveToNext());
			
			// Return a WeatherData list.
			return weatherList;
		}
	}

	/**
	 * Delete the Weather Values and Weather Conditions associated with a @a
	 * locationKey.
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

	/**
	 * Remove all expired WeatherData rows from the database. This method is
	 * called periodically via the AlarmManager.
	 */
	public void removeExpiredWeatherData() {
		// Defines the selection clause used to query for weather values
		// that has expired.
		final String EXPIRATION_SELECTION = WeatherValuesEntry.COLUMN_EXPIRATION_TIME
				+ " <= ?";

		// First query the db to find all expired Weather Values
		// objects' ids.
		try (Cursor expiredData = mContext.getContentResolver().query(
				WeatherValuesEntry.WEATHER_VALUES_CONTENT_URI,
				new String[] { WeatherValuesEntry.COLUMN_NAME },
				EXPIRATION_SELECTION,
				new String[] { String.valueOf(System.currentTimeMillis()) },
				null)) {
			// Use the expired data id's to delete the designated
			// entries from both tables.
			if (expiredData != null && expiredData.moveToFirst()) {
				do {
					// Get the location to delete.
					final String deleteLocation = expiredData
							.getString(expiredData
									.getColumnIndex(WeatherValuesEntry.COLUMN_NAME));
					remove(deleteLocation);
				} while (expiredData.moveToNext());
			}
		}
	}

	/**
	 * Helper method that uses AlarmManager to schedule Cache Cleanup at regular
	 * intervals.
	 * 
	 * @param context
	 */
	private void scheduleCacheCleanup(Context context) {
		// Only schedule the Alarm if it's not already scheduled.
		if (!isAlarmActive(context)) {
			// Schedule an alarm after a certain timeout to start a
			// service to delete expired data from Database.
			mAlarmManager.setInexactRepeating(
					AlarmManager.ELAPSED_REALTIME_WAKEUP,
					SystemClock.elapsedRealtime()
							+ CLEANUP_SCHEDULER_TIME_INTERVAL,
					CLEANUP_SCHEDULER_TIME_INTERVAL,
					CacheCleanupReceiver.makeReceiverPendingIntent(context));
		}
	}
	
    /**
     * Helper method to check whether the Alarm is already active or not.
     * 
     * @param context
     * @return boolean, whether the Alarm is already active or not
     */
    private boolean isAlarmActive(Context context) {
	// Check whether the Pending Intent already exists or not.
	return CacheCleanupReceiver.makeCheckAlarmPendingIntent(context) != null;
    }
}

