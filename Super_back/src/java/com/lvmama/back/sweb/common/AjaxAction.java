package com.lvmama.back.sweb.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.util.StringUtils;
import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.vo.mark.CouponUsegeModel;
import com.lvmama.comm.vo.Constant;

@ParentPackage("json-default")
public class AjaxAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6340669523167343353L;
	private PlaceService placeService;
	private List<Place> comPlaceList;
	private String name;
	private OrderService orderServiceProxy;
	private String orderId;
	private MarkCouponService markCouponService;
	private List<CouponUsegeModel> couponUsageModelList;
	private ProdProductService prodProductService;
	private String productId;
	private String subProductType;
	private List<MarkCoupon> markCouponList;
	private ProdProduct pp;
	private List<PayPayment> ordPaymentList;
	private List<OrdOrderAmountItem> listAmountItem;
	private PayPaymentService payPaymentService;
	@Action(value="/ajax/suggest",results=@Result(type="json",name="suggest",params={"includeProperties","comPlaceList\\[\\d+\\]\\.name,comPlaceList\\[\\d+\\]\\.placeId"}))
	public String getSuggestInfo(){
		if (name!=null && !"".equals(name)) {
			this.comPlaceList = this.placeService.selectSuggestPlaceByName(StringUtils.trimAllWhitespace(name));
		}
		return "suggest";
	}
	
	@Action(value="/ajax/loadUsedYouhui",results=@Result(name="loadUsedYouhui",location="/WEB-INF/pages/back/common/used_youhui.jsp"))
	public String loadUsedYouhui(){
		OrdOrder oo = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
		listAmountItem=orderServiceProxy.queryOrdOrderAmountItem(oo.getOrderId(), Constant.ORDER_AMOUNT_TYPE.ORDER_COUPON_AMOUNT.name());
		return "loadUsedYouhui";
	}
	
	@Action(value="/ajax/getCouponList",results=@Result(name="getCouponList",location="/WEB-INF/pages/back/phoneorder/youhui/list_coupon.jsp"))
	public String getCouponList(){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("productId", Long.valueOf(productId));
    	map.put("subProductType", subProductType);
		this.markCouponList = markCouponService.selectProductCanUseMarkCoupon(map);
		return "getCouponList";
	}
	
	@Action(value="/ajax/loadProd",results=@Result(name="loadProd",location="/WEB-INF/pages/back/phoneorder/tips/prod_info.jsp"))
	public String loadProductInfo(){
		this.pp = this.prodProductService.getProdProductById(Long.valueOf(productId));
		//StringUtils.
		return "loadProd";
	}
	
	@Action(value="/ajax/loadOrderPaymentInfo",results=@Result(name="loadOrderPaymentInfo",location="/WEB-INF/pages/back/common/payment_info.jsp"))
	public String loadOrderPaymentInfo(){
		ordPaymentList = payPaymentService.selectByObjectIdAndBizType(Long.parseLong(orderId), com.lvmama.comm.vo.Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
		return "loadOrderPaymentInfo";
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	} 
	
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public List<CouponUsegeModel> getCouponUsageModelList() {
		return couponUsageModelList;
	}

	public void setCouponUsageModelList(List<CouponUsegeModel> couponUsageModelList) {
		this.couponUsageModelList = couponUsageModelList;
	}
 

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<MarkCoupon> getMarkCouponList() {
		return markCouponList;
	}

	public void setMarkCouponList(List<MarkCoupon> markCouponList) {
		this.markCouponList = markCouponList;
	}

	public ProdProduct getPp() {
		return pp;
	}

	public void setPp(ProdProduct pp) {
		this.pp = pp;
	}

	public List<PayPayment> getOrdPaymentList() {
		return ordPaymentList;
	}

	public void setOrdPaymentList(List<PayPayment> ordPaymentList) {
		this.ordPaymentList = ordPaymentList;
	}

	public List<OrdOrderAmountItem> getListAmountItem() {
		return listAmountItem;
	}

	public void setListAmountItem(List<OrdOrderAmountItem> listAmountItem) {
		this.listAmountItem = listAmountItem;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public List<Place> getComPlaceList() {
		return comPlaceList;
	}
	
}
