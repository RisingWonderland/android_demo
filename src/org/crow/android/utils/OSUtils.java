package org.crow.android.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 系统工具类，涉及到针对系统的操作，例如亮度和声音的调节。
 * 
 * 关于屏幕亮度
 * 亮度调节的目标有两个：Activity和System。
 * Activity Brightness
 * 调节Activity亮度需要获得WindowManager.LayoutParams对象，设置它的screenBrightness的值。
 * 该参数接收一个[0.0000f， 1.0000f]区间的浮点数，精确到小数点后4位。
 * 目标Activity不能是某个控件的子Activity，例如ViewGroup。只有对父级Activity进行操作才有效。
 * 对Activity亮度进行调节后，如果离开该Activity，或者将screenBrightness的值
 * 设置为-1（WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE），屏幕亮度就会被恢复。
 * 在本工具类中，针对Activity的亮度调节，最低只能调节到0.1，此时屏幕非常黑，可控。
 * 此外，不同的Android设备，针对低亮度值而触发的反应应该有所不同。
 * 注意：screenBrightness的值默认小于零（-1），含义是亮度取决于系统。
 * System Brightness
 * 调节系统亮度需要使用如下代码：
 * Settings.System.putInt(cr, Settings.System.SCREEN_BRIGHTNESS, screenBrightValue);
 * 其中cr是ContentResolver对象，screenBrightValue是int型目标亮度值，范围是[0 ~ 255]。
 * 要手动设置系统亮度，首先需要确保亮度调节模式是手动，
 * 其次是要添加 android.permission.WRITE_SETTINGS 权限。
 * 获取当前系统亮度调节模式的代码如下：
 * Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS_MODE);
 * 亮度调节模式分为手动和自动，分别为：
 * Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
 * Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
 * 可以调用本工具类中的getScreenBrightnessMode方法快速获取亮度调节模式，
 * 之后可以和SCREEN_BRIGHTNESS_MODE_MANUAL和SCREEN_BRIGHTNESS_MODE_AUTOMATIC这两个静态变量相比较。
 * 
 * 
 * 关于网络连接
 * 进行网络相关操作需要android.permission.ACCESS_NETWORK_STATE权限
 * 
 * @author Crow
 *
 */
public class OSUtils {
	
	private static final String TAG = "Crow_OSUtils";
	
	private static int LIGHT_CHANGE_RANGE = 5;// 亮度增大、降低的幅度
	
	public static final int SCREEN_BRIGHTNESS_MODE_AUTOMATIC = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
	public static final int SCREEN_BRIGHTNESS_MODE_MANUAL = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
	
	private static final float MIN_ACTIVITY_BRIGHTNESS = 0.0f;
	private static final float MAX_ACTIVITY_BRIGHTNESS = 1.0f;
	private static final int MIN_SYSTEM_BRIGHTNESS = 0;
	private static final int MAX_SYSTEM_BRIGHTNESS = 255;
	
	
	/**
	 * 获得音频管理器
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 * @return
	 */
	public static AudioManager getAudioManager(Context context){
		return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	}
	
	/**
	 * 返回系统音量，数组元素1是当前音量，元素2是最大音量。
	 * 安卓的最大音量是15。
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 * @return
	 */
	public static int[] getSystemVolume(Context context){
		AudioManager am = getAudioManager(context);
		int maxSysVol = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
		int curSysVol = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
		return new int[]{curSysVol, maxSysVol};
	}
	/**
	 * 返回通话音量，数组元素1是当前音量，元素2是最大音量
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 * @return
	 */
	public static int[] getVoiceVolume(Context context){
		AudioManager am = getAudioManager(context);
		int maxVoiceVol = am.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
		int curVoiceVol = am.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
		return new int[]{curVoiceVol, maxVoiceVol};
	}
	/**
	 * 返回媒体音量，数组元素1是当前音量，元素2是最大音量
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 * @return
	 */
	public static int[] getMusicVolume(Context context){
		AudioManager am = getAudioManager(context);
		int maxMusicVol = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int curMusicVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		return new int[]{curMusicVol, maxMusicVol};
	}
	/**
	 * 返回铃声音量，数组元素1是当前音量，元素2是最大音量
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 * @return
	 */
	public static int[] getRingVolume(Context context){
		AudioManager am = getAudioManager(context);
		int maxRingVol = am.getStreamMaxVolume(AudioManager.STREAM_RING);
		int curRingVol = am.getStreamVolume(AudioManager.STREAM_RING);
		return new int[]{curRingVol, maxRingVol};
	}
	/**
	 * 返回闹钟提示音量，数组元素1是当前音量，元素2是最大音量
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 * @return
	 */
	public static int[] getAlarmVolume(Context context){
		AudioManager am = getAudioManager(context);
		int maxAlarmVol = am.getStreamMaxVolume(AudioManager.STREAM_ALARM);
		int curAlarmVol = am.getStreamVolume(AudioManager.STREAM_ALARM);
		return new int[]{curAlarmVol, maxAlarmVol};
	}
	
