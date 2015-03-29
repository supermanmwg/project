package com.example.momphoto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MotherAdapter extends BaseAdapter {

	private static final int PADDING = 8;
	private static final int WIDTH = 350;
	private static final int HEIGHT = 350;
	private  final String TAG = "MotherAdapter";
	
	private ArrayList<Integer> mPicId = null;
	private float[] mPicRate = null;
	private int sign;
	private Context mContext;
	
	private static ImageView imageView;
	
	public MotherAdapter(Context c, ArrayList<Integer> ids,float[] rates, int sign) {
		this.mPicId = ids;
		this.mPicRate = rates;
		this.sign = sign;
		this.mContext = c;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPicId.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		return mPicId.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		 imageView = (ImageView)convertView;
		
		if(imageView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(WIDTH,HEIGHT));
			imageView.setPadding(PADDING, PADDING, PADDING, PADDING);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			Log.e(TAG,"pic id is "+mPicId.get(position));

		}
		
		imageView.setImageResource(mPicId.get(position));
		/*
		Bitmap bitmap = ((BitmapDrawable) mContext.getResources().getDrawable(mPicId.get(position))).getBitmap();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 10, out);
		Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
		imageView.setImageBitmap(decoded);
        if (bitmap != null && !bitmap.isRecycled())
        bitmap.recycle();
        */

	//	bitmap.recycle();
	//	decoded.recycle();
		return imageView;
	}

}
