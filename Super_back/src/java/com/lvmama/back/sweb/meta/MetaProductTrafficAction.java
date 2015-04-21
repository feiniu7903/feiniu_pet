/**
 * 
 */
package com.lvmama.back.sweb.meta;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.meta.MetaProductTraffic;
import com.lvmama.comm.pet.po.place.PlaceFlight;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @author shihui
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/meta/add_traffic.jsp")
})
public class MetaProductTrafficAction extends MetaProductEditAction<MetaProductTraffic>{

	private PlaceFlightService placeFlightService;
	
	public MetaProductTrafficAction() {
		super(Constant.PRODUCT_TYPE.TRAFFIC.name());
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -6271562720774239397L;

	@Override
	@Action("/meta/toAddTraffic")
	public String addMetaProduct() {
		// 获取币种集合
		getCurrency();
		return goAfter();
	}

	@Override
	@Action("/meta/toEditTraffic")
	public String toEdit() {
		doBefore();
		//-----------航班信息begin----------------
		if(metaProduct.getGoFlight()!=null){
		PlaceFlight flight=placeFlightService.queryPlaceFlight(metaProduct.getGoFlight());
			if(flight!=null){
				metaProduct.setGoFlightName(flight.getFlightNo());
			}
		}
		if(metaProduct.getBackFlight()!=null){
		PlaceFlight flight=placeFlightService.queryPlaceFlight(metaProduct.getBackFlight());
		  if(flight!=null){
			  metaProduct.setBackFlightName(flight.getFlightNo());
		  }
		}
				//-----------航班信息end----------------
		getCurrency();
		return goAfter();
	}

	@Override
	@Action("/meta/saveTraffic")
	public void save() {
		// TODO Auto-generated method stub
		saveMetaProduct();
	}
	
	public List<CodeItem> getSubProductTypeList(){
		return ProductUtil.getTrafficSubTypeList();
	}
	
	public List<CodeItem> getDirectionTypeList(){
		return ProductUtil.getDirectionTypeList();
	}

	public void setPlaceFlightService(PlaceFlightService placeFlightService) {
		this.placeFlightService = placeFlightService;
	}
}
