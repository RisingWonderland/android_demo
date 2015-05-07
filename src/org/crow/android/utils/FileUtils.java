package org.crow.android.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
/**
 * File相关操作类
 * @author Crow
 * @date 2015-4-7
 */
public class FileUtils {
	
	private static final String TAG = "Crow_FileUtils";
	
	private static final String FILE_NORMAL = "fileNormal";// 文件是普通文件
	private static final String FILE_DIRECTORY = "fileDirectory";// 文件是目录
	private static final String FILE_SOFT_LINK = "fileSoftLink";// 文件是软连接
	private static final String FILE_UNKNOW = "fileUnknow";// 文件类型未知
	/*
	private static final String FILE_READABLE = "fileReadable";// 文件可读
	private static final String FILE_NON_READABLE = "fileNonReadable";// 文件不可读
	private static final String FILE_WRITABLE = "fileWritable";// 文件可写
	private static final String FILE_NON_WRITABLE = "fileNonWritable";// 文件不可写
	private static final String FILE_EXECUTABLE = "fileExecutable";// 文件可执行
	private static final String FILE_NON_EXECUTABLE = "fileNonExecutable";// 文件不可执行
	*/
	public final static Map<String, String> MIME_TYPE_MAP = new HashMap<String, String>();
	static {
		initMimeType();
	}
	
