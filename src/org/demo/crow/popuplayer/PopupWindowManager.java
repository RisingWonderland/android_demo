package org.demo.crow.popuplayer;

import java.util.HashMap;
import java.util.Map;

import org.crow.android.utils.CacheBasicInfo;

import org.demo.crow.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 创建PopupWindow的工具类
 * 可在指定控件的左上、中上、右上、正左、正右、左下、中下、中右
 * 屏幕的上端、下端、左侧、右侧展示PopupWindow弹出框。
 * 
 * LayoutParams.MATCH_PARENT的值是-1
 * LayoutParams.WRAP_CONTENT的值是-2
 * @author Crow
 *
 */
public class PopupWindowManager {
	
	private static final String TAG = "PopupWindowUtils";
	private PopupWindow mPW;
	// 缓存PopupWindowUtils创建的PW，如果调用者本次要创建的PW之前已经创建过，直接从缓存中获取
	private Map<Integer, PopupWindow> pwCacheList = new HashMap<Integer, PopupWindow>();
	
	private View pwView;// PW的布局视图
	private int pwWidth;// PW的宽度
	private int pwHeight;// PW的高度
	private boolean isFocusable = true;// PW能否获取焦点，默认为true
	private boolean isOutsideTouchDismiss = true;// 点击PW之外的其余部分，是否隐藏PW，默认为true
	private int spacing = 0;// PW与控件的间距，默认为0。如果要展示依附于屏幕边缘的PW，该值无效。
	private boolean isRelative = true;// PW与控件之间的位置是否是相对的，默认为true。如果PW的显示位置超出屏幕边界，PW将在用户指定位置相对的另一端展示出来。如果要展示依附于屏幕边缘的PW，该值无效。
	private Integer tag;// PW标记，该标记作为键，与生成的PW以键值对的形式存入缓存列表中
	private int pwAnimationStyle;// PW的展示、隐藏动画
	private int[] screenDpi;

	public PopupWindowManager(View pwView, int pwWidth, int pwHeight, 
			boolean isFocusable, boolean isOutsideTouchDismiss) {
		super();
		this.pwView = pwView;
		this.pwWidth = pwWidth;
		this.pwHeight = pwHeight;
		this.isFocusable = isFocusable;
		this.isOutsideTouchDismiss = isOutsideTouchDismiss;
		initPW();
	}

	public PopupWindowManager(View pwView, int pwWidth, int pwHeight, 
			boolean isFocusable, boolean isOutsideTouchDismiss, 
			int spacing) {
		super();
		this.pwView = pwView;
		this.pwWidth = pwWidth;
		this.pwHeight = pwHeight;
		this.isFocusable = isFocusable;
		this.isOutsideTouchDismiss = isOutsideTouchDismiss;
		this.spacing = spacing;
		initPW();
	}
	
	public PopupWindowManager(View pwView, int pwWidth, int pwHeight, 
			boolean isFocusable, boolean isOutsideTouchDismiss, 
			int spacing, boolean isRelative) {
		super();
		this.pwView = pwView;
		this.pwWidth = pwWidth;
		this.pwHeight = pwHeight;
		this.isFocusable = isFocusable;
		this.isOutsideTouchDismiss = isOutsideTouchDismiss;
		this.spacing = spacing;
		this.isRelative = isRelative;
		initPW();
	}

	/**
	 * 初始化：
	 * 默认动画、屏幕分辨率
	 */
	private void initPW() {
		this.pwAnimationStyle = -1;
		this.screenDpi = CacheBasicInfo.getInstance().getDpi();
	}
	
	/**
	 * 强制重新生成PW
	 * 根据tag，从缓存中清除相应的PW，即可在调用展示方法时生成全新的PW
	 * @param tag
	 */
	public void forceReset(Integer tag){
		for(Map.Entry<Integer, PopupWindow> entry : pwCacheList.entrySet()){
			Log.i(TAG, entry.getKey() + " / " + entry.getValue());
		}
		pwCacheList.remove(tag);
		for(Map.Entry<Integer, PopupWindow> entry : pwCacheList.entrySet()){
			Log.i(TAG, entry.getKey() + " / " + entry.getValue());
		}
	}
	
	/**
	 * 清空PW缓存
	 */
	public void clearCache(){
		for(Map.Entry<Integer, PopupWindow> entry : pwCacheList.entrySet()){
			Log.i(TAG, entry.getKey() + " / " + entry.getValue());
		}
		pwCacheList.clear();
		for(Map.Entry<Integer, PopupWindow> entry : pwCacheList.entrySet()){
			Log.i(TAG, entry.getKey() + " / " + entry.getValue());
		}
	}
	
