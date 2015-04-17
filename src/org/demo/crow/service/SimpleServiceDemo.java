package org.demo.crow.service;

import org.demo.crow.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SimpleServiceDemo extends Fragment {
	
	private static final String TAG = "SimpleServiceDemo";
	private Activity mActivity;
	
	private Button btn_showActiveAppEvery5Minutes;
	private Button btn_stopSimpleService1;
	private Button btn_bindSimpleService1;
	private Button btn_unbindSimpleService1;
	
	private ServiceConnection simpleServiceConn;
	
	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, "onAttach");
		super.onAttach(activity);
		mActivity = activity;
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
		View view = inflater.inflate(R.layout.fm_simple_service_demo, container, false);
		initControls(view);
		initControlsEvents();
		
		return view;
	}

	private void initControls(View view) {
		btn_showActiveAppEvery5Minutes = (Button) view.findViewById(R.id.btn_showActiveAppEvery5Minutes);
		btn_stopSimpleService1 = (Button) view.findViewById(R.id.btn_stopSimpleService1);
		btn_bindSimpleService1 = (Button) view.findViewById(R.id.btn_bindSimpleService1);
		btn_unbindSimpleService1 = (Button) view.findViewById(R.id.btn_unbindSimpleService1);
		
	}
	
	private void initControlsEvents() {
		// 发起服务，每隔5分钟展示当前活动程序信息
		btn_showActiveAppEvery5Minutes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mActivity, SimpleService.class);
				mActivity.startService(intent);
			}
		});
		
		// 停止销毁服务1，并立即停止服务内的工作线程
		btn_stopSimpleService1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, SimpleService.class);
				mActivity.stopService(intent);
			}
		});
		
		// 实现针对于简单服务1的ServiceConnection
		simpleServiceConn = new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.i(TAG, "SimpleService unbind");
			}
			
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.i(TAG, "SimpleService bind");
				SimpleService.MyBinder myBinder = (SimpleService.MyBinder) service;
				myBinder.showMyBinder();
			}
		};
		
		// 绑定服务1
		btn_bindSimpleService1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, SimpleService.class);
				mActivity.bindService(intent, simpleServiceConn, Context.BIND_AUTO_CREATE);
			}
		});
		
		// 解绑服务1
		btn_unbindSimpleService1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mActivity.unbindService(simpleServiceConn);
			}
		});
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
	
}
