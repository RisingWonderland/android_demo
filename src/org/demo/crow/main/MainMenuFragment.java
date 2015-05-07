package org.demo.crow.main;

import org.crow.android.utils.BasicUtils;
import org.crow.android.utils.CacheBasicInfo;
import org.demo.crow.activity.A;
import org.demo.crow.application.ApplicationTest;
import org.demo.crow.application.ApplicationTestActivity;
import org.demo.crow.bitmap.ThumbnailDemo;
import org.demo.crow.bitmap.ThumbnailDemoActivity;
import org.demo.crow.file.FileDemo;
import org.demo.crow.file.FileDemoActivity;
import org.demo.crow.fragment.SplitViewActivity;
import org.demo.crow.fragment.SplitViewDynamic;
import org.demo.crow.installanduninstall.InstallAndUninstall;
import org.demo.crow.installanduninstall.InstallAndUninstallActivity;
import org.demo.crow.list.BasicList;
import org.demo.crow.list.BasicListActivity;
import org.demo.crow.masklayer.CustomMaskLayer;
import org.demo.crow.masklayer.CustomMaskLayerActivity;
import org.demo.crow.os.OSFuncTest;
import org.demo.crow.os.OSFuncTestActivity;
import org.demo.crow.popuplayer.PopupWindowUtilsExample;
import org.demo.crow.popuplayer.PopupWindowUtilsExampleActivity;
import org.demo.crow.popuplayer.SimplePopupWindow;
import org.demo.crow.popuplayer.SimplePopupWindowActivity;
import org.demo.crow.service.SimpleServiceDemo;
import org.demo.crow.service.SimpleServiceDemoActivity;
import org.demo.crow.skin.ChangeSkin;
import org.demo.crow.skin.ChangeSkinActivity;
import org.demo.crow.soundlight.SoundAndLight;
import org.demo.crow.soundlight.SoundAndLightActivity;
import org.demo.crow.toastdialog.CustomToastDialog;
import org.demo.crow.toastdialog.CustomToastDialogActivity;
import org.demo.crow.view.CustomView;
import org.demo.crow.view.CustomViewActivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.demo.crow.R;

public class MainMenuFragment extends Fragment implements OnItemClickListener {
	
	private static final String TAG = "MainMenuFragment";
	private Activity mActivity;
	private ListView lv_mainMenuList;// 列表控件
	private ArrayAdapter<String> aAdapter;// 列表适配器
	private String[] mainMenuData;// 列表数据源
	private FragmentManager fm;
	private boolean is2Page;// 当前MainActivity布局是否为双页模式
	
	@Override
	public void onAttach(Activity activity) {
		Log.i(TAG, "onAttach");
		super.onAttach(activity);
		// 初始化列表数据源
		mainMenuData = initMainMenuData();
		// 配置列表适配器
		aAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, mainMenuData);
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
		
		mActivity = this.getActivity();
		fm = mActivity.getFragmentManager();
		
		View view = inflater.inflate(R.layout.fm_main_menu, container, false);
		// 初始化列表控件，配置适配器，设置列表项点击事件
		lv_mainMenuList = (ListView) view.findViewById(R.id.lv_main_menu_list);
		lv_mainMenuList.setAdapter(aAdapter);
		lv_mainMenuList.setOnItemClickListener(this);
		
