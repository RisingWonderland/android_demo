package org.demo.crow.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.demo.crow.R;
import org.demo.crow.entity.Eclipse;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class BasicList extends Fragment {
	
	private static final String TAG = "BasicList";
	private Activity mActivity;
	private ListView lv_basicList;
	private List<Eclipse> mData;
	private BasicListAdapter mAdapter;
	
	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, "onAttach");
		super.onAttach(activity);
		this.mActivity = activity;
		// 初始化记录
		initListData();
		// 初始化适配器
		mAdapter = new BasicListAdapter(mActivity, mData);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		
		View view = inflater.inflate(R.layout.fm_list_basic_list, container, false);
		lv_basicList = (ListView) view.findViewById(R.id.lv_basicList);
		lv_basicList.setAdapter(mAdapter);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.i(TAG, "onStart");
		super.onStart();
	}

	@Override
	public void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	public void onPause() {
		Log.i(TAG, "onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.i(TAG, "onStop");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.i(TAG, "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		Log.i(TAG, "onDetach");
		super.onDetach();
	}
	
	/**
	 * 初始化列表数据
	 */
	private void initListData() {
		mData = new ArrayList<Eclipse>();
		mData.add(new Eclipse("Callisto", "3.2"));
		mData.add(new Eclipse("Callisto", "3.2"));
		mData.add(new Eclipse("Europa", "3.3"));
		mData.add(new Eclipse("Ganymede", "3.4"));
		mData.add(new Eclipse("Galileo", "3.5"));
		mData.add(new Eclipse("Helios", "3.6"));
		mData.add(new Eclipse("Indigo", "3.7"));
		mData.add(new Eclipse("Juno", "3.8和4.2"));
		mData.add(new Eclipse("Kepler", "4.3"));
		mData.add(new Eclipse("Luna", "4.4"));
		mData.add(new Eclipse("Mars", "4.5"));
	}
	
}
