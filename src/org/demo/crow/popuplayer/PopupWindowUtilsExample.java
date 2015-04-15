package org.demo.crow.popuplayer;

import org.crow.android.utils.BasicUtils;
import org.crow.android.utils.TransferBasicInfo;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.demo.crow.R;

public class PopupWindowUtilsExample extends Fragment implements OnClickListener {
	
	private static final String TAG = "PopupWindowUtilsExample";
	private Activity mActivity;
	private View popupWindow;
	private PopupWindowManager pwm;

	private Button btn_showPWOnScreenTop;
	private Button btn_showPWOnScreenBottom;
	private Button btn_showPWOnScreenLeft;
	private Button btn_showPWOnScreenRight;
	private Button btn_showPWOnScreenCenter;
	private Button btn_showPWCoverScreen;
	
	private Button btn_showPWOnTopLeft;
	private Button btn_showPWOnTop;
	private Button btn_showPWOnTopRight;
	private Button btn_showPWOnLeft;
	private Button btn_showPWOnRight;
	private Button btn_showPWOnBottomLeft;
	private Button btn_showPWOnBottom;
	private Button btn_showPWOnBottomRight;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		mActivity = this.getActivity();
		View view = inflater.inflate(R.layout.fm_popupwindow_popupwindowutils_example, container, false);
		// 初始化控件
		initControls(view);
		
