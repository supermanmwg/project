package com.weather.mwg.provider;

import com.weather.mwg.provider.WeatherContract.WeatherValuesEntry;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Content Provider used to store information about weather data
 * returned from the Weather Service web service.
 */
public class WeatherProvider extends ContentProvider {

	 /**
     * The database helper that is used to manage the providers
     * database.
     */
	private WeatherDatabaseHelper mDataBaseHelper;
	
	
	@Override
	public boolean onCreate() {
		mDataBaseHelper = new WeatherDatabaseHelper(getContext(),"WeatherData.db", null, 1);
		return true;
	}
	

    /**
     * Helper method that appends a given key id to the end of the
     * WHERE statement parameter.
     */
    private static String addKeyIdCheckToWhereStatement(String whereStatement,
                                                        long id) {
        String newWhereStatement;
        if (TextUtils.isEmpty(whereStatement)) 
            newWhereStatement = "";
        else 
            newWhereStatement = whereStatement + " AND ";

        return newWhereStatement 
            + " _id = "
            + "'" 
            + id 
            + "'";
    }
    
    /**
     * Method called to handle query requests from client
     * applications.
     */
	@Override
	public Cursor query(Uri uri, String[] projection, String whereStatement,
			String[] whereStatementArgs, String sortOrder) {
    	// Create a SQLite query builder that will be modified based
    	// on the Uri passed.
        final SQLiteQueryBuilder queryBuilder =
            new SQLiteQueryBuilder();
        
		switch (WeatherContract.sUriMatcher.match(uri)) {
		 case WeatherContract.WEATHER_VALUES_ITEMS:
	            queryBuilder.setTables(WeatherValuesEntry.WEATHER_VALUES_TABLE_NAME);
	            break;
	        case WeatherContract.WEATHER_VALUES_ITEM:
	            queryBuilder.setTables(WeatherValuesEntry.WEATHER_VALUES_TABLE_NAME);
	            whereStatement =
	                addKeyIdCheckToWhereStatement(whereStatement,
	                                              ContentUris.parseId(uri));
	            break;
	        default:
	            throw new IllegalArgumentException("Unknown URI " + uri);
		}
		// Once the query builder has been initialized based on the
        // provided Uri, use it to query the database.
        final Cursor cursor =
            queryBuilder.query(mDataBaseHelper.getReadableDatabase(),
                               projection,
                               whereStatement,
                               whereStatementArgs,
                               null,	// GROUP BY (not used)
                               null,	// HAVING   (not used)
                               sortOrder);

        // Register to watch a content URI for changes.
        cursor.setNotificationUri(getContext().getContentResolver(),
                                  uri);
        return cursor;
	}
	
    /**
     * Method called to handle type requests from client applications.
     * It returns the MIME type of the data associated with each URI.
     */
	@Override
	public String getType(Uri uri) {
		switch (WeatherContract.sUriMatcher.match(uri)) {
		case WeatherContract.WEATHER_VALUES_ITEMS:
			return WeatherValuesEntry.WEATHER_VALUES_ITEMS;
		case WeatherContract.WEATHER_VALUES_ITEM:
			return WeatherValuesEntry.WEATHER_VALUES_ITEM;
        default:
            throw new IllegalArgumentException("Unknown URI " 
                                               + uri);
		}
	}

    /**
     * Method called to handle insert requests from client
     * applications.
     */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
		Uri mUri= null;
		
		switch (WeatherContract.sUriMatcher.match(uri)) {
		case WeatherContract.WEATHER_VALUES_ITEMS:
		case WeatherContract.WEATHER_VALUES_ITEM:
			mUri = WeatherValuesEntry.WEATHER_VALUES_CONTENT_URI;
			break;
		default:
			 throw new IllegalArgumentException("Unknown URI " 
                     + uri);
		}
		
		long weatherId = db.insert(WeatherValuesEntry.WEATHER_VALUES_TABLE_NAME,
				null, values);
		
