package com.ejingtong.view;

/**
 * Head类,两边是按钮，中间是文字，左边按钮只有一个，右边按钮可以随意添加
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ejingtong.R;

public class HeadView extends RelativeLayout {
	private Context mContext;
	private ImageView backBtn; // 返回按钮
	private LinearLayout moreBtnLayout; // 更多按钮容器
	private ImageView homeBtn; // 到首页按钮
	private Button unregisterBtn;
	private TextView titleText;

	public HeadView(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public HeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	public HeadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		inflater.inflate(R.layout.view_head, this);

		backBtn = (ImageView) findViewById(R.id.back);
		homeBtn = (ImageView) findViewById(R.id.home);
		unregisterBtn = (Button) findViewById(R.id.unregister);

		moreBtnLayout = (LinearLayout) findViewById(R.id.more_head_btn);
		titleText = (TextView) findViewById(R.id.title);
	}

	public void setBackBtnBg(int bgResource) {
		backBtn.setBackgroundResource(bgResource);
	}

	public void setBackBtnIcon(int icon) {
		backBtn.setImageResource(icon);
	}

	public void setBackkBtnListener(OnClickListener listener) {
		if (listener != null) {
			backBtn.setOnClickListener(listener);
		}
	}

	public void setBackBtnVisible(int visible) {
		backBtn.setVisibility(visible);
	}

	public void setTitleText(String text) {
		titleText.setText(text);
	}

	public void setTitleBg(int bgResource) {
		titleText.setBackgroundResource(bgResource);
	}

	public void setHomeBtnBg(int bgResource) {
		homeBtn.setBackgroundResource(bgResource);
	}

	public void setHomeBtnIcon(int iconResource) {
		homeBtn.setImageResource(iconResource);
	}

	public void setHomgBtnClickListener(OnClickListener listener) {
		homeBtn.setOnClickListener(listener);
	}

	public void setHomeBtnVisible(int visible) {
		homeBtn.setVisibility(visible);
	}

	public void setUnregisterBtnVisible(int visible) {
		unregisterBtn.setVisibility(visible);
	}

	public void setUnregisterBtnText(String text) {
		unregisterBtn.setText(text);
	}

	public void setUnregisterBtnListener(OnClickListener listener) {
		unregisterBtn.setOnClickListener(listener);
	}

	public Button addAction(String lable, int bgResource,
			OnClickListener listener) {
		Button btn = new Button(mContext);
		btn.setGravity(Gravity.CENTER);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		if (lable != null && !"".equals(lable)) {
			btn.setText(lable);
		}

		if (bgResource > 0) {
			btn.setBackgroundResource(bgResource);
		}

		if (listener != null) {
			btn.setOnClickListener(listener);
		}

		moreBtnLayout.addView(btn, lp);
		return btn;
	}

}
