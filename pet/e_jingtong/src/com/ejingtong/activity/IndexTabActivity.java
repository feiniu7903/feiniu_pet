package com.ejingtong.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TextView;
import com.ejingtong.R;
import com.ejingtong.activity.login.LoginActivity;
import com.ejingtong.activity.pass.SearchOrderActivity;
import com.ejingtong.common.Constans;
import com.ejingtong.help.SharedPreferenceHelper;
import com.ejingtong.view.HeadView;
import com.lvmm.zxing.CaptureActivity;

public class IndexTabActivity extends TabActivity implements OnClickListener {
	boolean isShown = true;
	private HeadView headView; // 头部

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_index_layout);
		initUi();
		setTabs();
	}

	private void initUi() {
		headView = (HeadView) findViewById(R.id.search_order_head_view);
		headView.setBackBtnVisible(View.GONE);
		headView.setTitleText(" 驴妈妈欢迎你!");
		headView.setHomeBtnVisible(View.GONE);
		headView.setUnregisterBtnListener(this);
		headView.setUnregisterBtnText("注  销");
	}

	private void setTabs() {
		addTab("二维码扫描", 1, CaptureActivity.class);
		addTab("手动输入", 2, SearchOrderActivity.class);
	}

	private void addTab(String label, int id, Class<?> c) {
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);

		TabHost.TabSpec spec = tabHost.newTabSpec(Integer.toString(id));
		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(label);
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.unregister:
			unRegist();
		}
	}

	public void unRegist() {
		SharedPreferenceHelper.getInstance(this, Constans.FILE_NAME_USER)
				.clean();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onStop() {
		isShown = false;
		super.onStop();
	}

	@Override
	protected void onResume() {
		isShown = true;
		super.onResume();
	}
}