	/**
	 * 直接调用PopupWindow的showAsDropDown(View anchor)方法
	 * @param view 让PopupWindow依附的控件
	 */
	public void showAsDropDown(View view){
		mPW = getPW(0);
		mPW.showAsDropDown(view);
	}
	/**
	 * 直接调用PopupWindow的showAsDropDown(View anchor, int xoff, int yoff)方法
	 * @param view 让PopupWindow依附的控件
	 */
	public void showAsDropDown(View view, int x, int y){
		mPW = getPW(0);
		mPW.showAsDropDown(view, x, y);
	}
	/**
	 * 直接调用PopupWindow的showAsDropDown(View anchor, int xoff, int yoff, int gravity)方法
	 * @param view 让PopupWindow依附的控件
	 */
	public void showAsDropDown(View view, int x, int y, int gravity){
		mPW = getPW(0);
		mPW.showAsDropDown(view, x, y, gravity);
	}

	/**
	 * 创建一个从屏幕顶部滑入的PopupWindow
	 * @param screenView 一个全屏范围的view，例如主activity，或全屏范围的fragment
	 */
	public void showPopupWindowFromScreenTopCenter(View screenView){
		// 从缓存中寻找是否创建过tag相同的PW，如果存在，返回
		mPW = pwCacheList.get(tag);
		
		if(mPW == null){
			mPW = new PopupWindow(pwView, pwWidth, pwHeight, isFocusable);
			if(this.isOutsideTouchDismiss){
				mPW.setTouchable(true);
				mPW.setOutsideTouchable(true);
				mPW.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			}
			if(pwAnimationStyle != -1){
				mPW.setAnimationStyle(pwAnimationStyle);
			}else{
				mPW.setAnimationStyle(R.style.anim_menu_topbar);
			}
			pwCacheList.put(tag, mPW);
		}else{
//			Log.i(TAG, "该PW被创建过");
//			for(Map.Entry<Integer, PopupWindow> entry : pwCacheList.entrySet()){
//				Log.i(TAG, entry.getKey() + " / " + entry.getValue());
//			}
		}
		mPW.showAtLocation(screenView, Gravity.TOP, 0, 0);
	}
	/**
	 * 创建一个从屏幕底部滑入的PopupWindow
	 * @param screenView 一个全屏范围的view，例如主activity，或全屏范围的fragment
	 */
	public void showPopupWindowFromScreenBottomCenter(View screenView){
		mPW = getPW(R.style.anim_menu_bottombar);
		mPW.showAtLocation(screenView, Gravity.BOTTOM, 0, 0);
	}
	/**
	 * 创建一个从屏幕左侧滑入的PopupWindow
	 * @param screenView 一个全屏范围的view，例如主activity，或全屏范围的fragment
	 */
	public void showPopupWindowFromScreenLeftCenter(View screenView){
		mPW = getPW(R.style.anim_menu_leftbar);
		mPW.showAtLocation(screenView, Gravity.LEFT, 0, 0);
	}
	/**
	 * 创建一个从屏幕右侧滑入的PopupWindow
	 * @param screenView 一个全屏范围的view，例如主activity，或全屏范围的fragment
	 */
	public void showPopupWindowFromScreenRightCenter(View screenView){
		mPW = getPW(R.style.anim_menu_rightbar);
		mPW.showAtLocation(screenView, Gravity.RIGHT, 0, 0);
	}
	/**
	 * 创建一个在屏幕中央展示的PopupWindow
	 * @param screenView 一个全屏范围的view，例如主activity，或全屏范围的fragment
	 */
	public void showPopupWindowOnScreenCenter(View screenView){
		mPW = getPW(R.style.anim_fade_pop);
		// 计算出PW的起始坐标点
		int pwStartXaxis = (this.screenDpi[0] - this.pwWidth) / 2;
		int pwStartYaxis = (this.screenDpi[1] - this.pwHeight) / 2;
		mPW.showAtLocation(screenView, Gravity.NO_GRAVITY, pwStartXaxis, pwStartYaxis);
	}
	/**
	 * 创建一个覆盖全屏幕的PopupWindow
	 * @param screenView 一个全屏范围的view，例如主activity，或全屏范围的fragment
	 */
	public void showPopupWindowCoverScreen(View screenView){
		mPW = getPW(R.style.anim_fade_pop);
		mPW.showAtLocation(screenView, Gravity.NO_GRAVITY, this.pwWidth, this.pwHeight);
	}
	
