package com.example.modernartui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private TextView redView;
	private TextView greenView;
	private TextView whiteView;
	private TextView blueView;
	private TextView yellowView;
	private ImageView easterEgg;
	private SeekBar seekBar;

	private int red = 0xFFCC0000;
	private int white = 0xFFFFFFFF;
	private int blue = 0xFF0000FF;
	private int green = 0xFF99CC00;
	private int yellow = 0xFF3E2723;
	
	//Dialog
	private DialogFragment mDialog;
	
	//visit webView
	private static WebView webView;
	
	//easter egg animation
	private AnimationDrawable mAnim=null;
	private ImageView imageView;
	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.main);

		initTextView();
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
					.setMessage(
							"Inspired by the works of artists such as Piet Mondrian and Ben Nicholson.\n\nClick below to learn more!")
					.setCancelable(false)
					.setPositiveButton("Visit MOMA", 
							new DialogInterface.OnClickListener() {
						
								@SuppressLint("SetJavaScriptEnabled")
								@Override
								public void onClick(DialogInterface dialog, int which) {
									webView =(WebView) getActivity().getLayoutInflater().inflate(R.layout.webview, null);
									getActivity().setContentView(webView);	
									webView.setWebViewClient(new HelloWebViewClient());	
									webView.getSettings().setJavaScriptEnabled(true);
									webView.loadUrl("www.moma.org/");
															
								}
								
								class HelloWebViewClient extends WebViewClient {
									private static final String TAG = "HelloWebViewClient";;

									// Give application a chance to catch additional URL loading requests
									@Override
									public boolean shouldOverrideUrlLoading(WebView view, String url) {
										Log.i(TAG, "About to load:" + url);
										view.loadUrl(url);
										return true;
									}
								}
							})
					.setNegativeButton("Not Now", null)
					.create();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.red:
			redView.setVisibility(View.INVISIBLE);
			break;
		case R.id.white:
			whiteView.setVisibility(View.INVISIBLE);
			break;
		case R.id.yellow:
			yellowView.setVisibility(View.INVISIBLE);
			easterEgg.setBackgroundResource(R.drawable.haha_part);
			break;
		case R.id.easter_egg:
			yellowView.setVisibility(View.INVISIBLE);
			easterEgg.setBackgroundResource(R.drawable.haha_part);
			// toastCustom();
			setContentView(R.layout.nephew);
			imageView = (ImageView)findViewById(R.id.my_nephew);
			imageView.setBackgroundResource(R.drawable.view_animation);
			mAnim = (AnimationDrawable) imageView.getBackground();
			mAnim.start();

			break;
		case R.id.green:
			greenView.setVisibility(View.INVISIBLE);
			break;
		case R.id.blue:
			blueView.setVisibility(View.INVISIBLE);
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onPause() {
 		super.onPause();
		if (mAnim!=null&&mAnim.isRunning()) {
			mAnim.stop();
		}
	}


	private void initTextView() {
		redView = (TextView) findViewById(R.id.red);
		whiteView = (TextView) findViewById(R.id.white);
		yellowView = (TextView) findViewById(R.id.yellow);
		greenView = (TextView) findViewById(R.id.green);
		blueView = (TextView) findViewById(R.id.blue);
		easterEgg = (ImageView) findViewById(R.id.easter_egg);
		redView.setOnClickListener(this);
		whiteView.setOnClickListener(this);
		yellowView.setOnClickListener(this);
		greenView.setOnClickListener(this);
		blueView.setOnClickListener(this);
		easterEgg.setOnClickListener(this);

		seekBar = (SeekBar) findViewById(R.id.seek_bar);
	}

	private void changeColor(int p) {
		int progress = p * 5;
		redView.setBackgroundColor(red + progress);
		whiteView.setBackgroundColor(white);
		yellowView.setBackgroundColor(yellow + progress);
		greenView.setBackgroundColor(green + progress);
		blueView.setBackgroundColor(blue + progress);
	}

	private void toastCustom() {
		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		View view = getLayoutInflater().inflate(R.layout.main, null);
		redView = (TextView) view.findViewById(R.id.red);
		whiteView = (TextView) view.findViewById(R.id.white);
		yellowView = (TextView) view.findViewById(R.id.yellow);
		greenView = (TextView) view.findViewById(R.id.green);
		blueView = (TextView) view.findViewById(R.id.blue);
		easterEgg = (ImageView) view.findViewById(R.id.easter_egg);
		redView.setOnClickListener(this);
		whiteView.setOnClickListener(this);
		yellowView.setOnClickListener(this);
		greenView.setOnClickListener(this);
		blueView.setOnClickListener(this);
		easterEgg.setOnClickListener(this);
		toast.setView(view);
		toast.show();
	}
}
