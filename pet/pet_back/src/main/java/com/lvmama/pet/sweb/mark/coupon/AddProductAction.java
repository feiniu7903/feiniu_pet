package com.lvmama.pet.sweb.mark.coupon;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponProduct;
import com.lvmama.comm.pet.po.mark.ProductTypeEle;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
/**
 *  优惠绑定产品
 * @author yuzhizeng
 *
 */
public class AddProductAction extends CouponBackBaseAction {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 4457317473166782049L;
 
	/**
	 * 优惠券批次标识
	 */
	private Long couponId;
	private String productName;
	private String proudctBizcode;
	private String productId;

	private String ticketSlct;
	private String hotelSlct;
	private String routeSlct;
	private String otherSlct;

	private List<ProdProduct> productList;
	List<String> pidList = new ArrayList<String>();
	private String pidListStr;
	
	// 门票列表. ProductTypeEle---包含优惠对象,产品(子)类型,金额,是否选中)
	private List<ProductTypeEle> ticketList = new ArrayList<ProductTypeEle>();
	// 多个子类型选中checkbox展示(CodeItem:code,name名称,checked)
	private List<CodeItem> ticketCodeItemList = new ArrayList<CodeItem>();

	private List<ProductTypeEle> hotelList = new ArrayList<ProductTypeEle>();
	private List<CodeItem> hotelCodeItemList = new ArrayList<CodeItem>();
	
	private List<ProductTypeEle> otherList = new ArrayList<ProductTypeEle>();
	private List<CodeItem> otherCodeItemList = new ArrayList<CodeItem>();
	
	private List<ProductTypeEle> routeList = new ArrayList<ProductTypeEle>();
	private List<CodeItem> routeCodeItemList = new ArrayList<CodeItem>();
	/**
	 * Tag Service
	 */
	private ProdProductTagService prodProductTagService;
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	
	/**
	 * 进入优惠绑定产品页(查询符合条件的优惠券(活动)结果集).
	 * @return
	 */
	@Action(value = "/mark/coupon/addProduct", results = { @Result(location = "/WEB-INF/pages/back/mark/addProduct.jsp") })
	public String addProduct() {
		Map<String, Object> searchConds = buildTypeSearchConds();
		if (StringUtils.isNotBlank(productName)) {
			searchConds.put("sProductName", productName);
		}
		
		// 依据条件查出所有产品
		Integer totalRecords = prodProductService.selectRowCount(searchConds);
		pagination = Page.page(10, page);
		pagination.setTotalResultSize(totalRecords);
		pagination.setActionType("js");
		pagination.setUrl("javascript:refreshProductList('" + initUrl("addProduct.do") + "')");
		searchConds.put("_startRow", pagination.getStartRows());
		searchConds.put("_endRow", pagination.getEndRows());

		//查询的所有产品
		productList = prodProductService.selectProductByParms(searchConds);
		
		//展示所有产品子类型
		showSubProdTypeCheckbox();
		
		return SUCCESS;
	}

