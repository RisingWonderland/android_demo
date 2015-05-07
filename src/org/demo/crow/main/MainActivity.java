package org.demo.crow.main;

import java.io.File;

import org.crow.android.utils.BasicUtils;
import org.crow.android.utils.CommonUtils;
import org.crow.android.utils.FileUtils;
import org.crow.android.utils.CacheBasicInfo;
import org.demo.crow.R;
import org.demo.crow.application.MyApplication;
import org.demo.crow.broadcastreceiver.DeviceBtnReceiver;
import org.demo.crow.broadcastreceiver.WpsReceiver;
import org.demo.crow.skin.SkinUtils;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * AndroidDemo演示程序的入口
 * @author Crow
 *
 */
public class MainActivity extends ActionBarActivity {
	
	private static final String TAG = "MainActivity";
	private MyApplication app;
	private Resources res;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private DeviceBtnReceiver deviceBtnReceiver;
	private WpsReceiver wpsReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		SkinUtils.setSkinOnActivityCreated(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		app = (MyApplication) this.getApplication();
		res = this.getResources();
		fm = getFragmentManager();
		// 获得中转站
		CacheBasicInfo tbc = CacheBasicInfo.getInstance();
		// 获得设备屏幕分辨率，缓存至中转站
		tbc.setDpi(BasicUtils.getScreenDpi(this));
		// 缓存双页模式下，主界面左侧列表区域的宽度
		tbc.setMainMenuWidth((int)res.getDimension(R.dimen.main_menu_width));
		
		// 修改Application中的数据
		app.setGlobalInfo("from MainActivity");
		
		// 检查SDCard根目录下是否存在名为android.crow.demo的文件夹，没有则创建
		FileUtils.createDir(new File(app.getSdcardPath() + "/android.crow.demo/"));
		
		// 注册广播监听器
		registerReceiver();
	}
	
	/**
	 * 注册所有需要的监听器
	 */
	private void registerReceiver() {
		deviceBtnReceiver = new DeviceBtnReceiver();
		IntentFilter deviceBtnFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		this.registerReceiver(deviceBtnReceiver, deviceBtnFilter);
		
		wpsReceiver = new WpsReceiver();
		IntentFilter wpsFilter = new IntentFilter();
		wpsFilter.addAction("cn.wps.moffice.file.save");
		wpsFilter.addAction("cn.wps.moffice.file.close");
		this.registerReceiver(wpsReceiver, wpsFilter);
	}
	/**
	 * 注销所有需要的监听器
	 */
	private void unregisterReceiver() {
		if(deviceBtnReceiver != null){
			this.unregisterReceiver(deviceBtnReceiver);
		}
		if(wpsReceiver != null){
			this.unregisterReceiver(wpsReceiver);
		}
	}

	@Override
	protected void onStart() {
		Log.i(TAG, "onStart");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
	}

	@Override
	protected void onRestart() {
		Log.i(TAG, "onCreate");
		super.onRestart();
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
		unregisterReceiver();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	@Override
	public void onBackPressed() {
		Toast.makeText(MainActivity.this, "返回键被按下", Toast.LENGTH_SHORT).show();
//		super.onBackPressed();// 注释本行代码，阻止程序返回
	}
	*/
	
	/*
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			if(event.getAction() == KeyEvent.ACTION_DOWN){
				Toast.makeText(MainActivity.this, "返回键被按下", Toast.LENGTH_SHORT).show();
			}else if(event.getAction() == KeyEvent.ACTION_UP){
				Toast.makeText(MainActivity.this, "返回键被松开", Toast.LENGTH_SHORT).show();
			}
			return false;// 此处无论返回true还是false，都可以阻止退出当前activity
		}else if(event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP){
			// 按音量+按键，调用返回键功能，退出当前activity
			return super.dispatchKeyEvent(new KeyEvent(event.getAction(), KeyEvent.KEYCODE_BACK));
		}else if(event.getKeyCode() == KeyEvent.KEYCODE_MENU){
			Toast.makeText(MainActivity.this, "菜单键被触发", Toast.LENGTH_SHORT).show();
		}
		return super.dispatchKeyEvent(event);
	}
	*/

	/*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Dialog dialog = new AlertDialog.Builder(this)
				.setMessage("确认退出？")
				.setTitle("退出程序")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						android.os.Process.killProcess(android.os.Process.myPid());
						finish();
					}
				})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			})
			.create();
			dialog.show();
			return false;
		}else if(keyCode == KeyEvent.KEYCODE_MENU){
			Toast.makeText(MainActivity.this, "触发了菜单键", Toast.LENGTH_SHORT).show();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	*/
}
