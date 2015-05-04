package com.example.mwgshowshow;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 
 * @author supermanmwg
 * 
 * @class PrimaryColor
 * 
 * @brief Handle Image Activity
 *
 */
public class PrimaryColor extends Activity implements OnSeekBarChangeListener{

	private ImageView mImageView;
	private SeekBar mHueSeekBar;
	private SeekBar mSaturationSeekBar;
	private SeekBar mLumSeekBar;
	private Bitmap mBitmap;
	private float mHue, mSaturation, mLum;
	private static int MAX_VALUE = 255;
	private static int MID_VALUE = 127;
	private static String TAG = "PrimaryColor";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.primary);
		
		mHue = MID_VALUE;
		mSaturation = 1.0f;
		mLum = 1.0f;
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.haha);
		mImageView = (ImageView) findViewById(R.id.image);
		mHueSeekBar = (SeekBar) findViewById(R.id.hue);
		mSaturationSeekBar = (SeekBar) findViewById(R.id.saturation);
		mLumSeekBar = (SeekBar) findViewById(R.id.lum);
		mHueSeekBar.setMax(MAX_VALUE);
		mSaturationSeekBar.setMax(MAX_VALUE);
		mLumSeekBar.setMax(MAX_VALUE);
		mHueSeekBar.setProgress(MID_VALUE);
		mSaturationSeekBar.setProgress(MID_VALUE);
		mLumSeekBar.setProgress(MID_VALUE);
		
		//set on Listener
		mHueSeekBar.setOnSeekBarChangeListener(this);
		mSaturationSeekBar.setOnSeekBarChangeListener(this);
		mLumSeekBar.setOnSeekBarChangeListener(this);
		
		mImageView.setImageBitmap(mBitmap);
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		
		switch (seekBar.getId()) {
		case R.id.hue:
			mHue = (progress - MID_VALUE) *1.0F /MID_VALUE * 180;
			break;
			
		case R.id.saturation:
			mSaturation = progress * 1.0f / MID_VALUE;
			break;	
			
		case R.id.lum:
			mLum = progress * 1.0f / MID_VALUE;
			break;	

		default:
			break;
		}
		Log.i(TAG, "hue = " + mHue);
		Log.i(TAG, "saturation = " + mSaturation);
		Log.i(TAG, "Lum = " + mLum);
		mImageView.setImageBitmap(ImageHandle.handleImageEffect(mBitmap, mHue, mSaturation, mLum));
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}
}
