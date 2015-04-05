package com.example.threadtest;

import com.example.threadtest.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener {

	Handler mHandler = new UIHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Button loadButton = (Button) findViewById(R.id.load_button);
		final Button otherButton = (Button) findViewById(R.id.other_button);

		loadButton.setOnClickListener(this);
		otherButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.load_button:
			// new LoadIconTask().execute(R.drawable.painter);
			new Thread(new HandlerIconTask(R.drawable.painter, mHandler))
					.start();
			break;
		case R.id.other_button:
			Toast.makeText(this, "hello world!", Toast.LENGTH_SHORT).show();
			;
			break;
		default:
			break;
		}
	}
}
