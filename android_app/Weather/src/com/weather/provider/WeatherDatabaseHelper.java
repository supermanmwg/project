package com.weather.provider;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class WeatherDatabaseHelper extends SQLiteOpenHelper {

	/**
	 * Database name.
	 */
	private static String DATABASE_NAME = "weather_db";

	/**
	 * Database version number, which is updated with each schema change.
	 */
	private static int DATABASE_VERSION = 1;

	/**
	 * SQL statement used to create the weather values table.
	 */
	public static final String CREATE_WEATHER = "create table "
			+ WeatherContract.WeatherValuesEntry.WEATHER_VALUES_TABLE_NAME
			+ "(" + WeatherContract.WeatherValuesEntry._ID
			+ " integer primary key, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_NAME + " text, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_SPEED + " real, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_DEG + " real, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_TEMP + " real, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_TEMP_MAX + " real, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_TEMP_MIN + " real, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_HUMIDITY + " integer, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_PRESSURE + " real, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_DATE + " integer, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_COUNTRY + " text, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_ICON + " text, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_DESCRIPTION + " text, "
			+ WeatherContract.WeatherValuesEntry.COLUMN_EXPIRATION_TIME
			+ " integer)";

	/**
	 * Constructor - initialize database name and version, but don't actually
	 * construct the database (which is done in the onCreate() hook method).
	 * 
	 * @param context
	 */
	public WeatherDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/**
	 * Hook method called when the database is created.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_WEATHER);
	}

	/**
	 * Hook method called when the database is upgraded.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
