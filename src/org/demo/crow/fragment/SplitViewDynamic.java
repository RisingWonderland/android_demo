package org.demo.crow.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.demo.crow.R;

public class SplitViewDynamic extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fm_fragment_split_view_dynamic, container, false);
		
		Fragment f1 = new SplitViewSub1();
		Fragment f2 = new SplitViewSub2();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.ll_fragmentSpliteViewSub1, f1).replace(R.id.ll_fragmentSpliteViewSub2, f2).commit();
		
		return view;
	}
}
