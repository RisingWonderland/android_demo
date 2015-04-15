package org.demo.crow.masklayer;

import org.crow.android.utils.BasicUtils;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.demo.crow.R;

public class CustomMaskLayer extends Fragment implements OnClickListener {
	
	private static final String TAG = "CustomMaskLayer";
	private Activity mActivity;
	private Button btn_showCustomMaskLayer1;
	private Button btn_showCustomMaskLayer2;
	private Button btn_showCustomMaskLayer3;
	private BgTranslucentDialogMasklayer maskLayer1;
	private BgTranslucentDialogMasklayer maskLayer2;
	private BgTranslucentDialogMasklayer maskLayer3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		mActivity = this.getActivity();
		
		View view = inflater.inflate(R.layout.fm_masklayer_custom_masklayer, container, false);
		btn_showCustomMaskLayer1 = (Button) view.findViewById(R.id.btn_showCustomMaskLayer1);
		btn_showCustomMaskLayer1.setOnClickListener(this);
		btn_showCustomMaskLayer2 = (Button) view.findViewById(R.id.btn_showCustomMaskLayer2);
		btn_showCustomMaskLayer2.setOnClickListener(this);
		btn_showCustomMaskLayer3 = (Button) view.findViewById(R.id.btn_showCustomMaskLayer3);
		btn_showCustomMaskLayer3.setOnClickListener(this);
		
		maskLayer1 = new BgTranslucentDialogMasklayer(mActivity);
		maskLayer2 = new BgTranslucentDialogMasklayer(mActivity, 330, 480);
		maskLayer3 = new BgTranslucentDialogMasklayer(mActivity, 700, 400);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_showCustomMaskLayer1:
			maskLayer1.show();
			break;
		case R.id.btn_showCustomMaskLayer2:
			maskLayer2.show();
			break;
		case R.id.btn_showCustomMaskLayer3:
			maskLayer3.show();
			break;
		}
	}
}
