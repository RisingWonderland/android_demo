package org.crow.android.utils;
/**
 * WPS for Android程序的支持工具类
 * @author Crow
 *
 */
public class WpsUtils {
	
	public static final String USER_NAME = "UserName";// String，指定用户名，代表操作文档的人
	
	public static final String OPEN_MODE = "OpenMode";// String，文件打开模式
	public static final String OPEN_MODE_NORMAL = "Normal";// String，文件打开模式：普通
	public static final String OPEN_MODE_READ_ONLY = "ReadOnly";// String，文件打开模式：只读
	public static final String OPEN_MODE_READ_MODE = "ReadMode";// String，文件打开模式：阅读器
	public static final String OPEN_MODE_SAVE_ONLY = "SaveOnly";// String，文件打开模式：打开、保存、关闭
	
	public static final String SAVE_PATH = "SavePath";// String，文件保存路径
	public static final String THIRD_PACKAGE = "ThirdPackage";// String，包含的第三方包名
	
	public static final String SEND_SAVE_BROAD = "SendSaveBroad";// boolean，文档保存时是否发送广播
	public static final String SEND_CLOSE_BROAD = "SendCloseBroad";// boolean，文档关闭时是否发送广播
	
	public static final String CLEAR_BUFFER = "ClearBuffer";// boolean，文档关闭时是否清空临时文件
	public static final String CLEAR_TRACE = "ClearTrace";// boolean，文档关闭时是否清除使用记录
	public static final String CLEAR_FILE = "ClearFile";// boolean，文档关闭时是否删除打开的文件
	
	public static final String VIEW_PROGRESS = "ViewProgress";// float，指定文件展示的进度
	public static final String AUTO_JUMP = "AutoJump";// boolean，是否自动跳转到上次的进度
	public static final String VIEW_SCALE = "ViewScale";// float，设置文档显示缩放比
	public static final String VIEW_SCROLL_X = "ViewScrollX";// int，设置文档显示视图的x坐标
	public static final String vIEW_SCROLL_Y = "ViewScrollY";// int，设置文档显示视图的y坐标
	
	public static final String OPEN_FILE = "OpenFile";// String，打开的文件的路径。
	public static final String CLOSE_FILE = "CloseFile";// String，关闭的文件的路径。如果在关闭之前，文件另存为，则返回的是另存为后的新文件的路径。
	
}
