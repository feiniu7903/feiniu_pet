/**
 * 
 */
package com.lvmama.comm.bee.vo.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;

/**
 * @author yangbin
 *
 */
public class ViewProdJourneyProduct extends ProdJourneyProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6829334666308729471L;
	private List<Time> timeInfos=new ArrayList<Time>();
	
	public static class Time implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -865744177564449293L;
		private Date date;
		private Long stock;
		private Long sellPrice;
		/**
		 * @return the date
		 */
		public Date getDate() {
			return date;
		}
		/**
		 * @return the stock
		 */
		public Long getStock() {
			return stock;
		}
		/**
		 * @return the sellPrice
		 */
		public Long getSellPrice() {
			return sellPrice;
		}
		
		
		/**
		 * @param stock the stock to set
		 */
		public void setStock(Long stock) {
			this.stock = stock;
		}
		/**
		 * @param sellPrice the sellPrice to set
		 */
		public void setSellPrice(Long sellPrice) {
			this.sellPrice = sellPrice;
		}
		public Time(Date date) {
			super();
			this.date = date;
		}
		
		
	}
	
	
	public void addTimeInfo(Time ti){
		timeInfos.add(ti);		
	}


	/**
	 * @return the timeInfos
	 */
	public List<Time> getTimeInfos() {
		return timeInfos;
	}
	

	/**
	 * @param timeInfos the timeInfos to set
	 */
	public void setTimeInfos(List<Time> timeInfos) {
		this.timeInfos = timeInfos;
	}


	/**
	 * 判断可销售的时间价格表是否为空
	 * @return
	 */
	public boolean isEmptyTimeInfo(){
		return timeInfos.isEmpty();
	}
}
