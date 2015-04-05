package com.example.networktest;

import java.io.Serializable;
import java.util.List;

import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends BaseActivity implements OnClickListener,HttpInterface,Serializable {
	
	private ListFragment mListFragment;
	private LinearLayout mlayout;
	private Button loadButton;
	private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
	private static final int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;
	public static final String LIST = "LIST_ARRAY";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		loadButton = (Button)findViewById(R.id.load_url);
		loadButton.setOnClickListener(this);
		
		mlayout = (LinearLayout) findViewById(R.id.list_layout);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.load_url:
			new AndroidHttpClientTask().execute();
			break;
		default:
			break;
		}
	}

	@Override
	public void setAdapter(List<String> result) {
		
		Bundle args = new Bundle();
		args.putSerializable(LIST, (Serializable) result);
		mListFragment = new ListFragment();
		mListFragment.setArguments(args);
		
		FragmentManager mFragmentManager = getFragmentManager();
		FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
		mFragmentTransaction.add(R.id.list_layout, mListFragment);
		mFragmentTransaction.addToBackStack(null);
		mFragmentTransaction.commit();
		
		mFragmentManager.executePendingTransactions();
		
		mFragmentManager.addOnBackStackChangedListener(new OnBackStackChangedListener() {
			
			@Override
			public void onBackStackChanged() {
				setLayout();
			}
		});	
	}

	protected void setLayout() {
		if(mListFragment.isAdded()) {
			mlayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
			loadButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 0));
		} else {
			loadButton.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
			mlayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 0));
		}
	}
	
}
