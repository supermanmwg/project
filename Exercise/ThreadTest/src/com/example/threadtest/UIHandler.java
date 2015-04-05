package com.example.threadtest;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class UIHandler extends Handler {

	public static final int SET_BITMAP = 0;
	private static final String TAG = "UIHandler";
	private ImageView mImageView;
	private int mDelay = 500;
	BaseActivity mParent;

	public UIHandler() {
		mParent = BaseActivity.getmActivity();
	}
	
	
	@Override
	public void handleMessage(Message msg) {
		BaseActivity parent = mParent;
		switch (msg.what) {
		case SET_BITMAP:
			mImageView = (ImageView) parent.getmView().findViewById(R.id.load_image);
			mImageView.setImageBitmap((Bitmap) msg.obj);
			break;
		default:
			break;
		}
	}
}
