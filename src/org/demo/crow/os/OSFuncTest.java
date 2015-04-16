package org.demo.crow.os;

import org.crow.android.utils.BasicUtils;
import org.crow.android.utils.CommonUtils;
import org.crow.android.utils.OSUtils;
import org.demo.crow.R;

import android.app.Activity;
import android.app.Fragment;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class OSFuncTest extends Fragment {
	
	private static final String TAG = "OSFuncTest";
	private Activity mActivity;
	
	private Button btn_isNetworkConnected;
	private Button btn_getActiveConnection;
	private Button btn_isWifiConnected;
	private Button btn_isMobileConnected;
	private Button btn_showAllCommonApps;
	private Button btn_showRunningApps;
	private Button btn_showActiveApp;
	private Button btn_showRunningServices;
	
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
		View view = inflater.inflate(R.layout.fm_os_func_test, container, false);
		initControls(view);
		initControlsEvents();
		return view;
	}

	/**
	 * 初始化界面控件
	 * @author Crow
	 * @date 2015-4-13下午5:10:01
	 * @param view
	 */
	private void initControls(View view) {
		btn_isNetworkConnected = (Button) view.findViewById(R.id.btn_isNetworkConnected);
		btn_getActiveConnection = (Button) view.findViewById(R.id.btn_getActiveConnection);
		btn_isWifiConnected = (Button) view.findViewById(R.id.btn_isWifiConnected);
		btn_isMobileConnected = (Button) view.findViewById(R.id.btn_isMobileConnected);
		btn_showAllCommonApps = (Button) view.findViewById(R.id.btn_showAllCommonApps);
		btn_showRunningApps = (Button) view.findViewById(R.id.btn_showRunningApps);
		btn_showActiveApp = (Button) view.findViewById(R.id.btn_showActiveApp);
		btn_showRunningServices = (Button) view.findViewById(R.id.btn_showRunningServices);
	}

	/**
	 * 设置控件交互事件
	 * @author Crow
	 * @date 2015-4-13下午5:10:10
	 */
	private void initControlsEvents() {
		// 判断当前网络连接是否可用
		btn_isNetworkConnected.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isNetworkConnected = OSUtils.isNetworkConnected(mActivity);
				BasicUtils.showToast(mActivity, isNetworkConnected ? "网络连接可用" : "网络连接不可用");
			}
		});
		// 获得当前活动网络连接
		btn_getActiveConnection.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NetworkInfo ni = OSUtils.getActiveConnection(mActivity);
				
				BasicUtils.showToast(mActivity, ni.getTypeName());
			}
		});
		// WIFI是否开启
		btn_isWifiConnected.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isWifiConnected = OSUtils.isWifiConnected(mActivity);
				BasicUtils.showToast(mActivity, isWifiConnected ? "WIFI可用" : "WIFI不可用");
			}
		});
		// 移动网络是否开启
		btn_isMobileConnected.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isMobileConnected = OSUtils.isMobileConnected(mActivity);
				BasicUtils.showToast(mActivity, isMobileConnected ? "移动网络可用" : "移动网络不可用");
			}
		});
		// 展示所有非系统应用
		btn_showAllCommonApps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonUtils.showAllCommonApps(mActivity);
			}
		});
		// 展示运行时进程
		btn_showRunningApps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonUtils.showRunningApps(mActivity);
			}
		});
		// 展示活动进程
		btn_showActiveApp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonUtils.showActiveApp(mActivity);
			}
		});
		// 展示运行中服务
		btn_showRunningServices.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonUtils.showRunningServices(mActivity);
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
