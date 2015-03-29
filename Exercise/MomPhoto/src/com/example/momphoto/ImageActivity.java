package com.example.momphoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class ImageActivity  extends Activity{
	
	private final String TAG="ImageActivity";
	private ImageView imageView;
	private RatingBar ratingBar;
	private int imageId;
	private int position;
	private int sign;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagefragment);
		
		imageView = (ImageView)findViewById(R.id.image_view);
		ratingBar = (RatingBar)findViewById(R.id.rate_bar);
		
		Intent intent = getIntent();
		imageId = intent.getIntExtra(GridFragment.IMAGEID, -1);
		position = intent.getIntExtra(GridFragment.POSITION, -1);
		sign = intent.getIntExtra(GridFragment.SIGN, -1);
		Log.i(TAG,"imageId = " + imageId);
		imageView.setImageResource(imageId);
		if(0 == sign) {
			ratingBar.setRating(MainActivity.muPicRate[position]);
			Log.i(TAG, "uu rate = "+ MainActivity.muPicRate[position]);
		}  else {
			ratingBar.setRating(MainActivity.msPicRate[position]);
			Log.i(TAG, "ss rate =" +  MainActivity.msPicRate[position]);
		}
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				if(0 == sign) {
					MainActivity.muPicRate[position] = rating;
					Log.i(TAG, "u rate = "+ rating);
				} else {
					MainActivity.msPicRate[position] = rating;
					Log.i(TAG, "s rate = "+ rating);
				}
				
			}
		});
	}
}
