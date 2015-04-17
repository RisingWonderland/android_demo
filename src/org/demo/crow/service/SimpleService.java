package org.demo.crow.service;

import org.crow.android.utils.CommonUtils;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class SimpleService extends Service {
	
	private static final String TAG	= "SimpleService";
	
	private static final int CYCLE = 10;
	private int cycleCount = 0;
	private Handler handler;
	private Runnable loopRun;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "onCreate");
		handler = new Handler();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.i(TAG, "onBind");
		return new MyBinder();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand");
		
		// 每隔一段时间执行某操作
		loopRun = new Runnable() {
			@Override
			public void run() {
				if(cycleCount < CYCLE){
					cycleCount++;
					CommonUtils.showActiveApp(SimpleService.this);
					handler.postDelayed(this, 5000);
				}else{
					handler.removeCallbacks(this);
					stopSelf();
				}
			}
		};
		handler.post(loopRun);
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(TAG, "onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		Log.i(TAG, "onRebind");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
		handler.removeCallbacks(loopRun);
	}
	
	
	class MyBinder extends Binder {
		public void showMyBinder(){
			Log.i(TAG, "I am Binder.");
		}
	}

}
