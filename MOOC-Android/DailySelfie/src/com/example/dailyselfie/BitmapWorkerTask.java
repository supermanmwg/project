package com.example.dailyselfie;

import java.lang.ref.WeakReference;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

        if (imageViewReference != null && result != null) {
            final ImageView imageView = imageViewReference.get();
            final BitmapWorkerTask bitmapWorkerTask =
                    getBitmapWorkerTask(imageView);
            if (this == bitmapWorkerTask && imageView != null) {
                imageView.setImageBitmap(result);
            }
        }
/*		if(mImageView != null) {
			mImageView.setImageBitmap(result);
		}*/
	}
	
	public static void loadBitmap(String name, ImageView imageView) {    
	    if (cancelPotentialWork(name, imageView)) {
	        final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
	        final AsyncDrawable asyncDrawable =
	                new AsyncDrawable(null, null, task);
	        imageView.setImageDrawable(asyncDrawable);
	        task.execute(name);
	    }
	}
	
	static class AsyncDrawable extends BitmapDrawable {
	    private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

	    public AsyncDrawable(Resources res, Bitmap bitmap,
	            BitmapWorkerTask bitmapWorkerTask) {
	        super(res, bitmap);
	        bitmapWorkerTaskReference =
	            new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
	    }

	    public BitmapWorkerTask getBitmapWorkerTask() {
	        return bitmapWorkerTaskReference.get();
	    }
	    
	}
	
	public static boolean cancelPotentialWork(String photoName, ImageView imageView) {
	    final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

	    if (bitmapWorkerTask != null) {
	        final String bitmapData = bitmapWorkerTask.currentFileName;
	        // If bitmapData is not yet set or it differs from the new data
	        if (bitmapData == null || bitmapData != photoName) {
	            // Cancel previous task
	            bitmapWorkerTask.cancel(true);
	        } else {
	            // The same work is already in progress
	            return false;
	        }
	    }
	    // No task associated with the ImageView, or an existing task was cancelled
	    return true;
	}
	
	private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
		   if (imageView != null) {
		       final Drawable drawable = imageView.getDrawable();
		       if (drawable instanceof AsyncDrawable) {
		           final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
		           return asyncDrawable.getBitmapWorkerTask();
		       }
		    }
		    return null;
		}
}
