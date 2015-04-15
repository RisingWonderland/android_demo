package org.demo.crow.bitmap;

import java.io.File;

import org.crow.android.utils.BitmapUtils;
import org.demo.crow.R;
import org.demo.crow.application.MyApplication;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class ThumbnailDemo extends Fragment {
	
	private static final String TAG = "ThumbnailDemo";
	private MyApplication app;
	private Button btn_getImageThumbnail;
	private Button btn_getVideoThumbnail;
	private ImageView iv_imageThumbnail;
	private ImageView iv_videoThumbnail;
	
	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, "onAttach");
		super.onAttach(activity);
		app = (MyApplication) activity.getApplication();
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
		
		View view = inflater.inflate(R.layout.fm_bitmap_thumbnail, container, false);
		initControls(view);
		setBtnClickEvent();
		
		return view;
	}

	private void setBtnClickEvent() {
		btn_getImageThumbnail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String targetImagePath = app.getSdcardPath() + "/android.crow.demo/hd1.jpg";
				Bitmap bitmap = BitmapUtils.getImageThumbnail(targetImagePath, 200, 200);
				iv_imageThumbnail.setImageBitmap(bitmap);
				// 将Bitmap输出到SDCard
				BitmapUtils.exportBitmap(bitmap, new File(app.getSdcardPath() + "/android.crow.demo/newImageThumbnail.jpg"));
			}
		});
		btn_getVideoThumbnail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String targetVideoPath = app.getSdcardPath() + "/android.crow.demo/电力安规2.mp4";
				iv_videoThumbnail.setImageBitmap(BitmapUtils.getVideoThumbnail(targetVideoPath, 200, 200, BitmapUtils.MINI_KIND));
			}
		});
	}

	private void initControls(View view) {
		btn_getImageThumbnail = (Button) view.findViewById(R.id.btn_getImageThumbnail);
		btn_getVideoThumbnail = (Button) view.findViewById(R.id.btn_getVideoThumbnail);
		iv_imageThumbnail = (ImageView) view.findViewById(R.id.iv_imageThumbnail);
		iv_videoThumbnail = (ImageView) view.findViewById(R.id.iv_videoThumbnail);
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
