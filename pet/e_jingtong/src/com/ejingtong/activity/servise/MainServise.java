package com.ejingtong.activity.servise;

import java.util.HashMap;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import com.ejingtong.R;
import com.ejingtong.common.Constans;
import com.ejingtong.help.Tools;
import com.ejingtong.log.LogManager;
import com.ejingtong.model.PushResponseData;
import com.ejingtong.push.ClientStart;
import com.ejingtong.uitls.ConnectThread;
import com.ejingtong.uitls.DeleteOrderRunnable;
import com.ejingtong.uitls.MyThreadPoolExecutor;
import com.ejingtong.uitls.Order2DbRunnalbe;
import com.ejingtong.uitls.SyncAddCodeRunnable;

/**
 * 根据推送下来的订单 获取订单详情并保存到数据库,
 * @author xuqun
 *
 */
public class MainServise extends Service {

	private final String TAG = "GetOrderListServise";

	private NotificationManager notificationManager;

	private ConnectivityManager connectivityManager;
	
	private NetworkInfo info;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		/**
		 * 开启同步数据线程
		 */
		MyThreadPoolExecutor.getInstance().submit(new SyncAddCodeRunnable(getApplicationContext()));
		/**
		 * 链接push服务
		 */
//		MyThreadPoolExecutor.getInstance().submit(new ConnectThread());
		MyThreadPoolExecutor.getInstance().submit(Order2DbRunnalbe.getInstance());
		
		IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                Log.d("mark", "网络状态已经改变");
                connectivityManager = (ConnectivityManager)      
                                         getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();  
                if(info != null && info.isAvailable()) {
                    String name = info.getTypeName();
                    Log.d("mark", "当前网络名称：" + name);
                    /**
                     * 重新连接push 服务 当有网络的时候
                     */
                    MyThreadPoolExecutor.getInstance().submit(new ConnectThread());
                } else {
                    Log.d("mark", "没有可用网络");
                }
            }
        }
    };

	
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if(null != intent){
		PushResponseData pushData = (PushResponseData) intent.getSerializableExtra("push_data");
		if(pushData != null){
		if(pushData.isSyncDatas()){
			//DbJobQueueUtils.getInstance(getApplicationContext()).getQueueList().offer(pushData.getDatas());
//			
//			Future f = MyThreadPoolExecutor.getInstance().submit(new Order2DbRunnalbe(pushData.getDatas()));
//			if (f.isDone()){
//				
//			}
			Order2DbRunnalbe.getInstance().getOrderQueue().offer(pushData.getDatas());
			
		} else  if(pushData.isDeleteOrder()){
			//删除订单
			MyThreadPoolExecutor.getInstance().submit(new DeleteOrderRunnable(pushData));	
		} else if(pushData.isNotifaction()){
			// 弹出通知栏
			showNotifyaction(pushData.getNotificationMsg());
		} else if(pushData.isUploadLog()){
			//上传日志
			Map<String, String> params = new HashMap<String, String>();
			params.put("pushId", pushData.getPushId());
			params.put("udid", Constans.IMEI);
			LogManager.uploadFiles(pushData.getDateStr(), params);
		}else if(pushData.isReStart()){
			//重启设备
			Tools.reStart();
		}
		}
		}

	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
	
	private void showNotifyaction(String tipText){
		notificationManager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
		
		Notification notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = tipText;
//		notification.tickerText = "个应用需要更新";
		notification.flags = Notification.FLAG_AUTO_CANCEL;  
		 
		notification.setLatestEventInfo(this, getResources().getString(R.string.app_name), "更新提示", null);  
		
		notificationManager.notify(0, notification);
		
	}

	
}
