package org.crow.android.utils;

/**
 * 字符串工具类
 * @author Crow
 *
 */
public class StringUtils {
	
	private static final String TAG = "Crow_StringUtils";
	

	/**
	 * 获取文件后缀名
	 * @author Crow
	 * @date 2015-4-8
	 * @param str
	 * @return
	 */
	public static String getSuffix(String str){
		return getSuffix(str, ".", true, "");
	}
	/**
	 * 获取文件后缀名
	 * @author Crow
	 * @date 2015-4-8
	 * @param str
	 * @param retainSeparator 是否保留后缀
	 * @return
	 */
	public static String getSuffix(String str, boolean retainSeparator){
		return getSuffix(str, ".", retainSeparator, "");
	}
	/**
	 * 根据指定的分隔符，获取文件后缀名
	 * @author Crow
	 * @date 2015-4-8
	 * @param str
	 * @param separator 用于截取后缀的分隔符
	 * @return
	 */
	public static String getSuffix(String str, String separator){
		return getSuffix(str, separator, true, "");
	}
	/**
	 * 根据指定的分隔符，获取文件后缀名
	 * @author Crow
	 * @date 2015-4-8
	 * @param str
	 * @param separator 用于截取后缀的分隔符
	 * @param retainSeparator 是否保留后缀
	 * @return
	 */
	public static String getSuffix(String str, String separator, boolean retainSeparator){
		return getSuffix(str, separator, retainSeparator, "");
	}
	/**
	 * 根据指定的分隔符，获取文件后缀名
	 * @author Crow
	 * @date 2015-4-8
	 * @param str 目标字符串
	 * @param separator 用于截取后缀名的分隔符
	 * @param retainSeparator 返回的后缀名是否保留分隔符
	 * @param defaultSuffix 如果文件没有后缀名，将返回的内容
	 * @return
	 */
	public static String getSuffix(String str, String separator, boolean retainSeparator, String defaultSuffix){
		// 如果lastIndexOf执行的结果是-1，说明该文件名字符串没有后缀，返回默认字符串
		int lastPointIndex = str.lastIndexOf(separator);
		if(lastPointIndex != -1){
			defaultSuffix = str.substring(lastPointIndex + (retainSeparator ? 0 : 1));
		}
		return defaultSuffix;
	}
}
