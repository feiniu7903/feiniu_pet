package com.ejingtong.activity.order;

import java.sql.SQLException;
import java.util.List;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.ejingtong.R;
import com.ejingtong.activity.BaseActivity;
import com.ejingtong.help.OrderManage;
import com.ejingtong.help.Tools;
import com.ejingtong.log.LogManager;
import com.ejingtong.model.KeyValue;
import com.ejingtong.model.Order;
import com.ejingtong.model.OrderFormatData;
import com.ejingtong.model.OrderMeta;
import com.ejingtong.view.HeadView;
import com.ejingtong.view.viewRowWithLine;
import com.ejingtong.view.viewRowWithLine.OnValueChangeListener;

public class OrderDetailActivity extends BaseActivity implements
		android.view.View.OnClickListener {
	private HeadView headView;
	private TextView tipsView;
	private View tipsLine;
	private LinearLayout rootView;
	// private LayoutInflater inflater;
	private Order orderData = null;
	private LayoutParams lp = null;
	private Button btnPass;
	private int totalPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);

		rootView = (LinearLayout) findViewById(R.id.container_view);
		orderData = (Order) getIntent().getSerializableExtra("order_detail");
		initUi();
		initDataInUi();
	}

	private void initUi() {
		headView = (HeadView) findViewById(R.id.order_detail_head_view);
		headView.setUnregisterBtnVisible(View.GONE);
		headView.setTitleText("订单明细");
		headView.setHomeBtnVisible(View.GONE);
		headView.setBackBtnVisible(View.GONE);
		tipsView = (TextView) findViewById(R.id.order_detail_tip);
		tipsLine = findViewById(R.id.order_detail_tip_line);
		btnPass = (Button) findViewById(R.id.order_pass);
		btnPass.setOnClickListener(this);
		this.setTipsVisible(View.GONE);
		setBackButton();
		setHomeButton();
	}

	private void initDataInUi() {
		LinearLayout baseinfoLayoutView = null;
		if (orderData.getBaseInfo().isToSupplier()) {
			tipsView.setVisibility(View.VISIBLE);
			tipsView.setText("未付款！需要向你支付" + getTotalPrice(0, 0) + "元");
		}
		baseinfoLayoutView = getBlockLayout(OrderFormatData
				.FormatOrderInfo(orderData));
		// 订单信息部分
		if (baseinfoLayoutView != null) {
			lp = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			rootView.addView(baseinfoLayoutView, lp);
		}

		// meta信息部分
		List<OrderFormatData> orderMetas = OrderFormatData
				.FormatOrderMeta(orderData);

		if (orderMetas != null) {
			LinearLayout personLayoutView = null;
			for (int i = 0; i < orderMetas.size(); i++) {
				personLayoutView = getBlockLayout(orderMetas.get(i));
				if (personLayoutView != null) {
					lp = new LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					lp.topMargin = 15;
					rootView.addView(personLayoutView, lp);
				}
			}
		}

		// 游客信息部分
		List<OrderFormatData> orderPersons = OrderFormatData
				.FormatOrderPerson(orderData);

		if (orderPersons != null) {
			LinearLayout personLayoutView = null;
			for (int i = 0; i < orderPersons.size(); i++) {
				personLayoutView = getBlockLayout(orderPersons.get(i));
				if (personLayoutView != null) {
					lp = new LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					lp.topMargin = 15;
					rootView.addView(personLayoutView, lp);
				}
			}
		}

	}

	private void setTipsVisible(int visible) {
		tipsView.setVisibility(visible);
		tipsLine.setVisibility(visible);
	}

	private LinearLayout getBlockLayout(OrderFormatData formatData) {
		if (formatData == null) {
			return null;
		}

		List<KeyValue> data = formatData.getData();

		if (data == null) {
			return null;
		}

		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setBackgroundColor(Color.WHITE);
		layout.setOrientation(LinearLayout.VERTICAL);

		lp = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);

		viewRowWithLine view = new viewRowWithLine(this);

		view.setLableText(formatData.getTitle());
		view.setBoldLineVisible(View.VISIBLE);

		view.setValueEnable(false);

		layout.addView(view, lp);
		view.setLableText(formatData.getTitle());

		for (int i = 0; i < data.size(); i++) {
			KeyValue keyValue = data.get(i);
			viewRowWithLine viewContent = new viewRowWithLine(this);
			viewContent.setValueEnable(false);

			viewContent.setLableText(keyValue.getKey() + ":");
			viewContent.setValueText(keyValue.getValue());

			if (orderData.getBaseInfo().isToSupplier()
					&& keyValue.getMeta() != null
					&& "REALQUANTITY".equalsIgnoreCase(keyValue.getTag())) {
				// TODO 如果支付给第三方，显示修改数量按钮
				viewContent.setBtnVisible(View.VISIBLE);
				viewContent.setMetaId(keyValue.getMeta().getOrderItemMetaId());
				viewContent.setmRange(keyValue.getMeta().getQuantity());
				viewContent
						.setValueChangeListener(new OnRealQuantityChangeListener());
				viewContent.setId(keyValue.getMeta().getOrderItemMetaId()
						.intValue());
			}

			if (i != (data.size() - 1)) {
				viewContent.setBaseLineVisible(View.VISIBLE);
			}
			layout.addView(viewContent, lp);
		}

		View lineView = new View(getApplicationContext());
		lp = new LayoutParams(LayoutParams.FILL_PARENT, 2);
		lineView.setBackgroundResource(R.drawable.line);
		layout.addView(lineView, lp);
		return layout;
	}

	private void pass() {
		// showToast("通关成功");
		// TODO 通关后改变数据库里面的订单状态并上报给服务器;
		if (orderData == null) {
			showToast("无效订单");
			return;
		}

		String time = Tools.getCurrentDate();
		orderData.getBaseInfo().getOrderViewStatus();
		orderData.getBaseInfo().setAddCodeStatus("USED");
		orderData.getBaseInfo().setUsedTime(time);
		orderData.getBaseInfo().setSyncStatus("-1"); // 同步状态, 0表示成功,-1表示失败
		try {
			OrderManage.getInstance(getApplicationContext()).getDBHelper()
					.getOrderHeadDao().update(orderData.getBaseInfo());
			if (!orderData.getBaseInfo().isToLvmm()) {
				for (OrderMeta orderMeta : orderData.getMetas()) {
					if (orderMeta.getQuantity() != orderMeta.getRealQuantity()) {
						OrderManage.getInstance(getApplicationContext())
								.getDBHelper().getOrderMetaDao()
								.update(orderMeta);
					}
				}
				showToast("通关成功");
			}
		} catch (SQLException e) {
			LogManager.log(Tools.getCurrentDate() + "码"
					+ orderData.getBaseInfo().getAddCode() + "更新同步状态到库失败");
			e.printStackTrace();
		}
		LogManager.log(time + "码" + orderData.getBaseInfo().getAddCode() + "通关");
//		Log.e("通关", "-----测试------"+time + "码" + orderData.getBaseInfo().getAddCode() + "通关");
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_pass:
			pass();
		}
	}

	/**
	 * 格式化订单子项，（格式 metaId_quantity eg: 10024_2,10025_1）
	 * @return
	 */
	private String getMetaQuanty() {
		String strMeta = "";
		if (orderData == null || orderData.getMetas() == null) {
			return strMeta;
		}

		int i = 0;
		viewRowWithLine valueView;
		for (OrderMeta orderMeta : orderData.getMetas()) {
			String value = orderMeta.getQuantity() + "";
			valueView = (viewRowWithLine) findViewById(orderMeta
					.getOrderItemMetaId().intValue());

			if (valueView != null) {
				value = valueView.getValue();
			}
			if (i == 0) {
				strMeta = orderMeta.getOrderItemMetaId() + "_" + value;
			} else {
				strMeta = strMeta + "," + orderMeta.getOrderItemMetaId() + "_"
						+ value;
			}
			i++;
		}
		return strMeta;
	}

	class OnRealQuantityChangeListener implements OnValueChangeListener {
		@Override
		public void onChange(TextView v, long id) {
			int newQuantity = 0;
			try {
				newQuantity = Integer.parseInt(v.getText().toString().trim());
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (newQuantity > 0) {

				tipsView.setText("未付款！需要向你支付" + getTotalPrice(id, newQuantity)
						+ "元");
			}
		}
	}

	public float getTotalPrice(long metaId, int quantity) {
		int totalPrice = 0;
		for (OrderMeta orderMeta : orderData.getMetas()) {
			if (metaId == orderMeta.getOrderItemMetaId()) {
				orderMeta.setRealQuantity(quantity);
			}
			totalPrice = totalPrice + orderMeta.getSellPrice()
					* orderMeta.getRealQuantity();
		}
		return (totalPrice / 100);
	}
}
