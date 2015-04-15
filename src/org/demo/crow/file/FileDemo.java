package org.demo.crow.file;

import java.io.File;

import org.crow.android.utils.BasicUtils;
import org.crow.android.utils.FileUtils;
import org.crow.android.utils.WpsUtils;
import org.demo.crow.R;
import org.demo.crow.application.MyApplication;
import org.demo.crow.main.MainActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FileDemo extends Fragment {
	
	private static final String TAG = "File";
	private MyApplication app;
	private Activity mActivity;
	private Button btn_openAImgFile;
	private Button btn_openAVideoFile;
	private Button btn_openAnAudioFile;
	private Button btn_openATextFile;
	private Button btn_openANonePostfixFile;
	private Button btn_openADocFile;
	private Button btn_openADocFileByWps;
	private Button btn_openAWebPage;
	private Button btn_openWebUrl;
	private Button btn_openAnApkFile;
	private Button btn_createShortcutOnHomeScreen;
	
	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, "onAttach");
		super.onAttach(activity);
		mActivity = activity;
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
		
		View view = inflater.inflate(R.layout.fm_file_filedemo, container, false);
		initControls(view);
		
		btn_openAImgFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// value资源文件中定义有一些文件后缀名，可以将支持的，能够打开的文件的后缀加入其中
				// 之后在需要时，如以下代码所示使用getResources().getStringArray()方法获取String数组
				// 判断目标文件的后缀在数组中是否出现，如果出现了，就调用获取intent的方法
				File file = new File(app.getSdcardPath() + "/android.crow.demo/1.jpg");
				String fileName = file.getName();
				if(checkPostfixInArray(fileName, getResources().getStringArray(R.array.image))){
					Intent intent = FileUtils.getFileIntent(file);
					startActivity(intent);
				}else{
					BasicUtils.showToast(mActivity, "不能打开该文件");
				}
			}
		});
		
		btn_openAVideoFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 下面代码所示的方法更简单，但执行的门槛降低，恐怕安全性不足。逻辑如下：
				// 定义一个map集合，保存文件后缀和mimetype的对应关系；
				// 获得目标文件的后缀，从集合中获取mimetype，之后即可获取intent
				File file = new File(app.getSdcardPath() + "/android.crow.demo/电力安规2.mp4");
				Intent intent = FileUtils.getFileIntent(file);
				startActivity(intent);
			}
		});
		
		btn_openAnAudioFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = new File(app.getSdcardPath() + "/android.crow.demo/一生有你 - 水木年华.mp3");
				Intent intent = FileUtils.getFileIntent(file);
				startActivity(intent);
			}
		});
		
		btn_openATextFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = new File(app.getSdcardPath() + "/android.crow.demo/访问路径.txt");
				Intent intent = FileUtils.getFileIntent(file);
				startActivity(intent);
			}
		});
		
		btn_openANonePostfixFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = new File(app.getSdcardPath() + "/android.crow.demo/e_config");
				Intent intent = FileUtils.getFileIntent(file);
				startActivity(intent);
			}
		});
		
		btn_openADocFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = new File(app.getSdcardPath() + "/android.crow.demo/C007.doc");
				Intent intent = FileUtils.getFileIntent(file);
				startActivity(intent);
			}
		});
		
		btn_openADocFileByWps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = new File(app.getSdcardPath() + "/android.crow.demo/C12.docx");
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setClassName("cn.wps.moffice_eng", "cn.wps.moffice.documentmanager.PreStartActivity2");
				// 如果想要直接打开某文件，而不是打开软件，需要设置setData的值为目标文件Uri
				intent.setDataAndType(Uri.fromFile(file), FileUtils.getFileSuffix(file));
				Bundle bundle = new Bundle();
				// Normal模式，默认，等于普通编辑模式。
				// ReadOnly模式，可以赋值文字，可以插入表格、图片等非常少量的内容，但无法保存。
				// ReadMode模式，阅读器模式。
				// SaveOnly，执行打开、另存为、关闭一连串操作，中间无停顿。如果要实现另存为，需要设置SavePath的值
				bundle.putString(WpsUtils.OPEN_MODE, WpsUtils.OPEN_MODE_SAVE_ONLY);
				bundle.putBoolean(WpsUtils.SEND_SAVE_BROAD, true);
				bundle.putBoolean(WpsUtils.SEND_CLOSE_BROAD, true);
				// 设置第三方程序的包名，wps关闭文档的广播返回的信息中包含该内容
				bundle.putString(WpsUtils.THIRD_PACKAGE, mActivity.getPackageName());
				// 该值在将打开模式设置为SaveOnly时有意义
				bundle.putString(WpsUtils.SAVE_PATH, app.getSdcardPath() + "/android.crow.demo/C3.docx");
				bundle.putString(WpsUtils.USER_NAME, "Crow");// 指定操作文档的人
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		btn_openAWebPage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = new File(app.getSdcardPath() + "/android.crow.demo/clog example.html");
				Intent intent = FileUtils.getFileIntent(file);
				// 或者：
				/*
				Uri uri = Uri.parse(file.toString())
						.buildUpon()
						.encodedAuthority("com.android.htmlfileprovider")
						.scheme("content")
						.encodedPath(file.toString())
						.build();
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(uri, "text/html");
				*/
				startActivity(intent);
			}
		});
		
		btn_openWebUrl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("http://www.imooc.com");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		
		btn_openAnApkFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				File file = new File(app.getSdcardPath() + "/android.crow.demo/es.apk");
				Intent intent = FileUtils.getFileIntent(file);
				startActivity(intent);
			}
		});
		
		btn_createShortcutOnHomeScreen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击快捷方式的Intent
				Intent shortcutClickIntent = new Intent(Intent.ACTION_MAIN);
				shortcutClickIntent.setClass(mActivity.getApplicationContext(), mActivity.getClass());
				// 创建快捷方式的Intent
				Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
				// 快捷方式名称
				intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "测试LINK");
				// 不可重复创建
				intent.putExtra("duplicate", false);
				// 快捷方式图片
				ShortcutIconResource sir = Intent.ShortcutIconResource.fromContext(mActivity.getApplicationContext(), R.drawable.ic_launcher);
				intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, sir);
				// 点击快捷方式后要启动的程序，此处指定启动当前程序的主Activity
				intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutClickIntent);
				mActivity.sendBroadcast(intent);
			}
		});
		
		return view;
	}

	private void initControls(View view) {
		btn_openAImgFile = (Button) view.findViewById(R.id.btn_openAImgFile);
		btn_openAVideoFile = (Button) view.findViewById(R.id.btn_openAVideoFile);
		btn_openAnAudioFile = (Button) view.findViewById(R.id.btn_openAnAudioFile);
		btn_openATextFile = (Button) view.findViewById(R.id.btn_openATextFile);
		btn_openANonePostfixFile = (Button) view.findViewById(R.id.btn_openANonePostfixFile);
		btn_openADocFile = (Button) view.findViewById(R.id.btn_openADocFile);
		btn_openADocFileByWps = (Button) view.findViewById(R.id.btn_openADocFileByWps);
		btn_openAWebPage = (Button) view.findViewById(R.id.btn_openAWebPage);
		btn_openWebUrl = (Button) view.findViewById(R.id.btn_openWebUrl);
		btn_openAnApkFile = (Button) view.findViewById(R.id.btn_openAnApkFile);
		btn_createShortcutOnHomeScreen = (Button) view.findViewById(R.id.btn_createShortcutOnHomeScreen);
	}
	
	private boolean checkPostfixInArray(String fileName, String[] filePostfixs){
		for(String str : filePostfixs){
			if(fileName.endsWith(str)){
				return true;
			}
		}
		return false;
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