	/**
	 * 判断一个文件是否存在
	 * @author Crow
	 * @date 2015-4-7
	 * @param file 目标文件对象
	 * @return
	 */
	public static boolean isFileExist(File file){
		if(file.exists()){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断一个文件是否存在
	 * @author Crow
	 * @date 2015-4-7
	 * @param filePath 目标文件的绝对路径
	 * @return
	 */
	public static boolean isFileExist(String filePath){
		File file = new File(filePath);
		return isFileExist(file);
	}
	
	/**
	 * 创建文件夹。如果文件夹存在，不删除，返回true。
	 * @author Crow
	 * @date 2015-4-7
	 * @param file
	 * @return
	 */
	public static boolean createDir(File file){
		return createDir(file, false);
	}
	/**
	 * 创建文件夹。如果文件夹存在，根据overwrite的值判断是否删除原有数据。
	 * @author Crow
	 * @date 2015-4-7
	 * @param file
	 * @param overwrite
	 * @return
	 */
	public static boolean createDir(File file, boolean overwrite){
		if(!file.isFile()){
			if(file.exists() && overwrite == true){
				deleteFile(file);
				file.mkdir();
			}else if(!file.exists()){
				file.mkdirs();
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 删除文件或文件夹
	 * @author Crow
	 * @date 2015-4-7
	 * @param file
	 */
	public static void deleteFile(File file){
		if(file.isFile()){
			file.delete();
		}else if(file.isDirectory()){
			File[] files = file.listFiles();
			if(files == null || files.length == 0){
				file.delete();
				return;
			}
			for(int i=0,l=files.length;i < l;i++){
				deleteFile(files[i]);
			}
			file.delete();
		}
	}
	
	/**
	 * 获取文件后缀名
	 * @author Crow
	 * @date 2015-4-8
	 * @param file
	 * @return
	 */
	public static String getFileSuffix(File file){
		return StringUtils.getSuffix(file.getName());
	}
	/**
	 * 获取文件后缀名
	 * @author Crow
	 * @date 2015-4-8
	 * @param file
	 * @param separator 在返回的字符串中是否保留后缀
	 * @return
	 */
	public static String getFileSuffix(File file, boolean retainSeparator){
		return StringUtils.getSuffix(file.getName(), retainSeparator);
	}
	/**
	 * 根据File对象获得用于启动程序的Intent
	 * 根据文件名的后缀，获得MimeType，然后据此设置Intent
	 * 如果目标文件无后缀，将其视为普通文本文件，在对应集合中MimeType的值为text/plain
	 * @author Crow
	 * @date 2015-4-8
	 * @param file
	 * @return
	 */
	public static Intent getFileIntent(File file){
		String fileName = file.getName();
		// 如果lastIndexOf执行的结果是-1，说明该文件名字符串没有后缀，直接将文件名后缀设置为空字符串
		String postfix = "";
		int lastPointIndex = fileName.lastIndexOf(".");
		if(lastPointIndex != -1){
			postfix = fileName.substring(lastPointIndex + 1).toLowerCase(Locale.getDefault());
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file), MIME_TYPE_MAP.get(postfix));
		return intent;
	}

	/**
	 * 获得文件类型，分别为-普通文件、d目录、l软连接
	 * @param filePath 目标文件目录
	 * @date 2015-4-8
	 * @return
	 */
	public static String getFileType(String filePath){
		String permission = getFilePermission(filePath);
		if(permission != null && permission != ""){
			char flag = permission.charAt(0);
			if(flag == '-'){
				return FILE_NORMAL;
			}else if(flag == 'd'){
				return FILE_DIRECTORY;
			}else if(flag == 'l'){
				return FILE_SOFT_LINK;
			}
		}
		return FILE_UNKNOW;
	}
	
	/**
	 * 判断文件是否可以写入
	 * @param binPath 目标文件路径
	 * @date 2015-4-8
	 * @return
	 */
	public static boolean isFileReadable(String filePath) {
		String permission = getFilePermission(filePath);
		if(permission != null && permission != ""){
			char flag = permission.charAt(1);
			if(flag == 's' || flag == 'r'){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断文件是否可以读取
	 * @param binPath 目标文件路径
	 * @date 2015-4-8
	 * @return
	 */
	public static boolean isFileWritable(String filePath) {
		String permission = getFilePermission(filePath);
		if(permission != null && permission != ""){
			char flag = permission.charAt(2);
			if(flag == 's' || flag == 'w'){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断文件是否可以执行
	 * @param binPath 目标文件路径
	 * @date 2015-4-8
	 * @return
	 */
	public static boolean isFileExecutable(String filePath) {
		String permission = getFilePermission(filePath);
		if(permission != null && permission != ""){
			char flag = permission.charAt(3);
			if(flag == 's' || flag == 'x'){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获得文件的权限信息，一般文本格式如下：lrwxrwxrwx
	 * @param filePath 目标文件的路径
	 * @date 2015-4-8
	 * @return 返回char型数组
	 */
	public static String getFilePermission(String filePath){
		Process process = null;
		String permission = null;
		try {
			process = Runtime.getRuntime().exec("ls -l " + filePath);
			// 获得返回内容
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String str = br.readLine();
			Log.i(TAG, str);
			if(str != null && str.length() >= 10){
				permission = str.substring(0, 10);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(process != null){
				process.destroy();
			}
		}
		return permission;
	}
	
	

	/**
	 * 初始化文件后缀与MimeType对应关系集合
	 * @author Crow
	 * @date 2015-4-8
	 */
	private static void initMimeType() {
		MIME_TYPE_MAP.put("", "text/plain");
		MIME_TYPE_MAP.put("3dm", "x-world/x-3dmf");
		MIME_TYPE_MAP.put("3dmf", "x-world/x-3dmf");
		MIME_TYPE_MAP.put("a", "application/octet-stream");
		MIME_TYPE_MAP.put("aab", "application/x-authorware-bin");
		MIME_TYPE_MAP.put("aam", "application/x-authorware-map");
		MIME_TYPE_MAP.put("aas", "application/x-authorware-seg");
		MIME_TYPE_MAP.put("abc", "text/vnd.abc");
		MIME_TYPE_MAP.put("acgi", "text/html");
		MIME_TYPE_MAP.put("afl", "video/animaflex");
		MIME_TYPE_MAP.put("ai", "application/postscript");
		MIME_TYPE_MAP.put("aif", "audio/aiff");
//		MIME_TYPE_MAP.put("aif", "audio/x-aiff");
		MIME_TYPE_MAP.put("aifc", "audio/aiff");
//		MIME_TYPE_MAP.put("aifc", "audio/x-aiff");
		MIME_TYPE_MAP.put("aiff", "audio/aiff");
//		MIME_TYPE_MAP.put("aiff", "audio/x-aiff");
		MIME_TYPE_MAP.put("aim", "application/x-aim");
		MIME_TYPE_MAP.put("aip", "text/x-audiosoft-intra");
		MIME_TYPE_MAP.put("ani", "application/x-navi-animation");
		MIME_TYPE_MAP.put("aos", "application/x-nokia-9000-communicator-add-on-software");
		MIME_TYPE_MAP.put("apk", "application/vnd.android.package-archive");
		MIME_TYPE_MAP.put("aps", "application/mime");
		MIME_TYPE_MAP.put("arc", "application/octet-stream");
		MIME_TYPE_MAP.put("arj", "application/arj");
//		MIME_TYPE_MAP.put("arj", "application/octet-stream");
		MIME_TYPE_MAP.put("art", "image/x-jg");
		MIME_TYPE_MAP.put("asf", "video/x-ms-asf");
		MIME_TYPE_MAP.put("asm", "text/x-asm");
		MIME_TYPE_MAP.put("asp", "text/asp");
//		MIME_TYPE_MAP.put("asx", "application/x-mplayer2");
		MIME_TYPE_MAP.put("asx", "video/x-ms-asf");
//		MIME_TYPE_MAP.put("asx", "video/x-ms-asf-plugin");
//		MIME_TYPE_MAP.put("au", "audio/basic");
		MIME_TYPE_MAP.put("au", "audio/x-au");
//		MIME_TYPE_MAP.put("avi", "application/x-troff-msvideo");
		MIME_TYPE_MAP.put("avi", "video/avi");
//		MIME_TYPE_MAP.put("avi", "video/msvideo");
//		MIME_TYPE_MAP.put("avi", "video/x-msvideo");
		MIME_TYPE_MAP.put("avs", "video/avs-video");
		MIME_TYPE_MAP.put("bcpio", "application/x-bcpio");
//		MIME_TYPE_MAP.put("bin", "application/mac-binary");
//		MIME_TYPE_MAP.put("bin", "application/macbinary");
//		MIME_TYPE_MAP.put("bin", "application/octet-stream");
		MIME_TYPE_MAP.put("bin", "application/x-binary");
//		MIME_TYPE_MAP.put("bin", "application/x-macbinary");
		MIME_TYPE_MAP.put("bm", "image/bmp");
		MIME_TYPE_MAP.put("bmp", "image/bmp");
//		MIME_TYPE_MAP.put("bmp", "image/x-windows-bmp");
		MIME_TYPE_MAP.put("boo", "application/book");
		MIME_TYPE_MAP.put("book", "application/book");
		MIME_TYPE_MAP.put("boz", "application/x-bzip2");
		MIME_TYPE_MAP.put("bsh", "application/x-bsh");
		MIME_TYPE_MAP.put("bz", "application/x-bzip");
		MIME_TYPE_MAP.put("bz2", "application/x-bzip2");
		MIME_TYPE_MAP.put("c", "text/plain");
//		MIME_TYPE_MAP.put("c", "text/x-c");
		MIME_TYPE_MAP.put("c++", "text/plain");
		MIME_TYPE_MAP.put("cat", "application/vnd.ms-pki.seccat");
		MIME_TYPE_MAP.put("cc", "text/plain");
//		MIME_TYPE_MAP.put("cc", "text/x-c");
		MIME_TYPE_MAP.put("ccad", "application/clariscad");
		MIME_TYPE_MAP.put("cco", "application/x-cocoa");
		MIME_TYPE_MAP.put("cdf", "application/cdf");
//		MIME_TYPE_MAP.put("cdf", "application/x-cdf");
//		MIME_TYPE_MAP.put("cdf", "application/x-netcdf");
		MIME_TYPE_MAP.put("cer", "application/pkix-cert");
//		MIME_TYPE_MAP.put("cer", "application/x-x509-ca-cert");
		MIME_TYPE_MAP.put("cha", "application/x-chat");
		MIME_TYPE_MAP.put("chat", "application/x-chat");
		MIME_TYPE_MAP.put("class", "application/java");
//		MIME_TYPE_MAP.put("class", "application/java-byte-code");
//		MIME_TYPE_MAP.put("class", "application/x-java-class");
//		MIME_TYPE_MAP.put("com", "application/octet-stream");
		MIME_TYPE_MAP.put("com", "text/plain");
		MIME_TYPE_MAP.put("conf", "text/plain");
		MIME_TYPE_MAP.put("cpio", "application/x-cpio");
		MIME_TYPE_MAP.put("cpp", "text/x-c");
		MIME_TYPE_MAP.put("cpt", "application/mac-compactpro");
//		MIME_TYPE_MAP.put("cpt", "application/x-compactpro");
//		MIME_TYPE_MAP.put("cpt", "application/x-cpt");
		MIME_TYPE_MAP.put("crl", "application/pkcs-crl");
//		MIME_TYPE_MAP.put("crl", "application/pkix-crl");
//		MIME_TYPE_MAP.put("crt", "application/pkix-cert");
//		MIME_TYPE_MAP.put("crt", "application/x-x509-ca-cert");
//		MIME_TYPE_MAP.put("crt", "application/x-x509-user-cert");
//		MIME_TYPE_MAP.put("csh", "application/x-csh");
		MIME_TYPE_MAP.put("csh", "text/x-script.csh");
//		MIME_TYPE_MAP.put("css", "application/x-pointplus");
		MIME_TYPE_MAP.put("css", "text/css");
		MIME_TYPE_MAP.put("cxx", "text/plain");
		MIME_TYPE_MAP.put("dcr", "application/x-director");
		MIME_TYPE_MAP.put("deepv", "application/x-deepv");
		MIME_TYPE_MAP.put("def", "text/plain");
		MIME_TYPE_MAP.put("der", "application/x-x509-ca-cert");
		MIME_TYPE_MAP.put("dif", "video/x-dv");
		MIME_TYPE_MAP.put("dir", "application/x-director");
		MIME_TYPE_MAP.put("dl", "video/dl");
//		MIME_TYPE_MAP.put("dl", "video/x-dl");
		MIME_TYPE_MAP.put("doc", "application/msword");
		MIME_TYPE_MAP.put("docx", "application/msword");
		MIME_TYPE_MAP.put("dot", "application/msword");
		MIME_TYPE_MAP.put("dp", "application/commonground");
		MIME_TYPE_MAP.put("drw", "application/drafting");
		MIME_TYPE_MAP.put("dump", "application/octet-stream");
		MIME_TYPE_MAP.put("dv", "video/x-dv");
		MIME_TYPE_MAP.put("dvi", "application/x-dvi");
		MIME_TYPE_MAP.put("dwf", "drawing/x-dwf (old)");
//		MIME_TYPE_MAP.put("dwf", "model/vnd.dwf");
		MIME_TYPE_MAP.put("dwg", "application/acad");
//		MIME_TYPE_MAP.put("dwg", "image/vnd.dwg");
//		MIME_TYPE_MAP.put("dwg", "image/x-dwg");
		MIME_TYPE_MAP.put("dxf", "application/dxf");
//		MIME_TYPE_MAP.put("dxf", "image/vnd.dwg");
//		MIME_TYPE_MAP.put("dxf", "image/x-dwg");
		MIME_TYPE_MAP.put("dxr", "application/x-director");
		MIME_TYPE_MAP.put("el", "text/x-script.elisp");
//		MIME_TYPE_MAP.put("elc", "application/x-bytecode.elisp (compiled elisp)");
		MIME_TYPE_MAP.put("elc", "application/x-elc");
		MIME_TYPE_MAP.put("env", "application/x-envoy");
		MIME_TYPE_MAP.put("eps", "application/postscript");
		MIME_TYPE_MAP.put("es", "application/x-esrehber");
		MIME_TYPE_MAP.put("etx", "text/x-setext");
		MIME_TYPE_MAP.put("evy", "application/envoy");
//		MIME_TYPE_MAP.put("evy", "application/x-envoy");
		MIME_TYPE_MAP.put("exe", "application/octet-stream");
		MIME_TYPE_MAP.put("f", "text/plain");
//		MIME_TYPE_MAP.put("f", "text/x-fortran");
		MIME_TYPE_MAP.put("f77", "text/x-fortran");
		MIME_TYPE_MAP.put("f90", "text/plain");
//		MIME_TYPE_MAP.put("f90", "text/x-fortran");
		MIME_TYPE_MAP.put("fdf", "application/vnd.fdf");
//		MIME_TYPE_MAP.put("fif", "application/fractals");
		MIME_TYPE_MAP.put("fif", "image/fif");
		MIME_TYPE_MAP.put("fli", "video/fli");
//		MIME_TYPE_MAP.put("fli", "video/x-fli");
		MIME_TYPE_MAP.put("flo", "image/florian");
		MIME_TYPE_MAP.put("flx", "text/vnd.fmi.flexstor");
		MIME_TYPE_MAP.put("fmf", "video/x-atomic3d-feature");
		MIME_TYPE_MAP.put("for", "text/plain");
//		MIME_TYPE_MAP.put("for", "text/x-fortran");
		MIME_TYPE_MAP.put("fpx", "image/vnd.fpx");
//		MIME_TYPE_MAP.put("fpx", "image/vnd.net-fpx");
		MIME_TYPE_MAP.put("frl", "application/freeloader");
		MIME_TYPE_MAP.put("funk", "audio/make");
		MIME_TYPE_MAP.put("g", "text/plain");
		MIME_TYPE_MAP.put("g3", "image/g3fax");
		MIME_TYPE_MAP.put("gif", "image/gif");
		MIME_TYPE_MAP.put("gl", "video/gl");
//		MIME_TYPE_MAP.put("gl", "video/x-gl");
		MIME_TYPE_MAP.put("gsd", "audio/x-gsm");
		MIME_TYPE_MAP.put("gsm", "audio/x-gsm");
		MIME_TYPE_MAP.put("gsp", "application/x-gsp");
		MIME_TYPE_MAP.put("gss", "application/x-gss");
		MIME_TYPE_MAP.put("gtar", "application/x-gtar");
//		MIME_TYPE_MAP.put("gz", "application/x-compressed");
		MIME_TYPE_MAP.put("gz", "application/x-gzip");
		MIME_TYPE_MAP.put("gzip", "application/x-gzip");
//		MIME_TYPE_MAP.put("gzip", "multipart/x-gzip");
		MIME_TYPE_MAP.put("h", "text/plain");
//		MIME_TYPE_MAP.put("h", "text/x-h");
		MIME_TYPE_MAP.put("hdf", "application/x-hdf");
		MIME_TYPE_MAP.put("help", "application/x-helpfile");
		MIME_TYPE_MAP.put("hgl", "application/vnd.hp-hpgl");
		MIME_TYPE_MAP.put("hh", "text/plain");
//		MIME_TYPE_MAP.put("hh", "text/x-h");
		MIME_TYPE_MAP.put("hlb", "text/x-script");
		MIME_TYPE_MAP.put("hlp", "application/hlp");
//		MIME_TYPE_MAP.put("hlp", "application/x-helpfile");
//		MIME_TYPE_MAP.put("hlp", "application/x-winhelp");
		MIME_TYPE_MAP.put("hpg", "application/vnd.hp-hpgl");
		MIME_TYPE_MAP.put("hpgl", "application/vnd.hp-hpgl");
		MIME_TYPE_MAP.put("hqx", "application/binhex");
//		MIME_TYPE_MAP.put("hqx", "application/binhex4");
//		MIME_TYPE_MAP.put("hqx", "application/mac-binhex");
//		MIME_TYPE_MAP.put("hqx", "application/mac-binhex40");
//		MIME_TYPE_MAP.put("hqx", "application/x-binhex40");
//		MIME_TYPE_MAP.put("hqx", "application/x-mac-binhex40");
		MIME_TYPE_MAP.put("hta", "application/hta");
		MIME_TYPE_MAP.put("htc", "text/x-component");
		MIME_TYPE_MAP.put("htm", "text/html");
		MIME_TYPE_MAP.put("html", "text/html");
		MIME_TYPE_MAP.put("htmls", "text/html");
		MIME_TYPE_MAP.put("htt", "text/webviewhtml");
		MIME_TYPE_MAP.put("htx", "text/html");
		MIME_TYPE_MAP.put("ice", "x-conference/x-cooltalk");
		MIME_TYPE_MAP.put("ico", "image/x-icon");
		MIME_TYPE_MAP.put("idc", "text/plain");
		MIME_TYPE_MAP.put("ief", "image/ief");
		MIME_TYPE_MAP.put("iefs", "image/ief");
//		MIME_TYPE_MAP.put("iges", "application/iges");
		MIME_TYPE_MAP.put("iges", "model/iges");
//		MIME_TYPE_MAP.put("igs", "application/iges");
		MIME_TYPE_MAP.put("igs", "model/iges");
		MIME_TYPE_MAP.put("ima", "application/x-ima");
		MIME_TYPE_MAP.put("imap", "application/x-httpd-imap");
		MIME_TYPE_MAP.put("inf", "application/inf");
		MIME_TYPE_MAP.put("ins", "application/x-internett-signup");
		MIME_TYPE_MAP.put("ip", "application/x-ip2");
		MIME_TYPE_MAP.put("isu", "video/x-isvideo");
		MIME_TYPE_MAP.put("it", "audio/it");
		MIME_TYPE_MAP.put("iv", "application/x-inventor");
		MIME_TYPE_MAP.put("ivr", "i-world/i-vrml");
		MIME_TYPE_MAP.put("ivy", "application/x-livescreen");
		MIME_TYPE_MAP.put("jam", "audio/x-jam");
		MIME_TYPE_MAP.put("jav", "text/plain");
//		MIME_TYPE_MAP.put("jav", "text/x-java-source");
		MIME_TYPE_MAP.put("java", "text/plain");
//		MIME_TYPE_MAP.put("java", "text/x-java-source");
		MIME_TYPE_MAP.put("jcm", "application/x-java-commerce");
		MIME_TYPE_MAP.put("jfif", "image/jpeg");
//		MIME_TYPE_MAP.put("jfif", "image/pjpeg");
		MIME_TYPE_MAP.put("jfif-tbnl", "image/jpeg");
		MIME_TYPE_MAP.put("jpe", "image/jpeg");
//		MIME_TYPE_MAP.put("jpe", "image/pjpeg");
		MIME_TYPE_MAP.put("jpeg", "image/jpeg");
//		MIME_TYPE_MAP.put("jpeg", "image/pjpeg");
		MIME_TYPE_MAP.put("jpg", "image/jpeg");
//		MIME_TYPE_MAP.put("jpg", "image/pjpeg");
//		MIME_TYPE_MAP.put("jps", "image/x-jps");
//		MIME_TYPE_MAP.put("js", "application/x-javascript");
//		MIME_TYPE_MAP.put("js", "application/javascript");
//		MIME_TYPE_MAP.put("js", "application/ecmascript");
		MIME_TYPE_MAP.put("js", "text/javascript");
//		MIME_TYPE_MAP.put("js", "text/ecmascript");
		MIME_TYPE_MAP.put("jut", "image/jutvision");
		MIME_TYPE_MAP.put("kar", "audio/midi");
//		MIME_TYPE_MAP.put("kar", "music/x-karaoke");
//		MIME_TYPE_MAP.put("ksh", "application/x-ksh");
		MIME_TYPE_MAP.put("ksh", "text/x-script.ksh");
		MIME_TYPE_MAP.put("la", "audio/nspaudio");
//		MIME_TYPE_MAP.put("la", "audio/x-nspaudio");
		MIME_TYPE_MAP.put("lam", "audio/x-liveaudio");
		MIME_TYPE_MAP.put("latex", "application/x-latex");
		MIME_TYPE_MAP.put("lha", "application/lha");
//		MIME_TYPE_MAP.put("lha", "application/octet-stream");
//		MIME_TYPE_MAP.put("lha", "application/x-lha");
		MIME_TYPE_MAP.put("lhx", "application/octet-stream");
		MIME_TYPE_MAP.put("list", "text/plain");
		MIME_TYPE_MAP.put("lma", "audio/nspaudio");
//		MIME_TYPE_MAP.put("lma", "audio/x-nspaudio");
		MIME_TYPE_MAP.put("log", "text/plain");
//		MIME_TYPE_MAP.put("lsp", "application/x-lisp");
		MIME_TYPE_MAP.put("lsp", "text/x-script.lisp");
		MIME_TYPE_MAP.put("lst", "text/plain");
//		MIME_TYPE_MAP.put("lsx", "text/x-la-asf");
		MIME_TYPE_MAP.put("ltx", "application/x-latex");
		MIME_TYPE_MAP.put("lzh", "application/octet-stream");
//		MIME_TYPE_MAP.put("lzh", "application/x-lzh");
		MIME_TYPE_MAP.put("lzx", "application/lzx");
//		MIME_TYPE_MAP.put("lzx", "application/octet-stream");
//		MIME_TYPE_MAP.put("lzx", "application/x-lzx");
		MIME_TYPE_MAP.put("m", "text/plain");
//		MIME_TYPE_MAP.put("m", "text/x-m");
		MIME_TYPE_MAP.put("m1v", "video/mpeg");
		MIME_TYPE_MAP.put("m2a", "audio/mpeg");
		MIME_TYPE_MAP.put("m2v", "video/mpeg");
		MIME_TYPE_MAP.put("m3u", "audio/x-mpequrl");
		MIME_TYPE_MAP.put("man", "application/x-troff-man");
		MIME_TYPE_MAP.put("map", "application/x-navimap");
		MIME_TYPE_MAP.put("mar", "text/plain");
		MIME_TYPE_MAP.put("mbd", "application/mbedlet");
		MIME_TYPE_MAP.put("mc$", "application/x-magic-cap-package-1.0");
		MIME_TYPE_MAP.put("mcd", "application/mcad");
//		MIME_TYPE_MAP.put("mcd", "application/x-mathcad");
		MIME_TYPE_MAP.put("mcf", "image/vasa");
//		MIME_TYPE_MAP.put("mcf", "text/mcf");
		MIME_TYPE_MAP.put("mcp", "application/netmc");
		MIME_TYPE_MAP.put("me", "application/x-troff-me");
		MIME_TYPE_MAP.put("mht", "message/rfc822");
		MIME_TYPE_MAP.put("mhtml", "message/rfc822");
//		MIME_TYPE_MAP.put("mid", "application/x-midi");
		MIME_TYPE_MAP.put("mid", "audio/midi");
//		MIME_TYPE_MAP.put("mid", "audio/x-mid");
//		MIME_TYPE_MAP.put("mid", "audio/x-midi");
//		MIME_TYPE_MAP.put("mid", "music/crescendo");
//		MIME_TYPE_MAP.put("mid", "x-music/x-midi");
//		MIME_TYPE_MAP.put("midi", "application/x-midi");
		MIME_TYPE_MAP.put("midi", "audio/midi");
//		MIME_TYPE_MAP.put("midi", "audio/x-mid");
//		MIME_TYPE_MAP.put("midi", "audio/x-midi");
//		MIME_TYPE_MAP.put("midi", "music/crescendo");
//		MIME_TYPE_MAP.put("midi", "x-music/x-midi");
		MIME_TYPE_MAP.put("mif", "application/x-frame");
//		MIME_TYPE_MAP.put("mif", "application/x-mif");
//		MIME_TYPE_MAP.put("mime", "message/rfc822");
		MIME_TYPE_MAP.put("mime", "www/mime");
		MIME_TYPE_MAP.put("mjf", "audio/x-vnd.audioexplosion.mjuicemediafile");
		MIME_TYPE_MAP.put("mjpg", "video/x-motion-jpeg");
		MIME_TYPE_MAP.put("mm", "application/base64");
//		MIME_TYPE_MAP.put("mm", "application/x-meme");
		MIME_TYPE_MAP.put("mme", "application/base64");
		MIME_TYPE_MAP.put("mod", "audio/mod");
//		MIME_TYPE_MAP.put("mod", "audio/x-mod");
		MIME_TYPE_MAP.put("moov", "video/quicktime");
		MIME_TYPE_MAP.put("mov", "video/quicktime");
		MIME_TYPE_MAP.put("movie", "video/x-sgi-movie");
		MIME_TYPE_MAP.put("mp2", "audio/mpeg");
//		MIME_TYPE_MAP.put("mp2", "audio/x-mpeg");
//		MIME_TYPE_MAP.put("mp2", "video/mpeg");
//		MIME_TYPE_MAP.put("mp2", "video/x-mpeg");
//		MIME_TYPE_MAP.put("mp2", "video/x-mpeq2a");
		MIME_TYPE_MAP.put("mp3", "audio/mpeg3");
//		MIME_TYPE_MAP.put("mp3", "audio/x-mpeg-3");
//		MIME_TYPE_MAP.put("mp3", "video/mpeg");
//		MIME_TYPE_MAP.put("mp3", "video/x-mpeg");
		MIME_TYPE_MAP.put("mpa", "audio/mpeg");
//		MIME_TYPE_MAP.put("mpa", "video/mpeg");
		MIME_TYPE_MAP.put("mpc", "application/x-project");
		MIME_TYPE_MAP.put("mpe", "video/mpeg");
//		MIME_TYPE_MAP.put("mpeg", "video/mpeg");
//		MIME_TYPE_MAP.put("mpg", "audio/mpeg");
		MIME_TYPE_MAP.put("mpg", "video/mpeg");
		MIME_TYPE_MAP.put("mpga", "audio/mpeg");
		MIME_TYPE_MAP.put("mpp", "application/vnd.ms-project");
		MIME_TYPE_MAP.put("mpt", "application/x-project");
		MIME_TYPE_MAP.put("mpv", "application/x-project");
		MIME_TYPE_MAP.put("mpx", "application/x-project");
		MIME_TYPE_MAP.put("mrc", "application/marc");
		MIME_TYPE_MAP.put("ms", "application/x-troff-ms");
		MIME_TYPE_MAP.put("mv", "video/x-sgi-movie");
		MIME_TYPE_MAP.put("my", "audio/make");
		MIME_TYPE_MAP.put("mzz", "application/x-vnd.audioexplosion.mzz");
		MIME_TYPE_MAP.put("nap", "image/naplps");
		MIME_TYPE_MAP.put("naplps", "image/naplps");
		MIME_TYPE_MAP.put("nc", "application/x-netcdf");
		MIME_TYPE_MAP.put("ncm", "application/vnd.nokia.configuration-message");
		MIME_TYPE_MAP.put("nif", "image/x-niff");
		MIME_TYPE_MAP.put("niff", "image/x-niff");
		MIME_TYPE_MAP.put("nix", "application/x-mix-transfer");
		MIME_TYPE_MAP.put("nsc", "application/x-conference");
		MIME_TYPE_MAP.put("nvd", "application/x-navidoc");
		MIME_TYPE_MAP.put("o", "application/octet-stream");
		MIME_TYPE_MAP.put("oda", "application/oda");
		MIME_TYPE_MAP.put("omc", "application/x-omc");
		MIME_TYPE_MAP.put("omcd", "application/x-omcdatamaker");
		MIME_TYPE_MAP.put("omcr", "application/x-omcregerator");
		MIME_TYPE_MAP.put("p", "text/x-pascal");
		MIME_TYPE_MAP.put("p10", "application/pkcs10");
//		MIME_TYPE_MAP.put("p10", "application/x-pkcs10");
		MIME_TYPE_MAP.put("p12", "application/pkcs-12");
//		MIME_TYPE_MAP.put("p12", "application/x-pkcs12");
		MIME_TYPE_MAP.put("p7a", "application/x-pkcs7-signature");
		MIME_TYPE_MAP.put("p7c", "application/pkcs7-mime");
//		MIME_TYPE_MAP.put("p7c", "application/x-pkcs7-mime");
		MIME_TYPE_MAP.put("p7m", "application/pkcs7-mime");
//		MIME_TYPE_MAP.put("p7m", "application/x-pkcs7-mime");
		MIME_TYPE_MAP.put("p7r", "application/x-pkcs7-certreqresp");
		MIME_TYPE_MAP.put("p7s", "application/pkcs7-signature");
		MIME_TYPE_MAP.put("part", "application/pro_eng");
		MIME_TYPE_MAP.put("pas", "text/pascal");
		MIME_TYPE_MAP.put("pbm", "image/x-portable-bitmap");
		MIME_TYPE_MAP.put("pcl", "application/vnd.hp-pcl");
//		MIME_TYPE_MAP.put("pcl", "application/x-pcl");
		MIME_TYPE_MAP.put("pct", "image/x-pict");
		MIME_TYPE_MAP.put("pcx", "image/x-pcx");
		MIME_TYPE_MAP.put("pdb", "chemical/x-pdb");
		MIME_TYPE_MAP.put("pdf", "application/pdf");
		MIME_TYPE_MAP.put("pfunk", "audio/make");
//		MIME_TYPE_MAP.put("pfunk", "audio/make.my.funk");
		MIME_TYPE_MAP.put("pgm", "image/x-portable-graymap");
//		MIME_TYPE_MAP.put("pgm", "image/x-portable-greymap");
		MIME_TYPE_MAP.put("pic", "image/pict");
		MIME_TYPE_MAP.put("pict", "image/pict");
		MIME_TYPE_MAP.put("pkg", "application/x-newton-compatible-pkg");
		MIME_TYPE_MAP.put("pko", "application/vnd.ms-pki.pko");
		MIME_TYPE_MAP.put("pl", "text/plain");
//		MIME_TYPE_MAP.put("pl", "text/x-script.perl");
		MIME_TYPE_MAP.put("plx", "application/x-pixclscript");
		MIME_TYPE_MAP.put("pm", "image/x-xpixmap");
//		MIME_TYPE_MAP.put("pm", "text/x-script.perl-module");
		MIME_TYPE_MAP.put("pm4", "application/x-pagemaker");
		MIME_TYPE_MAP.put("pm5", "application/x-pagemaker");
		MIME_TYPE_MAP.put("png", "image/png");
//		MIME_TYPE_MAP.put("pnm", "application/x-portable-anymap");
		MIME_TYPE_MAP.put("pnm", "image/x-portable-anymap");
		MIME_TYPE_MAP.put("pot", "application/mspowerpoint");
//		MIME_TYPE_MAP.put("pot", "application/vnd.ms-powerpoint");
		MIME_TYPE_MAP.put("pov", "model/x-pov");
		MIME_TYPE_MAP.put("ppa", "application/vnd.ms-powerpoint");
		MIME_TYPE_MAP.put("ppm", "image/x-portable-pixmap");
		MIME_TYPE_MAP.put("pps", "application/mspowerpoint");
//		MIME_TYPE_MAP.put("pps", "application/vnd.ms-powerpoint");
		MIME_TYPE_MAP.put("ppt", "application/mspowerpoint");
//		MIME_TYPE_MAP.put("ppt", "application/powerpoint");
//		MIME_TYPE_MAP.put("ppt", "application/vnd.ms-powerpoint");
//		MIME_TYPE_MAP.put("ppt", "application/x-mspowerpoint");
		MIME_TYPE_MAP.put("ppz", "application/mspowerpoint");
		MIME_TYPE_MAP.put("pre", "application/x-freelance");
		MIME_TYPE_MAP.put("prt", "application/pro_eng");
		MIME_TYPE_MAP.put("ps", "application/postscript");
		MIME_TYPE_MAP.put("psd", "application/octet-stream");
		MIME_TYPE_MAP.put("pvu", "paleovu/x-pv");
		MIME_TYPE_MAP.put("pwz", "application/vnd.ms-powerpoint");
		MIME_TYPE_MAP.put("py", "text/x-script.phyton");
		MIME_TYPE_MAP.put("pyc", "application/x-bytecode.python");
		MIME_TYPE_MAP.put("qcp", "audio/vnd.qcelp");
		MIME_TYPE_MAP.put("qd3", "x-world/x-3dmf");
		MIME_TYPE_MAP.put("qd3d", "x-world/x-3dmf");
		MIME_TYPE_MAP.put("qif", "image/x-quicktime");
		MIME_TYPE_MAP.put("qt", "video/quicktime");
		MIME_TYPE_MAP.put("qtc", "video/x-qtc");
		MIME_TYPE_MAP.put("qti", "image/x-quicktime");
		MIME_TYPE_MAP.put("qtif", "image/x-quicktime");
		MIME_TYPE_MAP.put("ra", "audio/x-pn-realaudio");
//		MIME_TYPE_MAP.put("ra", "audio/x-pn-realaudio-plugin");
//		MIME_TYPE_MAP.put("ra", "audio/x-realaudio");
		MIME_TYPE_MAP.put("ram", "audio/x-pn-realaudio");
//		MIME_TYPE_MAP.put("ras", "application/x-cmu-raster");
		MIME_TYPE_MAP.put("ras", "image/cmu-raster");
//		MIME_TYPE_MAP.put("ras", "image/x-cmu-raster");
		MIME_TYPE_MAP.put("rast", "image/cmu-raster");
		MIME_TYPE_MAP.put("rexx", "text/x-script.rexx");
		MIME_TYPE_MAP.put("rf", "image/vnd.rn-realflash");
		MIME_TYPE_MAP.put("rgb", "image/x-rgb");
//		MIME_TYPE_MAP.put("rm", "application/vnd.rn-realmedia");
		MIME_TYPE_MAP.put("rm", "audio/x-pn-realaudio");
		MIME_TYPE_MAP.put("rmi", "audio/mid");
		MIME_TYPE_MAP.put("rmm", "audio/x-pn-realaudio");
		MIME_TYPE_MAP.put("rmp", "audio/x-pn-realaudio");
//		MIME_TYPE_MAP.put("rmp", "audio/x-pn-realaudio-plugin");
		MIME_TYPE_MAP.put("rng", "application/ringing-tones");
//		MIME_TYPE_MAP.put("rng", "application/vnd.nokia.ringing-tone");
		MIME_TYPE_MAP.put("rnx", "application/vnd.rn-realplayer");
		MIME_TYPE_MAP.put("roff", "application/x-troff");
		MIME_TYPE_MAP.put("rp", "image/vnd.rn-realpix");
		MIME_TYPE_MAP.put("rpm", "audio/x-pn-realaudio-plugin");
		MIME_TYPE_MAP.put("rt", "text/richtext");
//		MIME_TYPE_MAP.put("rt", "text/vnd.rn-realtext");
//		MIME_TYPE_MAP.put("rtf", "application/rtf");
//		MIME_TYPE_MAP.put("rtf", "application/x-rtf");
		MIME_TYPE_MAP.put("rtf", "text/richtext");
//		MIME_TYPE_MAP.put("rtx", "application/rtf");
		MIME_TYPE_MAP.put("rtx", "text/richtext");
		MIME_TYPE_MAP.put("rv", "video/vnd.rn-realvideo");
		MIME_TYPE_MAP.put("s", "text/x-asm");
		MIME_TYPE_MAP.put("s3m", "audio/s3m");
		MIME_TYPE_MAP.put("saveme", "application/octet-stream");
		MIME_TYPE_MAP.put("sbk", "application/x-tbook");
//		MIME_TYPE_MAP.put("scm", "application/x-lotusscreencam");
//		MIME_TYPE_MAP.put("scm", "text/x-script.guile");
//		MIME_TYPE_MAP.put("scm", "text/x-script.scheme");
		MIME_TYPE_MAP.put("scm", "video/x-scm");
		MIME_TYPE_MAP.put("sdml", "text/plain");
		MIME_TYPE_MAP.put("sdp", "application/sdp");
//		MIME_TYPE_MAP.put("sdp", "application/x-sdp");
		MIME_TYPE_MAP.put("sdr", "application/sounder");
		MIME_TYPE_MAP.put("sea", "application/sea");
//		MIME_TYPE_MAP.put("sea", "application/x-sea");
		MIME_TYPE_MAP.put("set", "application/set");
		MIME_TYPE_MAP.put("sgm", "text/sgml");
//		MIME_TYPE_MAP.put("sgm", "text/x-sgml");
		MIME_TYPE_MAP.put("sgml", "text/sgml");
//		MIME_TYPE_MAP.put("sgml", "text/x-sgml");
//		MIME_TYPE_MAP.put("sh", "application/x-bsh");
//		MIME_TYPE_MAP.put("sh", "application/x-sh");
//		MIME_TYPE_MAP.put("sh", "application/x-shar");
		MIME_TYPE_MAP.put("sh", "text/x-script.sh");
		MIME_TYPE_MAP.put("shar", "application/x-bsh");
//		MIME_TYPE_MAP.put("shar", "application/x-shar");
		MIME_TYPE_MAP.put("shtml", "text/html");
//		MIME_TYPE_MAP.put("shtml", "text/x-server-parsed-html");
		MIME_TYPE_MAP.put("sid", "audio/x-psid");
		MIME_TYPE_MAP.put("sit", "application/x-sit");
//		MIME_TYPE_MAP.put("sit", "application/x-stuffit");
		MIME_TYPE_MAP.put("skd", "application/x-koan");
		MIME_TYPE_MAP.put("skm", "application/x-koan");
		MIME_TYPE_MAP.put("skp", "application/x-koan");
		MIME_TYPE_MAP.put("skt", "application/x-koan");
		MIME_TYPE_MAP.put("sl", "application/x-seelogo");
		MIME_TYPE_MAP.put("smi", "application/smil");
		MIME_TYPE_MAP.put("smil", "application/smil");
		MIME_TYPE_MAP.put("snd", "audio/basic");
//		MIME_TYPE_MAP.put("snd", "audio/x-adpcm");
		MIME_TYPE_MAP.put("sol", "application/solids");
//		MIME_TYPE_MAP.put("spc", "application/x-pkcs7-certificates");
		MIME_TYPE_MAP.put("spc", "text/x-speech");
		MIME_TYPE_MAP.put("spl", "application/futuresplash");
		MIME_TYPE_MAP.put("spr", "application/x-sprite");
		MIME_TYPE_MAP.put("sprite", "application/x-sprite");
		MIME_TYPE_MAP.put("src", "application/x-wais-source");
		MIME_TYPE_MAP.put("ssi", "text/x-server-parsed-html");
		MIME_TYPE_MAP.put("ssm", "application/streamingmedia");
		MIME_TYPE_MAP.put("sst", "application/vnd.ms-pki.certstore");
		MIME_TYPE_MAP.put("step", "application/step");
		MIME_TYPE_MAP.put("stl", "application/sla");
//		MIME_TYPE_MAP.put("stl", "application/vnd.ms-pki.stl");
//		MIME_TYPE_MAP.put("stl", "application/x-navistyle");
		MIME_TYPE_MAP.put("stp", "application/step");
		MIME_TYPE_MAP.put("sv4cpio", "application/x-sv4cpio");
		MIME_TYPE_MAP.put("sv4crc", "application/x-sv4crc");
		MIME_TYPE_MAP.put("svf", "image/vnd.dwg");
//		MIME_TYPE_MAP.put("svf", "image/x-dwg");
//		MIME_TYPE_MAP.put("svr", "application/x-world");
		MIME_TYPE_MAP.put("svr", "x-world/x-svr");
		MIME_TYPE_MAP.put("swf", "application/x-shockwave-flash");
		MIME_TYPE_MAP.put("t", "application/x-troff");
		MIME_TYPE_MAP.put("talk", "text/x-speech");
		MIME_TYPE_MAP.put("tar", "application/x-tar");
		MIME_TYPE_MAP.put("tbk", "application/toolbook");
//		MIME_TYPE_MAP.put("tbk", "application/x-tbook");
//		MIME_TYPE_MAP.put("tcl", "application/x-tcl");
		MIME_TYPE_MAP.put("tcl", "text/x-script.tcl");
		MIME_TYPE_MAP.put("tcsh", "text/x-script.tcsh");
		MIME_TYPE_MAP.put("tex", "application/x-tex");
		MIME_TYPE_MAP.put("texi", "application/x-texinfo");
		MIME_TYPE_MAP.put("texinfo", "application/x-texinfo");
//		MIME_TYPE_MAP.put("text", "application/plain");
		MIME_TYPE_MAP.put("text", "text/plain");
		MIME_TYPE_MAP.put("tgz", "application/gnutar");
//		MIME_TYPE_MAP.put("tgz", "application/x-compressed");
		MIME_TYPE_MAP.put("tif", "image/tiff");
//		MIME_TYPE_MAP.put("tif", "image/x-tiff");
		MIME_TYPE_MAP.put("tiff", "image/tiff");
//		MIME_TYPE_MAP.put("tiff", "image/x-tiff");
		MIME_TYPE_MAP.put("tr", "application/x-troff");
		MIME_TYPE_MAP.put("tsi", "audio/tsp-audio");
//		MIME_TYPE_MAP.put("tsp", "application/dsptype");
		MIME_TYPE_MAP.put("tsp", "audio/tsplayer");
		MIME_TYPE_MAP.put("tsv", "text/tab-separated-values");
		MIME_TYPE_MAP.put("turbot", "image/florian");
		MIME_TYPE_MAP.put("txt", "text/plain");
		MIME_TYPE_MAP.put("uil", "text/x-uil");
//		MIME_TYPE_MAP.put("uni", "text/uri-list");
		MIME_TYPE_MAP.put("unis", "text/uri-list");
		MIME_TYPE_MAP.put("unv", "application/i-deas");
		MIME_TYPE_MAP.put("uri", "text/uri-list");
		MIME_TYPE_MAP.put("uris", "text/uri-list");
//		MIME_TYPE_MAP.put("ustar", "application/x-ustar");
		MIME_TYPE_MAP.put("ustar", "multipart/x-ustar");
//		MIME_TYPE_MAP.put("uu", "application/octet-stream");
		MIME_TYPE_MAP.put("uu", "text/x-uuencode");
		MIME_TYPE_MAP.put("uue", "text/x-uuencode");
		MIME_TYPE_MAP.put("vcd", "application/x-cdlink");
		MIME_TYPE_MAP.put("vcs", "text/x-vcalendar");
		MIME_TYPE_MAP.put("vda", "application/vda");
		MIME_TYPE_MAP.put("vdo", "video/vdo");
		MIME_TYPE_MAP.put("vew", "application/groupwise");
		MIME_TYPE_MAP.put("viv", "video/vivo");
//		MIME_TYPE_MAP.put("viv", "video/vnd.vivo");
		MIME_TYPE_MAP.put("vivo", "video/vivo");
//		MIME_TYPE_MAP.put("vivo", "video/vnd.vivo");
		MIME_TYPE_MAP.put("vmd", "application/vocaltec-media-desc");
		MIME_TYPE_MAP.put("vmf", "application/vocaltec-media-file");
		MIME_TYPE_MAP.put("voc", "audio/voc");
//		MIME_TYPE_MAP.put("voc", "audio/x-voc");
		MIME_TYPE_MAP.put("vos", "video/vosaic");
		MIME_TYPE_MAP.put("vox", "audio/voxware");
		MIME_TYPE_MAP.put("vqe", "audio/x-twinvq-plugin");
		MIME_TYPE_MAP.put("vqf", "audio/x-twinvq");
		MIME_TYPE_MAP.put("vql", "audio/x-twinvq-plugin");
//		MIME_TYPE_MAP.put("vrml", "application/x-vrml");
//		MIME_TYPE_MAP.put("vrml", "model/vrml");
		MIME_TYPE_MAP.put("vrml", "x-world/x-vrml");
		MIME_TYPE_MAP.put("vrt", "x-world/x-vrt");
		MIME_TYPE_MAP.put("vsd", "application/x-visio");
		MIME_TYPE_MAP.put("vst", "application/x-visio");
		MIME_TYPE_MAP.put("vsw", "application/x-visio");
		MIME_TYPE_MAP.put("w60", "application/wordperfect6.0");
		MIME_TYPE_MAP.put("w61", "application/wordperfect6.1");
		MIME_TYPE_MAP.put("w6w", "application/msword");
//		MIME_TYPE_MAP.put("wav", "audio/wav");
		MIME_TYPE_MAP.put("wav", "audio/x-wav");
		MIME_TYPE_MAP.put("wb1", "application/x-qpro");
		MIME_TYPE_MAP.put("wbmp", "image/vnd.wap.wbmp");
		MIME_TYPE_MAP.put("web", "application/vnd.xara");
		MIME_TYPE_MAP.put("wiz", "application/msword");
		MIME_TYPE_MAP.put("wk1", "application/x-123");
		MIME_TYPE_MAP.put("wmf", "windows/metafile");
		MIME_TYPE_MAP.put("wml", "text/vnd.wap.wml");
		MIME_TYPE_MAP.put("wmlc", "application/vnd.wap.wmlc");
		MIME_TYPE_MAP.put("wmls", "text/vnd.wap.wmlscript");
		MIME_TYPE_MAP.put("wmlsc", "application/vnd.wap.wmlscriptc");
		MIME_TYPE_MAP.put("word", "application/msword");
		MIME_TYPE_MAP.put("wp", "application/wordperfect");
//		MIME_TYPE_MAP.put("wp5", "application/wordperfect");
		MIME_TYPE_MAP.put("wp5", "application/wordperfect6.0");
		MIME_TYPE_MAP.put("wp6", "application/wordperfect");
//		MIME_TYPE_MAP.put("wpd", "application/wordperfect");
		MIME_TYPE_MAP.put("wpd", "application/x-wpwin");
		MIME_TYPE_MAP.put("wq1", "application/x-lotus");
//		MIME_TYPE_MAP.put("wri", "application/mswrite");
		MIME_TYPE_MAP.put("wri", "application/x-wri");
//		MIME_TYPE_MAP.put("wrl", "application/x-world");
//		MIME_TYPE_MAP.put("wrl", "model/vrml");
		MIME_TYPE_MAP.put("wrl", "x-world/x-vrml");
//		MIME_TYPE_MAP.put("wrz", "model/vrml");
		MIME_TYPE_MAP.put("wrz", "x-world/x-vrml");
		MIME_TYPE_MAP.put("wsc", "text/scriplet");
		MIME_TYPE_MAP.put("wsrc", "application/x-wais-source");
		MIME_TYPE_MAP.put("wtk", "application/x-wintalk");
//		MIME_TYPE_MAP.put("xbm", "image/x-xbitmap");
//		MIME_TYPE_MAP.put("xbm", "image/x-xbm");
		MIME_TYPE_MAP.put("xbm", "image/xbm");
		MIME_TYPE_MAP.put("xdr", "video/x-amt-demorun");
		MIME_TYPE_MAP.put("xgz", "xgl/drawing");
		MIME_TYPE_MAP.put("xif", "image/vnd.xiff");
		MIME_TYPE_MAP.put("xl", "application/excel");
//		MIME_TYPE_MAP.put("xla", "application/excel");
//		MIME_TYPE_MAP.put("xla", "application/x-excel");
		MIME_TYPE_MAP.put("xla", "application/x-msexcel");
//		MIME_TYPE_MAP.put("xlb", "application/excel");
//		MIME_TYPE_MAP.put("xlb", "application/vnd.ms-excel");
		MIME_TYPE_MAP.put("xlb", "application/x-excel");
//		MIME_TYPE_MAP.put("xlc", "application/excel");
//		MIME_TYPE_MAP.put("xlc", "application/vnd.ms-excel");
		MIME_TYPE_MAP.put("xlc", "application/x-excel");
//		MIME_TYPE_MAP.put("xld", "application/excel");
		MIME_TYPE_MAP.put("xld", "application/x-excel");
//		MIME_TYPE_MAP.put("xlk", "application/excel");
		MIME_TYPE_MAP.put("xlk", "application/x-excel");
//		MIME_TYPE_MAP.put("xll", "application/excel");
//		MIME_TYPE_MAP.put("xll", "application/vnd.ms-excel");
		MIME_TYPE_MAP.put("xll", "application/x-excel");
//		MIME_TYPE_MAP.put("xlm", "application/excel");
//		MIME_TYPE_MAP.put("xlm", "application/vnd.ms-excel");
		MIME_TYPE_MAP.put("xlm", "application/x-excel");
//		MIME_TYPE_MAP.put("xls", "application/excel");
//		MIME_TYPE_MAP.put("xls", "application/vnd.ms-excel");
//		MIME_TYPE_MAP.put("xls", "application/x-excel");
		MIME_TYPE_MAP.put("xls", "application/x-msexcel");
//		MIME_TYPE_MAP.put("xlt", "application/excel");
		MIME_TYPE_MAP.put("xlt", "application/x-excel");
//		MIME_TYPE_MAP.put("xlv", "application/excel");
		MIME_TYPE_MAP.put("xlv", "application/x-excel");
//		MIME_TYPE_MAP.put("xlw", "application/excel");
//		MIME_TYPE_MAP.put("xlw", "application/vnd.ms-excel");
//		MIME_TYPE_MAP.put("xlw", "application/x-excel");
		MIME_TYPE_MAP.put("xlw", "application/x-msexcel");
		MIME_TYPE_MAP.put("xm", "audio/xm");
//		MIME_TYPE_MAP.put("xml", "application/xml");
		MIME_TYPE_MAP.put("xml", "text/xml");
		MIME_TYPE_MAP.put("xmz", "xgl/movie");
		MIME_TYPE_MAP.put("xpix", "application/x-vnd.ls-xpix");
//		MIME_TYPE_MAP.put("xpm", "image/x-xpixmap");
		MIME_TYPE_MAP.put("xpm", "image/xpm");
		MIME_TYPE_MAP.put("x-png", "image/png");
		MIME_TYPE_MAP.put("xsr", "video/x-amt-showrun");
//		MIME_TYPE_MAP.put("xwd", "image/x-xwd");
		MIME_TYPE_MAP.put("xwd", "image/x-xwindowdump");
		MIME_TYPE_MAP.put("xyz", "chemical/x-pdb");
//		MIME_TYPE_MAP.put("z", "application/x-compress");
		MIME_TYPE_MAP.put("z", "application/x-compressed");
//		MIME_TYPE_MAP.put("zip", "application/x-compressed");
//		MIME_TYPE_MAP.put("zip", "application/x-zip-compressed");
//		MIME_TYPE_MAP.put("zip", "application/zip");
		MIME_TYPE_MAP.put("zip", "multipart/x-zip");
		MIME_TYPE_MAP.put("zoo", "application/octet-stream");
		MIME_TYPE_MAP.put("zsh", "text/x-script.zsh");
	}
	
	
}
