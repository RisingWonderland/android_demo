package org.crow.android.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.util.Log;

/**
 * 常用、通用工具类，需要BasicUtils类的支持。
 * @author Crow
 * @date 2015-4-8
 *
 */
public class CommonUtils {
	private static final String TAG = "Crow_CommonUtils";
	
	private static final String SILENT_INSTALL_CMD = "pm install -r ";
	private static final String SILENT_UNINSTALL_CMD = "pm uninstall ";// 如果要保留数据，需要加-k参数，但是卸载会不完全
	
	
	/**
	 * 判断当前Android设备是否root
	 * @date 2015-4-8
	 */
	public static boolean rootStatus(){
		String binPath = "/system/bin/su";
		String xbinPath = "/system/xbin/su";
		if(new File(binPath).exists() && FileUtils.isFileExecutable(binPath)){
			return true;
		}
		if(new File(xbinPath).exists() && FileUtils.isFileExecutable(xbinPath)){
			return true;
		}
		return false;
	}

	/**
	 * 标准安装app
	 * @param context 
	 * @param apkPath 要安装的apk文件的路径
	 * @date 2015-4-8
	 */
	public static void install(Context context, String apkPath){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}
	
	/**
	 * 标准卸载app
	 * @param activity 
	 * @param packagename 目标app的包名
	 * @date 2015-4-8
	 */
	public static void uninstall(Context context, String packageName){
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
		context.startActivity(intent);
	}
	
