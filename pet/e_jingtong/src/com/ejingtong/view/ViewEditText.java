package com.ejingtong.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ejingtong.R;

public class ViewEditText extends LinearLayout {

	private TextView viewLableText;
	private EditText viewValueText;
	private Context mContext;
	private View rootView;
	public ViewEditText(Context context) {
		super(context);
		mContext = context;
		init();

	}

	public ViewEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	
	private void init(){
		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.view_edittext, this);
		rootView = findViewById(R.id.view_edittext_root);
		viewLableText = (TextView)findViewById(R.id.lable);
		viewValueText = (EditText)findViewById(R.id.value);
		viewValueText.setBackgroundResource(0);
		
	}
	
	
	public void setBackground(int resource){
		rootView.setBackgroundResource(resource);
	}
	
	public void setLable(String lable){
		viewLableText.setText(lable);
	}
	
	public void setValue(String value){
		viewValueText.setText(value);
	}
	
	public String getValue(){
		return viewValueText.getText().toString();
	}
	
	public void setFocauseAble(boolean focusable){
		viewValueText.setFocusable(focusable);
		
	}
	
	public void reQuestFocause(){
		viewValueText.requestFocus();
	}
	
	public void force(){
		viewValueText.forceLayout();
	}
	
	public void setEditable(){
		viewValueText.setEditableFactory(Editable.Factory.getInstance());
	}
	
	
	
	public void setError(String msg) {
		viewValueText.setError(msg);
	}
	
	public void setUseAble(InputMethodManager imm){
//		viewValueText.clearFocus();
//		viewValueText.setFocusable(false);
//		viewValueText.setInputType(InputType.TYPE_NULL);
//		
//        imm.hideSoftInputFromWindow(viewValueText.getWindowToken(),0);

		
//		viewValueText.setFocusable(useAble);
//		viewValueText.setPressed(useAble);
//		viewValueText.setClickable(useAble);
//		viewValueText.setFocusableInTouchMode(false);
	}
	
	public void setCursorVisible(boolean visible){
		viewValueText.setCursorVisible(visible);
		
	}
	
	
	
	public void setIsPassWord(boolean isPassWord){
		if(isPassWord){
			viewValueText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
	}

}
