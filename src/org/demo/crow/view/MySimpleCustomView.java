package org.demo.crow.view;

import org.crow.android.utils.CacheBasicInfo;

import org.demo.crow.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
/**
 * 这是一个简单的用于演示的自定义视图类
 * 由于onDraw方法一般会被频繁调用执行，所以方法内new对象的操作越少越好。
 * 
 * @author Crow
 *
 */
public class MySimpleCustomView extends View {

	private static final String TAG = "MySimpleCustomView";
	private boolean is2Page;
	private static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;
	private static int MAIN_MENU_WIDTH;
	private Paint mPaint;
	private Rect mRect1;
	private Rect mRect2;
	private TypedArray mTypedArray;
	private int fillColor;
	private int textColor;
	private float textSize;
	
	public MySimpleCustomView(Context context) {
		super(context);
		init();
	}

	public MySimpleCustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.MySimpleView);
		// 获得自定义属性，属性值来自于xml，为防止xml中没有设定属性值，参数二设定默认值。
		// 如果xml中设置了自定义属性值，此处的默认值不起作用。
		fillColor = mTypedArray.getColor(R.styleable.MySimpleView_fillColor, Color.GREEN);
		textColor = mTypedArray.getColor(R.styleable.MySimpleView_textColor, Color.DKGRAY);
		textSize = mTypedArray.getDimension(R.styleable.MySimpleView_textSize, 40);
		mTypedArray.recycle();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {// onDraw方法经常会运行，所以new对象的操作越少越好
		super.onDraw(canvas);
		if(mRect1 == null || mRect2 == null){
			return;
		}
		
		// 设置画笔颜色
		mPaint.setColor(Color.MAGENTA);
		// 设置填充
		mPaint.setStyle(Style.FILL);
		// 绘制一个矩形，参数1和2是矩形左上角的坐标，参数3和4是矩形右下角的坐标
		canvas.drawRect(mRect1, mPaint);
		
		// 修改画笔颜色
		mPaint.setColor(Color.CYAN);
		mPaint.setTextSize(30);
		// 绘制一段文字
		canvas.drawText(TAG, 10, 50, mPaint);// 文字将被绘制在矩形内
		
		
		// 绘制另一个矩形和文字，相关属性来自自定义属性
		mPaint.setColor(fillColor);
		canvas.drawRect(mRect2, mPaint);
		mPaint.setColor(textColor);
		mPaint.setTextSize(textSize);
		canvas.drawText(TAG, 10, 160, mPaint);
	}
	
	private void init(){
		mPaint = new Paint();
		CacheBasicInfo tbc = CacheBasicInfo.getInstance();
		is2Page = tbc.isIs2Page();
		SCREEN_WIDTH = tbc.getDpi()[0];
		SCREEN_HEIGHT = tbc.getDpi()[1];
		MAIN_MENU_WIDTH = tbc.getMainMenuWidth();
		if(is2Page == true){
			mRect1 = new Rect(10, 10, SCREEN_WIDTH - (MAIN_MENU_WIDTH + 10), 110);// 绘制一个距上、左、右边距均为10dip的矩形
			mRect2 = new Rect(10, 120, SCREEN_WIDTH - (MAIN_MENU_WIDTH + 10), 220);
		}else{
			mRect1 = new Rect(10, 10, SCREEN_WIDTH - 10, 110);
			mRect2 = new Rect(10, 120, SCREEN_WIDTH - 10, 220);
		}
	}

}
