package com.ejingtong.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ejingtong.R;

public class MDialog {
	
	private static Context mContext;
	private static String mTip;
	private static String mTitle;
	private static String btn1Text;
	private static String btn2Text;
	private static View.OnClickListener btn1OnClickListener;
	private static View.OnClickListener btn2OnClickListener;
	
	private Button btn1;
	private Button btn2;
	private TextView textView, textTitle;
	private View lineView, lineView2;
	
	private View rootView;
	LayoutInflater inflater;
	private Dialog dialog;
	

	public MDialog(Context context) {
		mContext = context;
		dialog = new Dialog(mContext, R.style.dialog);
		dialog.setCanceledOnTouchOutside(false);
		initUi();
	}
	
	private void initUi(){
//		inflater = LayoutInflater.from(mContext);
//		rootView = inflater.inflate(R.layout.dialog_commen, null);
		dialog.setContentView(R.layout.dialog_commen);
		
		btn1 = (Button)dialog.findViewById(R.id.dialog_btn1);
		btn2 = (Button)dialog.findViewById(R.id.dialog_btn2);
		textView = (TextView)dialog.findViewById(R.id.dialog_tip);
		textTitle = (TextView)dialog.findViewById(R.id.dialog_title);
		lineView = dialog.findViewById(R.id.dialog_sep_line);
		lineView2 = dialog.findViewById(R.id.dialog_sep_line2);
	}
	
	public void setTip(String tip){
		mTip = tip;
		textView.setText(mTip);
		textView.setVisibility(View.VISIBLE);
	}
	
	public void setTitle(String title){
		mTitle = title;
		textTitle.setText(mTitle);
		textTitle.setVisibility(View.VISIBLE);
		lineView.setVisibility(View.VISIBLE);
	}
	
	public void setBtn1OnClickListener(String btnText, View.OnClickListener listener){
		
		btn1Text = btnText;
		btn1.setText(btn1Text);
		
		btn1OnClickListener = listener;
		btn1.setOnClickListener(btn1OnClickListener);
		btn1.setVisibility(View.VISIBLE);
//		lineView2.setVisibility(View.VISIBLE);
	}
	
	public void setBtn2OnClickListener(String btnText, View.OnClickListener listener){
		
		btn2Text = btnText;
		btn2.setText(btn2Text);
		
		btn2OnClickListener = listener;
		btn2.setOnClickListener(btn2OnClickListener);
		
		btn2.setVisibility(View.VISIBLE);
//		lineView2.setVisibility(View.VISIBLE);
	}
	
	public void show(){
		dialog.show();
	}
	
	public void dismiss(){
		dialog.dismiss();
	}
	
	public void hide(){
		dialog.hide();
	}
	
}


