package com.example.momphoto;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.momphoto.GridFragment.GridListener;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements GridListener {

	private final String TAG = "MainActivity";
	
	private static final String SELECTED_STRING = "被选图片";
	private static final String UNSELECTED_STRING = "已选图片";
	protected static final String SELECTED_SIGN ="选择标记";
	protected static final String PIC_ID = "Mother ID";
	protected static final String PIC_RATE = "Mother Rate";
	
	protected final static String IMAGEID = "image_id";
	protected final static String RARTINGBARSTAR = "ratingbarstar"; 
	protected final static String SIGN = "sign"; 
	protected final static String POSITION = "position"; 

	protected static ArrayList<Integer> muPicId = new ArrayList<Integer>(Arrays.asList(
			R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4,
			R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8,
			R.drawable.p11, R.drawable.p12/*,
			R.drawable.p13, R.drawable.p14, R.drawable.p15, R.drawable.p16,
			R.drawable.p17, R.drawable.p18, R.drawable.p19, R.drawable.p20,
			R.drawable.p21, R.drawable.p22, R.drawable.p23*/));
	protected static float[] muPicRate = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	private GridFragment selectedPicFrag;
	private ImageFragment imageFragment;
	
	private LinearLayout layout1;
	private LinearLayout layout2;
	private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		layout1 = (LinearLayout) findViewById(R.id.fragment_container);
		layout2 = (LinearLayout) findViewById(R.id.fragment_container_2);
		selectedPicFrag = new GridFragment();
		
		//extend to get mPicID & mPicRate from file or database
		Bundle args = new Bundle();
		args.putIntegerArrayList(PIC_ID, muPicId);
		selectedPicFrag.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, selectedPicFrag);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		fragmentManager.executePendingTransactions();
		fragmentManager.addOnBackStackChangedListener(new OnBackStackChangedListener() {
			
			@Override
			public void onBackStackChanged() {
				setLayout();
			}


		});
	}
	private void setLayout() {
		if(imageFragment.isAdded()) {
			layout2.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
			layout1.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 0));
		} else {
			layout1.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
			layout2.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 0));
		}
	}
	
	@Override
	public void onGridListener(int id, int pos) {
		// TODO Auto-generated method stub
		Bundle args = new Bundle();
		args.putInt(POSITION, pos);
		args.putInt(IMAGEID,  id);
		
		Log.i(TAG,"position = " + pos + " imageId = " + id);
		imageFragment = new ImageFragment();
		imageFragment.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container_2, imageFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		fragmentManager.executePendingTransactions();
	}
}
