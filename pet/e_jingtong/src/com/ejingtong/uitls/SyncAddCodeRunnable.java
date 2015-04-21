package com.ejingtong.uitls;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import android.content.Context;
import android.util.Log;

import com.ejingtong.help.OrderManage;
import com.ejingtong.help.Tools;
import com.ejingtong.http.ResClient;
import com.ejingtong.log.LogManager;
import com.ejingtong.model.Order;
import com.ejingtong.model.OrderHead;
import com.ejingtong.model.OrderMeta;
import com.ejingtong.model.OrderPerson;
import com.ejingtong.model.ResponseData;
import com.ejingtong.push.ClientStart;
import com.google.gson.Gson;
import com.j256.ormlite.stmt.PreparedQuery;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SyncAddCodeRunnable implements Runnable{
	private Context context;
	public SyncAddCodeRunnable(Context context){
		this.context = context;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			 
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IoSession session = SessionManager.getInstance().getCurrentSession();
			if(session==null||session.isBothIdle()){
				PreparedQuery<OrderHead> prepareQuery;
				try {
				prepareQuery = OrderManage.getDefaultDbHelper().getOrderHeadDao()
						.queryBuilder().where().eq("USED_STATUS", "USED")
						.and().eq("SYNC_STATUS", "-1").prepare();
				List<OrderHead> orderHeads = OrderManage.getDefaultDbHelper().getOrderHeadDao().query(prepareQuery);
				LogManager.log("开始同步 找到:"+orderHeads.size()+"条待同步的数据");
				for(OrderHead orderHead : orderHeads){
					if(orderHead == null){
						continue;
					}
					Order order = new Order();
					Map<String, Object> queryParameter = new HashMap<String, Object>();
					queryParameter.put("ORDER_ID", orderHead.getOrderId());
					
					List<OrderMeta> orderMetas = OrderManage.getDefaultDbHelper().getOrderMetaDao().queryForFieldValues(queryParameter);
					List<OrderPerson> orderPersons = OrderManage.getDefaultDbHelper().getOrderPersonDao().queryForFieldValues(queryParameter);
					order.setBaseInfo(orderHead);
					order.setMetas(orderMetas);
					order.setPersons(orderPersons);
					this.syncStatus2Servise(order);
					Thread.sleep(3*1000);
				}
				
				}catch(Exception ex){
					ex.printStackTrace();
				}
			} else {
				LogManager.log("session 繁忙 稍后 同步");
			}
		}
		
		
		
	}
	
	private String getMetaQuanty(Order orderData){
		String strMeta = "";
		if(orderData == null || orderData.getMetas() == null){
			return strMeta;
		}
		
		int i=0;
		for(OrderMeta orderMeta : orderData.getMetas()){
			String value = orderMeta.getRealQuantity() + "";
			
			if(i==0){
				strMeta = orderMeta.getOrderItemMetaId() + "_" + value;
			}else{
				strMeta = strMeta + "," + orderMeta.getOrderItemMetaId() + "_" + value;
			}
			i++;
		}
		return strMeta;
	}
	
	private void syncStatus2Servise(final Order orderData){
		
		
		RequestParams params = new RequestParams();
		params.put("addCode", orderData.getBaseInfo().getAddCode());
		params.put("performTime", Tools.getFormateDate("yyyy-MM-dd HH:mm:ss"));
		params.put("item", getMetaQuanty(orderData));
		ResClient.pass(this.context,orderData.getBaseInfo().getAddCode(), params, new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Log.i("", "同步通关状态失败");
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onFinish() {
				
				super.onFinish();
			}

			@Override
			public void onStart() {
				Log.i("", "同步通关状态开始");
				super.onStart();
			}

			@Override
			public void onSuccess(String arg0) {
				Gson gson = new Gson();
				ResponseData responseData = gson.fromJson(arg0, ResponseData.class);
				if(responseData.getCode() == 0){
					LogManager.log(responseData.getSyncTime() + "码" + orderData.getBaseInfo().getAddCode() + "同步成功");
					orderData.getBaseInfo().setSyncTime(responseData.getSyncTime());
					orderData.getBaseInfo().setSyncStatus("0");
					
					try {
						
						OrderManage.getDefaultDbHelper().getOrderHeadDao().update(orderData.getBaseInfo());
					} catch (SQLException e) {
						LogManager.log(Tools.getCurrentDate() + "码" + orderData.getBaseInfo().getAddCode() +  "更新同步状态到库失败");
						e.printStackTrace();
					}
					
				}
				
				super.onSuccess(arg0);
			}
			
		});
	}

}
