package com.example.modernartui;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
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

	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.main);
		
		initTextView();
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

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
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
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
			Toast.makeText(getApplicationContext(), "haha", Toast.LENGTH_SHORT).show();
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
	
	private void initTextView() {
		redView = (TextView)findViewById(R.id.red);
		whiteView = (TextView)findViewById(R.id.white);
		yellowView = (TextView)findViewById(R.id.yellow);
		greenView = (TextView)findViewById(R.id.green);
		blueView = (TextView)findViewById(R.id.blue);
		easterEgg = (ImageView)findViewById(R.id.easter_egg);
		redView.setOnClickListener(this);
		whiteView.setOnClickListener(this);
		yellowView.setOnClickListener(this);
		greenView.setOnClickListener(this);
		blueView.setOnClickListener(this);
		easterEgg.setOnClickListener(this);
		
		seekBar = (SeekBar)findViewById(R.id.seek_bar);
	}
	
	private void changeColor(int p) {
		int progress = p*5;
		redView.setBackgroundColor(red+progress);
		whiteView.setBackgroundColor(white);
		yellowView.setBackgroundColor(yellow+progress);
		greenView.setBackgroundColor(green+progress);
		blueView.setBackgroundColor(blue+progress);
	}

}
