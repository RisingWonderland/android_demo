package org.demo.crow.popuplayer;

import org.demo.crow.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SimplePopupWindowActivity extends Activity {
	
	private static final String TAG = "SimplePopupWindowActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popupwindow_simple_popupwindow);
	}
}
