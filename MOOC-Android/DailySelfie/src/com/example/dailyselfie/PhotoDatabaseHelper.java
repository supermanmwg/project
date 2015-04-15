package com.example.dailyselfie;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PhotoDatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "PhotoDatabaseHelper";
	private static final String CREATE_PHOTO = "Create table Photo ("
														+ "id integer primary key autoincrement,"
														+ "photo_name text)";
	private Context mContext;
	public PhotoDatabaseHelper(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_PHOTO);
			Log.i(TAG, "database photo is created!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
