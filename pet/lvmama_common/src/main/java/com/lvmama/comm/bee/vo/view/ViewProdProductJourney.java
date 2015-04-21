/**
 * 
 */
package com.lvmama.comm.bee.vo.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductJourney;
import com.lvmama.comm.utils.ord.ProductJourneyUtil;
import com.lvmama.comm.vo.Constant;


/**
 * 行程数据.
 * @author yangbin
 *
 */
public class ViewProdProductJourney extends ProdProductJourney implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 594391647382322219L;

	private Map<String,List<ProdProduct>> productMap=new HashMap<String, List<ProdProduct>>();
	
	private Date beginTime;
	private Date hotelEndTime;
	private Date endTime;

	/* (non-Javadoc)
	 * @see com.lvmama.common.prd.po.ProdProductJourney#setProdJourneyGroup(java.util.Map)
	 */
	@Override
	public void setProdJourneyGroup(Map<String, List<ProdJourneyProduct>> map) {		
		super.setProdJourneyGroup(map);
		for(String key:map.keySet()){
			productMap.put(key, ProductJourneyUtil.converToProduct(map.get(key)));
		}
	}

	/**
	 * @return the productMap
	 */
	public Map<String, List<ProdProduct>> getProductMap() {
		return productMap;
	}
	
	
	public List<ProdProduct> getHotelProductList(){
		return productMap.get(Constant.PRODUCT_TYPE.HOTEL.name());
	}
	
	public List<ProdProduct> getTicketProductList(){
		return productMap.get(Constant.PRODUCT_TYPE.TICKET.name());
	}
	
	public List<ProdProduct> getRouteProductList(){
		return productMap.get(Constant.PRODUCT_TYPE.ROUTE.name());
	}
	
	public List<ProdProduct> getTrafficProductList(){
		return productMap.get(Constant.PRODUCT_TYPE.TRAFFIC.name());
	}
	
	public void setBeginTime(Date date){
		this.beginTime=date;
		this.hotelEndTime=DateUtils.addDays(beginTime, getMaxTime().getNights().intValue());//以离店时间来显示
		this.endTime=DateUtils.addDays(beginTime, getMaxTime().getDays().intValue());
	}

	/**
	 * @return the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @return the hotelEndTime
	 */
	public Date getHotelEndTime() {
		return hotelEndTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	
	public List<Date> getTimeList(){
		List<Date> list=new ArrayList<Date>();
		list.add(beginTime);
		for(int i=1;i<getMaxTime().getDays();i++){
			list.add(DateUtils.addDays(beginTime, i));
		}
		return list;
	}
	
	/**
	 * 判断当前行程段是否有酒店
	 * @return
	 */
	public boolean hasHotel(){
		return CollectionUtils.isNotEmpty(getHotelList());
	}
	
	/**
	 * 判断当前行程段是否有酒店
	 * @return
	 */
	public boolean hasTicket(){
		return CollectionUtils.isNotEmpty(getTicketList());
	}
	
	public boolean hasRoute(){
		return CollectionUtils.isNotEmpty(getRouteList());
	}
	
	public boolean hasTraffic(){
		return CollectionUtils.isNotEmpty(getTrafficList());
	}
}