	/**
	 * 以具体数值设置媒体音量
	 * @author Crow
	 * @date 2015-4-12
	 * @param am
	 * @param value
	 */
	public static void setVolume(AudioManager am, int value){
		if(value < 0){
			value = 0;
		}
		if(value > 255){
			value = 255;
		}
		Log.i(TAG, value + "");
		am.setStreamVolume(AudioManager.STREAM_MUSIC, value, AudioManager.FLAG_SHOW_UI|AudioManager.FLAG_PLAY_SOUND);
	}
	
	/**
	 * 将设备媒体音量设为0。
	 * 如果要恢复到静音前的音量，调用restoreVolume方法，但两个方法要传入相同的activity
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 */
	public static void deviceSilence(Context context){
		AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		// 获得媒体音量
		int curMusicVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		// 将当前音量存入SharedPreferences，用于支持restoreVolume方法
		SharedPreferences sp = context.getSharedPreferences("osutils_audio_last_vol", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("lastMusicVol", curMusicVol);
		editor.commit();
		// 设置为静音
		setVolume(am, 0);
	}
	
	/**
	 * 将设备媒体音量设置为执行了deviceSilence方法前的大小。
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 */
	public static void restoreVolume(Context context){
		restoreVolume(context, -1);
	}
	/**
	 * 将设备媒体音量设置为执行了deviceSilence方法前的大小。
	 * 如果没有找到之前缓存的音量值，并且提供的默认音量值是正数，将该值设置用于媒体音量。
	 * 否则，不做任何修改。
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 * @param defValue
	 */
	public static void restoreVolume(Context context, int defValue){
		AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		// 获得SharedPreferences里面保存的音量值
		SharedPreferences sp = context.getSharedPreferences("osutils_audio_last_vol", Context.MODE_PRIVATE);
		int lastMusicVol;
		if( sp.contains("lastMusicVol") ){
			lastMusicVol = sp.getInt("lastMusicVol", 0);
		}else if( (!sp.contains("lastMusicVol")) && (defValue >= 0) ){
			lastMusicVol = defValue > 255 ? 255 : defValue;
		}else{
			return;
		}
		am.setStreamVolume(AudioManager.STREAM_MUSIC, lastMusicVol, AudioManager.FLAG_SHOW_UI);
	}
	
	/**
	 * 降低媒体音量
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 * @return
	 */
	public static int lowerVolume(Context context){
		AudioManager am = getAudioManager(context);
		am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI|AudioManager.FLAG_PLAY_SOUND);
		return getMusicVolume(context)[0];
	}
	/**
	 * 提高媒体音量
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 * @return
	 */
	public static int higherVolume(Context context){
		AudioManager am = getAudioManager(context);
		am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI|AudioManager.FLAG_PLAY_SOUND);
		return getMusicVolume(context)[0];
	}
	
	
	
	
	
	/**
	 * 获得当前系统屏幕亮度
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 * @return
	 */
	public static int getSystemScreenBrightness(Context context){
		ContentResolver cr = context.getContentResolver();
		return Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS, -1);
	}
	/**
	 * 获得当前系统屏幕亮度模式。得出的结果可以与当前类中的
	 * SCREEN_BRIGHTNESS_MODE_AUTOMATIC和SCREEN_BRIGHTNESS_MODE_MANUAL进行比较。
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 * @return
	 */
	public static int getScreenBrightnessMode(Context context){
		ContentResolver cr = context.getContentResolver();
		return Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS_MODE, -1);
	}
	/**
	 * 返回当前是否是自动亮度调节模式
	 * @author Crow
	 * @date 2015-4-13
	 * @param context
	 * @return
	 */
	public static boolean isBrightnessAutomatic(Context context){
		return (getScreenBrightnessMode(context) == SCREEN_BRIGHTNESS_MODE_AUTOMATIC) ? true : false;
	}
	/**
	 * 将当前亮度调节模式设置为自动
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 */
	public static void setBrightnessAutomatic(Context context){
		ContentResolver cr = context.getContentResolver();
		Settings.System.putInt(cr, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
	}
	/**
	 * 将当前亮度调节模式设置为手动
	 * @author Crow
	 * @date 2015-4-12
	 * @param context
	 */
	public static void setBrightnessManual(Context context){
		ContentResolver cr = context.getContentResolver();
		Settings.System.putInt(cr, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
	}
	
	/**
	 * 降低指定Activity所在的屏幕亮度
	 * @author Crow
	 * @date 2015-4-13
	 * @param activity
	 */
	public static void decreaseActivityBrightness(Activity activity){
		changeBrightnessByStep(activity, (Math.abs(LIGHT_CHANGE_RANGE) * -1), false);
	}
	/**
	 * 提高指定Activity所在的屏幕亮度
	 * @author Crow
	 * @date 2015-4-13
	 * @param activity
	 */
	public static void increaseActivityBrightness(Activity activity){
		changeBrightnessByStep(activity, Math.abs(LIGHT_CHANGE_RANGE), false);
	}
	/**
	 * 降低系统屏幕亮度
	 * @author Crow
	 * @date 2015-4-12
	 * @param activity
	 */
	public static void decreaseSystemBrightness(Activity activity){
		changeBrightnessByStep(activity, (Math.abs(LIGHT_CHANGE_RANGE) * -1), true);
	}
	/**
	 * 提高系统屏幕亮度
	 * @author Crow
	 * @date 2015-4-12
	 * @param activity
	 */
	public static void increaseSystemBrightness(Activity activity){
		changeBrightnessByStep(activity, Math.abs(LIGHT_CHANGE_RANGE), true);
	}
	/**
	 * 增大或降低屏幕亮度
	 * 如果参数controlSystemBrightness的值为true，调节系统亮度，否则只调节当前Activity的亮度。
	 * @author Crow
	 * @date 2015-4-12
	 * @param activity
	 * @param value
	 * @param controlSystemBrightness 是否调节系统亮度
	 */
	private static void changeBrightnessByStep(Activity activity, int value, boolean controlSystemBrightness){
		// 如果当前是自动调节模式，切换为手动调节模式
		if(isBrightnessAutomatic(activity)){
			setBrightnessManual(activity);
		}
		
		// 获得当前屏幕亮度
		int screenBrightValue = getSystemScreenBrightness(activity);
		screenBrightValue += value;
		// 亮度值范围限定
		if(screenBrightValue < MIN_SYSTEM_BRIGHTNESS){
			screenBrightValue = MIN_SYSTEM_BRIGHTNESS;
		}else if(screenBrightValue > MAX_SYSTEM_BRIGHTNESS){
			screenBrightValue = MAX_SYSTEM_BRIGHTNESS;
		}
		
		// 定义Activity应设置成的亮度值
		WindowManager.LayoutParams wml = activity.getWindow().getAttributes();
		float activityBrightness = wml.screenBrightness;
		
		// 如果条件为true，改变系统亮度
		if(controlSystemBrightness){
			ContentResolver cr = activity.getContentResolver();
			Settings.System.putInt(cr, Settings.System.SCREEN_BRIGHTNESS, screenBrightValue);
			activityBrightness = screenBrightValue / 255.0f;
		}else{
			// 如果只改变目标Activity的亮度，根据Settings.System.getInt方法获得的亮度的值是系统的，
			// 又由于系统亮度不会发生变化，	该值是恒定的，无法改变下文中wml.screenBrightness的值
			// 所以需要获得wml.screenBrightness值，加上或减去“亮度步进值与亮度最大值的比”，该值有效
			float stepPer = (LIGHT_CHANGE_RANGE / (float) MAX_SYSTEM_BRIGHTNESS);
			if(value < 0){
				stepPer *= -1;
			}
			if(activityBrightness < 0){
				activityBrightness = screenBrightValue / 255.0f;
			}
			activityBrightness += stepPer;
			// 亮度值范围限定
			if(activityBrightness < MIN_ACTIVITY_BRIGHTNESS){
				activityBrightness = MIN_ACTIVITY_BRIGHTNESS;
			}else if(activityBrightness > MAX_ACTIVITY_BRIGHTNESS){
				activityBrightness = MAX_ACTIVITY_BRIGHTNESS;
			}
		}
		
		wml.screenBrightness = activityBrightness;
		activity.getWindow().setAttributes(wml);
	}
	/**
	 * 设置目标Activity的亮度
	 * @author Crow
	 * @date 2015-4-12
	 * @param activity
	 * @param value 0 ~ 255
	 */
	public static void setActivityBrightness(Activity activity, int value){
		setBrightness(activity, value, false);
	}
	/**
	 * 设置系统的屏幕亮度
	 * @author Crow
	 * @date 2015-4-13
	 * @param activity
	 * @param value
	 */
	public static void setSystemBrightness(Activity activity, int value){
		setBrightness(activity, value, true);
	}
	/**
	 * 以具体的数值设置屏幕亮度，如果参数三位true，设置系统亮度，否则只设置目标Activity的亮度。
	 * @author Crow
	 * @date 2015-4-13
	 * @param activity
	 * @param value  0 ~ 255
	 * @param controlSystemBrightness
	 */
	private static void setBrightness(Activity activity, int value, boolean controlSystemBrightness){
		// 如果当前是自动调节模式，切换为手动调节模式
		if(isBrightnessAutomatic(activity)){
			setBrightnessManual(activity);
		}
		ContentResolver cr = activity.getContentResolver();
		if(controlSystemBrightness){
			Settings.System.putInt(cr, Settings.System.SCREEN_BRIGHTNESS, value);
		}
		WindowManager.LayoutParams wml = activity.getWindow().getAttributes();
		wml.screenBrightness = value / 255.0f;
		activity.getWindow().setAttributes(wml);
	}
	
	/**
	 * 保持屏幕开启，只针对单个Activity有效
	 * @author Crow
	 * @date 2015-4-12
	 * @param activity
	 */
	public static void keepScreenOn(Activity activity){
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	/**
	 * 取消屏幕常量，只针对单个Activity有效
	 * @author Crow
	 * @date 2015-4-12
	 * @param activity
	 */
	public static void releaseScreenOn(Activity activity){
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	
	
	
	/**
	 * 判断当前网络连接是否可用（存在）
	 * @author Crow
	 * @date 2015-4-13
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] nis = cm.getAllNetworkInfo();
		for(NetworkInfo ni : nis){
			if(ni.isConnected() && ni.isAvailable()){
				return true;
			}
		}
		return false;
	}
	/**
	 * 获得当前网络连接
	 * @author Crow
	 * @date 2015-4-13
	 * @param context
	 * @return
	 */
	public static NetworkInfo getActiveConnection(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}
	/**
	 * 判断WIFI网络是否连接可用
	 * @author Crow
	 * @date 2015-4-13
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni_wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(ni_wifi.isConnected() && ni_wifi.isAvailable()){
			return true;
		}
		return false;
	}
	/**
	 * 判断Mobile网络是否可用
	 * @author Crow
	 * @date 2015-4-13
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni_mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(ni_mobile.isConnected() && ni_mobile.isAvailable()){
			return true;
		}
		return false;
	}
	/**
	 * 进入网络配置界面
	 * @author Crow
	 * @date 2015-4-13
	 * @param context
	 */
	public static void enterWirelessConfigurationActivity(Context context){
		context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
	}
	
	
	
	
	
	/**
	 * 让虚拟键盘在开启和关闭之间转换
	 * @author Crow
	 * @date 2015-4-13
	 * @param context
	 */
	public static void toggleVirtualKeyboard(Context context){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
}
