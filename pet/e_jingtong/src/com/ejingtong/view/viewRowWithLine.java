package com.ejingtong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ejingtong.R;

public class viewRowWithLine extends RelativeLayout {
	
	private Context mContext;
	private LayoutInflater layoutInflater;
	private TextView lableText;
	private EditText valueText;
	private Button btnChangeQuantity;
	private View lineView;
	private View boldLineView;
	private MPopView popView;
	private OnValueChangeListener valueChangeListener;
	private long metaId;
	
	private String[] arrRange = null;
	
	private int mRange;

	
	public viewRowWithLine(Context context) {
		super(context);
		mContext = context;
		initUi();
	}

	public viewRowWithLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initUi();
	}

	private void initUi(){
		layoutInflater = LayoutInflater.from(mContext);
		layoutInflater.inflate(R.layout.view_row_with_line, this);
		
		lableText = (TextView)findViewById(R.id.lable);
		valueText = (EditText)findViewById(R.id.value);
		btnChangeQuantity = (Button)findViewById(R.id.change_quantity);
		lineView = findViewById(R.id.base_line);
		boldLineView = findViewById(R.id.bold_line);
		
		btnChangeQuantity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(arrRange == null){
					return;
				}
				popView = new MPopView(mContext, arrRange);
				popView.show(valueText, R.drawable.pop_dark_up_bg);
				popView.setOnItemClickListener(new MOntemClickListener());
			}
		});
		
	}
	
	public void setValueEnable(boolean enabled){
		if(!enabled){
			valueText.setBackgroundColor(0);
		}else{
//			valueText.setBackgroundResource(R.drawable.edit_text);
		}
		valueText.setEnabled(enabled);
	}
	
	public String getValue(){
		return valueText.getText().toString().trim();
	}
	
	public void setBtnVisible(int visible){
		btnChangeQuantity.setVisibility(visible);
	}
	
	public void setValueChangeListener(OnValueChangeListener listener){
		valueChangeListener = listener;
	}
//	public void setLineDrawableRes(int resId){
//		lineView.setBackgroundResource(resId);
//	} 
//	
//	public void setLineColor(int color){
//		lineView.setBackgroundColor(color);
//	}
//	
//	public void setLineVisible(int viaible){
//		lineView.setVisibility(View.GONE);
//	}
	
	public void setBoldLineVisible(int visible){
		boldLineView.setVisibility(visible);
	}
	
	public void setBaseLineVisible(int visible){
		lineView.setVisibility(visible);
	}
	
	public void setLableText(String text){
		lableText.setText(text);
	}
	
	public void setLableTextColor(int color){
		lableText.setTextColor(color);
	}
	
	public void setValueText(String text){
		valueText.setText(text);
	}
	
	public void setValueTextColor(int color){
		valueText.setTextColor(color);
	}
	
	public long getMetaId() {
		return metaId;
	}

	public void setMetaId(long metaId) {
		this.metaId = metaId;
	}

	public int getmRange() {
		return mRange;
	}

	public void setmRange(int mRange) {
		this.mRange = mRange;
		
		initRange();
	}

	private String[] initRange(){
		
		if(mRange > 0){
			arrRange = new String[mRange];
			
			for(int i=1; i<=mRange; i++){
				arrRange[i-1] = i+"";
			}
		}
		
		return arrRange;
	}


	class MOntemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			if(arrRange == null){
				return;
			}
			
			valueText.setText(arrRange[position]);
			if(valueChangeListener != null){
				valueChangeListener.onChange(valueText, metaId);
			}
			popView.dismiss();
		}
		
	}
	
	public interface OnValueChangeListener{
		public void onChange(TextView v, long metaid);
	}
}
