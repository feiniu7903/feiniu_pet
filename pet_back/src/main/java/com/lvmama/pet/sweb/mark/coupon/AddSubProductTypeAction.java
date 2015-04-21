package com.lvmama.pet.sweb.mark.coupon;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponProduct;
import com.lvmama.comm.pet.po.mark.ProductTypeEle;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;

public class AddSubProductTypeAction extends CouponBackBaseAction {
	 
	/**
	 * 序列
	 */
	private static final long serialVersionUID = -1210624059261975321L;
	  
	/**
	 * 优惠券批次标识
	 */
	private Long couponId;
	private Long time;
	
	private Long delAmount;//删除记录对应的优惠金额
	private String delSubProductType;
	
	private String checkedSubProdTypeVal;//checked的子类型值
	private boolean isCheckedSubProdType;//checked值
	private Float amountYuan;
	private	Float amount;
	private String amountMode = "";
	
	private MarkCoupon markCoupon = new MarkCoupon();

	private String productName;
	private String proudctBizcode;
	private String productId;

	private String ticketSlct;
	private String hotelSlct;
	private String routeSlct;
	private String otherSlct;

	//记录当前优惠活动所关联的所有产品子类型Code值数组列表.(门票,联票,酒店套餐....)
	private List<String> checkedSubProductTypeList = new ArrayList<String>(); 
	
	//记录当前优惠活动所关联的所有产品子类型列表,对markCouponProductList列表进行处理,针对产品类型和优惠价格相同的合并为一个ProductTypeEle对象.
	private List<ProductTypeEle> subProductTypeEleList;//表列表显示用
	
	// 门票列表. ProductTypeEle---包含优惠对象,产品(子)类型,金额,是否选中)
	private List<ProductTypeEle> ticketList = new ArrayList<ProductTypeEle>();
	//多个子类型选中checkbox展示(CodeItem:code,name名称,checked)
	private List<CodeItem> ticketCodeItemList = new ArrayList<CodeItem>();
	
	// 酒店列表.
	private List<ProductTypeEle> hotelList = new ArrayList<ProductTypeEle>();
	private List<CodeItem> hotelCodeItemList = new ArrayList<CodeItem>();
	// 其它列表.
	private List<ProductTypeEle> otherList = new ArrayList<ProductTypeEle>();
	private List<CodeItem> otherCodeItemList = new ArrayList<CodeItem>();
	// 线路列表.
	private List<ProductTypeEle> routeList = new ArrayList<ProductTypeEle>();
	private List<CodeItem> routeCodeItemList = new ArrayList<CodeItem>();

	private String errorMessageFlag = "";//显示错误信息
	
	
	/**
	 * 进入优惠绑定产品页.
	 * @return
	 */
	@Action(value = "/mark/coupon/addSubProductType", results = { @Result(location = "/WEB-INF/pages/back/mark/addSubProductType.jsp") })
	public String addSubProductType() {
		if (null == couponId) return SUCCESS;
		
		MarkCoupon mc = this.markCouponService.selectMarkCouponByPk(couponId);
		if("AMOUNT".equals(mc.getFavorUnit())){
			amountMode = "amountYuan";
		}else{
			amountMode = "amount";
		}
		
		//一个优惠可能对一个产品或N种产品子类型(优惠活动定性在子类型上)
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("couponId", couponId);
		parameters.put("couponProductType", "2");//只关注产品类型绑定
		List<MarkCouponProduct>  markCouponProductList = this.markCouponProductService.selectMarkCouponProductByParam(parameters);
		
		//获取该优惠券的所有产品子类型列表,前台页面展示使用
		subProductTypeEleList = new ArrayList<ProductTypeEle>();
		for (MarkCouponProduct e : markCouponProductList) {
			//过滤以产品ID绑定活动的记录.--- 子产品类型不为空SubProductType
			if (e.getSubProductType() == null || "".equals(e.getSubProductType())) {
				continue;
			}
			ProductTypeEle temp = new ProductTypeEle();
			temp.setProductType(Constant.PRODUCT_TYPE.getCnName(Constant.ProductTypeUtils.getProdTypeBySubProductType(e.getSubProductType()).name()));
			if(e.getAmount()!= null){
				temp.setAmount(e.getAmount());
			}
			temp.setSubProductTypeNames(ProductUtil.getSubProdTypeByCode(e.getSubProductType()).getName());
			temp.setSubProductType(ProductUtil.getSubProdTypeByCode((e.getSubProductType())).getCode());
			temp.setCouponProductIds("" + e.getCouponProductId());
			subProductTypeEleList.add(temp);
			
			this.checkedSubProductTypeList.add(e.getSubProductType());
		}
	
		//展示和设置优惠券绑定的的子类型;
		fillAndShowSubProdTypeCheckbox(checkedSubProductTypeList);
		
		return SUCCESS;
	}

