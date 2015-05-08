package com.example.mwgshowshow;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class PixelEffect extends Activity {

	private ImageView mImage1, mImage2, mImage3, mImage4;
	private Bitmap mBitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pixel);
		
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.haha);
		mImage1 = (ImageView) findViewById(R.id.image1);
		mImage2 = (ImageView) findViewById(R.id.image2);
		mImage3 = (ImageView) findViewById(R.id.image3);
		mImage4 = (ImageView) findViewById(R.id.image4);
		
		mImage1.setImageBitmap(mBitmap);
		mImage2.setImageBitmap(ImageHandle.handleImageNegative(mBitmap));
		mImage3.setImageBitmap(ImageHandle.handleImagePixelsOldPhoto(mBitmap));
		mImage4.setImageBitmap(ImageHandle.handleImagePixelsRelief(mBitmap));
	}
}
