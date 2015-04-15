package org.demo.crow.toastdialog;

import org.crow.android.utils.BasicUtils;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.demo.crow.R;

public class CustomToastDialog extends Fragment implements OnClickListener {
	
	private static final String TAG = "CustomToastDialog";
	private Activity mActivity;
	private Button btn_showBasicToast;
	private Button btn_showCustomCenterToast;
	private Button btn_showCustomOffsetToast;
	private Button btn_showCustomViewToast;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		mActivity = this.getActivity();
		
		View view = inflater.inflate(R.layout.fm_toastdialog_custom_toastdialog, container, false);
		btn_showBasicToast = (Button) view.findViewById(R.id.btn_showBasicToast);
		btn_showCustomCenterToast = (Button) view.findViewById(R.id.btn_showCustomCenterToast);
		btn_showCustomOffsetToast = (Button) view.findViewById(R.id.btn_showCustomOffsetToast);
		btn_showCustomViewToast = (Button) view.findViewById(R.id.btn_showCustomViewToast);
		btn_showBasicToast.setOnClickListener(this);
		btn_showCustomCenterToast.setOnClickListener(this);
		btn_showCustomOffsetToast.setOnClickListener(this);
		btn_showCustomViewToast.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_showBasicToast:
			BasicUtils.showToast(mActivity, "基本Toast，系统预设");
			break;
		case R.id.btn_showCustomCenterToast:
			Toast toast1 = Toast.makeText(mActivity, "位于屏幕中央", Toast.LENGTH_SHORT);
			toast1.setGravity(Gravity.CENTER, 0, 0);
			toast1.show();
			break;
		case R.id.btn_showCustomOffsetToast:
			Toast toast2 = Toast.makeText(mActivity, "带有偏移量", Toast.LENGTH_SHORT);
			// 参数1是Toast的起始位置，参数2是X轴方向偏移量，参数3是Y轴方向偏移量
			toast2.setGravity(Gravity.TOP|Gravity.LEFT, 50, 120);
			toast2.show();
			break;
		case R.id.btn_showCustomViewToast:
			LayoutInflater inflater = mActivity.getLayoutInflater();
			View view = inflater.inflate(R.layout.layout_custom_toast_view1, null);
			TextView tv = (TextView) view.findViewById(R.id.tv_customViewToast);
			tv.setText("自定义布局");
			// 一般情况应使用Toast的makeText方法输出Toast
			// 如果要使用new实例的方式输出Toast，必须确保调用setView方法设置Toast的布局
			Toast toast3 = new Toast(mActivity);
			toast3.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast3.setDuration(Toast.LENGTH_SHORT);
			toast3.setView(view);
			toast3.show();
			break;
		}
	}
}
