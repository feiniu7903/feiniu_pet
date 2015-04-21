package com.ejingtong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ejingtong.R;

public class SoftwareAdaptor extends BaseAdapter {

	private Context mContext;
	private String[] mData;
	private LayoutInflater inflater;
	public SoftwareAdaptor(Context context, String[] data) {
		mContext = context;
		mData = data;
		
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if(mData == null){
			return 0;
		}
		return mData.length;
	}

	@Override
	public Object getItem(int position) {
		if(mData == null){
			return null;
		}
		return mData[position];
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView itemView;
		if(convertView == null){
			itemView = (TextView)inflater.inflate(R.layout.view_adaptor_item_software, null);
		}else{
			itemView = (TextView)convertView;
		}
		
		itemView.setText(mData[position]);
		return itemView;
	}

}
