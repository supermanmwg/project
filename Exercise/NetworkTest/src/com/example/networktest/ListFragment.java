package com.example.networktest;

import java.util.List;

import android.R.integer;
import android.app.Fragment;
import android.content.Loader.ForceLoadContentObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListFragment extends Fragment  {


	private static final String TAG = "ListFragment";
	@SuppressWarnings("unchecked")
	private List<String> arrayString ;
	private ListView mListView;
	

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.list_view, container, false);
		mListView = (ListView) view.findViewById(R.id.listview);
		Bundle args = getArguments();
		arrayString = (List<String>) args.getSerializable(MainActivity.LIST); 
		
		Log.i(TAG, "begin to receive!!");
		for(int i = 0; i< arrayString.size(); i++) {
			Log.i(TAG, arrayString.get(i));
		}
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, arrayString);
		mListView.setAdapter(adapter);
	}
	
}
