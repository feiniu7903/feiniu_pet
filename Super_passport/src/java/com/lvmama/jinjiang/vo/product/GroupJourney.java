package com.lvmama.jinjiang.vo.product;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.lvmama.comm.bee.po.prod.ViewJourney;


/**
 * 团行程
 * @author yanzhirong
 *
 */
public class GroupJourney  {
	private Set<Date> departDates;//出团日期
	private String key;//MD5加密行程明细后的密钥
	private String priceInclude;//费用包含
	private String priceExclude;//费用不包含
	private List<ViewJourney> viewJourneyList;
	
	public Set<Date> getDepartDates() {
		return departDates;
	}
	public void setDepartDates(Set<Date> departDates) {
		this.departDates = departDates;
	}
	public String getPriceInclude() {
		return priceInclude;
	}
	public void setPriceInclude(String priceInclude) {
		this.priceInclude = priceInclude;
	}
	public String getPriceExclude() {
		return priceExclude;
	}
	public void setPriceExclude(String priceExclude) {
		this.priceExclude = priceExclude;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<ViewJourney> getViewJourneyList() {
		return viewJourneyList;
	}
	public void setViewJourneyList(List<ViewJourney> viewJourneyList) {
		this.viewJourneyList = viewJourneyList;
	}	
	
}
