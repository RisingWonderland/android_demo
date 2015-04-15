package org.demo.crow.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DeviceBtnReceiver extends BroadcastReceiver {

	private static final String TAG = "DeviceBtnReceiver";
	private static final String SYSTEM_DIALOG_REASON = "reason";
	private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
	private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
	private static final String SYSTEM_DIALOG_REASON_LOCK = "lock";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){
			String eventReason = intent.getStringExtra(SYSTEM_DIALOG_REASON);
			if(eventReason != null){
				if(eventReason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)){
					Log.i(TAG, "用户触发了HOME键");
				}else if(eventReason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)){
					Log.i(TAG, "用户触发了近期程序键");
				}else if(eventReason.equals(SYSTEM_DIALOG_REASON_LOCK)){
					Log.i(TAG, "用户触发了锁屏、电源键");
				}
			}
		}
	}

}
