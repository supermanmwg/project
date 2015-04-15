package com.example.dailyselfie;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class PhotoAdapter extends ArrayAdapter<Photo> {

	private Context mContext;
	private int mResource;
	
	public PhotoAdapter(Context context, int resource, List<Photo> objects) {
		super(context, resource, objects);
		mContext = context;
		mResource = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Photo mPhoto = getItem(position);
		View view = null;
		ViewHolder holder;
		if(null == convertView) {
			holder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(mResource, null);
			holder.mPhotoView = (ImageView) view.findViewById(R.id.photo_item);
			holder.mTextView = (TextView) view.findViewById(R.id.photo_name);;
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		//File f = new File(Environment.getExternalStorageDirectory(),mPhoto.getName() + ".jpg");
		
		BitmapWorkerTask.loadBitmap(mPhoto.getName(), holder.mPhotoView);
		holder.mTextView.setText(mPhoto.getName());

		return view;
	}
	
	static class ViewHolder {
		
		ImageView mPhotoView;
		TextView mTextView;
	}
	

}
