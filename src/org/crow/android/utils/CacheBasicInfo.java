package org.crow.android.utils;

public class CacheBasicInfo {

	private CacheBasicInfo() {}
	private static CacheBasicInfo self = new CacheBasicInfo();
	public static CacheBasicInfo getInstance() {
		return self;
	}

	// 缓存屏幕分辨率
	private int[] dpi;
	public int[] getDpi() {
		return dpi;
	}
	public void setDpi(int[] dpi) {
		this.dpi = dpi;
	}
	// 缓存设备显示状态，单页模式或双页模式
	private boolean is2Page;
	public boolean isIs2Page() {
		return is2Page;
	}
	public void setIs2Page(boolean is2Page) {
		this.is2Page = is2Page;
	}
	// 缓存双页模式下，主界面左侧列表的宽度
	private int mainMenuWidth;
	public int getMainMenuWidth() {
		return mainMenuWidth;
	}
	public void setMainMenuWidth(int mainMenuWidth) {
		this.mainMenuWidth = mainMenuWidth;
	}
}
