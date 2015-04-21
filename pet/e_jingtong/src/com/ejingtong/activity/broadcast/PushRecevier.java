package com.ejingtong.activity.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ejingtong.activity.login.LoginActivity;
import com.ejingtong.activity.servise.MainServise;

public class PushRecevier extends BroadcastReceiver {

	private final String TAG = "PushRecevier";
	private Context mContext;
	@Override
	public void onReceive(Context context, Intent intent) {
		String action  = intent.getAction();
		Log.i("onReceive", "action：" + action);
		if(action.equals(Intent.ACTION_BOOT_COMPLETED)){
			
			Intent intent2 = new Intent(context,LoginActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent2);
			
//			Intent intent3 = new Intent(context, GetOrderListServise.class);
//			context.startService(intent3);
//			
//			Intent intent4 = new Intent(context, SyncAddcodeState.class);
//			context.startService(intent4);
		}
//		if(databaseHelper == null){
//			databaseHelper = new DatabaseHelperOrmlite(mContext);
//		}
//		mContext = context;
//		
//			PushResponseData pushData = (PushResponseData)intent.getSerializableExtra(Constans.KEY_INTENT_ORDER);
//			
//			if(pushData == null){
//				return;
//			}
//			
//			if(pushData.isPullOrder()){
//				downloadOrder(pushData.getAddCode());
//			}else if(pushData.isOrderUpdate()){
//				updataOrder(pushData.getOrderId());
//			}
		
	}

}
