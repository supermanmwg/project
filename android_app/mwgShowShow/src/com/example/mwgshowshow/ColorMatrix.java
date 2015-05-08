package com.example.mwgshowshow;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 
 * @author supermanmwg
 * 
 * @class ColorMatrix
 *
 *@brief Use color matrix to adjust the photo
 */
public class ColorMatrix extends Activity{

	private static String TAG = "ColorMatrix";
	private ImageView mImageView;
	private Bitmap bm;
	private EditText mEditText[] = new EditText[20];
	private GridLayout mGroup;
	private float mMatrix[] = new float[20];
	private int mWidth;
	private int mHeight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.colormatrix);
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.haha);
		mImageView = (ImageView) findViewById(R.id.image);
		mImageView.setImageBitmap(bm);
		mGroup = (GridLayout) findViewById(R.id.grid);
		mGroup.post(new Runnable() {
			
			@Override
			public void run() {
				mWidth = mGroup.getWidth() / 5;
				mHeight = mGroup.getHeight() / 4;
				addEdx();
				InitMatrix();
				Log.i(TAG, "grid width is " + mWidth);
				Log.i(TAG, "grid height is " + mHeight);
			}
		});
	}
	
	private void InitMatrix() {
		for (int i = 0; i < 20; i++) {
			if(i % 6 == 0)
				mEditText[i].setText(String.valueOf(1));
			else {
				mEditText[i].setText(String.valueOf(0));
			}
		}
	}

	private void getMatrix() {
		for(int i = 0; i < 20; i++) {
			mMatrix[i] = Float.valueOf(mEditText[i].getText().toString());
		}
	}
	public void addEdx() {
		for (int i = 0; i < 20; i++) {
			EditText sEditText = new EditText(ColorMatrix.this);
			mEditText[i] = sEditText;
			
			mGroup.addView(sEditText,mWidth,mHeight);
		}
	}
	
	public void setImageMatrix() {
		android.graphics.ColorMatrix iColorMatrix = new android.graphics.ColorMatrix();
		iColorMatrix.set(mMatrix);
		
		Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColorFilter(new ColorMatrixColorFilter(iColorMatrix));
		canvas.drawBitmap(bm, 0, 0, paint);
		mImageView.setImageBitmap(bmp);
	}
	
	public void change(View v) {
		getMatrix();
		setImageMatrix();
	}
	
	public void reset(View v) {
		InitMatrix();
		getMatrix();
		setImageMatrix();
		
		
	}
}
