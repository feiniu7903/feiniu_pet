package com.lvmama.back.sweb.prod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONException;
import org.json.JSONObject;
import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.json.JSONResult;

/**
 * 产品返现设置
 * 
 * @author taiqichao
 * 
 */
@Results({ @Result(name = "prodBonusReturnSettingList", location = "/WEB-INF/pages/back/prod/prod_bonus_return_setting.jsp") })
public class ProdBonusReturnSettingAction extends BaseAction {

	private static final long serialVersionUID = 3052122466528210389L;
	
	private String productName;
	
	private String productId;
	
	private ProdProductService prodProductService;
	
	/**返现类型:手动:manual 自动:auto **/
	private String returnMode;
	
	private String subProductType;
	/**
	 * 返现金额(元)
	 */
	private Double returnMoney;
	
	private String isRefundable;
	
	private String isManualBonus;
	
	private Long[] productIds;

	/**
	 * 转到产品奖金返现设置列表
	 * @return
	 */
	@Action("/prod/toProdBonusSettingList")
	public String toProdBonusSettingList() {
		Map<String,Object> terms = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(productName)){
			terms.put("sProductName", productName);
		}
		if(StringUtils.isNotBlank(this.productId)&&NumberUtils.toLong(productId)>0){
			terms.put("productId", productId);
		}
		if(StringUtils.isNotBlank(subProductType)) {
			terms.put("subProductType", subProductType);
		}
		if(StringUtils.isNotBlank(isRefundable)) {
			terms.put("isRefundable", isRefundable);
		}
		if(StringUtils.isNotBlank(isManualBonus)) {
			terms.put("isManualBonus", isManualBonus);
		}
		if(terms.keySet().size()>0){
			Integer totalRowCount = prodProductService.selectRowCount(terms);
			pagination = super.initPagination();
			pagination.setTotalRecords(totalRowCount);
			terms.put("_startRow", pagination.getFirstRow());
			terms.put("_endRow", pagination.getLastRow());
			List<ProdProduct> productList = prodProductService.selectProductByParms(terms);
			pagination.setRecords(productList);
			pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		}
		return "prodBonusReturnSettingList";
	}
	
	
	@Action(value="/prod/saveProdBonusSetting")
	public void doSaveProdBonusSetting() throws JSONException{
		
		JSONObject result=new JSONObject();
		
		if(StringUtils.isBlank(productId)){
			result.put("success", false);
			result.put("msg", "请选择产品");
			sendAjaxResultByJson(result.toString());
			return;
		}
		
		if(StringUtils.isBlank(this.returnMode)){
			result.put("success", false);
			result.put("msg", "请选择返现方式");
			sendAjaxResultByJson(result.toString());
			return;
		}
		
		if("manual".equals(this.returnMode)){
			if(null==this.returnMoney||this.returnMoney<0){
				result.put("success", false);
				result.put("msg", "请输入正确的返现金额");
				sendAjaxResultByJson(result.toString());
				return;
			}
		}
		
		ProdProduct product=prodProductService.getProdProductById(Long.valueOf(this.productId));
		
		if(null==product){
			result.put("success", false);
			result.put("msg", "未知的产品");
			sendAjaxResultByJson(result.toString());
			return;
		}
			
		
		if("manual".equals(this.returnMode)){//手动返现
			product.setIsManualBonus("Y");
			product.setMaxCashRefund((long)(this.returnMoney*100));
		}else if("auto".equals(this.returnMode)){//自动返现
			product.setIsManualBonus("N");
			if(product.getSellPrice()!=null) {
				Long bonusReturnAmount=prodProductService.getProductBonusReturnAmount(product);
				product.setMaxCashRefund(bonusReturnAmount);
			}else{
				product.setMaxCashRefund(0L);
			}
		}
		prodProductService.updateByPrimaryKey(product,null);	
		result.put("success", true);
		result.put("msg", "设置成功");
		sendAjaxResultByJson(result.toString());
	}
	
	/**
	 * 批量操作
	 * */
	@Action(value = "/prod/batchOperate")
	public void batchOperate() {
		JSONResult result = new JSONResult();
		try {
			if (productIds == null || productIds.length < 1) {
				throw new Exception("操作异常!");
			}
			Map<String, Object> params = new HashMap<String, Object>();
			if(StringUtils.isNotEmpty(isManualBonus)) {
				params.put("isManualBonus", isManualBonus);
			} else if(StringUtils.isNotEmpty(isRefundable)) {
				params.put("isRefundable", isRefundable);
			} else {
				throw new Exception("操作异常!");
			}
			params.put("productIds", productIds);
			prodProductService.updateRefundByProductIds(params);
		} catch (Exception e) {
			result.raise(e.getMessage());
		}
		result.output(getResponse());
	}
	
	public List<CodeItem> getSubProductTypeList() {
		List<CodeItem> list = new ArrayList<CodeItem>();
		list.addAll(ProductUtil.getTicketSubTypeList());
		list.addAll(ProductUtil.getHotelSubTypeList());
		list.addAll(ProductUtil.getRouteSubTypeList());
		return list;
	}
	

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}


	public void setReturnMode(String returnMode) {
		this.returnMode = returnMode;
	}


	public void setReturnMoney(Double returnMoney) {
		this.returnMoney = returnMoney;
	}


	public String getSubProductType() {
		return subProductType;
	}


	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}


	public void setIsRefundable(String isRefundable) {
		this.isRefundable = isRefundable;
	}


	public void setIsManualBonus(String isManualBonus) {
		this.isManualBonus = isManualBonus;
	}


	public void setProductIds(Long[] productIds) {
		this.productIds = productIds;
	}


	public String getIsRefundable() {
		return isRefundable;
	}


	public String getIsManualBonus() {
		return isManualBonus;
	}
	
	

}
