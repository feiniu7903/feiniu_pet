package com.ejingtong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ejingtong.R;


public class ScoreSelectPopAdaptor extends BaseAdapter {

	private Context mContext; 
	private String[] chScore;
	private int[] nuScore;
	public ScoreSelectPopAdaptor(Context context, String[] ch, int[] score) {
		mContext = context;
		chScore = ch;
		nuScore = score;
	}

	@Override
	public int getCount() {
		if(chScore == null){
			return 0;
		}
		return chScore.length;
	}

	@Override
	public Object getItem(int position) {
		if(null == chScore){
			return null;
		}
		return chScore[position];
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView;
		if(null == convertView){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.view_pop_item, null); 
		}
		
		textView = (TextView)convertView.findViewById(R.id.pop_item_text);
		if(chScore != null && nuScore != null){
			textView.setText(chScore[position] + "(" + nuScore[position] + ")");
		}
		if(chScore != null && nuScore == null){
			textView.setText(chScore[position]);
		}
		return convertView;
	}

}
