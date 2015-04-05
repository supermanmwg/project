package com.example.threadtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class LoadIconTask extends AsyncTask<Integer, Integer, Bitmap> {

	private static final String TAG = "LoadIconTask";
	private ImageView mImageView;
	private int mDelay = 500;

	@Override
	protected void onPreExecute() {

		View view;
		view = BaseActivity.getmView();
		mImageView = (ImageView) view.findViewById(R.id.load_image);

		super.onPreExecute();
	}

	@Override
	protected Bitmap doInBackground(Integer... params) {

		Bitmap tmp = BitmapFactory.decodeResource(BaseActivity.getmContext()
				.getResources(), params[0]);
		
		for (int i = 0; i< 11; i++) {
			sleep();
			
		}
		return tmp;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		
		mImageView.setImageBitmap(result);
		super.onPostExecute(result);
	}


	private void sleep() {
		try {
			Thread.sleep(mDelay);
		} catch (InterruptedException e) {
			Log.e(TAG, e.toString());
		}
	}

}
