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

public class SimplePopupWindow extends Fragment implements OnClickListener {
	
	private static final String TAG = "SimplePopupWindow";
	private Activity mActivity;
	private PopupWindow pw;
	private PopupWindow mPopupWindow1;
	private PopupWindow mPopupWindow2;
	private PopupWindow mPopupWindow3;
	private PopupWindow mPopupWindow4;
	private Button btn_showSimplePopupWindow1;
	private Button btn_showSimplePopupWindowFromBottom;
	private Button btn_showSimplePopupWindowFromLeft;
	private Button btn_showSimplePopupWindowFromBtnTop;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		mActivity = this.getActivity();
		View view = inflater.inflate(R.layout.fm_popupwindow_simple_popupwindow, container, false);
		
		btn_showSimplePopupWindow1 = (Button) view.findViewById(R.id.btn_showSimplePopupWindow1);
		btn_showSimplePopupWindow1.setOnClickListener(this);
		btn_showSimplePopupWindowFromBottom = (Button) view.findViewById(R.id.btn_showSimplePopupWindowFromBottom);
		btn_showSimplePopupWindowFromBottom.setOnClickListener(this);
		btn_showSimplePopupWindowFromLeft = (Button) view.findViewById(R.id.btn_showSimplePopupWindowFromLeft);
		btn_showSimplePopupWindowFromLeft.setOnClickListener(this);
		btn_showSimplePopupWindowFromBtnTop = (Button) view.findViewById(R.id.btn_showSimplePopupWindowFromBtnTop);
		btn_showSimplePopupWindowFromBtnTop.setOnClickListener(this);
		
		// 获得PopupWindow需要的视图
		View popupWindow = inflater.inflate(R.layout.simple_popupwindow1, null);
		// 设置PopupWindpw对话框内的内容
		TextView tv_simplePopupWindow = (TextView) popupWindow.findViewById(R.id.tv_simplePopupWindow);
		tv_simplePopupWindow.setText("halleluyah");

		
		
		// 设置PopupWindow1
		// 实例化PopupWindow，设置能够获得焦点
		// 在代码中设置PopupWindow的大小，xml中第一层view的宽高将被忽略
		mPopupWindow1 = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		// 以下四行代码用于设置点击空白处隐藏PopupWindow
		mPopupWindow1.setTouchable(true);
		mPopupWindow1.setOutsideTouchable(true);
		// 要实现点击PopupWindow之外的地方让其自身消失，需要为其设置背景，下面几种方法等效
//		mPopupWindow1.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
//		mPopupWindow1.setBackgroundDrawable(new ColorDrawable(0x00000000));
		mPopupWindow1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
		
		// 设置PopupWindow2
		mPopupWindow2 = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		mPopupWindow2.setTouchable(true);
		mPopupWindow2.setOutsideTouchable(true);
		mPopupWindow2.setBackgroundDrawable(new ColorDrawable(0x00000000));
		mPopupWindow2.setAnimationStyle(R.style.anim_menu_bottombar);// 设置从底部划入划出的动画
		mPopupWindow2.getContentView().setFocusableInTouchMode(true);
		// PopupWindow弹出后，所有的触屏和物理按键点击事件都由PopupWindow处理
		// 如果PopupWindow中有Editor，Focusable的值必须为true
		mPopupWindow2.getContentView().setFocusable(true);
		mPopupWindow2.getContentView().setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0 && event.getAction() == KeyEvent.ACTION_DOWN){
					if(mPopupWindow2 != null && mPopupWindow2.isShowing()){
						mPopupWindow2.dismiss();
					}
					return true;
				}
				return false;
			}
		});
		
		
		// 设置PopupWindow3
		mPopupWindow3 = new PopupWindow(popupWindow, (int) getResources().getDimension(R.dimen.main_menu_width), LayoutParams.WRAP_CONTENT, true);
		mPopupWindow3.setTouchable(true);
		mPopupWindow3.setOutsideTouchable(true);
		mPopupWindow3.setBackgroundDrawable(new ColorDrawable(0x00000000));
		mPopupWindow3.setAnimationStyle(R.style.anim_menu_leftbar);// 设置从底部划入划出的动画
		mPopupWindow3.getContentView().setFocusableInTouchMode(true);
		// PopupWindow弹出后，所有的触屏和物理按键点击事件都由PopupWindow处理
		// 如果PopupWindow中有Editor，Focusable的值必须为true
		mPopupWindow3.getContentView().setFocusable(true);
		mPopupWindow3.getContentView().setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0 && event.getAction() == KeyEvent.ACTION_DOWN){
					if(mPopupWindow2 != null && mPopupWindow2.isShowing()){
						mPopupWindow2.dismiss();
					}
					return true;
				}
				return false;
			}
		});
		
		
		// 设置PopupWindow4
		mPopupWindow4 = new PopupWindow(popupWindow, 300, 400, true);
		mPopupWindow4.setTouchable(true);
		mPopupWindow4.setOutsideTouchable(true);
		mPopupWindow4.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
		
		return view;
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.btn_showSimplePopupWindow1:
			// 让PopupWindow以向下弹出的形式展示出来，可以为其设置偏移值
			mPopupWindow1.showAsDropDown(v);
			break;
		case R.id.btn_showSimplePopupWindowFromBottom:
			// 让PopupWindow从屏幕下端滑入
			mPopupWindow2.showAtLocation(mActivity.findViewById(R.id.fm_popupwindowSimplePopupwindow), Gravity.BOTTOM, 0, 0);
			break;
		case R.id.btn_showSimplePopupWindowFromLeft:
			// 让PopupWindow从屏幕左侧滑入
			mPopupWindow3.showAtLocation(mActivity.findViewById(R.id.fm_popupwindowSimplePopupwindow), Gravity.LEFT, 0, 0);
			break;
		case R.id.btn_showSimplePopupWindowFromBtnTop:
//			mPopupWindow4.showAsDropDown(v);
//			mPopupWindow4.showAtLocation(mActivity.findViewById(R.id.fm_popupwindowSimplePopupwindow), Gravity.LEFT, 0, 0);
			// 获得该控件在屏幕上的起始坐标点
			int[] location = new int[2];
			v.getLocationOnScreen(location);
			// 获得屏幕分辨率
			int[] screenDpi = TransferBasicInfo.getInstance().getDpi();
			mPopupWindow4.showAtLocation(v, Gravity.NO_GRAVITY, screenDpi[0] - mPopupWindow4.getWidth() - 4, location[1] - mPopupWindow4.getHeight());
			break;
		}
		
	}
}
