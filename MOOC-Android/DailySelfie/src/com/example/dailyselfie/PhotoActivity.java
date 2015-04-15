package com.example.dailyselfie;

import java.io.File;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class PhotoActivity extends Activity {

	private ImageView mImageView;
	private String photoName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.second);
		Intent intent = getIntent();
		photoName = intent.getStringExtra(MainActivity.PHOTO_NAME);
		
		mImageView = (ImageView) findViewById(R.id.photo);
		BitmapWorkerTask.loadBitmap(photoName, mImageView);
	}
}
