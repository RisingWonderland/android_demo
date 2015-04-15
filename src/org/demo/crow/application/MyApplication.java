package org.demo.crow.application;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
/**
 * 自定义Application
 * @author Crow
 *
 */
public class MyApplication extends Application {
	
	private static final String TAG	= "MyApplication";
	
	private boolean sdcardMounted;
	private String sdcardPath;
	private String globalInfo;

	@Override
	public void onCreate() {
		Log.i(TAG, "--onCreate--");
		super.onCreate();
		// 确认当前设备SDCcard状态和路径
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			sdcardMounted = true;
			this.sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
		}
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}



	public boolean isSdcardMounted() {
		return sdcardMounted;
	}
	public String getSdcardPath() {
		return sdcardPath;
	}
	
	
	public String getGlobalInfo() {
		return globalInfo;
	}
	public void setGlobalInfo(String globalInfo) {
		this.globalInfo = globalInfo;
	}
}
