package com.weather.mwg.provider;

import android.content.ContentUris;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

public class WeatherContract {
	/**
	 * The weather provider's unique authority identify;
	 */
	public static final String AUTHORITY = 
			"com.weather.weatherprovider";
	
    /**
     * The base of all URIs that are used to communicate with the
     * WeatherProvider.
     */
    private static final Uri BASE_URI =
        Uri.parse("content://"
                  + AUTHORITY);
	
    /**
     * Constant for a directory MIME type.
     */
    private static final String MIME_TYPE_DIR =
        "vnd.android.cursor.dir/";

    /**
     * Constant for a single item MIME type.
     */
    private static final String MIME_TYPE_ITEM =
        "vnd.android.cursor.item/";
    
    
    /**
     * UriMatcher code for the Weather Values table.
     */
	public static final int WEATHER_VALUES_ITEMS = 0;

    /**
     * UriMatcher code for a specific row in the Weather Values table.
     */
	public static final int WEATHER_VALUES_ITEM = 1;

    /**
     * Inner class defining the contents of the Weather Values table.
     */
    public static final class WeatherValuesEntry 
                        implements BaseColumns {
        /**
         * Weather Values's Table name.
         */
        public static String WEATHER_VALUES_TABLE_NAME =
            "Weather";

        /**
         * Unique URI for the Weather Values table.
         */
        public static final Uri WEATHER_VALUES_CONTENT_URI =
            BASE_URI.buildUpon()
                    .appendPath(WEATHER_VALUES_TABLE_NAME)
                    .build();

        /**
         * MIME type for multiple Weather Values rows.
         */
        public static final String WEATHER_VALUES_ITEMS =
            MIME_TYPE_DIR
            + AUTHORITY 
            + "/" 
            + WEATHER_VALUES_TABLE_NAME;

        /**
         * MIME type for a single Weather Values row
         */
        public static final String WEATHER_VALUES_ITEM =
            MIME_TYPE_ITEM
            + AUTHORITY 
            + "/" 
            + WEATHER_VALUES_TABLE_NAME;

        /*
         * Weather Values Table's Columns.
         */
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_TEMP = "temp";
        public static final String COLUMN_TEMP_MAX = "temp_max";
        public static final String COLUMN_TEMP_MIN = "temp_min";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_SPEED = "speed";
        public static final String COLUMN_DEG = "deg";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_EXPIRATION_TIME = "expiration_time";

        /**
         * Return a URI that points to the row containing the given
         * ID.
         */
        public static Uri buildRowAccessUri(Long id) {
            return ContentUris.withAppendedId(WEATHER_VALUES_CONTENT_URI,
                                              id);
        }
    }
    
    /**
     * UriMatcher that is used to demultiplex the incoming URIs into
     * requests.
     */
    public static final UriMatcher sUriMatcher =
        buildUriMatcher();

    /**
     * Build the UriMatcher for this Content Provider.
     */
    public static UriMatcher buildUriMatcher() {
        // Add default 'no match' result to matcher.
        final UriMatcher matcher =
            new UriMatcher(UriMatcher.NO_MATCH);

        // Initialize the matcher with the URIs used to access each
        // table.
        matcher.addURI(WeatherContract.AUTHORITY,
                       WeatherContract.WeatherValuesEntry.WEATHER_VALUES_TABLE_NAME,
                       WEATHER_VALUES_ITEMS);
        matcher.addURI(WeatherContract.AUTHORITY,
                       WeatherContract.WeatherValuesEntry.WEATHER_VALUES_TABLE_NAME 
                       + "/#",
                       WEATHER_VALUES_ITEM);
        
        return matcher;
    }
}
