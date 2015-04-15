package org.demo.crow.skin;

import org.crow.android.utils.BasicUtils;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import org.demo.crow.R;

public class ChangeSkin extends Fragment implements OnClickListener {
	
	private static final String TAG = "ChangeSkin";
	private Activity mActivity;
	private Button btn_changeSkinDefault;
	private Button btn_changeSkin1;
	private Button btn_changeSkin2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		mActivity = this.getActivity();
		View view = inflater.inflate(R.layout.fm_skin_changeskin, container, false);
		
		btn_changeSkinDefault = (Button) view.findViewById(R.id.btn_changeSkinDefault);
		btn_changeSkin1 = (Button) view.findViewById(R.id.btn_changeSkin1);
		btn_changeSkin2 = (Button) view.findViewById(R.id.btn_changeSkin2);
		btn_changeSkinDefault.setOnClickListener(this);
		btn_changeSkin1.setOnClickListener(this);
		btn_changeSkin2.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.btn_changeSkinDefault:
			BasicUtils.showToast(mActivity, "切换至默认皮肤");
			SkinUtils.changeSkin(mActivity, 0);
			break;
		case R.id.btn_changeSkin1:
			BasicUtils.showToast(mActivity, "切换至日间皮肤");
			SkinUtils.changeSkin(mActivity, 1);
			break;
		case R.id.btn_changeSkin2:
			BasicUtils.showToast(mActivity, "切换至夜间皮肤");
			SkinUtils.changeSkin(mActivity, 2);
			break;
		}
		
	}
}
