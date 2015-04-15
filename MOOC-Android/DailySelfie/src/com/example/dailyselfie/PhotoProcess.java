package com.example.dailyselfie;

import java.io.File;
import java.security.PublicKey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.StaticLayout;
import android.view.View;
import android.widget.ImageView;

public class PhotoProcess {

	public static Bitmap decodeFile(String name, int w, int h) {
		String mCurrentPhotoPath;
		Bitmap bitmap = null;
		int targetW = w;
		int targetH = h;
		
		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		File f = new File(Environment.getExternalStorageDirectory(), name +".jpg");
		mCurrentPhotoPath = f.getAbsolutePath();
		BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		
		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
		
		return bitmap;
	}
}
