package com.example.momphoto;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

@SuppressWarnings("deprecation")
public class TabActionListener implements ActionBar.TabListener {

	private final Fragment mFragment;
	private final String TAG="TabListener";
	
	public TabActionListener(Fragment ft) {
		mFragment = ft;
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.i(TAG, "onTabSelected called");
		
		if (null != mFragment) {
			ft.replace(R.id.fragment_container, mFragment);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (null != mFragment) {
			ft.remove(mFragment);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}

}