		return view;
	}

	/*
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Log.i(TAG, "onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}
	*/

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
		// 所依存的Activity创建完毕后，检测Activity所使用的布局文件中是否存在ID为fl_main_details_view的控件。
		// 如果存在，是双页模式，动态设置Fragment展示内容；
		// 如果不存在，是单页模式，打开新的Activity展示内容。
		CacheBasicInfo tbc = CacheBasicInfo.getInstance();
		if(mActivity.findViewById(R.id.fl_main_details_view) != null){
			is2Page = true;
		}else{
			is2Page = false;
		}
		tbc.setIs2Page(is2Page);
	}

	/*
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		Log.i(TAG, "onViewStateRestored");
		super.onViewStateRestored(savedInstanceState);
	}
	*/

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
	
	
	/**
	 * 初始化列表数据
	 * @return 列表数据源，一个数组。
	 */
	private String[] initMainMenuData(){
		String[] data = new String[]{
			"Fragment拆分视图（静态）", 
			"Fragment拆分视图（动态）", 
			"自定义View",
			"更改皮肤",
			"自定义Toast和Dialog",
			"PopupWindow",
			"PopupWindowManager",
			"MaskLayer",
			"Fragment嵌套",
			"Application",
			"安装和卸载",
			"BasicList",
			"File相关操作演示",
			"缩略图相关演示",
			"声音和亮度",
			"系统相关功能",
			"Service演示",
			"关于Activity"
		};
		return data;
	}

	/**
	 * 列表项点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 触发点击事件，获取is2page的值
		// 如果值为true，设置切换Fragment；如果值为false，设置打开Activity。
		if(is2Page == true){
			Fragment fragment = null;
			if(position == 0 && mActivity.findViewById(R.id.fm_fragmentSplitViewStatic) == null){
				Intent intent = new Intent(mActivity, SplitViewActivity.class);
				startActivity(intent);
				return;
			}else if(position == 1 && mActivity.findViewById(R.id.fm_fragmentSplitViewDynamic) == null){
				fragment = new SplitViewDynamic();
			}else if(position == 2 && mActivity.findViewById(R.id.fm_viewCustomView) == null){
				fragment = new CustomView();
			}else if(position == 3 && mActivity.findViewById(R.id.fm_skinChangeSkin) == null){
				fragment = new ChangeSkin();
			}else if(position == 4 && mActivity.findViewById(R.id.fm_toastdialogCustomToastDialog) == null){
				fragment = new CustomToastDialog();
			}else if(position == 5 && mActivity.findViewById(R.id.fm_popupwindowSimplePopupwindow) == null){
				fragment = new SimplePopupWindow();
			}else if(position == 6 && mActivity.findViewById(R.id.fm_popupwindowPopupWindowUtilsExample) == null){
				fragment = new PopupWindowUtilsExample();
			}else if(position == 7 && mActivity.findViewById(R.id.fm_masklayerCustomMasklayer) == null){
				fragment = new CustomMaskLayer();
			}else if(position == 8){
				BasicUtils.showToast(mActivity, "尚未实现");
			}else if(position == 9 && mActivity.findViewById(R.id.fm_applicationApplicationTest) == null){
				fragment = new ApplicationTest();
			}else if(position == 10 && mActivity.findViewById(R.id.fm_installAndUninstall) == null){
				fragment = new InstallAndUninstall();
			}else if(position == 11 && mActivity.findViewById(R.id.fm_listBasicList) == null){
				fragment = new BasicList();
			}else if(position == 12 && mActivity.findViewById(R.id.fm_fileFileDemo) == null){
				fragment = new FileDemo();
			}else if(position == 13 && mActivity.findViewById(R.id.fm_bitmapThumbnail) == null){
				fragment = new ThumbnailDemo();
			}else if(position == 14 && mActivity.findViewById(R.id.fm_soundAndLight) == null){
				fragment = new SoundAndLight();
			}else if(position == 15 && mActivity.findViewById(R.id.fm_osFuncTest) == null){
				fragment = new OSFuncTest();
			}else if(position == 16 && mActivity.findViewById(R.id.fm_simpleServiceDemo) == null){
				fragment = new SimpleServiceDemo();
			}else if(position == 17){
				startActivity(new Intent(mActivity, A.class));
				return;
			}
			if(fragment == null){
				return;
			}
			
			FragmentTransaction ft = fm.beginTransaction();
			ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);//v4的Fragment不支持objectAnimator标签的动画
			ft.replace(R.id.fl_main_details_view, fragment);
			ft.commit();
		}else{
			Intent intent = null;
			switch(position){
			case 0:
			case 1:
				intent = new Intent(mActivity, SplitViewActivity.class);
				break;
			case 2:
				intent = new Intent(mActivity, CustomViewActivity.class);
				break;
			case 3:
				intent = new Intent(mActivity, ChangeSkinActivity.class);
				break;
			case 4:
				intent = new Intent(mActivity, CustomToastDialogActivity.class);
				break;
			case 5:
				intent = new Intent(mActivity, SimplePopupWindowActivity.class);
				break;
			case 6:
				intent = new Intent(mActivity, PopupWindowUtilsExampleActivity.class);
				break;
			case 7:
				intent = new Intent(mActivity, CustomMaskLayerActivity.class);
				break;
			case 8:
				BasicUtils.showToast(mActivity, "尚未实现");
				break;
			case 9:
				intent = new Intent(mActivity, ApplicationTestActivity.class);
				break;
			case 10:
				intent = new Intent(mActivity, InstallAndUninstallActivity.class);
				break;
			case 11:
				intent = new Intent(mActivity, BasicListActivity.class);
				break;
			case 12:
				intent = new Intent(mActivity, FileDemoActivity.class);
				break;
			case 13: 
				intent = new Intent(mActivity, ThumbnailDemoActivity.class);
				break;
			case 14: 
				intent = new Intent(mActivity, SoundAndLightActivity.class);
				break;
			case 15: 
				intent = new Intent(mActivity, OSFuncTestActivity.class);
				break;
			case 16: 
				intent = new Intent(mActivity, SimpleServiceDemoActivity.class);
				break;
			case 17: 
				intent = new Intent(mActivity, A.class);
				break;
			}
			startActivity(intent);
		}
	}
	
}