		// 获得PopupWindow需要的视图
		popupWindow = inflater.inflate(R.layout.simple_popupwindow1, null);
		TextView tv_simplePopupWindow = (TextView) popupWindow.findViewById(R.id.tv_simplePopupWindow);
		tv_simplePopupWindow.setText("halleluyah");
		pwm = new PopupWindowManager(popupWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true, true, 0);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.btn_showPWOnScreenTop:
			pwm.setTag(R.id.btn_showPWOnScreenTop);
			pwm.setPwWidth(LayoutParams.MATCH_PARENT);
			pwm.setPwHeight(LayoutParams.WRAP_CONTENT);
			pwm.showPopupWindowFromScreenTopCenter(mActivity.findViewById(R.id.fm_popupwindowPopupWindowUtilsExample));
			break;
		case R.id.btn_showPWOnScreenBottom:
			pwm.setTag(R.id.btn_showPWOnScreenBottom);
			pwm.setPwWidth(LayoutParams.MATCH_PARENT);
			pwm.setPwHeight(LayoutParams.WRAP_CONTENT);
			pwm.showPopupWindowFromScreenBottomCenter(mActivity.findViewById(R.id.fm_popupwindowPopupWindowUtilsExample));
			break;
		case R.id.btn_showPWOnScreenLeft:
			pwm.setTag(R.id.btn_showPWOnScreenLeft);
			pwm.setPwWidth(LayoutParams.WRAP_CONTENT);
			pwm.setPwHeight(LayoutParams.MATCH_PARENT);
			pwm.showPopupWindowFromScreenLeftCenter(mActivity.findViewById(R.id.fm_popupwindowPopupWindowUtilsExample));
			break;
		case R.id.btn_showPWOnScreenRight:
			pwm.setTag(R.id.btn_showPWOnScreenRight);
			pwm.setPwWidth(LayoutParams.WRAP_CONTENT);
			pwm.setPwHeight(LayoutParams.MATCH_PARENT);
			pwm.showPopupWindowFromScreenRightCenter(mActivity.findViewById(R.id.fm_popupwindowPopupWindowUtilsExample));
			break;
		case R.id.btn_showPWOnScreenCenter:
			pwm.setTag(R.id.btn_showPWOnScreenCenter);
			pwm.setPwWidth(360);
			pwm.setPwHeight(600);
			pwm.showPopupWindowOnScreenCenter(mActivity.findViewById(R.id.fm_popupwindowPopupWindowUtilsExample));
			break;
		case R.id.btn_showPWCoverScreen:
			pwm.setTag(R.id.btn_showPWCoverScreen);
			pwm.setPwWidth(LayoutParams.MATCH_PARENT);
			pwm.setPwHeight(LayoutParams.MATCH_PARENT);
			pwm.showPopupWindowCoverScreen(mActivity.findViewById(R.id.fm_popupwindowPopupWindowUtilsExample));
			break;
		case R.id.btn_showPWOnTopLeft:
			pwm.setTag(R.id.btn_showPWOnTopLeft);
			pwm.setPwWidth(250);
			pwm.setPwHeight(300);
			pwm.showPopupWindowOnTopLeft(v);
			break;
		case R.id.btn_showPWOnTop:
			pwm.setTag(R.id.btn_showPWOnTop);
			pwm.setPwWidth(250);
			pwm.setPwHeight(300);
			pwm.showPopupWindowOnTop(v);
			break;
		case R.id.btn_showPWOnTopRight:
			pwm.setTag(R.id.btn_showPWOnTopRight);
			pwm.setPwWidth(250);
			pwm.setPwHeight(300);
			pwm.showPopupWindowOnTopRight(v);
			break;
		case R.id.btn_showPWOnLeft:
			pwm.setTag(R.id.btn_showPWOnLeft);
			pwm.setPwWidth(250);
			pwm.setPwHeight(300);
			pwm.showPopupWindowOnLeft(v);
			break;
		case R.id.btn_showPWOnRight:
			pwm.setTag(R.id.btn_showPWOnRight);
			pwm.setPwWidth(250);
			pwm.setPwHeight(300);
			pwm.showPopupWindowOnRight(v);
			break;
		case R.id.btn_showPWOnBottomLeft:
			pwm.setTag(R.id.btn_showPWOnBottomLeft);
			pwm.setPwWidth(250);
			pwm.setPwHeight(300);
			pwm.showPopupWindowOnBottomLeft(v);
			break;
		case R.id.btn_showPWOnBottom:
			pwm.setTag(R.id.btn_showPWOnBottom);
			pwm.setPwWidth(250);
			pwm.setPwHeight(300);
			pwm.showPopupWindowOnBottom(v);
			break;
		case R.id.btn_showPWOnBottomRight:
			pwm.setTag(R.id.btn_showPWOnBottomRight);
			pwm.setPwWidth(250);
			pwm.setPwHeight(300);
			pwm.showPopupWindowOnBottomRight(v);
			break;
		}
		
	}

	/**
	 * 初始化控件
	 * @param view
	 */
	private void initControls(View view) {
		btn_showPWOnScreenTop = (Button) view.findViewById(R.id.btn_showPWOnScreenTop);
		btn_showPWOnScreenTop.setOnClickListener(this);
		btn_showPWOnScreenBottom = (Button) view.findViewById(R.id.btn_showPWOnScreenBottom);
		btn_showPWOnScreenBottom.setOnClickListener(this);
		btn_showPWOnScreenLeft = (Button) view.findViewById(R.id.btn_showPWOnScreenLeft);
		btn_showPWOnScreenLeft.setOnClickListener(this);
		btn_showPWOnScreenRight = (Button) view.findViewById(R.id.btn_showPWOnScreenRight);
		btn_showPWOnScreenRight.setOnClickListener(this);
		btn_showPWOnScreenCenter = (Button) view.findViewById(R.id.btn_showPWOnScreenCenter);
		btn_showPWOnScreenCenter.setOnClickListener(this);
		btn_showPWCoverScreen = (Button) view.findViewById(R.id.btn_showPWCoverScreen);
		btn_showPWCoverScreen.setOnClickListener(this);
		
		btn_showPWOnTopLeft = (Button) view.findViewById(R.id.btn_showPWOnTopLeft);
		btn_showPWOnTopLeft.setOnClickListener(this);
		btn_showPWOnTop = (Button) view.findViewById(R.id.btn_showPWOnTop);
		btn_showPWOnTop.setOnClickListener(this);
		btn_showPWOnTopRight = (Button) view.findViewById(R.id.btn_showPWOnTopRight);
		btn_showPWOnTopRight.setOnClickListener(this);
		btn_showPWOnLeft = (Button) view.findViewById(R.id.btn_showPWOnLeft);
		btn_showPWOnLeft.setOnClickListener(this);
		btn_showPWOnRight = (Button) view.findViewById(R.id.btn_showPWOnRight);
		btn_showPWOnRight.setOnClickListener(this);
		btn_showPWOnBottomLeft = (Button) view.findViewById(R.id.btn_showPWOnBottomLeft);
		btn_showPWOnBottomLeft.setOnClickListener(this);
		btn_showPWOnBottom = (Button) view.findViewById(R.id.btn_showPWOnBottom);
		btn_showPWOnBottom.setOnClickListener(this);
		btn_showPWOnBottomRight = (Button) view.findViewById(R.id.btn_showPWOnBottomRight);
		btn_showPWOnBottomRight.setOnClickListener(this);
	}
}
