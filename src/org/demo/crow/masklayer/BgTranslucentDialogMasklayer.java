package org.demo.crow.masklayer;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.demo.crow.R;

/**
 * 背景为半透明效果的遮罩层
 * @author Crow
 *
 */
public class BgTranslucentDialogMasklayer implements OnClickListener {
	
	private Activity mActivity;
	private Dialog dialog;
	private ImageView iv_close;
	private int width = LayoutParams.WRAP_CONTENT;
	private int height = LayoutParams.WRAP_CONTENT;

	public BgTranslucentDialogMasklayer(Activity mActivity){
		super();
		this.mActivity = mActivity;
		createMaskLayer();
	}
	
	public BgTranslucentDialogMasklayer(Activity mActivity, int width,
			int height) {
		super();
		this.mActivity = mActivity;
		this.width = width;
		this.height = height;
		createMaskLayer();
	}

	/**
	 * 创建遮罩层
	 */
	private void createMaskLayer(){
		dialog = new Dialog(mActivity, R.style.masklayer1);
		LinearLayout popView = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.view_masklayer1, null);
		// 获得关闭遮罩层按钮，添加点击事件
		iv_close = (ImageView) popView.findViewById(R.id.iv_masklayer1_close);
		iv_close.setOnClickListener(this);
		dialog.setContentView(
			popView, 
			new LayoutParams(width, height)// 此处决定了遮罩层的宽高值
		);
		dialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);
	}

	@Override
	public void onClick(View v) {
		hide();
	}
	
	/**
	 * 展示自定义遮罩层
	 */
	public void show(){
		dialog.show();
	}
	
	/**
	 * 关闭自定义遮罩层
	 */
	public void hide(){
		dialog.hide();
	}
}
