package com.lvmama.back.sweb.ord;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.service.ord.OrderHotelService;
@Results({
	@Result(name="list",location="/WEB-INF/pages/back/ord/ord_hotel_monitor_list.jsp")
})
public class OrdOrderHotelAction extends BackBaseAction{
	private static final long serialVersionUID = 4298294543398712635L;
	private OrderHotelService orderHotelService;
	//驴妈妈订单号
	private String lvmamaOrderId;
	//合作伙伴订单号
	private String partnerOrderId;
	//订单子子项编号
	private String  orderItemId; 
	
	@Action("/ord/showOrdOrderHotelList")
	public String showOrdOrderHotelList(){
		Map<String,Object> param = initParam();
		pagination = initPage();
		param.put("startRows", pagination.getStartRows());
		param.put("endRows", pagination.getEndRows());
		System.out.println(orderHotelService);
		pagination.setTotalResultSize(orderHotelService.countOrdOrderHotelListByParam(param));
		pagination.setItems(orderHotelService.queryOrdOrderHotelListByParam(param));
		pagination.buildUrl(getRequest());
		return "list";
	}

	/**
	 * 初始化查询参数
	 * @return
	 */
	private Map<String, Object> initParam() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(lvmamaOrderId)) {
			param.put("lvmamaOrderId",Long.valueOf(lvmamaOrderId.trim()));
		}
		if (StringUtils.isNotBlank(partnerOrderId)) {
			param.put("partnerOrderId", partnerOrderId.trim());
		}
		if (StringUtils.isNotBlank(orderItemId)) {
			param.put("orderItemId",Long.valueOf(orderItemId.trim()));
		}
		return param;
	}
	public void setOrderHotelService(OrderHotelService orderHotelService) {
		this.orderHotelService = orderHotelService;
	}

	public void setLvmamaOrderId(String lvmamaOrderId) {
		this.lvmamaOrderId = lvmamaOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getLvmamaOrderId() {
		return lvmamaOrderId;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public String getOrderItemId() {
		return orderItemId;
	}
	
}
