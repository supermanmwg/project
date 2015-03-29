package com.example.modernartui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {
	
	//home color rectangle 
	private ImageView redView;
	private ImageView greenView;
	private ImageView whiteView;
	private ImageView blueView;
	private ImageView yellowView;
	private SeekBar seekBar;

	// Dialog
	private DialogFragment mDialog;

	// easter egg animation
	private TextView easterEgg;
	private AnimationDrawable mAnim = null;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.main);

		initTextView();
		
		//set on seekbar listener to change rectangle color except white rect.
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				changeColor(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
		});

	}

	// Create Options Menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.top_menu, menu);
		return true;
	}

	// Process clicks on Options Menu items
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.more_information:
			mDialog = MoreInfoFragment.newInstance();
			mDialog.show(getFragmentManager(), "MoreInfo");
			return true;
		default:
			return false;
		}
	}

	// create the more information dialog
	public static class MoreInfoFragment extends DialogFragment {

		public static MoreInfoFragment newInstance() {
			return new MoreInfoFragment();
		}

		// Build more information dialog using AlertDialog.Builder
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new AlertDialog.Builder(getActivity())
					.setMessage(R.string.more_info_tips)
					.setCancelable(false)
					.setPositiveButton(R.string.visit_moma,
							new DialogInterface.OnClickListener() {
								@SuppressLint("SetJavaScriptEnabled")
								@Override
								public void onClick(DialogInterface dialog,int which) {
									Intent intent = new Intent(getActivity(),WebActivity.class);
									startActivity(intent);
								}
							}).setNegativeButton(R.string.not_visit, null).create();
		}
	}
	
	//click event process
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.red:
			redView.setVisibility(View.INVISIBLE);
			checkVisible();
			break;
		case R.id.white:
			whiteView.setVisibility(View.INVISIBLE);
			checkVisible();
			break;
		case R.id.yellow:
			yellowView.setVisibility(View.INVISIBLE);
			checkVisible();
			break;
		case R.id.green:
			greenView.setVisibility(View.INVISIBLE);
			checkVisible();
			break;
		case R.id.blue:
			blueView.setVisibility(View.INVISIBLE);
			checkVisible();
			break;
		case R.id.easter_egg:
			setContentView(R.layout.nephew);
			imageView = (ImageView) findViewById(R.id.my_nephew);
			imageView.setBackgroundResource(R.drawable.view_animation);
			mAnim = (AnimationDrawable) imageView.getBackground();
			mAnim.start();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(mAnim !=null && !mAnim.isRunning()) {
			mAnim.start();
		}
	};
	@Override
	protected void onPause() {
		super.onPause();
		if (mAnim != null && mAnim.isRunning()) {
			mAnim.stop();
		}
	}

	private void initTextView() {
		redView = (ImageView) findViewById(R.id.red);
		whiteView = (ImageView) findViewById(R.id.white);
		yellowView = (ImageView) findViewById(R.id.yellow);
		greenView = (ImageView) findViewById(R.id.green);
		blueView = (ImageView) findViewById(R.id.blue);

		redView.setOnClickListener(this);
		whiteView.setOnClickListener(this);
		yellowView.setOnClickListener(this);
		greenView.setOnClickListener(this);
		blueView.setOnClickListener(this);

		seekBar = (SeekBar) findViewById(R.id.seek_bar);
	}

	private void changeColor(int p) {
		int progress = p * 5;
		redView.setBackgroundColor(Color.red + progress);
		whiteView.setBackgroundColor(Color.white);
		yellowView.setBackgroundColor(Color.yellow + progress);
		greenView.setBackgroundColor(Color.green + progress);
		blueView.setBackgroundColor(Color.blue + progress);
	}
	
	// Check all the image views are invisible or not
	private void checkVisible() {
		int visible = View.INVISIBLE;     // VISIBLE:0   INVISIBLE:4     GONE:8
		visible &= redView.getVisibility();
		visible &= blueView.getVisibility();
		visible &= greenView.getVisibility();
		visible &= whiteView.getVisibility();
		visible &= yellowView.getVisibility();

		if (visible == View.INVISIBLE) {
			// Toast.makeText(getApplicationContext(),
			// "all image view disappear", Toast.LENGTH_SHORT).show();
			setContentView(R.layout.easter_egg);
			easterEgg = (TextView) findViewById(R.id.easter_egg);
			easterEgg.setOnClickListener(this);
		}
	}
}
