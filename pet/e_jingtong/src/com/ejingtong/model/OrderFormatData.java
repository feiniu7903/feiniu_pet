package com.ejingtong.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderFormatData {

	private String title;
	private List<KeyValue> data = new ArrayList<KeyValue>();
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<KeyValue> getData() {
		return data;
	}

	public void setData(List<KeyValue> data) {
		this.data = data;
	}

	public static OrderFormatData FormatOrderInfo(Order order){
		OrderFormatData formatData = new OrderFormatData();
		
		 try{
			formatData.title = "订单信息 ";
			
			formatData.data.add(new KeyValue("订单号", "" + order.getBaseInfo().getOrderId()));
			formatData.data.add(new KeyValue("取票人", order.getContactName()));
			formatData.data.add(new KeyValue("手机号", order.getMobileNumber()));
			formatData.data.add(new KeyValue("游玩时间", order.getBaseInfo().getValidTime()));
		}catch(NullPointerException e){
			return null;
		}
		 
		return formatData;
	}

	
	
	public static List<OrderFormatData> FormatOrderMeta(Order order){
		
		if(order == null){
			return null;
		}
		
		List<OrderFormatData> formatDatas = new ArrayList<OrderFormatData>();
		List<OrderMeta> metas = order.getMetas();
		
		if(metas == null || metas.size() == 0){
			return null;
		}
		
		for(OrderMeta meta : metas){
			OrderFormatData formatData = new OrderFormatData();
			try{
				formatData.title = meta.getProductName();
				formatData.data.add(new KeyValue("预定数量", meta.getQuantity() + ""));
				if(meta.getRealQuantity() < 1){
					meta.setRealQuantity(meta.getQuantity());
				}
				formatData.data.add(new KeyValue("实际数量", meta.getRealQuantity() + "", meta, "REALQUANTITY"));
				
				formatDatas.add(formatData);
			}catch(NullPointerException e){
				continue;
			}
		}
		
		return formatDatas;
	}
	
	public static List<OrderFormatData> FormatOrderPerson(Order order){
		
		if(order == null){
			return null;
		}
		
		List<OrderFormatData> formatDatas = new ArrayList<OrderFormatData>();
		List<OrderPerson> persons = order.getPersons();
		
		if(persons == null || persons.size() == 0){
			return null;
		}
		
		for(int i=0; i<persons.size(); i++){
			OrderFormatData formatData = new OrderFormatData();
			try{
				formatData.title = "游客" + (i + 1);
				formatData.data.add(new KeyValue("姓名", persons.get(i).getName()));
				formatData.data.add(new KeyValue("证件类型", "" + persons.get(i).getZhCertType()));
				formatData.data.add(new KeyValue("证件号码", persons.get(i).getCertNo()));
				formatData.data.add(new KeyValue("联系电话", "" + persons.get(i).getMobile()));
				
				formatDatas.add(formatData);
			}catch(NullPointerException e){
				continue;
			}
		}
		
		return formatDatas;
	}

		
	}