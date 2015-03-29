package com.example.momphoto;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private static final String SELECTED_STRING = "被选图片";
	private static final String UNSELECTED_STRING = "已选图片";
	protected static final String SELECTED_SIGN ="选择标记";
	protected static final String PIC_ID = "Mother ID";
	protected static final String PIC_RATE = "Mother Rate";

	protected static ArrayList<Integer> muPicId = new ArrayList<Integer>(Arrays.asList(
			R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4,
			R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8,
			R.drawable.p11, R.drawable.p12/*,
			R.drawable.p13, R.drawable.p14, R.drawable.p15, R.drawable.p16,
			R.drawable.p17, R.drawable.p18, R.drawable.p19, R.drawable.p20,
			R.drawable.p21, R.drawable.p22, R.drawable.p23*/));
	protected static float[] muPicRate = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	protected static ArrayList<Integer> msPicId = new ArrayList<Integer>(Arrays.asList(R.drawable.p1));
	protected static float[] msPicRate = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final ActionBar tabBar = getActionBar();
		tabBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		GridFragment selectedPicFrag = new GridFragment();

		//extend to get mPicID & mPicRate from file or database
		Bundle args = new Bundle();
		args.putIntegerArrayList(PIC_ID, muPicId);
		//args.putFloatArray(PIC_RATE, muPicRate);
		args.putInt(SELECTED_SIGN, 1);
		selectedPicFrag.setArguments(args);

		tabBar.addTab(tabBar.newTab().setText(SELECTED_STRING)
				.setTabListener(new TabActionListener(selectedPicFrag)));
		

		GridFragment unselectedPicFrag = new GridFragment();

		//extend to get mPicID & mPicRate from file or database
		args = new Bundle();
		args.putIntegerArrayList(PIC_ID, msPicId);
		//args.putFloatArray(PIC_RATE, msPicRate);
		args.putInt(SELECTED_SIGN, 0);
		unselectedPicFrag.setArguments(args);

		tabBar.addTab(tabBar.newTab().setText(UNSELECTED_STRING)
				.setTabListener(new TabActionListener(unselectedPicFrag)));	
	}
}
