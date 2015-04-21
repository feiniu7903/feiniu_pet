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
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class EditBoundedProductsAction extends CouponBackBaseAction {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 7740656322682227632L;
	 
	private Long couponId;// 优惠券批次标识
	private String productName;
	private String productId;
	
	private List<MarkCouponProduct> markCouponProdList;
	private Float amountYuan;
	private	Float amount;
	private String amountMode = "";
	/**
	 * Tag Service
	 */
	private ProdProductTagService prodProductTagService;
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	
	/**
	 * 进入优惠绑定产品列表页.
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Action(value = "/mark/coupon/editBindingProduct", results = { @Result(location = "/WEB-INF/pages/back/mark/editBoundedProducts.jsp") })
	public String editBoundedProduct() throws UnsupportedEncodingException {
		
		Map<String,Object> parameters = new HashMap<String,Object>();
		if (productName != null && StringUtils.isNotBlank(productName)) {
			parameters.put("productName", productName);
		}
		if (productId != null && StringUtils.isNotBlank(productId)) {
				parameters.put("productIdLike", productId);
		}
		parameters.put("couponId", couponId);
		parameters.put("couponProductType", "1");//只关注单个产品绑定
		
		Long totalRowCount = markCouponProductService.selectMarkCouponProductRowCount(parameters);
		pagination = Page.page(10, page);
		pagination.setTotalResultSize(totalRowCount);
		
		pagination.setActionType("js");
		pagination.setUrl("javascript:refreshBoundedProductList('" + initUrl("editBindingProduct.do") + "')");
		parameters.put("skipResults", pagination.getStartRows());
		parameters.put("maxResults", pagination.getEndRows());
		
		markCouponProdList = this.markCouponProductService.selectMarkCouponProductByParam(parameters);
		MarkCoupon markCoupon = markCouponService.selectMarkCouponByPk(couponId);
		if("AMOUNT".equals(markCoupon.getFavorUnit())){
			amountMode = "amountYuan";
		}else{
			amountMode = "amount";
		}
		
		return SUCCESS;
	}

	/**
	 * 删除页面条件查的所有产品与当前优惠的关系.
	 * @param params
	 */
	@Action(value = "/mark/coupon/ajaxDeleteAllProds")
	public void ajaxDeleteAllProds() {
		Map<String, Object> resultParam = new HashMap<String, Object>();
		Map<String,Object> parameters = new HashMap<String,Object>();
		if (productName != null && StringUtils.isNotBlank(productName)) {
			parameters.put("productName", productName);
		}
		if (productId != null && StringUtils.isNotBlank(productId)) {
				parameters.put("productIdLike", productId);
		}
		parameters.put("couponId", couponId);
		parameters.put("couponProductType", "1");//只关注单个产品绑定
		this.markCouponProductService.deleteMarkCouponProdByMap(parameters);
		
		String OperateName = super.getSessionUserName();
		this.saveComLog("COUPON_BUSINESS_BIND", couponId, OperateName,
				Constant.COUPON_ACTION.COUPON_BIND_PRODUCT.name(),
				"绑定产品类型", "优惠券绑定产品IDS:" + parameters.get("productIds"));
		
			resultParam.put("success", true);
		try {
			getResponse().getWriter().print(JSONObject.fromObject(resultParam));
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe.toString());
		}
	}
	
	//单个产品删除
	@Action(value = "/mark/coupon/ajaxDeleteByCouponProductId")
	public void ajaxDeleteByCouponProductId(){
		Map<String, Object> resultParam = new HashMap<String, Object>();
		Long couponProductId = Long.parseLong(getRequest().getParameter("couponProductId"));
		
		if (couponProductId != null){
			MarkCouponProduct mcp = new MarkCouponProduct();
			mcp.setCouponProductId(couponProductId);
			this.markCouponProductService.delete(mcp);
			
			String OperateName = super.getSessionUserName();
			this.saveComLog("COUPON_BUSINESS_BIND", couponId, OperateName, Constant.COUPON_ACTION.COUPON_BIND_PRODUCT.name(),
				"删除绑定产品", "优惠绑定的产品是:" + couponProductId);
			resultParam.put("success", true);
		}else{
			resultParam.put("success", false);
			resultParam.put("errorMessage", "couponProductId 是空");
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(resultParam));
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe.toString());
		}
	}
	
	/**
	 * 批量删除优惠绑定产品关系
	 */
	@Action(value = "/mark/coupon/ajaxBatchDelete")
	public void ajaxBatchDelete() {
		Map<String, Object> resultParam = new HashMap<String, Object>();
		String couponProductIdStr = (String)getRequest().getParameter("couponProductIds");
		
		if(couponProductIdStr != null && StringUtils.isNotBlank(couponProductIdStr)){
			String[] couponProductIds = couponProductIdStr.split(",");
			List<Long> couponProductIdList = new ArrayList<Long>();
			for(String id : couponProductIds){
				couponProductIdList.add(Long.parseLong(id));
			}
			
			if (couponProductIdList != null && couponProductIdList.size() > 0) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("couponProductIds", couponProductIdList);
				List<MarkCouponProduct> markCouponProducts=markCouponProductService.selectMarkCouponProductByParam(param);
				this.markCouponProductService.deleteMarkCouponProdByMap(param);
				
				String OperateName = super.getSessionUserName();
				this.saveComLog("COUPON_BUSINESS_BIND", couponId, OperateName,
						Constant.COUPON_ACTION.COUPON_BIND_PRODUCT.name(),
						"绑定产品类型", "优惠券绑定产品IDS:" + couponProductIdList);
				//系统自动删除银行活动Tag==========Begin==========
				if(markCouponProducts!=null && markCouponProducts.size()>0){
					List<Long> productIds = new ArrayList<Long>();
					for(MarkCouponProduct markCouponProduct:markCouponProducts){
						productIds.add(markCouponProduct.getProductId());
					}
					List<ProdProductTag> prodProductTags=markCouponService.checkProductTag(productIds);
					prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, Constant.PROD_TAG_NAME.BANK_ACTIVITY.getCnName());
					//通知更新PRODUCT_SEARCH_INFO
					for(Long productId:productIds){
						comSearchInfoUpdateService.productUpdated(productId);
					}
				}
				//系统自动删除银行活动Tag==========End==========
				
			}
			resultParam.put("success", true);
		}else{
			resultParam.put("success", false);
			resultParam.put("errorMessage", "获取不到优惠");
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(resultParam));
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe.toString());
		}
	}
	 
	//修改优惠金额
	@Action(value = "/mark/coupon/ajaxUpdateCouponAmount")
	public void ajaxUpdateCouponAmount() throws Exception{
		 
		Long couponProductId = Long.parseLong(getRequest().getParameter("couponProductId"));
		Map<String, Object> resultParam = new HashMap<String, Object>();
		MarkCouponProduct markCouponProd = markCouponProductService.selectMarkCouponProdByPK(couponProductId);

		if (null == markCouponProd) {
			resultParam.put("success", false);
			resultParam.put("errorMessage", "不能获取到优惠的产品绑定关系");
		}else if(amountYuan != null || amount != null){//如果等于null则不要修改
			Long oldAmount = markCouponProd.getAmount();
			Long newAmount = 0l; //有可能表示折扣或者是元
			if(amountYuan != null){
				newAmount = (long)(amountYuan*100);
			}else{//amount != null
				newAmount = (long)(amount*1.0);
			}
			markCouponProd.setOldamount(oldAmount);
			markCouponProd.setAmount(newAmount);
			markCouponProductService.update(markCouponProd);
			 
			if(oldAmount != newAmount)
			{
				String OperateName = super.getSessionUserName();
				this.saveComLog("COUPON_BUSINESS_BIND", markCouponProd.getCouponId(), OperateName, Constant.COUPON_ACTION.COUPON_BIND_PRODUCT.name(),
						"修改绑定产品", "原金额： " + PriceUtil.convertToYuan(oldAmount) + "修改为：" + PriceUtil.convertToYuan(newAmount));
			}
			resultParam.put("success", true);
		}else{
			resultParam.put("success", false);
			resultParam.put("errorMessage", "修改金额不为空。");
		}
		
		try {
			getResponse().getWriter().print(JSONObject.fromObject(resultParam));
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe.toString());
		}
	}
	  
	/**
	 * 填充产品名称给List.
	 * @param markCouponProdList
	 */
	private void fillProdPropertyforList(List<MarkCouponProduct> markCouponProdList){
		for(MarkCouponProduct markCouponProd : markCouponProdList){
			 if(null != markCouponProd.getProductId())
				{
					ProdProduct product = prodProductService.getProdProductById(markCouponProd.getProductId());
					if(product != null)
					{
						markCouponProd.setProductName(product.getProductName());
					}
				}
		}
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<MarkCouponProduct> getMarkCouponProdList() {
		return markCouponProdList;
	}

	public void setMarkCouponProdList(List<MarkCouponProduct> markCouponProdList) {
		this.markCouponProdList = markCouponProdList;
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

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}

	public void setComSearchInfoUpdateService(
			ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}

	
}
