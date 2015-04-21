package com.lvmama.back.sweb.phoneorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.vo.mark.MarkCouponProductModel;
import com.lvmama.comm.pet.vo.mark.ProductCoupon;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;
import com.lvmama.comm.utils.ActivityUtil;
import com.lvmama.comm.vo.Constant;
@ParentPackage("json-default")

@Results( { 
	@Result(name="orderCoupon",location="/WEB-INF/pages/back/ord/youhui.jsp")
})
public class CouponAction extends  BaseAction{

	/**
	 * 
	 */
	private Logger logger = Logger.getLogger(CouponAction.class);
	private static final long serialVersionUID = -7068613384454125778L;
	HttpSession session = this.getSession();
	/**
	 * 订单服务接口.
	 */
	private OrderService orderServiceProxy;
	private List<ProductCoupon> productCouponList;
	private List<MarkCoupon> orderCouponList;
	private String jsonMsg;
	private String productId;
	private String userId;
	private String code;
	private ValidateCodeInfo info;
	private String couponId;
	private String uuid;
	private String checkType;
	private MarkCouponService markCouponService;
	private List<MarkCouponProductModel> listCouponProductModel;
	private boolean showYouHui = true;
	private ProdProduct product;
	private List<MarkCoupon> partyCouponList;
	private Long orderId;
	
	/**
	 * 销售产品DAO.
	 */
	private ProdProductService prodProductService;
	
	private boolean userCoupon=true;

	
	
	@Action(value="/shoppingCard/loadChoseCoupon",results=@Result(name="loadChoseCoupon",location="/WEB-INF/pages/back/phoneorder/youhui/chosecoupon.jsp"))
	public String loadChoseCoupon(){
   		
		return "loadChoseCoupon";
	}
	
	/**
	 * 订单中的销售产品是否可以使用优惠券.
	 * @return
	 */
	@Action("/orderCoupon/allMarkCoupon")
	public String updateOrderCoupon(){
		OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
		userCoupon=this.findUserCouponByOrderId(); 
		this.initProdProduct();
		if(!order.isPaymentSucc()){
		     loadCoupon(findProductbyOrder(order));
		}else{
			showYouHui = false;
		}
		return "orderCoupon";
	}

	/**
	 * 订单的销售产品.
	 * @param order
	 * @return
	 */
	private List<ProdProduct> findProductbyOrder(OrdOrder order) {
		List<ProdProduct> productList=new ArrayList<ProdProduct>();
		for (OrdOrderItemProd ordOrderItemProd : order
				.getOrdOrderItemProds()) {
			// 销售产品
			 ProdProduct prodProduct = prodProductService.getProdProduct(ordOrderItemProd.getProductId());
			 if(prodProduct != null) {
				 productList.add(prodProduct);
			 }
		}
        return productList;
	}
	
	/**
	 * 产品是否可以使用优惠券.
	 * @param productList
	 */
	private void loadCoupon(List<ProdProduct> productList){
		try {
			List<Long> ids = new ArrayList<Long>();
			List<String> subProductTypes = new ArrayList<String>();
			productCouponList = new ArrayList<ProductCoupon>();
			if(productList != null && productList.size() > 0){
				for (ProdProduct pp : productList) {
	 				//if(pp.getPayToSupplier().equalsIgnoreCase("true")) {//支付给供应商
					if("true".equalsIgnoreCase(pp.getPayToSupplier())) {//支付给供应商
						showYouHui = false;
						break;
					}
					ids.add(pp.getProductId());
					subProductTypes.add(pp.getSubProductType());
				}
			}
			if(ids.size()>0){
				
		    	Map<String,Object> map = new HashMap<String,Object>();
		    	map.put("productIds", ids);
		    	map.put("subProductTypes", subProductTypes);
		    	map.put("withCode", "false");//只取优惠活动
				this.partyCouponList = markCouponService.selectAllCanUseAndProductCanUseMarkCoupon(map);
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
 
	
	
	/**
	 * 订单的优惠券列表.
	 * @return
	 */
    private boolean findUserCouponByOrderId(){
    	//boolean isCounpon=true;
    	//优惠券列表
    	List listAmountItem=orderServiceProxy.queryOrdOrderAmountItem(new Long(orderId), Constant.ORDER_AMOUNT_TYPE.ORDER_COUPON_AMOUNT.name());
		if(listAmountItem!=null && listAmountItem.size()>0){
    		//isCounpon=false;
    		return false;
    	}
    	return true;
    	//return isCounpon;
    }
    
    private void initProdProduct() {
    	//订单所关联的主销售产品是否可以参与优惠券(活动).
    	OrdOrder ordOrder = this.orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(this.orderId));
    	Long mainProductId = ordOrder.getMainProduct().getProductId();
    	product = this.prodProductService.getProdProductById(mainProductId);
		//五周年活动不适用优惠券
		if(ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
			product.setCouponAble("false");
			product.setCouponActivity("false");
		}
    }
   
	public ProdProduct getProduct() {
		return product;
	}

	public void setProduct(ProdProduct product) {
		this.product = product;
	}

	public List<ProductCoupon> getProductCouponList() {
		return productCouponList;
	}

	public void setProductCouponList(List<ProductCoupon> productCouponList) {
		this.productCouponList = productCouponList;
	}

	public List<MarkCoupon> getOrderCouponList() {
		return orderCouponList;
	}

	public void setOrderCouponList(List<MarkCoupon> orderCouponList) {
		this.orderCouponList = orderCouponList;
	}

	public String getJsonMsg() {
		return jsonMsg;
	}

	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ValidateCodeInfo getInfo() {
		return info;
	}

	public void setInfo(ValidateCodeInfo info) {
		this.info = info;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setListCouponProductModel(
			List<MarkCouponProductModel> listCouponProductModel) {
		this.listCouponProductModel = listCouponProductModel;
	}

	public List<MarkCouponProductModel> getListCouponProductModel() {
		return listCouponProductModel;
	}

	public boolean isShowYouHui() {
		return showYouHui;
	}

	public List<MarkCoupon> getPartyCouponList() {
		return partyCouponList;
	}

	public void setPartyCouponList(List<MarkCoupon> partyCouponList) {
		this.partyCouponList = partyCouponList;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public boolean isUserCoupon() {
		return userCoupon;
	}
	public void setUserCoupon(boolean userCoupon) {
		this.userCoupon = userCoupon;
	}
	
}
