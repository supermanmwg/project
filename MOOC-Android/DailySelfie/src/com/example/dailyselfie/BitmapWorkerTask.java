package com.example.dailyselfie;

import java.lang.ref.WeakReference;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

	private WeakReference<ImageView> imageViewReference;
	private String currentFileName;
	private ImageView mImageView = null;
	
	public BitmapWorkerTask(ImageView imageView) {
		imageViewReference =new  WeakReference<ImageView>(imageView);
	}
	@Override
	protected void onPreExecute() {
		 if (imageViewReference != null) {
	             mImageView = imageViewReference.get();
		 }
	}
	@Override
	protected Bitmap doInBackground(String... params) {
		currentFileName = params[0];
		 
		return PhotoProcess.decodeFile(currentFileName, mImageView.getWidth(), mImageView.getHeight());
	}

	@Override
	protected void onPostExecute(Bitmap result) {

		if(mImageView != null) {
			mImageView.setImageBitmap(result);
		}
	}
	
	public static void loadBitmap(String name, ImageView imageView) {
	    BitmapWorkerTask task = new BitmapWorkerTask(imageView);
	    task.execute(name);
	}
}
