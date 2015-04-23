package org.demo.crow.activity;

import org.crow.android.utils.BasicUtils;
import org.demo.crow.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Activity B
 * @author Crow
 * @date 2015-4-23上午9:49:46
 */
public class B extends Activity {

	private static final String TAG = "Crow - Activity B";
	public static final String BACK_A_NAME = "infoBackAfromB";
	
	private Button btn_backAfromB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);
		
		initControls();
		initControlsEvents();
		
		BasicUtils.showToast(this, getIntent().getStringExtra(A.TO_B_NAME));
	}

	private void initControlsEvents() {
		btn_backAfromB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(B.this, A.class);
				intent.putExtra(BACK_A_NAME, "I sent you a message, A.");
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	private void initControls() {
		btn_backAfromB = (Button) this.findViewById(R.id.btn_backAfromB);
		
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
