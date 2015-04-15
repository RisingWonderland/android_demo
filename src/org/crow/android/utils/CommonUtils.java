package org.crow.android.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.util.Log;

/**
 * 常用、通用工具类，需要BasicUtils类的支持。
 * @author Crow
 *
 */
public class CommonUtils {
	private static final String TAG = "Crow_CommonUtils";
	
	private static final String SILENT_INSTALL_CMD = "pm install -r ";
	private static final String SILENT_UNINSTALL_CMD = "pm uninstall ";// 如果要保留数据，需要加-k参数，但是卸载会不完全
	
	
	/**
	 * 判断当前Android设备是否root
	 */
	public static boolean rootStatus(){
		String binPath = "/system/bin/su";
		String xBinPath = "/system/xbin/su";
		if(new File(binPath).exists() && FileUtils.isFileExecutable(binPath)){
			return true;
		}
		if(new File(xBinPath).exists() && FileUtils.isFileExecutable(xBinPath)){
			return true;
		}
		return false;
	}

	/**
	 * 标准安装app
	 * @param activity 
	 * @param apkPath 要安装的apk文件的路径
	 */
	public static void install(Activity activity, String apkPath){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
		activity.startActivity(intent);
	}
	
	/**
	 * 标准卸载app
	 * @param activity 
	 * @param packagename 目标app的包名
	 */
	public static void uninstall(Activity activity, String packageName){
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
		activity.startActivity(intent);
	}
	
	/**
	 * 静默（后台）安装app，实现该功能需要Android设备已经root。返回一个int型状态码。
	 * @param apkPath 目标apk文件路径
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
	 * log输出所有非系统应用的包名信息
	 * @author Crow
	 * @date 2015-4-9下午9:11:43
	 */
	public static void showAllCommonApps(Activity activity){
		List<PackageInfo> packages = activity.getPackageManager().getInstalledPackages(0);
        for(int i=0,l=packages.size();i < l;i++){
        	PackageInfo pi = packages.get(i); 
        	if((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
        		Log.i("Package: ", pi.packageName);
        	}
        }
	}
	
}
