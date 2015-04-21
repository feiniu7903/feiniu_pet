package com.ejingtong.view;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ejingtong.R;
import com.ejingtong.adapter.ScoreSelectPopAdaptor;
import com.ejingtong.help.Tools;

public class MPopView {
	
	public static final int CENTER = 0;
	public static final int TOP = 1;
	public static final int BOTTOM = 2;
	
	private View popView;
	private ListView listView;
	
	private Context mContext;
	private LayoutInflater inflater;
	private Resources resources;
	private String[] mItem;
	private int[] mItem2 = null;
	private static PopupWindow popWindow;
	
	public MPopView(Context context, String[] item){
//		super();
		mContext =context;
		mItem = item;
		inflater = LayoutInflater.from(mContext);
		resources = context.getResources();
		init();
		
	}
	
	public MPopView(Context context, String[] item, int[] item2){
		mContext =context;
		mItem = item;
		mItem2 = item2;
		inflater = LayoutInflater.from(mContext);
		resources = context.getResources();
		init();
	}
	
	public MPopView(Context context, List<String> data){
		super();
		mContext =context;
		mItem = (String[])data.toArray();
		inflater = LayoutInflater.from(mContext);
		init();
	};
	
	private void init(){
		popView = inflater.inflate(R.layout.view_pop_window, null);
		listView = (ListView)popView.findViewById(R.id.pop_list_view);
		ScoreSelectPopAdaptor adaptor = new ScoreSelectPopAdaptor(mContext, mItem,mItem2);
		listView.setAdapter(adaptor);
			popWindow = new PopupWindow(popView, Tools.dip2px(mContext, 300), LayoutParams.WRAP_CONTENT, true);
			popWindow.setOutsideTouchable(true);
			popWindow.setTouchable(true);
			popWindow.update();
			
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		if(listView != null){
			listView.setOnItemClickListener(listener);
		}
	}
	
	/**
	 * 基于parentView的位置显示popView
	 * 
	 * @param parentView
	 */
	public void show(View parentView, int backgrounfDrawable){
		popWindow.setBackgroundDrawable(resources.getDrawable(backgrounfDrawable));
		popWindow.showAsDropDown(parentView);
	}
	
	public void show(View parentView, int drawable, int width){
		popWindow.setBackgroundDrawable(resources.getDrawable(drawable));
		popWindow.showAsDropDown(parentView, width, 0);
	}
	
	public void show(View parentView, int drawable, int width, int height){
		popWindow.setBackgroundDrawable(resources.getDrawable(drawable));
		popWindow.showAsDropDown(parentView, width, height);
	}
	
	public void show(View parentView, int drawable, int gravity, int widht, int height){
		popWindow.setBackgroundDrawable(resources.getDrawable(drawable));
		popWindow.showAtLocation(parentView, gravity, widht, height);
	}
	
	/**
	 * popView显示在中间
	 */
	public void showCenter(View v, int drawable){
		popWindow.setBackgroundDrawable(resources.getDrawable(drawable));
		popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
	}
	
	
	/**
	 * popView 显示在底部
	 */
	public void showBottom(View v, int drawable){
		popWindow.setBackgroundDrawable(resources.getDrawable(drawable));
		popWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
//		popView.showAtLocation(parent, gravity, x, y)
	}
	
	public void dismiss(){
		if(popWindow != null && popWindow.isShowing()){
			popWindow.dismiss();
		}
	}
	
	public int getWidth(){
		if(null != popWindow){
			return popWindow.getWidth();
		}else{
			return 0;
		}
	}
}
