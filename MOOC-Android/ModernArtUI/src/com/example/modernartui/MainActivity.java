package com.example.modernartui;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	private TextView redView;
	private TextView greenView;
	private TextView whiteView;
	private TextView blueView;
	private TextView yellowView;
	private ImageView easterEgg;

	@Override
	protected void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setContentView(R.layout.main);
		
		initTextView();

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
	
	public void initTextView() {
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
	}

}