	/**
	 * 创建一个位于指定控件左上方的PW
	 * @param view 让PW依附的控件
	 */
	public void showPopupWindowOnTopLeft(View view){
		showPopupWindowOnTopLeft(view, 0, 0);
	}
	/**
	 * 创建一个位于指定控件左上方的PW
	 * @param view 让PW依附的控件
	 * @param x X轴偏移量
	 * @param y Y轴偏移量
	 */
	public void showPopupWindowOnTopLeft(View view, int x, int y){
		mPW = getPW(R.style.anim_fade_pop);
		// 获得该控件在屏幕上的起始坐标点
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		// 如果PW的上边框超出了屏幕边界，将其展示于左下方
		// 计算PW的上边框在y轴上的值，与0进行比较
		int pwBorderTopYaxis = location[1] - mPW.getHeight() - this.spacing + y;
		if(pwBorderTopYaxis < 0){
			showPopupWindowOnBottomLeft(view, x, y);
		}
		mPW.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + x, location[1] - mPW.getHeight() - this.spacing + y);
	}
	/**
	 * 创建一个位于指定控件正上方的PW
	 * @param view 让PW依附的控件
	 */
	public void showPopupWindowOnTop(View view){
		showPopupWindowOnTop(view, 0, 0);
	}
	/**
	 * 创建一个位于指定控件正上方的PW
	 * @param view 让PW依附的控件
	 * @param x X轴偏移量
	 * @param y Y轴偏移量
	 */
	public void showPopupWindowOnTop(View view, int x, int y){
		mPW = getPW(R.style.anim_fade_pop);
		// 获得该控件在屏幕上的起始坐标点
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int pwBorderTopYaxis = location[1] - mPW.getHeight() - this.spacing + y;
		if(pwBorderTopYaxis < 0){
			showPopupWindowOnBottom(view, x, y);
		}
		mPW.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + (view.getWidth() - this.pwWidth) / 2 + x, location[1] - mPW.getHeight() - this.spacing + y);
	}
	/**
	 * 创建一个位于指定控件右上方的PW
	 * @param view 让PW依附的控件
	 */
	public void showPopupWindowOnTopRight(View view){
		showPopupWindowOnTopRight(view, 0, 0);
	}
	/**
	 * 创建一个位于指定控件右上方的PW
	 * @param view 让PW依附的控件
	 * @param x X轴偏移量
	 * @param y Y轴偏移量
	 */
	public void showPopupWindowOnTopRight(View view, int x, int y){
		mPW = getPW(R.style.anim_fade_pop);
		// 获得该控件在屏幕上的起始坐标点
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int pwBorderTopYaxis = location[1] - mPW.getHeight() - this.spacing + y;
		if(pwBorderTopYaxis < 0){
			showPopupWindowOnBottomRight(view, x, y);
		}
		mPW.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.getWidth() - this.pwWidth + x, location[1] - mPW.getHeight() - this.spacing + y);
	}
	/**
	 * 创建一个位于指定控件正左方的PW
	 * @param view 让PW依附的控件
	 */
	public void showPopupWindowOnLeft(View view){
		showPopupWindowOnLeft(view, 0, 0);
	}
	/**
	 * 创建一个位于指定控件正左方的PW
	 * @param view 让PW依附的控件
	 * @param x X轴偏移量
	 * @param y Y轴偏移量
	 */
	public void showPopupWindowOnLeft(View view, int x, int y){
		mPW = getPW(R.style.anim_fade_pop);
		// 获得该控件在屏幕上的起始坐标点
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		// 如果PW的左边框超出了屏幕边界，将其展示于正右方
		// 计算PW的左边框在x轴上的值，与0进行比较
		int pwBorderLeftXaxis = location[0] - this.pwWidth - this.spacing + x;
		if(pwBorderLeftXaxis < 0){
			showPopupWindowOnRight(view, x, y);
		}
		mPW.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - this.pwWidth - this.spacing + x, location[1] + (view.getHeight() - this.pwHeight) / 2 + y);
	}
	/**
	 * 创建一个位于指定控件正右方的PW
	 * @param view 让PW依附的控件
	 */
	public void showPopupWindowOnRight(View view){
		showPopupWindowOnRight(view, 0, 0);
	}
	/**
	 * 创建一个位于指定控件正右方的PW
	 * @param view 让PW依附的控件
	 * @param x X轴偏移量
	 * @param y Y轴偏移量
	 */
	public void showPopupWindowOnRight(View view, int x, int y){
		mPW = getPW(R.style.anim_fade_pop);
		// 获得该控件在屏幕上的起始坐标点
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int pwBorderRightXaxis = location[0] + view.getWidth() + this.pwWidth + this.spacing + x;
		if(pwBorderRightXaxis > screenDpi[0]){
			showPopupWindowOnLeft(view, x, y);
		}
		mPW.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.getWidth() + this.spacing + x, location[1] + (view.getHeight() - this.pwHeight) / 2 + y);
	}
	/**
	 * 创建一个位于指定控件左下方的PW
	 * @param view 让PW依附的控件
	 */
	public void showPopupWindowOnBottomLeft(View view){
		showPopupWindowOnBottomLeft(view, 0, 0);
	}
	/**
	 * 创建一个位于指定控件左下方的PW
	 * @param view 让PW依附的控件
	 * @param x X轴偏移量
	 * @param y Y轴偏移量
	 */
	public void showPopupWindowOnBottomLeft(View view, int x, int y){
		mPW = getPW(R.style.anim_fade_pop);
		// 获得该控件在屏幕上的起始坐标点
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		// 如果PW的下边框超出了屏幕边界，将其展示于左上方
		// 计算PW的下边框在y轴上的值，与屏幕分辨率的高度值进行比较
		int pwBorderBottomYaxis = location[1] + view.getHeight() + this.spacing + this.pwHeight;
		if(pwBorderBottomYaxis > screenDpi[1]){
			showPopupWindowOnTopLeft(view, x, y);
		}
		mPW.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + x, location[1] + view.getHeight() + this.spacing + y);
	}
	/**
	 * 创建一个位于指定控件正下方的PW
	 * @param view 让PW依附的控件
	 */
	public void showPopupWindowOnBottom(View view){
		showPopupWindowOnBottom(view, 0, 0);
	}
	/**
	 * 创建一个位于指定控件正下方的PW
	 * @param view 让PW依附的控件
	 * @param x X轴偏移量
	 * @param y Y轴偏移量
	 */
	public void showPopupWindowOnBottom(View view, int x, int y){
		mPW = getPW(R.style.anim_fade_pop);
		// 获得该控件在屏幕上的起始坐标点
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int pwBorderBottomYaxis = location[1] + view.getHeight() + this.spacing + this.pwHeight;
		if(pwBorderBottomYaxis > screenDpi[1]){
			showPopupWindowOnTop(view, x, y);
		}
		mPW.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + (view.getWidth() - this.pwWidth) / 2 + x, location[1] + view.getHeight() + this.spacing + y);
	}
	/**
	 * 创建一个位于指定控件右下方的PW
	 * @param view 让PW依附的控件
	 */
	public void showPopupWindowOnBottomRight(View view){
		showPopupWindowOnBottomRight(view, 0, 0);
	}
	/**
	 * 创建一个位于指定控件右下方的PW
	 * @param view 让PW依附的控件
	 * @param x X轴偏移量
	 * @param y Y轴偏移量
	 */
	public void showPopupWindowOnBottomRight(View view, int x, int y){
		mPW = getPW(R.style.anim_fade_pop);
		// 获得该控件在屏幕上的起始坐标点
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int pwBorderBottomYaxis = location[1] + view.getHeight() + this.spacing + this.pwHeight;
		if(pwBorderBottomYaxis > screenDpi[1]){
			showPopupWindowOnTopRight(view, x, y);
		}
		mPW.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.getWidth() - this.pwWidth + x, location[1] + view.getHeight() + this.spacing + y);
	}
	
	/**
	 * 获得PW，该方法被众多展示PW的方法调用
	 * @return PopupWindow
	 */
	private PopupWindow getPW(int animStyle){
		PopupWindow pw = pwCacheList.get(tag);
		
		if(pw == null){
			pw = new PopupWindow(pwView, pwWidth, pwHeight, isFocusable);
			if(this.isOutsideTouchDismiss){
				pw.setTouchable(true);
				pw.setOutsideTouchable(true);
				pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			}
			if(pwAnimationStyle != -1){
				pw.setAnimationStyle(pwAnimationStyle);
			}else{
				pw.setAnimationStyle(animStyle);
			}
			pwCacheList.put(tag, pw);
		}
		return pw;
	}
	
	

	public Integer getTag() {
		return tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	public boolean isFocusable() {
		return isFocusable;
	}
	public void setFocusable(boolean isFocusable) {
		this.isFocusable = isFocusable;
	}
	public boolean isOutsideTouchDismiss() {
		return isOutsideTouchDismiss;
	}
	public void setOutsideTouchDismiss(boolean isOutsideTouchDismiss) {
		this.isOutsideTouchDismiss = isOutsideTouchDismiss;
	}
	public int getPwAnimationStyle() {
		return pwAnimationStyle;
	}
	public void setPwAnimationStyle(int pwAnimationStyle) {
		this.pwAnimationStyle = pwAnimationStyle;
	}
	public int getPwWidth() {
		return pwWidth;
	}
	public void setPwWidth(int pwWidth) {
		this.pwWidth = pwWidth;
	}
	public int getPwHeight() {
		return pwHeight;
	}
	public void setPwHeight(int pwHeight) {
		this.pwHeight = pwHeight;
	}
	public int getSpacing() {
		return spacing;
	}
	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}
	public boolean isRelative() {
		return isRelative;
	}
	public void setRelative(boolean isRelative) {
		this.isRelative = isRelative;
	}
}
