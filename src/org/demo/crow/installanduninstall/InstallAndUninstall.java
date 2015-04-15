package org.demo.crow.installanduninstall;

import java.io.File;
import java.util.HashMap;

import org.crow.android.utils.BasicUtils;
import org.crow.android.utils.CommonUtils;
import org.demo.crow.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class InstallAndUninstall extends Fragment {
	
	private static final String TAG = "InstallAndUninstall";
	private Activity mActivity;
	private Button btn_checkIsRoot;
	private Button btn_standardInstallApp;
	private Button btn_standardUninstallApp;
	private Button btn_silentInstallApp;
	private Button btn_silentUninstallApp;
	
	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, "onAttach");
		super.onAttach(activity);
		this.mActivity = activity;
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
		
		HashMap<String, String> sdcardInfoMap = BasicUtils.getSDCardInfo();
		String sdcardRootPath = sdcardInfoMap.get("path");
		String apkName = "es.apk";
		final String apkPath = sdcardRootPath + "/" + apkName;
		final String appPackageName = "com.estrongs.android.pop";// ES文件浏览器的包名
		File targetFile = new File(apkPath);
		if(!targetFile.exists()){
			BasicUtils.showToast(mActivity, "目标apk文件不存在");
		}
		
		View view = inflater.inflate(R.layout.fm_install_and_uninstall, container, false);
		btn_checkIsRoot = (Button) view.findViewById(R.id.btn_checkIsRoot);
		btn_standardInstallApp = (Button) view.findViewById(R.id.btn_standardInstallApp);
		btn_standardUninstallApp = (Button) view.findViewById(R.id.btn_standardUninstallApp);
		btn_silentInstallApp = (Button) view.findViewById(R.id.btn_silentInstallApp);
		btn_silentUninstallApp = (Button) view.findViewById(R.id.btn_silentUninstallApp);
		
		btn_checkIsRoot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(CommonUtils.rootStatus()){
					BasicUtils.showToast(mActivity, "设备已经ROOT");
				}
			}
		});
		
		btn_standardInstallApp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonUtils.install(mActivity, apkPath);
			}
		});
		
		btn_standardUninstallApp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommonUtils.uninstall(mActivity, appPackageName);
			}
		});
		
		btn_silentInstallApp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int result = CommonUtils.silentInstall(apkPath);
				Log.i(TAG, result + "");
			}
		});
		
		btn_silentUninstallApp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int result = CommonUtils.silentUninstall(appPackageName);
				Log.i(TAG, result + "");
			}
		});
		
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
	
}
