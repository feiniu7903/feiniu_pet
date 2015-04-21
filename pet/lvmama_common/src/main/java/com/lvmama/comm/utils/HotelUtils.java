package com.lvmama.comm.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;

public class HotelUtils {
	
	public static int getHotelRoomQuantity(List<OrdTimeInfo> timeInfoList){
		int quantity = 0;
		for (OrdTimeInfo timeInfo : timeInfoList) {
			quantity+=timeInfo.getQuantity();
		}
		return quantity;
	}
	
	

	public static void createOrderItemTimeInfo(Date visitTime,Date leaveTime,Item item){
		List<OrdTimeInfo> timeInfoList = new ArrayList<OrdTimeInfo>();
		List<Date> dateList = DateUtil.getDateList(visitTime, leaveTime);
		dateList.remove(dateList.size()-1);

			for (Date date : dateList) {
				OrdTimeInfo ti = new OrdTimeInfo();
				ti.setProductId(item.getProductId());
				ti.setProductBranchId(item.getProductBranchId());
				ti.setVisitTime(date);
				ti.setQuantity(Long.valueOf(item.getQuantity()));
				timeInfoList.add(ti);
			}
			item.setTimeInfoList(timeInfoList);
			item.setQuantity(getHotelRoomQuantity(timeInfoList));
			
		
		

		
	}
	
	
}
