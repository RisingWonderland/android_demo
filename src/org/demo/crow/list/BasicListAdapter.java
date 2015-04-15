package org.demo.crow.list;

import java.util.HashMap;
import java.util.List;

import org.crow.android.utils.BasicUtils;
import org.demo.crow.R;
import org.demo.crow.entity.Eclipse;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BasicListAdapter extends BaseAdapter {
	
	private Activity mActivity;
	private List<Eclipse> mData;
	private LayoutInflater mInflater;

	public BasicListAdapter(Activity mActivity, List<Eclipse> mData) {
		this.mActivity = mActivity;
		this.mData = mData;
		this.mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		TextView eclipseName = null;
		ImageView eclipseIcon = null;
		Button showEclipseInfo = null;
		
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.adapter_basiclist, null);
			viewHolder = new ViewHolder();
			// 获取控件，复制，存入ViewHolder
			eclipseName = (TextView) convertView.findViewById(R.id.tv_eclipseName);
			eclipseName.setText(mData.get(position).getName());
			viewHolder.tv_eclipseName = eclipseName;
			
			eclipseIcon = (ImageView) convertView.findViewById(R.id.iv_eclipseIcon);
			eclipseIcon.setBackgroundResource(R.drawable.ic_launcher);
			viewHolder.iv_eclipseIcon = eclipseIcon;
			
			showEclipseInfo = (Button) convertView.findViewById(R.id.btn_showEclipseInfo);
			showEclipseInfo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					BasicUtils.showToast(mActivity, mData.get(position).getVersion());
				}
			});
			viewHolder.btn_showEclipseInfo = showEclipseInfo;
			
			convertView.setTag(viewHolder);// 绑定、缓存ViewHolder对象
		}else{
			viewHolder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
		}
		
		return convertView;
	}
	
	
	public static class ViewHolder{
		public TextView tv_eclipseName;
		public ImageView iv_eclipseIcon;
		public Button btn_showEclipseInfo;
	}

}