	/**
	 * 优惠券绑定产品
	 */
	@Action(value = "/mark/coupon/ajaxSaveProductCoupon")
	public void ajaxSaveProductCoupon() {
		Map<String, Object> resultParam = new HashMap<String, Object>();
		MarkCoupon markCoupon = null == couponId ? null : markCouponService.selectMarkCouponByPk(couponId);

		if (null == markCoupon) {
			resultParam.put("success", false);
			resultParam.put("errorMessage", "不能找到对应的优惠。");
		} else {
			//获取前段传递的所有产品
			Map<String, Object> queryParam = new HashMap<String, Object>();
			List<Long> _pidList = new ArrayList<Long>();
			for (String prodId : pidListStr.split(",")) {
				_pidList.add(Long.parseLong(prodId));
			}
			queryParam.put("productIds", _pidList);
			List<ProdProduct> _productList = prodProductService.selectProductByParms(queryParam);
			if (_productList == null) {
				resultParam.put("success", false);
				resultParam.put("errorMessage", "不能找到产品。");
			}else{
				// 获取满足绑定条件的产品单
				List<Long> _boundPidList = new ArrayList<Long>();
				for (ProdProduct prod : _productList) {
					Boolean result = checkProductIdAgainBound(prod, couponId, resultParam);
					if (result == true) {
						_boundPidList.add(prod.getProductId());
					}
				}
				// _boundPidList一起绑定
				saveProductCoupon(markCoupon, _boundPidList);
				resultParam.put("success", true);
				//系统自动添加银行活动Tag==========Begin==========
				if(_boundPidList.size()>0){
					if("true".equals(markCoupon.getValid())&&markCoupon.getPaymentChannel()!=null && !"".equals(markCoupon.getPaymentChannel())&& "PRODUCT".equals(markCoupon.getCouponTarget())){
						List<ProdProductTag> prodProductTags=markCouponService.checkProductTag(_boundPidList);
						if(prodProductTags!=null && prodProductTags.size()>0){
							prodProductTagService.addSystemProgProductTags(prodProductTags, _boundPidList, Constant.PROD_TAG_NAME.BANK_ACTIVITY.getCnName());
							//通知更新PRODUCT_SEARCH_INFO
							for(ProdProductTag prodProductTag:prodProductTags){
								comSearchInfoUpdateService.productUpdated(prodProductTag.getProductId());
							}
						}
					}
				}
				//系统自动添加银行活动Tag==========End==========
			}
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(resultParam));
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe.toString());
		}
	}

	/**
	 * 绑定所有符合条件的产品
	 * @throws UnsupportedEncodingException 
	 */
	@Action(value = "/mark/coupon/ajaxSaveAllProductCoupon")
	public void ajaxSaveAllProductCoupon() throws UnsupportedEncodingException {

		Map<String, Object> resultParam = new HashMap<String, Object>();
		MarkCoupon mc = null == couponId ? null : markCouponService.selectMarkCouponByPk(couponId);
		
		if (null == mc) {
			resultParam.put("success", false);
			resultParam.put("errorMessage", "不能找到对应的优惠。");
		} else {
			
			// 获取符合查询条件的所有产品
			Map<String, Object> searchConds = buildTypeSearchConds();
			if (StringUtils.isNotBlank(productName)) {
				searchConds.put("sProductName", java.net.URLDecoder.decode(getRequest().getParameter("productName"),"UTF-8"));
			}
			List<ProdProduct> _productList = prodProductService.selectProductByParms(searchConds);

			// 获取满足绑定条件的产品单
			List<Long> _pidList = new ArrayList<Long>();
			for (ProdProduct prod : _productList) {
				Boolean result = checkProductIdAgainBound(prod, couponId, resultParam);
				if (result == true) {
					_pidList.add(prod.getProductId());
				}
			}

			// 保存优惠和满足条件的所有产品关系
			if (_pidList.size() != 0) {
				saveProductCoupon(mc, _pidList);
				resultParam.put("success", true);
				log.info("auto to bounding all search products:" + _pidList + ".");
			} else {
				resultParam.put("success", false);
				resultParam.put("errorMessage", "产品不满足绑定条件。");
				log.info("can't find coupon to bounding");
			}
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(resultParam));
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe.toString());
		}
	}

	/**
	 * 绑定产品前做业务检查。
	 * 备注：业务上一优惠只能绑一产品一次,一优惠只能绑一子类型一次
	 * @param prod
	 * @param couponId
	 * @param resultParam 返回信息
	 */
	private Boolean checkProductIdAgainBound(ProdProduct prod, Long couponId, Map<String, Object> resultParam) {

		// 产品已绑定
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("couponId", couponId);
		queryParam.put("productId", prod.getProductId());
		List<MarkCouponProduct> list = markCouponProductService.selectMarkCouponProductByParam(queryParam);
		if (prod.canBindingCoupon() && list != null && list.size() > 0) {
			resultParam.put("success", false);
			resultParam.put("errorMessage", "产品 [" + prod.getProductName() + "] 已经绑定,不能重复绑定");
			return false;
		}

		// 当前产品的产品子类型已绑定了当前的优惠活动,则不允许再次绑定此活动.
		MarkCouponProduct mcp = new MarkCouponProduct();
		mcp.setCouponId(couponId);
		mcp.setProductId(prod.getProductId());
		String subProductType = this.markCouponProductService.checkProductIdOrSubProductTypeAgainBound(mcp);
		if (prod.getSubProductType() != null && prod.getSubProductType().equals(subProductType)) {
			resultParam.put("success", false);
			resultParam.put("errorMessage","当前产品 "+ prod.getProductId()
							+ "所属的产品子类型["+ Constant.SUB_PRODUCT_TYPE.getCnName(prod.getSubProductType()) + "]已经绑定当前活动.");
			return false;
		}
		return true;
	}

	/**
	 * 绑定优惠券和产品 此方法忽略了重复绑定所带来的问题，请在使用此方法前确认产品列表不会重复绑定相同优惠券。
	 * @param mc
	 * @param _pidList
	 */
	private void saveProductCoupon(final MarkCoupon mc, final List<Long> _pidList) {
		if (Constant.COUPON_TARGET.PRODUCT.name().equals(mc.getCouponTarget())) {
			if (_pidList.size() != 0) {
				this.markCouponProductService.saveProductCoupon(couponId, _pidList);
				
				String OperateName = super.getSessionUserName();
				this.saveComLog("COUPON_BUSINESS_BIND", couponId, OperateName,
						Constant.COUPON_ACTION.COUPON_BIND_PRODUCT.name(),
						"绑定产品类型", "优惠券绑定产品IDS:" + _pidList);
			}
		} else {
			log.info("只有是针对于产品的优惠活动才能绑定产品");
		}
	}

	// 构造查询条件
	private Map<String, Object> buildTypeSearchConds() {
		Map<String, Object> searchConds = new HashMap<String, Object>();
		//支付给驴妈妈的产品才享受各种优惠
		searchConds.put("payToLvmama", "true");
		
		if (StringUtils.isNotBlank(proudctBizcode)) {
			searchConds.put("bizcode", proudctBizcode);
		}
		if (StringUtils.isNotBlank(productId)) {
			searchConds.put("productId", productId);
		}
		// 产品ID构造searchConds.get("productId，productIds"构造2种查询条件
		// 用户商品ID可以输一个也能逗号输多个
		String productType = null;
		if (searchConds.get("productId") != null) {
			if (searchConds.get("productId") instanceof Long) {
				searchConds.remove("productIds");
			}
			// productId条件只限字符
			if (searchConds.get("productId") instanceof String
					&& !StringUtils.isEmpty((String) searchConds.get("productId"))) {

				String sProductId = (String) searchConds.get("productId");
				if (sProductId.indexOf(",") == -1) {
					try {
						searchConds.put("productId", Long.parseLong(sProductId));
						searchConds.remove("productIds");
					} catch (Exception e) {
						log.info("输入产品ID不合法");
					}
				} else {
					String[] sProductIds = sProductId.split(",");
					ArrayList<Long> lProductIds = new ArrayList<Long>();
					for (String id : sProductIds) {
						try {
							lProductIds.add(Long.parseLong(id));
						} catch (Exception e) {
							log.info("输入产品ID不合法。(" + id + "不是有效的产品ID)");
						}
					}
					searchConds.remove("productId");
					if (!lProductIds.isEmpty()) {
						searchConds.put("productIds", lProductIds);
					}
				}
			} else {
				searchConds.remove("productIds");
				searchConds.remove("productId");
			}
		}

		/*
		 * productType选中单个,productTypeList前台选中多个产品类型叠加 同上
		 * subProductTypeList选中的子类型
		 */
		List<String> productTypeList = new ArrayList<String>();
		List<String> subProductTypeList = new ArrayList<String>();
		if (ticketSlct != null) {
			productType = Constant.PRODUCT_TYPE.TICKET.name();
			productTypeList.add(Constant.PRODUCT_TYPE.TICKET.name());
			if(StringUtils.isNotEmpty(ticketSlct)){
				subProductTypeList.add(ticketSlct);
			}
		}
		if (hotelSlct != null) {
			productType = Constant.PRODUCT_TYPE.HOTEL.name();
			productTypeList.add(Constant.PRODUCT_TYPE.HOTEL.name());
			if(StringUtils.isNotEmpty(hotelSlct)){
				subProductTypeList.add(hotelSlct);
			}
		}
		if (routeSlct != null) {
			productType = Constant.PRODUCT_TYPE.ROUTE.name();
			productTypeList.add(Constant.PRODUCT_TYPE.ROUTE.name());
			if(StringUtils.isNotEmpty(routeSlct)){
				subProductTypeList.add(routeSlct);
			}
		}
		if (otherSlct != null) {
			productType = Constant.PRODUCT_TYPE.OTHER.name();
			productTypeList.add(Constant.PRODUCT_TYPE.OTHER.name());
			if(StringUtils.isNotEmpty(otherSlct)){
				subProductTypeList.add(otherSlct);
			}
		}

		// productTypeList=1选中一个主产品(当前主产品productType)，子类型同理
		searchConds.remove("productTypeList");
		searchConds.remove("productType");
		if (productTypeList.size() == 1) {
			searchConds.put("productType", productType);
		} else {
			if(productTypeList .size() > 1){
				searchConds.put("productTypeList", productTypeList);
			}
		}
		searchConds.remove("subProductTypeList");
		searchConds.remove("subProductType");
		if (subProductTypeList.size() == 1) {
			searchConds.put("subProductType", subProductTypeList.get(0));
		} else {
			if(subProductTypeList.size() > 1){
				searchConds.put("subProductTypeList", subProductTypeList);
			}
		}

		return searchConds;
	}
	
	/**
	 * 填充和展示子类型.
	 */
	private void showSubProdTypeCheckbox() {
		
		//获取所有门票的子类型(展示所有子类型ticketSubProductTypeList)
		List<CodeItem> ticketSubProductTypeList = Constant.ProductTypeUtils.getSubProductTypesByProdType(Constant.PRODUCT_TYPE.TICKET);
		//处理当前优惠券绑定的子类型(checked)
		this.ticketList = this.subProductTypeList2ProductTypeEleList(ticketSubProductTypeList);
		//当前优惠对应的显示子类型ticketList,显示的ticketCodeItemList子类型及选中的checkbox
		this.ticketCodeItemList = this.productTypeEleList2CodeItemList(this.ticketList);
		
		List<CodeItem> hotelSubProductTypeList = Constant.ProductTypeUtils.getSubProductTypesByProdType(Constant.PRODUCT_TYPE.HOTEL);
		this.hotelList = this.subProductTypeList2ProductTypeEleList(hotelSubProductTypeList);
		this.hotelCodeItemList = this.productTypeEleList2CodeItemList(this.hotelList);
		
		List<CodeItem> otherSubProductTypeList = Constant.ProductTypeUtils.getSubProductTypesByProdType(Constant.PRODUCT_TYPE.OTHER);
		this.otherList = this.subProductTypeList2ProductTypeEleList(otherSubProductTypeList);
		this.otherCodeItemList = this.productTypeEleList2CodeItemList(this.otherList);
		
		List<CodeItem> routeSubProductTypeList = Constant.ProductTypeUtils.getSubProductTypesByProdType(Constant.PRODUCT_TYPE.ROUTE);
		this.routeList = this.subProductTypeList2ProductTypeEleList(routeSubProductTypeList);
		this.routeCodeItemList = this.productTypeEleList2CodeItemList(this.routeList);
	}

	/**
	 * 根据定义的产品子类型枚举列表,生成ProductTypeEle列表.
	 * @param subProductTypeList Constant中定义的产品子类型枚举列表.
	 * @return ProductTypeEle列表.
	 */
	private List<ProductTypeEle> subProductTypeList2ProductTypeEleList(List<CodeItem> subProductTypeList) {
		List<ProductTypeEle> result = new ArrayList<ProductTypeEle>();
		for (CodeItem e : subProductTypeList) {
			ProductTypeEle pte0 = new ProductTypeEle();
			pte0.setChecked(false);
			pte0.setProductType(Constant.ProductTypeUtils.getProdTypeBySubProductType(e.getCode()).name());
			pte0.setSubProductType(e.getCode());
			pte0.setCodeItem(e);
			result.add(pte0);
		}
		return result;
	}
	
	/**
	 *  ------------------------------------  get and set property -------------------------------------------
	 */
	
	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		try {
			this.productName = new String(productName.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			
		}
	}

	public String getProudctBizcode() {
		return proudctBizcode;
	}

	public void setProudctBizcode(String proudctBizcode) {
		this.proudctBizcode = proudctBizcode;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTicketSlct() {
		return ticketSlct;
	}

	public void setTicketSlct(String ticketSlct) {
		this.ticketSlct = ticketSlct;
	}

	public String getHotelSlct() {
		return hotelSlct;
	}

	public void setHotelSlct(String hotelSlct) {
		this.hotelSlct = hotelSlct;
	}

	public String getRouteSlct() {
		return routeSlct;
	}

	public void setRouteSlct(String routeSlct) {
		this.routeSlct = routeSlct;
	}

	public String getOtherSlct() {
		return otherSlct;
	}

	public void setOtherSlct(String otherSlct) {
		this.otherSlct = otherSlct;
	}
 
	public List<ProdProduct> getProductList() {
		return productList;
	}

	public void setProductList(List<ProdProduct> productList) {
		this.productList = productList;
	}
 
	public List<String> getPidList() {
		return pidList;
	}

	public void setPidList(List<String> pidList) {
		this.pidList = pidList;
	}

	public String getPidListStr() {
		return pidListStr;
	}

	public void setPidListStr(String pidListStr) {
		this.pidListStr = pidListStr;
	}

	public List<CodeItem> getTicketCodeItemList() {
		return ticketCodeItemList;
	}

	public void setTicketCodeItemList(List<CodeItem> ticketCodeItemList) {
		this.ticketCodeItemList = ticketCodeItemList;
	}

	public List<CodeItem> getHotelCodeItemList() {
		return hotelCodeItemList;
	}

	public void setHotelCodeItemList(List<CodeItem> hotelCodeItemList) {
		this.hotelCodeItemList = hotelCodeItemList;
	}

	public List<CodeItem> getOtherCodeItemList() {
		return otherCodeItemList;
	}

	public void setOtherCodeItemList(List<CodeItem> otherCodeItemList) {
		this.otherCodeItemList = otherCodeItemList;
	}

	public List<CodeItem> getRouteCodeItemList() {
		return routeCodeItemList;
	}

	public void setRouteCodeItemList(List<CodeItem> routeCodeItemList) {
		this.routeCodeItemList = routeCodeItemList;
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}

	public void setComSearchInfoUpdateService(
			ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}
	

}
