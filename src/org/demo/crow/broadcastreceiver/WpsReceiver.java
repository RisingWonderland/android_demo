package org.demo.crow.broadcastreceiver;

import java.util.Iterator;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WpsReceiver extends BroadcastReceiver {

	private static final String TAG = "WpsReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		
		if(action.equals("cn.wps.moffice.file.save")){
			Log.i(TAG, "wps has been saved");
			analyseIntentBundle(intent);
		}
		if(action.equals("cn.wps.moffice.file.close")){
			Log.i(TAG, "wps has been closed");
			analyseIntentBundle(intent);
		}
	}
	
	/**
	 * 解析Intent中的Bundle信息
	 * @author Crow
	 * @date 2015-4-10下午4:28:54
	 * @param intent
	 */
	private static void analyseIntentBundle(Intent intent){
		Bundle bundle = intent.getExtras();
		Log.i(TAG, bundle.size() + "");
		Set<String> set = bundle.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String key = it.next();
			Log.i(TAG, key + ": " + bundle.get(key).toString());
		}
	}

}
