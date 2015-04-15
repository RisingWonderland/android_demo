package org.demo.crow.soundlight;

import org.crow.android.utils.BasicUtils;
import org.crow.android.utils.OSUtils;
import org.demo.crow.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SoundAndLight extends Fragment {
	
	private static final String TAG = "SoundAndLight";
	private Activity mActivity;
	
	private Button btn_getMusicVolume;
	private Button btn_deviceSilence;
	private Button btn_restoreSound;
	private Button btn_decreaseVolume;
	private Button btn_increaseVolume;
	private SeekBar sb_changeVolume;
	
	
	private Button btn_getSysBrightness;
	private Button btn_keepScreenOn;
	private Button btn_releaseScreenOn;
	private Button btn_getBrightnessManageMode;
	private Button btn_openBrightnessAudoMode;
	private Button btn_openBrightnessManualMode;
	private Button btn_decreaseBrightness;
	private Button btn_increaseBrightness;
	private SeekBar sb_changeBrightness;
	
	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, "onAttach");
		super.onAttach(activity);
		mActivity = activity;
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
		View view = inflater.inflate(R.layout.fm_sound_and_light, container, false);
		initControls(view);
		initContrilsEvent();
		
		return view;
	}
	
	/**
	 * 初始化控件
	 * @author Crow
	 * @date 2015-4-11下午9:20:20
	 * @param view
	 */
	private void initControls(View view) {
		btn_getMusicVolume = (Button) view.findViewById(R.id.btn_getMusicVolume);
		btn_deviceSilence = (Button) view.findViewById(R.id.btn_deviceSilence);
		btn_restoreSound = (Button) view.findViewById(R.id.btn_restoreSound);
		btn_decreaseVolume = (Button) view.findViewById(R.id.btn_decreaseVolume);
		btn_increaseVolume = (Button) view.findViewById(R.id.btn_increaseVolume);
		sb_changeVolume = (SeekBar) view.findViewById(R.id.sb_changeVolume);
		
		btn_getSysBrightness = (Button) view.findViewById(R.id.btn_getSysBrightness);
		btn_keepScreenOn = (Button) view.findViewById(R.id.btn_keepScreenOn);
		btn_releaseScreenOn = (Button) view.findViewById(R.id.btn_releaseScreenOn);
		btn_getBrightnessManageMode = (Button) view.findViewById(R.id.btn_getBrightnessManageMode);
		btn_openBrightnessAudoMode = (Button) view.findViewById(R.id.btn_openBrightnessAudoMode);
		btn_openBrightnessManualMode = (Button) view.findViewById(R.id.btn_openBrightnessManualMode);
		btn_decreaseBrightness = (Button) view.findViewById(R.id.btn_decreaseBrightness);
		btn_increaseBrightness = (Button) view.findViewById(R.id.btn_increaseBrightness);
		sb_changeBrightness = (SeekBar) view.findViewById(R.id.sb_changeBrightness);
	}

	/**
	 * 为控件设置交互事件
	 * @author Crow
	 * @date 2015-4-11下午9:23:44
	 */
	private void initContrilsEvent() {
		// 获得当前媒体音量
		btn_getMusicVolume.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int[] vol = OSUtils.getMusicVolume(mActivity);
				BasicUtils.showToast(mActivity, "当前媒体音量：" + vol[0] + " / 最大媒体音量：" + vol[1]);
			}
		});
		// 静音按钮
		btn_deviceSilence.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OSUtils.deviceSilence(mActivity);
			}
		});
		// 从静音恢复音量按钮
		btn_restoreSound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OSUtils.restoreVolume(mActivity);
			}
		});
		// 降低音量按钮
		btn_decreaseVolume.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OSUtils.lowerVolume(mActivity);
			}
		});
		// 提高音量按钮
		btn_increaseVolume.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OSUtils.higherVolume(mActivity);
			}
		});
		sb_changeVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				Log.i(TAG, "drag begin");
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				Log.i(TAG, "drag end");
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				int sb_value = seekBar.getProgress();
				OSUtils.setVolume(OSUtils.getAudioManager(mActivity), sb_value);
			}
		});
		
		
		
		
		// 获得当前系统亮度
		btn_getSysBrightness.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int value = OSUtils.getSystemScreenBrightness(mActivity);
				BasicUtils.showToast(mActivity, value + "");
			}
		});
		// 开启屏幕常量
		btn_keepScreenOn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OSUtils.keepScreenOn(mActivity);
			}
		});
		// 关闭屏幕常亮
		btn_releaseScreenOn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OSUtils.releaseScreenOn(mActivity);
			}
		});
		// 当前亮度调节模式
		btn_getBrightnessManageMode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int type = OSUtils.getScreenBrightnessMode(mActivity);
				if(type == OSUtils.SCREEN_BRIGHTNESS_MODE_AUTOMATIC){
					BasicUtils.showToast(mActivity, "当前是自动调节亮度模式");
				}else if(type == OSUtils.SCREEN_BRIGHTNESS_MODE_MANUAL){
					BasicUtils.showToast(mActivity, "当前是手动调节亮度模式");
				}else{
					BasicUtils.showToast(mActivity, "当前亮度模式未知，代码有错");
				}
			}
		});
		// 开启自动调节模式
		btn_openBrightnessAudoMode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OSUtils.setBrightnessAutomatic(mActivity);
			}
		});
		// 开启手动调节模式
		btn_openBrightnessManualMode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OSUtils.setBrightnessManual(mActivity);
			}
		});
		// 降低亮度按钮
		btn_decreaseBrightness.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OSUtils.decreaseActivityBrightness(mActivity);
			}
		});
		// 提高亮度按钮
		btn_increaseBrightness.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				OSUtils.increaseActivityBrightness(mActivity);
			}
		});
		// 调节系统亮度滑块
		sb_changeBrightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				int sb_value = seekBar.getProgress();
				OSUtils.setActivityBrightness(mActivity, sb_value);
			}
		});
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
