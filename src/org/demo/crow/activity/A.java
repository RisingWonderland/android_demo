package org.demo.crow.activity;

import java.io.File;

import org.crow.android.utils.BasicUtils;
import org.demo.crow.R;
import org.demo.crow.application.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Activity A
 * @author Crow
 * @date 2015-4-23上午9:49:16
 */
public class A extends Activity {
	
	private static final String TAG = "Crow - Activity A";
	private MyApplication app;
	public static final String TO_B_NAME = "infoFromA";
	private static final int TO_B_REQUEST_CODE = 1000;
	private static final int OPEN_GALLERY_REQUEST_CODE = 1001;
	
	private Button btn_fromA2B;
	private Button btn_openImgWaitForBack;
	private Button btn_openSpecifyImgFolder;
	
	private String SCAN_PATH;
	private String targetPath;
	private String[] imgNames;
	private MediaScannerConnection msconn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		
		initRes();
		initControls();
		initControlsEvents();
		
		
	}

	private void initControlsEvents() {
		btn_fromA2B.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(A.this, B.class);
				intent.putExtra(TO_B_NAME, "Please send me a message, B.");
				startActivityForResult(intent, TO_B_REQUEST_CODE);
			}
		});
		// Open Android default Gallery App
		btn_openImgWaitForBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, OPEN_GALLERY_REQUEST_CODE);
			}
		});
		// TODO: Open specific image folder using Android default Gallery App ---> failure
		btn_openSpecifyImgFolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File imgFolder = new File(app.getSdcardPath() + File.separator + SCAN_PATH);
				imgNames = imgFolder.list();
				Log.i(TAG, "Directory that contains the following content: ");
				for(int i=0,l=imgNames.length;i < l;i++){
					Log.i(TAG, imgNames[i]);
				}
				targetPath = app.getSdcardPath() + File.separator + SCAN_PATH + File.separator + imgNames[0];
				Log.i(TAG, SCAN_PATH);
				startScan();
				
			}
		});
	}

	private void startScan() {
		if(msconn != null){
			msconn.disconnect();
		}
		msconn = new MediaScannerConnection(this, new MediaScannerConnectionClient() {
			@Override
			public void onScanCompleted(String path, Uri uri) {
				try {
					Log.i(TAG, path);
					Log.i(TAG, uri.getPath());
					if(uri != null){
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setData(uri);
						startActivity(intent);
					}
				} finally {
					msconn.disconnect();
					msconn = null;
				}
			}
			
			@Override
			public void onMediaScannerConnected() {
				msconn.scanFile(targetPath, "image/*");
			}
		});
		msconn.connect();
	}

	private void initRes() {
		app = (MyApplication) this.getApplication();
		SCAN_PATH = getResources().getString(R.string.test_img_folder_path);
	}

	private void initControls() {
		btn_fromA2B = (Button) this.findViewById(R.id.btn_fromA2B);
		btn_openImgWaitForBack = (Button) this.findViewById(R.id.btn_openImgWaitForBack);
		btn_openSpecifyImgFolder = (Button) this.findViewById(R.id.btn_openSpecifyImgFolder);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case TO_B_REQUEST_CODE: 
			if(data != null){
				String str = data.getStringExtra(B.BACK_A_NAME);
				BasicUtils.showToast(A.this, str);
			}
			break;
		case OPEN_GALLERY_REQUEST_CODE:
			BasicUtils.showToast(A.this, "App Gallery is closed.");
			break;
		}
	}

	@Override
	protected void onStart() {
		Log.i(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		Log.i(TAG, "onRestart");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.i(TAG, "onNewIntent");
		super.onNewIntent(intent);
	}

	@Override
	protected void onPause() {
		Log.i(TAG, "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i(TAG, "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}
}
