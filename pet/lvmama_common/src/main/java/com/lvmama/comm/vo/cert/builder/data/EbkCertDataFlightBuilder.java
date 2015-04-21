/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.data;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.pet.po.place.PlaceFlight;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.cert.builder.EbkCertDataBuilder;

/**
 * @author yangbin
 *
 */
public class EbkCertDataFlightBuilder extends EbkCertDataBuilder {

	public EbkCertDataFlightBuilder(Map<String, Object> params) {
		super(params);
	}

	@Override
	protected void makeCertItemData(EbkCertificateItem item) {
		Map<String,Object> map =new HashMap<String, Object>();
		converProductName(item.getOrderItemMeta().getProductName(), map);
		map.put("quantity", item.getOrderItemMeta().getQuantity()*item.getOrderItemMeta().getProductQuantity());
		map.put("settlementPrice", item.getOrderItemMeta().getActualSettlementPriceYuan());
		JSONObject obj = JSONObject.fromObject(map);
		addData(item.getEbkCertificateItemId(), Constant.EBK_CERT_OBJ_TYPE.EBK_CERTIFICATE_ITEM, obj.toString());
		totalSettlementPrice+=item.getOrderItemMeta().getTotalSettlementPrice();
		
		
		if(first){
			first=false;
			flightCode =item.getOrderItemMeta().getGoFlightCode();
			flightTime =item.getOrderItemMeta().getGoFlightTimeStr();
			if(StringUtils.equals(Constant.TRAFFIC_DIRECTION.ROUND.name(), item.getOrderItemMeta().getDirection())){
				flightCode +="/";
				flightCode +=item.getOrderItemMeta().getBackFlightCode();
				
				flightTime +="/";
				flightTime +=item.getOrderItemMeta().getBackFlightTimeStr();
				
			}
			
			placeFlightService =SpringBeanProxy.getBean(PlaceFlightService.class,"placeFlightService");
			PlaceFlight pf = placeFlightService.queryPlaceFlightDetail(item.getOrderItemMeta().getGoFlightCode());
			if(pf!=null){
				placeAirportName = pf.getStartAirport().getAirportName();
				placeAirportName += "/";
				placeAirportName += pf.getArriveAirport().getAirportName();
			}
		}
	}
	
	private boolean first=true;
	
	@Override
	protected Map<String, Object> getCertBaseInfo() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("totalSettlementPrice", totalSettlementPrice);
		map.put("metaProductName", metaProductName);
		
		map.put("flightCode", flightCode);
		map.put("flightTime", flightTime);
		map.put("placeAirportName", placeAirportName);
		return map;
	}


	private long totalSettlementPrice=0;
	private String metaProductName;
	private String flightCode;
	private String flightTime;
	private String placeAirportName;
	private PlaceFlightService placeFlightService;
}
