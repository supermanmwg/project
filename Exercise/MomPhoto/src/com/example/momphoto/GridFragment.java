package com.example.momphoto;

import java.security.PublicKey;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
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

public class GridFragment extends Fragment implements OnItemClickListener {

	private final String TAG = "GridFragment";
	private ArrayList<Integer> mPicId = new ArrayList<Integer>(0);
	private float[] mPicRate;
	private int sign;
	private GridView mGridView;
	private GridListener  gridListener = null;
	
	public interface GridListener {
		 public void onGridListener(int id, int pos);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			gridListener = (GridListener)activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mPicId = getArguments().getIntegerArrayList(MainActivity.PIC_ID);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG,"onActivityCreated called");
		super.onActivityCreated(savedInstanceState);
		
		mGridView.setAdapter(new MotherAdapter(getActivity(), mPicId));
		mGridView.setOnItemClickListener(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG,"onCreateView called");
		View view = inflater.inflate(R.layout.gridfragment, container,false);
		mGridView  = (GridView) view.findViewById(R.id.gridview);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		gridListener.onGridListener((int) id, position);
		
	}
}
