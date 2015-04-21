package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;

public class ShoppingCardItem  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3112881007920859629L;
	private int quantity;
	private String faxMemo;
	private String visitDate;
	private String leaveDate;
	ProdProduct prodProduct;

	private List<OrdTimeInfo> timeInfoList;
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getFaxMemo() {
		return faxMemo;
	}
	public void setFaxMemo(String faxMemo) {
		this.faxMemo = faxMemo;
	}
	public ProdProduct getProdProduct() {
		return prodProduct;
	}
	public void setProdProduct(ProdProduct prodProduct) {
		this.prodProduct = prodProduct;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public List<OrdTimeInfo> getTimeInfoList() {
		return timeInfoList;
	}
	public void setTimeInfoList(List<OrdTimeInfo> timeInfoList) {
		this.timeInfoList = timeInfoList;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	public float getTotalTimeInfoSellPrice() {
		float p =0f;
		for (OrdTimeInfo info : timeInfoList) {
			p+=info.getSellPriceYuan()*info.getQuantity();
		}
		return p;
	}
	
	public float getTotalTimeInfoMarkPrice() {
		float p =0f;
		for (OrdTimeInfo info : timeInfoList) {
			p+=info.getMarketPriceYuan()*info.getQuantity();
		}
		return p;
	}
	
	public String getCheckInAndCheckOutDate(){
		List<Date> dateList = new ArrayList<Date>();
		for (OrdTimeInfo info : timeInfoList) {
			dateList.add(info.getVisitTime());
		}
		if (!dateList.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Collections.sort(dateList);
		Calendar c = Calendar.getInstance();
		c.setTime(dateList.get(dateList.size()-1));
		c.add(Calendar.DATE, 1);
		return sdf.format(dateList.get(0))+"至"+sdf.format(c.getTime());
		}
		return null;
	}
	
	public String getCheckInDate(){
		List<Date> dateList = new ArrayList<Date>();
		for (OrdTimeInfo info : timeInfoList) {
			dateList.add(info.getVisitTime());
		}
		if (!dateList.isEmpty()) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf.format(dateList.get(0));
		}
		return null;
	}
	
	
	public String getCheckOutDate(){
		List<Date> dateList = new ArrayList<Date>();
		for (OrdTimeInfo info : timeInfoList) {
			dateList.add(info.getVisitTime());
		}
		if (!dateList.isEmpty()) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Collections.sort(dateList);
		Calendar c = Calendar.getInstance();
		c.setTime(dateList.get(dateList.size()-1));
		c.add(Calendar.DATE, 1);
		return sdf.format(c.getTime());
		}
		return null;
	}

}
