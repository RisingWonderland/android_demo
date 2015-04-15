package org.crow.android.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.Toast;
/**
 * 基本工具，全部是最基本的内容，是CommonUtils的支持类。
 * @author Crow
 *
 */
public class BasicUtils {
	
	private static final String TAG = "Crow_BasicUtils";
	
	/**
	 * 获得设备分辨率
	 * @param context Activity上下文
	 * @return
	 */
	public static int[] getScreenDpi(Context context){
		int[] dpi = new int[2];
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		dpi[0] = dm.widthPixels;
		dpi[1] = dm.heightPixels;
		return dpi;
	}
	
	/**
	 * 获得SDCard状态和路径等信息。
	 * 如果SDCard当前状态挂载中，可读或可写，返回不以“/”结尾的路径；否则返回空字符串。
	 * @return HashMap
	 */
	public static HashMap<String, String> getSDCardInfo(){
		String sdcardStatus = getSDCardStatus();
		String sdcardPath = "";
		if(sdcardStatus.equals(Environment.MEDIA_MOUNTED) || sdcardStatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			sdcardPath = getSDCardPath();
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("status", sdcardStatus);
		map.put("path", sdcardPath);
		return map;
	}
	/**
	 * 获得SDCard状态
	 * @return
	 */
	public static String getSDCardStatus(){
		return Environment.getExternalStorageState();
	}
	
	/**
	 * 获得SDCard路径，不以“/”结尾
	 * @return
	 */
	public static String getSDCardPath(){
		return Environment.getExternalStorageDirectory().getAbsolutePath().toString().replaceAll("/$", "");
	}
	
	
	/**
	 * 显示一个Toast
	 * @param context
	 * @param msg
	 * @param longTime 值为true，显示时间长；值为false，显示时间短。默认为false
	 */
	public static void showToast(Context context, String msg, boolean longTime){
		if(longTime == true){
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * 显示一个短Toast
	 * @param context
	 * @param msg
	 */
	public static void showToast(Context context, String msg){
		showToast(context, msg, false);
	}

	
	
	
	/**
	 * 兼容方法，为SharedPreferences提供apply方法。
	 * 该方法与commit方法作用相同，但因为是异步方法，可以给主线程适当减轻压力。
	 * @author Crow
	 *
	 */
	private static class SharedPreferencesCompat{
		private static final Method sApplyMethod = findApplyMethod();  
		  
	    private static Method findApplyMethod() {
	        try {
	            Class<Editor> cls = SharedPreferences.Editor.class;
	            return cls.getMethod("apply");
	        } catch (NoSuchMethodException unused) {
	            // fall through
	        }
	        return null;
	    }
	    
	    public static void apply(SharedPreferences.Editor editor) {
	        if (sApplyMethod != null) {
	            try {
	                sApplyMethod.invoke(editor);
	                return;
	            } catch (InvocationTargetException unused) {
	                // fall through
	            } catch (IllegalAccessException unused) {
	                // fall through
	            }
	        }
	        editor.commit();
	    }
	}
	/**
	 * 获得一个SharedPreferences对象
	 * @author Crow
	 * @date 2015-4-14下午2:17:43
	 * @param activity
	 * @param name
	 * @param mode Operating mode. Use 0 or MODE_PRIVATE for the default operation, MODE_WORLD_READABLE and MODE_WORLD_WRITEABLE to control permissions. The bit MODE_MULTI_PROCESS can also be used if multiple processes are mutating the same SharedPreferences file. MODE_MULTI_PROCESS is always on in apps targetting Gingerbread (Android 2.3) and below, and off by default in later versions.
	 * @return
	 */
	public static SharedPreferences getSharedPreferences(Activity activity, String name, int mode){
		return activity.getSharedPreferences(name, mode);
	}
	/**
	 * 向指定的SharedPreferences插入一条数据。
	 * 私有模式。
	 * @author Crow
	 * @date 2015-4-14下午2:24:37
	 * @param activity
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void setPrivateSharedPrefsData(Activity activity, String name, String key, String value){
		setSharedPrefsData(activity, name, key, value, Context.MODE_PRIVATE);
	}
	/**
	 * 向指定的SharedPreferences插入一条数据。
	 * 模式自定
	 * @author Crow
	 * @date 2015-4-15上午9:36:03
	 * @param activity
	 * @param name
	 * @param key
	 * @param value
	 * @param mode
	 */
	public static void setSharedPrefsData(Activity activity, String name, String key, Object value, int mode){
		SharedPreferences sp = getSharedPreferences(activity, name, mode);
		SharedPreferences.Editor editor = sp.edit();
		if(value instanceof Integer){
			editor.putInt(key, (int) value);
		}else if(value instanceof Float){
			editor.putFloat(key, (float) value);
		}else if(value instanceof Long){
			editor.putLong(key, (long) value);
		}else if(value instanceof Boolean){
			editor.putBoolean(key, (boolean) value);
		}else{
			// 如果不是上述几种基本数据类型，将其作为String类型
			// WARNING: 此处不能使用toString方法，因为参数可能是null
			editor.putString(key, String.valueOf(value));
		}
		SharedPreferencesCompat.apply(editor);
	}
	/**
	 * 从指定的SharedPreferences获取一条数据。
	 * 私有模式。
	 * @author Crow
	 * @date 2015-4-15上午9:45:14
	 * @param activity
	 * @param name
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static Object getPrivateSharedPrefsData(Activity activity, String name, String key, Object defValue){
		return getSharedPrefsData(activity, name, key, defValue, Context.MODE_PRIVATE);
	}
	/**
	 * 从指定的SharedPreferences获取一条数据。
	 * 模式自定。
	 * @author Crow
	 * @date 2015-4-15上午9:43:52
	 * @param activity
	 * @param name
	 * @param key
	 * @param defValue
	 * @param mode
	 * @return
	 */
	public static Object getSharedPrefsData(Activity activity, String name, String key, Object defValue, int mode){
		SharedPreferences sp = getSharedPreferences(activity, name, mode);
		if(defValue instanceof Integer){
			return sp.getInt(key, (int) defValue);
		}else if(defValue instanceof Float){
			return sp.getFloat(key, (float) defValue);
		}else if(defValue instanceof Long){
			return sp.getLong(key, (long) defValue);
		}else if(defValue instanceof Boolean){
			return sp.getBoolean(key, (boolean) defValue);
		}else{
			// 如果不是上述几种基本数据类型，将其作为String类型
			// WARNING: 此处不能使用toString方法，因为参数可能是null
			return sp.getString(key, String.valueOf(defValue));
		}
	}
	/**
	 * 检查指定的SharedPreferences是否存在对应的key值
	 * @author Crow
	 * @date 2015-4-15上午9:55:56
	 * @param activity
	 * @param name
	 * @param key
	 * @param mode
	 * @return
	 */
	public static boolean checkSharedPrefsKey(Activity activity, String name, String key, int mode){
		SharedPreferences sp = getSharedPreferences(activity, name, mode);
		return sp.contains(key);
	}
	/**
	 * 从指定的SharedPreferences中获取一个包含所有键值对的Map集合
	 * @author Crow
	 * @date 2015-4-15上午9:56:23
	 * @param activity
	 * @param name
	 * @param mode
	 * @return
	 */
	public static Map<String, ?> getSharedPrefsDataMap(Activity activity, String name, int mode){
		SharedPreferences sp = getSharedPreferences(activity, name, mode);
		return sp.getAll();
	}
	/**
	 * 移除指定的SharedPreferences中某值
	 * @author Crow
	 * @date 2015-4-15上午9:58:04
	 * @param activity
	 * @param name
	 * @param key
	 * @param mode
	 */
	public static void removeSharedPrefsData(Activity activity, String name, String key, int mode){
		SharedPreferences sp = getSharedPreferences(activity, name, mode);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}
	/**
	 * 移除指定的SharedPreferences中的所有值
	 * @author Crow
	 * @date 2015-4-15上午10:00:41
	 * @param activity
	 * @param name
	 * @param mode
	 */
	public static void removeAllSharedPrefsData(Activity activity, String name, int mode){
		SharedPreferences sp = getSharedPreferences(activity, name, mode);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}
	
	
	
}
