package com.example.momphoto;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SignalStrength;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RatingBar;

public class GridFragment extends Fragment {

	private final String TAG = "GridFragment";
	private ArrayList<Integer> mPicId = new ArrayList<Integer>(0);
	private float[] mPicRate;
	private int sign;
	private GridView mGridView;
	
	protected final static String IMAGEID = "image_id";
	protected final static String RARTINGBARSTAR = "ratingbarstar"; 
	protected final static String SIGN = "sign"; 
	protected final static String POSITION = "position"; 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sign = getArguments().getInt(MainActivity.SELECTED_SIGN);
		mPicId = getArguments().getIntegerArrayList(MainActivity.PIC_ID);
	//	mPicRate = getArguments().getFloatArray(MainActivity.PIC_RATE);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG,"onActivityCreated called");
		super.onActivityCreated(savedInstanceState);
		
	
	mGridView.setAdapter(new MotherAdapter(getActivity(), mPicId, mPicRate, sign));
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra(SIGN, sign);
				intent.putExtra(POSITION, position);
				intent.putExtra(IMAGEID, (int)id);
				intent.setAction("com.example.momphoto.ImageActivity");
				startActivity(intent);
				/*
				ImageFragment imageFragment = new ImageFragment();
				imageFragment.setArguments(args);
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.add(R.id.fragment_container, imageFragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
				fragmentManager.executePendingTransactions();
				*/
			}
		});
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gridfragment, container,false);
		mGridView  = (GridView) view.findViewById(R.id.gridview);
		return view;
	}
}
