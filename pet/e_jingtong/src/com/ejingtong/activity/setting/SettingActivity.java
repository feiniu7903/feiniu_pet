package com.ejingtong.activity.setting;

import java.sql.SQLException;

import org.apache.mina.core.session.IoSession;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ejingtong.R;
import com.ejingtong.activity.BaseActivity;
import com.ejingtong.common.Constans;
import com.ejingtong.help.OrderManage;
import com.ejingtong.help.SharedPreferenceHelper;
import com.ejingtong.push.ClientStart;
import com.ejingtong.uitls.CommandUtils;
import com.ejingtong.uitls.SessionManager;
import com.ejingtong.view.HeadView;

public class SettingActivity extends BaseActivity {

	private HeadView headView;
	private EditText addrLogicText;
	private EditText addrPushCallbackText;
	private EditText addrPushServerText;
	private EditText addrPushPortText;
	
	private TextView usedText;
	private TextView unusedText;
	private TextView cancledText;
	private Button newDeviceSyncBtn;
	private Button deviceSyncBtn;
	private Handler mHandler ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		this.initUi();
		mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch(msg.arg1){
				case R.id.setting_order_cancle_value:
					cancledText.setText("" + msg.obj);
					break;
				case R.id.setting_order_unused_value:
					unusedText.setText("" + msg.obj);
					break;
				case R.id.setting_order_used_value:
					usedText.setText("" + msg.obj);
					break;
				}
			}
			
		};
		
		
		initData();
		
		initOrderCount();
		
		
	}
	
	
	private void initUi(){
		headView = (HeadView)findViewById(R.id.setting_head_view);
		headView.setTitleText("配置信息");
		headView.setBackBtnVisible(View.VISIBLE);
		headView.setHomeBtnVisible(View.GONE);
		headView.setUnregisterBtnVisible(View.GONE);
		headView.setBackkBtnListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SettingActivity.this.finish();
			}
		});
		
		addrLogicText = (EditText)findViewById(R.id.setting_addr_logic_value);
		addrPushCallbackText = (EditText)findViewById(R.id.setting_addr_push_back_value);
		addrPushServerText = (EditText)findViewById(R.id.setting_addr_push_server_value);
		addrPushPortText = (EditText)findViewById(R.id.setting_addr_push_server_port_value);
		newDeviceSyncBtn = (Button) this.findViewById(R.id.newDeviceSyncBtn);
		newDeviceSyncBtn.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				IoSession session = SessionManager.getInstance().getCurrentSession();
				if(session==null||!session.isConnected()){
					showToast("push服务未连接");
				}  else {
					CommandUtils.sendCommand(Constans.CLIENT_COMMAND_TYPE.SYNC_DATA_NEW_DEVICE.name(), getApplicationContext());
					showToast("命令已发送");
				}
			}
		});
		deviceSyncBtn = (Button) this.findViewById(R.id.deviceSyncBtn);
		
		deviceSyncBtn.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IoSession session = SessionManager.getInstance().getCurrentSession();
				if(session==null||!session.isConnected()){
					showToast("push服务未连接");
				}  else {
					CommandUtils.sendCommand(Constans.CLIENT_COMMAND_TYPE.SYNC_DATA.name(), getApplicationContext());
					showToast("命令已发送");
				}
			}
		});
		
		addrLogicText.addTextChangedListener(new TextChangeListener(R.id.setting_addr_logic_value));
		addrPushCallbackText.addTextChangedListener(new TextChangeListener(R.id.setting_addr_push_back_value));
		addrPushServerText.addTextChangedListener(new TextChangeListener(R.id.setting_addr_push_server_value));
		addrPushPortText.addTextChangedListener(new TextChangeListener(R.id.setting_addr_push_server_port_value));
		
		usedText = (TextView)findViewById(R.id.setting_order_used_value);
		unusedText = (TextView)findViewById(R.id.setting_order_unused_value);
		cancledText = (TextView)findViewById(R.id.setting_order_cancle_value);
		
	}
	
	private void initData(){
		addrLogicText.setText(Constans.ADDR_LOGIC);
		addrPushCallbackText.setText(Constans.ADDR_PUSH_BACK);
		addrPushServerText.setText(Constans.ADDR_PUSH_SERVER);
		addrPushPortText.setText(Constans.PORT_PUSH_SERVER + "");
	}
	
	
	private void initOrderCount(){
		new Thread(){

			@Override
			public void run() {
				super.run();
				OrderManage orderManager = OrderManage.getInstance();
				if(mHandler!=null){
					Message msg = mHandler.obtainMessage();
					msg.arg1 = R.id.setting_order_cancle_value;
					try {
						msg.obj = orderManager.getCancleOrderCount();
					} catch (SQLException e) {
						msg.obj = "统计取消订单失败";
					}
					
					mHandler.sendMessage(msg);
					
					msg = mHandler.obtainMessage();
					msg.arg1 = R.id.setting_order_unused_value;
					try {
						msg.obj = orderManager.getUnUsedOrderCount();
					} catch (SQLException e) {
						msg.obj = "统计未通关订单失败";
					}
					mHandler.sendMessage(msg);
					
					
					msg = mHandler.obtainMessage();
					msg.arg1 = R.id.setting_order_used_value;
					try {
						msg.obj = orderManager.getUsedOrderCount();
					} catch (SQLException e) {
						msg.obj = "统计通关订单失败";
					}
					mHandler.sendMessage(msg);
				}
			}
			
		}.start();
	}
	
	
	
	class TextChangeListener implements TextWatcher{
		int viewId = -1;
		public TextChangeListener(int viewId){
			this.viewId = viewId;
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			switch(viewId){
			case R.id.setting_addr_logic_value: Constans.ADDR_LOGIC = s.toString();
				 SharedPreferenceHelper.getInstance(getApplicationContext(), Constans.FILE_NAME_SETTING).saveStr(Constans.KEY_ADDR_LOGIC_VALUE, Constans.ADDR_LOGIC);
				break;
			case R.id.setting_addr_push_back_value:Constans.ADDR_PUSH_BACK = s.toString();
			SharedPreferenceHelper.getInstance(getApplicationContext(), Constans.FILE_NAME_SETTING).saveStr(Constans.KEY_ADDR_PUSH_CALLBACK_VALUE, Constans.ADDR_PUSH_BACK);
				break;
			case R.id.setting_addr_push_server_value:Constans.ADDR_PUSH_SERVER = s.toString();
			SharedPreferenceHelper.getInstance(getApplicationContext(), Constans.FILE_NAME_SETTING).saveStr(Constans.KEY_ADDR_PUSH_SERVER_VALUE, Constans.ADDR_PUSH_SERVER);break;
			case R.id.setting_addr_push_server_port_value:
				try{
					Constans.PORT_PUSH_SERVER = Integer.parseInt(s.toString());
					SharedPreferenceHelper.getInstance(getApplicationContext(), Constans.FILE_NAME_SETTING).saveInt(Constans.KEY_PORT_PUSH_SERVER_VALUE, Constans.PORT_PUSH_SERVER);break;
				}catch(Exception e){
					addrPushPortText.setError("端口只能包含数字");
				}
				break;
			}
			
		}
		
	}
	
	

}