	/**
	 * 静默（后台）安装app，实现该功能需要Android设备已经root。返回一个int型状态码。
	 * @param apkPath 目标apk文件路径
	 * @date 2015-4-8
	 * @return result 安装状态码
	 */
	public static int silentInstall(String apkPath){
		String installCmd = SILENT_INSTALL_CMD + apkPath;// PM指令不支持中文
		int result = -1;
		DataOutputStream dos = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("su");
			dos = new DataOutputStream(process.getOutputStream());
			Log.i("静默安装的命令：", installCmd);
			dos.writeBytes(installCmd + "\n");
			dos.flush();
			dos.writeBytes("exit\n");
			dos.flush();
			process.waitFor();
			result = process.exitValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(dos != null) {
					dos.close();
				}
				if(process != null){
					process.destroy();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 静默（后台）安装app，实现该功能需要Android设备已经root，返回一个int型状态码。
	 * @param appPackageNames 目标app的包名
	 * @date 2015-4-8
	 * @return result 卸载状态码
	 */
	public static int silentUninstall(String appPackageName){
		String uninstallCmd = SILENT_UNINSTALL_CMD + appPackageName;
		int result = -1;
		DataOutputStream dos = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("su");
			dos = new DataOutputStream(process.getOutputStream());
			Log.i("静默卸载的命令：", uninstallCmd);
			dos.writeBytes(uninstallCmd + "\n");
			dos.flush();
			dos.writeBytes("exit\n");
			dos.flush();
			process.waitFor();
			result = process.exitValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(dos != null) {
					dos.close();
				}
				if(process != null){
					process.destroy();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 获得所有已安装应用的PackageInfo列表
	 * @author Crow
	 * @date 2015-4-16
	 * @param context
	 * @param flags
	 * @return
	 */
	public static List<PackageInfo> getAllAppsPackageInfo(Context context, int flags){
		return context.getPackageManager().getInstalledPackages(flags);
	}
	
	/**
	 * 获得所有系统应用的PackageInfo列表
	 * @author Crow
	 * @date 2015-4-23
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getSystemApps(Context context){
		List<PackageInfo> piList = getAllAppsPackageInfo(context, 0);
		List<PackageInfo> appList = new ArrayList<PackageInfo>();
		for(int i=0,l=piList.size();i < l;i++){
			PackageInfo pi = piList.get(i);
			if((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
				appList.add(pi);
			}
		}
		return appList;
	}
	
	/**
	 * 获得所有非系统应用的PackageInfo列表
	 * @author Crow
	 * @date 2015-4-23
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getCommonApps(Context context){
		List<PackageInfo> piList = getAllAppsPackageInfo(context, 0);
		List<PackageInfo> appList = new ArrayList<PackageInfo>();
		for(int i=0,l=piList.size();i < l;i++){
			PackageInfo pi = piList.get(i);
			if((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
				appList.add(pi);
			}
		}
		return appList;
	}
	
	/**
	 * log输出所有非系统应用的包名信息
	 * @author Crow
	 * @date 2015-4-9
	 * @param context
	 */
	public static void showCommonApps(Context context){
        List<PackageInfo> list = getCommonApps(context);
		for(int i=0,l=list.size();i < l;i++){
			Log.i(TAG, list.get(i).packageName);
		}
	}
	
	/**
	 * log输出所有系统应用的包名信息
	 * @author Crow
	 * @date 2015-4-23
	 * @param context
	 */
	public static void showSystemApps(Context context){
		List<PackageInfo> list = getSystemApps(context);
		for(int i=0,l=list.size();i < l;i++){
			Log.i(TAG, list.get(i).packageName);
		}
	}
	
	/**
	 * 获得正在运行中的进程的RunningAppProcessInfo对象的列表
	 * @author Crow
	 * @date 2015-4-16
	 * @param activity
	 * @return
	 */
	public static List<RunningAppProcessInfo> getRunningProcessesList(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		return am.getRunningAppProcesses();
	}
	
	/**
	 * log输出正在运行的进程的名称
	 * @author Crow
	 * @date 2015-4-16
	 * @param context
	 */
	public static void showRunningApps(Context context){
		List<RunningAppProcessInfo> runAppsList = getRunningProcessesList(context);
		for(ActivityManager.RunningAppProcessInfo info : runAppsList){
			Log.i(TAG, info.processName);
		}
	}
	
	/**
	 * 获得当前活动程序的相关信息
	 * @author Crow
	 * @date 2015-4-16
	 * @param context
	 * @return
	 */
	public static ComponentName getActiveApp(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		return am.getRunningTasks(1).get(0).topActivity;
	}
	
	/**
	 * log输出当前活动程序的
	 * @author Crow
	 * @date 2015-4-16
	 * @param context
	 */
	public static void showActiveApp(Context context){
		ComponentName cn = getActiveApp(context);
		Log.i(TAG, cn.getPackageName() + " / " + cn.getClassName());
	}
	
	/**
	 * 获得运行中的Service的列表
	 * @author Crow
	 * @date 2015-4-16
	 * @param context
	 * @return
	 */
	public static List<RunningServiceInfo> getRunningServicesList(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		return am.getRunningServices(Integer.MAX_VALUE);
	}
	
	/**
	 * log输出所有运行中的Service
	 * @author Crow
	 * @date 2015-4-16
	 * @param activity
	 */
	public static void showRunningServices(Context context){
		List<RunningServiceInfo> serviceList = getRunningServicesList(context);
		for(RunningServiceInfo info : serviceList){
			Log.i(TAG, info.service.getPackageName() + " / " + info.service.getClassName());
		}
	}
	
	/**
	 * 获得指定APP的所有权限列表
	 * @author Crow
	 * @date 2015-5-6
	 * @param context
	 * @param packageName
	 */
	public static List<PermissionInfo> getAppPermissionList(Context context, String packageName){
		List<PermissionInfo> permissionInfoList = new ArrayList<PermissionInfo>();
		PackageManager pm = context.getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
			String[] permissions = pi.requestedPermissions;
			if(permissions != null){
				for(String str : permissions){
					PermissionInfo permissionInfo = context.getPackageManager().getPermissionInfo(str, 0);
					permissionInfoList.add(permissionInfo);
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return permissionInfoList;
	}
	
	/**
	 * Log输出指定APP的所有权限列表
	 * @author Crow
	 * @date 2015-5-6
	 * @param context
	 * @param packageName
	 */
	public static void showAppPermissionList(Context context, String packageName){
		List<PermissionInfo> permissionInfoList = getAppPermissionList(context, packageName);
		for(PermissionInfo permInfo : permissionInfoList){
			Log.i(TAG, permInfo.name);
		}
	}
}
