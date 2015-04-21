package com.lvmama.distribution.model.ckdevice;

import javax.xml.bind.annotation.XmlRootElement;

import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;

/**
 * 时间价格
 * @author gaoxin
 *
 */
@XmlRootElement
public class Price {
	private TimePrice timePrice;
	private String date;
	private String sellPrice;
	private String marketPrice;
	private String stock;
	private String aheadHour;
	public Price(){}
	
	public Price(TimePrice timePrice){
		this.date = DateUtil.getFormatDate(timePrice.getSpecDate(),"yyyy-MM-dd");
		this.sellPrice = String.valueOf(timePrice.getSellPriceFloat());
		this.marketPrice = String.valueOf(timePrice.getMarketPriceFloat());
		this.stock = String.valueOf(timePrice.getDayStock());
		this.aheadHour = String.valueOf(PriceUtil.convertToHoursForDistribution(timePrice.getAheadHour()));
	}

	public TimePrice getTimePrice() {
		return timePrice;
	}

	public void setTimePrice(TimePrice timePrice) {
		this.timePrice = timePrice;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getAheadHour() {
		return aheadHour;
	}

	public void setAheadHour(String aheadHour) {
		this.aheadHour = aheadHour;
	}

}
