package com.example.dailyselfie;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
		View view = LayoutInflater.from(mContext).inflate(mResource, null);
		ImageView mPhotoView  = (ImageView) view.findViewById(R.id.photo_item);
		TextView mTextView = (TextView) view.findViewById(R.id.photo_name);
		mPhotoView.setImageResource(R.drawable.ic_launcher);
		mTextView.setText("haha");
		
		return view;
	}

}