	/**
	 * 修改产品子类型绑定的优惠价格.
	 */
	@Action(value = "/mark/coupon/ajaxUpdateSubTypeCouponAmount")
	public void ajaxUpdateSubTypeCouponAmount() {
		
		Map<String, Object> resultParam = new HashMap<String, Object>();
		Long couponProductId = Long.valueOf(getRequest().getParameter("couponProductId"));
		
		MarkCouponProduct markCouponProd = this.markCouponProductService.selectMarkCouponProdByPK(couponProductId);
		if (null == markCouponProd) {
			resultParam.put("success", false);
			resultParam.put("errorMessage", "没获取到优惠的绑定产品的记录");
		}else if(amountYuan != null || amount != null){//如果等于null则不要修改
			Long oldAmount = markCouponProd.getAmount();
			Long newAmount = 0l; //有可能表示折扣或者是元,该优惠存在两种金额方式
			if(amountYuan != null){
				newAmount = (long)(amountYuan*100);
			}else{//amount != null
				newAmount = (long)(amount*1.0);
			}
			markCouponProd.setOldamount(oldAmount);
			markCouponProd.setAmount(newAmount);
			markCouponProductService.update(markCouponProd);
		
			//写金额变更日志
			if(oldAmount != newAmount)
			{
				String OperateName = super.getSessionUserName();
				this.saveComLog("COUPON_BUSINESS_BIND", couponId, OperateName, Constant.COUPON_ACTION.COUPON_BIND_PRODUCT.name(),
						"绑定产品类型", "优惠券(活动)绑定产品子类型:  " + ProductUtil.getSubProdTypeByCode((markCouponProd.getSubProductType())).getName() + "  关联优惠价格由:  " + PriceUtil.convertToYuan(oldAmount) + "修改为:  " + PriceUtil.convertToYuan(newAmount));
			}
			resultParam.put("success", true);
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(resultParam));
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe.toString());
		}
	}
	
	/**
	 * 根据活动ID及产品子类型删除绑定活动的信息.
	 * @param params
	 */
	@Action(value = "/mark/coupon/deleteSubProductType", results=@Result(name="addSubProductType",location="/mark/coupon/addSubProductType.do?couponId=${couponId}&_=${time}",type="redirect"))
	public String deleteSubProductType() {
		Long couponProductId = Long.valueOf(getRequest().getParameter("couponProductId"));
		String subProductTypeName = ProductUtil.getSubProdTypeByCode(delSubProductType).getName();
		
		MarkCouponProduct markCouponProduct = new MarkCouponProduct();
		markCouponProduct.setCouponProductId(couponProductId);
		this.markCouponProductService.delete(markCouponProduct);
		
		//记录移除操作日志
		String OperateName = super.getSessionUserName();
		this.saveComLog("COUPON_BUSINESS_BIND", couponId, OperateName, Constant.COUPON_ACTION.COUPON_BIND_PRODUCT.name(),
				"绑定产品类型", "优惠券解除绑定产品子类型为:" + subProductTypeName + "关联优惠价格为:" + PriceUtil.convertToYuan(delAmount) +"");
		
		time = new Date().getTime();
		return "addSubProductType";
	}
	
	/**
	 * 页面某一子类型复选框点击事件处理.
	 */
	@Action(value = "/mark/coupon/checkSubProductTypeOptions", results=@Result(name="addSubProductType",location="/mark/coupon/addSubProductType.do?couponId=${couponId}&errorMessageFlag=${errorMessageFlag}&_=${time}",type="redirect"))
	public String checkSubProductTypeOptions() {
		
		String OperateName = super.getSessionUserName();
		
		//---绑定优惠与子类型关系---.
		if (isCheckedSubProdType) {
			
			//绑定前条件检测(新增就在单个优惠券或活动基础上增加子类型绑定)
			MarkCoupon mc = this.markCouponService.selectMarkCouponByPk(this.couponId);
			if (!Constant.COUPON_TARGET.PRODUCT.name().equals(mc.getCouponTarget())) {
				errorMessageFlag = "onlyBoundOrder";
			}
			MarkCouponProduct mcp = new MarkCouponProduct();
			mcp.setCouponId(this.couponId);
			mcp.setSubProductType(checkedSubProdTypeVal);
			mcp.setAmount(mc.getFavorTypeAmount());
			String productIds = this.markCouponProductService.checkProductIdOrSubProductTypeAgainBound(mcp);
			if (productIds != null && productIds.length() > 0) {
				errorMessageFlag = "boundedSubprod";
			}else{
				//绑定,记日志
				this.markCouponProductService.insert(mcp);
				float amountyuan = 0.0f;
				if(mcp.getAmountYuan() != null){
					amountyuan = mcp.getAmountYuan();//为空会有类型转换出错
				}
				this.saveComLog("COUPON_BUSINESS_BIND",couponId,OperateName,Constant.COUPON_ACTION.COUPON_BIND_PRODUCT.name(),
						"绑定产品类型",
						"优惠券(活动)新增绑定产品子类型为:  "
						+ ProductUtil.getSubProdTypeByCode(mcp.getSubProductType()).getName()
						+ "  关联优惠价格为:  " + amountyuan + "");
			}
		} else {
			//---解除优惠与子类型间关系---
			float Amountyuan = 0.0f;
			
			//删除就可以直接依据优惠号获取绑定产品类型数据操作
			List<MarkCouponProduct> markCouponProductList = markCouponProductService.selectMarkCouponProdByCouponId(this.couponId);
			for (MarkCouponProduct mcp : markCouponProductList) {
				if(mcp.getSubProductType()!= null && mcp.getSubProductType().equals(checkedSubProdTypeVal)){
					if(mcp.getAmountYuan() != null){
						Amountyuan = mcp.getAmountYuan();//为空会有类型转换出错
					}
				}
			}
			
			//删除该子类型与优惠ID的关系.
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("couponId", this.couponId);
			param.put("subProductType", checkedSubProdTypeVal);
			this.markCouponProductService.deleteMarkCouponProdByMap(param);
			this.saveComLog(
					"COUPON_BUSINESS_BIND",couponId,OperateName,Constant.COUPON_ACTION.COUPON_BIND_PRODUCT.name(),
					"解除绑定产品类型",
					"优惠券(活动)解除绑定产品子类型为:  "
					+ ProductUtil.getSubProdTypeByCode(checkedSubProdTypeVal).getName()
					+ "  关联优惠价格为:  " + Amountyuan + "");
		}
		
		time = new Date().getTime();
		
		return "addSubProductType";
	}
	
	/**
	 * 填充和展示子类型.
	 */
	private void fillAndShowSubProdTypeCheckbox(List<String> checkedSubProductTypeList) {
		
		//获取所有门票的子类型(展示所有子类型ticketSubProductTypeList)
		List<CodeItem> ticketSubProductTypeList = Constant.ProductTypeUtils.getSubProductTypesByProdType(Constant.PRODUCT_TYPE.TICKET);
		//处理当前优惠券绑定的子类型(checked)
		this.ticketList = this.subProductTypeList2ProductTypeEleList(ticketSubProductTypeList, checkedSubProductTypeList);
		//当前优惠对应的显示子类型ticketList,显示的ticketCodeItemList子类型及选中的checkbox
		this.ticketCodeItemList = this.productTypeEleList2CodeItemList(this.ticketList);
		
		List<CodeItem> hotelSubProductTypeList = Constant.ProductTypeUtils.getSubProductTypesByProdType(Constant.PRODUCT_TYPE.HOTEL);
		this.hotelList = this.subProductTypeList2ProductTypeEleList(hotelSubProductTypeList, checkedSubProductTypeList);
		this.hotelCodeItemList = this.productTypeEleList2CodeItemList(this.hotelList);
		
//		List<CodeItem> otherSubProductTypeList = Constant.ProductTypeUtils.getSubProductTypesByProdType(Constant.PRODUCT_TYPE.OTHER);
//		this.otherList = this.subProductTypeList2ProductTypeEleList(otherSubProductTypeList, checkedSubProductTypeList);
//		this.otherCodeItemList = this.productTypeEleList2CodeItemList(this.otherList);
		
		List<CodeItem> routeSubProductTypeList = Constant.ProductTypeUtils.getSubProductTypesByProdType(Constant.PRODUCT_TYPE.ROUTE);
		this.routeList = this.subProductTypeList2ProductTypeEleList(routeSubProductTypeList, checkedSubProductTypeList);
		this.routeCodeItemList = this.productTypeEleList2CodeItemList(this.routeList);
	}
	
	/**
	 * 根据定义的产品子类型枚举列表,生成ProductTypeEle列表.
	 * @param subProductTypeList Constant中定义的产品子类型枚举列表.
	 * @return ProductTypeEle列表.
	 */
	private List<ProductTypeEle> subProductTypeList2ProductTypeEleList(List<CodeItem> subProductTypeList, List<String> checkedSubProductTypeList) {
		List<ProductTypeEle> result = new ArrayList<ProductTypeEle>();
		for (CodeItem e : subProductTypeList) {
			ProductTypeEle pte0 = new ProductTypeEle();
			if (checkedSubProductTypeList.contains(e.getCode())) {
				pte0.setChecked(true);
			}else{
				pte0.setChecked(false);
			}
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

	public MarkCoupon getMarkCoupon() {
		return markCoupon;
	}

	public void setMarkCoupon(MarkCoupon markCoupon) {
		this.markCoupon = markCoupon;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Long getDelAmount() {
		return delAmount;
	}

	public void setDelAmount(Long delAmount) {
		this.delAmount = delAmount;
	}

	public String getDelSubProductType() {
		return delSubProductType;
	}

	public void setDelSubProductType(String delSubProductType) {
		this.delSubProductType = delSubProductType;
	}

	public String getCheckedSubProdTypeVal() {
		return checkedSubProdTypeVal;
	}

	public void setCheckedSubProdTypeVal(String checkedSubProdTypeVal) {
		this.checkedSubProdTypeVal = checkedSubProdTypeVal;
	}

	public boolean getIsCheckedSubProdType() {
		return isCheckedSubProdType;
	}

	public void setIsCheckedSubProdType(boolean isCheckedSubProdType) {
		this.isCheckedSubProdType = isCheckedSubProdType;
	}

	public List<String> getCheckedSubProductTypeList() {
		return checkedSubProductTypeList;
	}

	public void setCheckedSubProductTypeList(List<String> checkedSubProductTypeList) {
		this.checkedSubProductTypeList = checkedSubProductTypeList;
	}

	public List<ProductTypeEle> getSubProductTypeEleList() {
		return subProductTypeEleList;
	}

	public void setSubProductTypeEleList(List<ProductTypeEle> subProductTypeEleList) {
		this.subProductTypeEleList = subProductTypeEleList;
	}

	public List<ProductTypeEle> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<ProductTypeEle> ticketList) {
		this.ticketList = ticketList;
	}

	public List<CodeItem> getTicketCodeItemList() {
		return ticketCodeItemList;
	}

	public void setTicketCodeItemList(List<CodeItem> ticketCodeItemList) {
		this.ticketCodeItemList = ticketCodeItemList;
	}

	public List<ProductTypeEle> getHotelList() {
		return hotelList;
	}

	public void setHotelList(List<ProductTypeEle> hotelList) {
		this.hotelList = hotelList;
	}

	public List<CodeItem> getHotelCodeItemList() {
		return hotelCodeItemList;
	}

	public void setHotelCodeItemList(List<CodeItem> hotelCodeItemList) {
		this.hotelCodeItemList = hotelCodeItemList;
	}

	public List<ProductTypeEle> getOtherList() {
		return otherList;
	}

	public void setOtherList(List<ProductTypeEle> otherList) {
		this.otherList = otherList;
	}

	public List<CodeItem> getOtherCodeItemList() {
		return otherCodeItemList;
	}

	public void setOtherCodeItemList(List<CodeItem> otherCodeItemList) {
		this.otherCodeItemList = otherCodeItemList;
	}

	public List<ProductTypeEle> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<ProductTypeEle> routeList) {
		this.routeList = routeList;
	}

	public List<CodeItem> getRouteCodeItemList() {
		return routeCodeItemList;
	}

	public void setRouteCodeItemList(List<CodeItem> routeCodeItemList) {
		this.routeCodeItemList = routeCodeItemList;
	}

	public Float getAmountYuan() {
		return amountYuan;
	}

	public void setAmountYuan(Float amountYuan) {
		this.amountYuan = amountYuan;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getAmountMode() {
		return amountMode;
	}

	public void setAmountMode(String amountMode) {
		this.amountMode = amountMode;
	}

	public String getErrorMessageFlag() {
		return errorMessageFlag;
	}

	public void setErrorMessageFlag(String errorMessageFlag) {
		this.errorMessageFlag = errorMessageFlag;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	
}
