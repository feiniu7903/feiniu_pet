/**
 * 
 */
package com.lvmama.comm.bee.vo.view;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.lvmama.comm.bee.po.prod.ProdProductJourney;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
public class ViewProdProductJourneyDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2428359905850113629L;

	private List<ViewProdProductJourney> productJourneyList;
	
	/**
	 * 该值的返回值代表当前的行程是否可用
	 */
	private boolean success=true;

	private String msg;
	
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	
	
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	public void raise(Exception ex){
		this.success=false;
		this.msg=ex.getMessage();
	}
	
	
	/**
	 * 查询所有的行程当中是否存在酒店
	 * @return
	 */
	public boolean hasHotel(){
		return exists(Constant.PRODUCT_TYPE.HOTEL);
	}
	
	public boolean hasTicket(){
		return exists(Constant.PRODUCT_TYPE.TICKET);
	}
	
	public boolean hasRoute(){
		return exists(Constant.PRODUCT_TYPE.ROUTE);
	}
	
	public boolean hasTraffic(){
		return exists(Constant.PRODUCT_TYPE.TRAFFIC);
	}
	
	private boolean exists(Constant.PRODUCT_TYPE productType){
		boolean flag=false;
		for(ProdProductJourney ppj:productJourneyList){
			switch (productType) {
			case HOTEL:
				flag=CollectionUtils.isNotEmpty(ppj.getHotelList());				
				break;
			case TICKET:
				flag=CollectionUtils.isNotEmpty(ppj.getTicketList());
				break;
			case ROUTE:
				flag=CollectionUtils.isNotEmpty(ppj.getRouteList());
				break;
			case TRAFFIC:
				flag=CollectionUtils.isNotEmpty(ppj.getTrafficList());
				break;
			default:				
				break;
			}
			if(flag){
				break;
			}
		}
		return flag;
	}



	/**
	 * @return the productJourneyList
	 */
	public List<ViewProdProductJourney> getProductJourneyList() {
		return productJourneyList;
	}



	/**
	 * @param productJourneyList the productJourneyList to set
	 */
	public void setProductJourneyList(
			List<ViewProdProductJourney> productJourneyList) {
		this.productJourneyList = productJourneyList;
	}
	
}
