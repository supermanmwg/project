package com.weather.provider;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDatabaseHelper extends SQLiteOpenHelper{

    /**
     * Database name.
     */
    private static String DATABASE_NAME =
        "weather_db";

    /**
     * Database version number, which is updated with each schema
     * change.
     */
    private static int DATABASE_VERSION = 1;
    
    /**
     * SQL statement used to create the weather values table.
     */
	public static final String CREATE_WEATHER = "create table Weather ("
			+ "id integer, "
			+ "name text, "
			+ "speed real, "
			+ "degree real, "
			+ "temp real, "
			+ "tempmax real, "
			+ "tempmin real, "
			+ "humidity integer, "
			+ "pressure real, "
			+ "date integer, "
			+ "country text, "
			+ "icon text,"
			+ "description text)";
	
    /**
    * Constructor - initialize database name and version, but don't
    * actually construct the database (which is done in the
    * onCreate() hook method). It places the database in the
    * application's cache directory, which will be automatically
    * cleaned up by Android if the device runs low on storage space.
    * 
    * @param context
    */
	public WeatherDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		 super(context, 
	              context.getCacheDir()
	              + File.separator 
	              + DATABASE_NAME, 
	              null,
	              DATABASE_VERSION);
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