		if(weatherId > 0) {
			Uri newUri = ContentUris.withAppendedId(mUri, weatherId);
			 
			// Register to watch a content URI for changes.
            getContext().getContentResolver().notifyChange(newUri,
                                                           null);
			return newUri;
		} else {
			throw new SQLException("Fail to add a new record into " 
                    + uri);
		}
	}	
	
	/**
     * Method that handles bulk insert requests.
     */
    @Override
    public int bulkInsert(Uri uri,
                          ContentValues[] values) {
    	// Fetch the db from the helper.
        final SQLiteDatabase db =
            mDataBaseHelper.getWritableDatabase();
        
        String dbName;
        
        // Match the Uri against the table's uris to determine the
        // table in which table to insert the values.
    	switch(WeatherContract.sUriMatcher.match(uri)) {
    	case WeatherContract.WEATHER_VALUES_ITEM:
    	case WeatherContract.WEATHER_VALUES_ITEMS:
            dbName =
                WeatherValuesEntry.WEATHER_VALUES_TABLE_NAME;
            break;
    	default:
            throw new IllegalArgumentException("Unknown URI " 
                                               + uri);
    	}
        
    	// Insert the values into the table in one transaction by
        // beginning a transaction in EXCLUSIVE mode.
        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                final long id =
                    db.insert(dbName,
                              null,
                              value);
                if (id != -1)
                    returnCount++;
            }
            // Marks the current transaction as successful.
            db.setTransactionSuccessful();
        } finally {
            // End the transaction
            db.endTransaction();
        }
        
        // Notifies registered observers that rows were updated and
        // attempt to sync changes to the network.
        getContext().getContentResolver().notifyChange(uri,
                                                       null);
        return returnCount;
    } 

	
    /**
     * Method called to handle delete requests from client
     * applications.
     */
    @Override
    public int delete(Uri uri,
                      String whereStatement,
                      String[] whereStatementArgs) {
        // Number of rows deleted.
        int rowsDeleted;

        final SQLiteDatabase db =
        		mDataBaseHelper.getWritableDatabase();

        // Delete the appropriate rows based on the Uri. If the URI 
        // includes a specific row to delete, add that row to the 
        // WHERE statement.
        switch (WeatherContract.sUriMatcher.match(uri)) {
        case WeatherContract.WEATHER_VALUES_ITEMS:
            rowsDeleted =
                db.delete(WeatherValuesEntry.WEATHER_VALUES_TABLE_NAME,
                          whereStatement,
                          whereStatementArgs);
            break;
        case WeatherContract.WEATHER_VALUES_ITEM:
            rowsDeleted = 
                db.delete(WeatherValuesEntry.WEATHER_VALUES_TABLE_NAME,
                          addKeyIdCheckToWhereStatement
                              (whereStatement,
                               ContentUris.parseId(uri)),
                          whereStatementArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " 
                                               + uri);
        }

        // Register to watch a content URI for changes.
        getContext().getContentResolver().notifyChange(uri, 
                                                       null);
        return rowsDeleted;
    }

    /**
     * Method called to handle update requests from client
     * applications.
     */
    @Override
    public int update(Uri uri,
                      ContentValues values,
                      String whereStatement,
                      String[] whereStatementArgs) {
        // Number of rows updated.
        int rowsUpdated;

        final SQLiteDatabase db = 
            mDataBaseHelper.getWritableDatabase();

        // Update the appropriate rows.  If the URI includes a
        // specific row to update, add that row to the where
        // statement.
        switch (WeatherContract.sUriMatcher.match(uri)) {
        case WeatherContract.WEATHER_VALUES_ITEMS:
            rowsUpdated =
                db.update(WeatherValuesEntry.WEATHER_VALUES_TABLE_NAME,
                          values,
                          whereStatement,
                          whereStatementArgs);
            break;
        case WeatherContract.WEATHER_VALUES_ITEM:
            rowsUpdated =
                db.update(WeatherValuesEntry.WEATHER_VALUES_TABLE_NAME,
                          values,
                          addKeyIdCheckToWhereStatement
                              (whereStatement,
                               ContentUris.parseId(uri)),
                          whereStatementArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " 
                                               + uri);
        }

        // Register to watch a content URI for changes.
        getContext().getContentResolver().notifyChange(uri,
                                                       null);

        return rowsUpdated;
    }
}
