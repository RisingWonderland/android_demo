package org.demo.crow.main;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.demo.crow.R;

public class MyAdapter extends BaseAdapter {
	
	private Activity mActivity = null;
	private ArrayList<Map<String, Object>> data = null;
	private LayoutInflater inflate = null;

	public MyAdapter(Activity mActivity, ArrayList<Map<String, Object>> data) {
		super();
		this.mActivity = mActivity;
		this.inflate = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data = data;
	}

	public MyAdapter() {
		super();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// 如果数据源中提供有id，则根据position的值获得id值，并返回；
		// 如果没有提供id，直接返回position
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflate.inflate(R.layout.support_simple_spinner_dropdown_item, null);
			TextView tv = (TextView) convertView.findViewById(R.id.action_bar);
			ImageView iv = (ImageView) convertView.findViewById(R.id.btn_changeSkin1);
			holder.tv = tv;
			holder.iv = iv;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}
	
	private static class ViewHolder{
		TextView tv;
		ImageView iv;
	}

}
