package com.example.momphoto;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class ImageFragment extends Fragment {
	
	private static final String TAG = "ImageFragment";
	private ImageView imageView;
	private RatingBar ratingBar;
	private int imageId;
	private float barStar;
	private int sign;
	private int position;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		imageId = getArguments().getInt(MainActivity.IMAGEID);
		position = getArguments().getInt(MainActivity.POSITION);
		Log.i(TAG, "imageId" + imageId);
		Log.i(TAG, "position" + position);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.imagefragment, container, false);
		imageView = (ImageView)layout.findViewById(R.id.image_view);
		ratingBar = (RatingBar)layout.findViewById(R.id.rate_bar);
		ratingBar.setRating(MainActivity.muPicRate[position]);
		return layout;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		imageView.setImageResource(imageId);

		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {

					MainActivity.muPicRate[position] = rating;
			}
		});
	}
}
