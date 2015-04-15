package org.demo.crow.skin;

import org.demo.crow.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class ChangeSkinActivity extends Activity {
	
	private static final String TAG = "ChangeSkinActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_skin_changeskin);
	}
}
