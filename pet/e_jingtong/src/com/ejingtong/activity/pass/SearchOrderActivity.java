package com.ejingtong.activity.pass;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.ejingtong.R;
import com.ejingtong.activity.BaseActivity;
import com.ejingtong.help.OrderManage;
import com.ejingtong.model.Order;

public class SearchOrderActivity extends BaseActivity implements
		OnClickListener, OnLongClickListener {
	private final String TAG = "SearchOrderActivity";
	private TextView valueText;
	private String value = "";
	private long firstTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_search_order);
		initUi();
	}

	private void initUi() {
		valueText = (TextView) findViewById(R.id.search_order_value);
		initKeyBoard();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.unregister : unRegist(); finish();
		case R.id.software_key_delete:
			delete();
			break;
		case R.id.software_key_search:
			search(value);
			break;
		default:
			inputAction(v);
			break;
		}
	}

	private void delete() {
		if (value.length() > 0) {
			value = value.substring(0, value.length() - 1);
			valueText.setText(value);
		}
	}

	private void inputAction(View v) {
		Button button = (Button) v;
		value = value + button.getText().toString();
		valueText.setText(value);
	}

	private void initKeyBoard() {
		Button key0, key1, key2, key3, key4, key5, key6, key7, key8, key9, keyDelete, keySearch;
		key0 = (Button) findViewById(R.id.software_key_0);
		key1 = (Button) findViewById(R.id.software_key_1);
		key2 = (Button) findViewById(R.id.software_key_2);
		key3 = (Button) findViewById(R.id.software_key_3);
		key4 = (Button) findViewById(R.id.software_key_4);
		key5 = (Button) findViewById(R.id.software_key_5);
		key6 = (Button) findViewById(R.id.software_key_6);
		key7 = (Button) findViewById(R.id.software_key_7);
		key8 = (Button) findViewById(R.id.software_key_8);
		key9 = (Button) findViewById(R.id.software_key_9);
		keyDelete = (Button) findViewById(R.id.software_key_delete);
		keySearch = (Button) findViewById(R.id.software_key_search);

		key0.setOnClickListener(this);
		key1.setOnClickListener(this);
		key2.setOnClickListener(this);
		key3.setOnClickListener(this);
		key4.setOnClickListener(this);
		key5.setOnClickListener(this);
		key6.setOnClickListener(this);
		key7.setOnClickListener(this);
		key8.setOnClickListener(this);
		key9.setOnClickListener(this);
		keyDelete.setOnClickListener(this);
		keyDelete.setOnLongClickListener(this);
		keySearch.setOnClickListener(this);
	}

	@Override
	public boolean onLongClick(View v) {
		if (v.getId() == R.id.software_key_delete) {
			value = "";
			valueText.setText(value);
		}
		return false;
	}

	private void search(String strAddCode) {
		if (strAddCode == null || "".equals(strAddCode)) {
			showToast("请输入标识码");
			return;
		}

		int addCode = -1;
		try {
			addCode = Integer.parseInt(strAddCode);

		} catch (NumberFormatException e) {
			showToast("不合法的标识码");
			return;
		}

		Order order = OrderManage.getInstance(getApplicationContext())
				.searchOrder(addCode);
		if (order == null) {
			downloadOrder(this, addCode);
		} else {
			canToPass(this, order);
		}
	}

	@Override
	public void onBackPressed() {
		long secondeTime = System.currentTimeMillis();
		if ((secondeTime - firstTime) > 1000) {
			showToast("再按一次退出");
			// OpenHelperManager.releaseHelper();
			firstTime = secondeTime;
		} else {
			// ClientStart.getInstance().disConnect();
			super.onBackPressed();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
