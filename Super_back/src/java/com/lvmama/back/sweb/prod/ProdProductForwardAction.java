package com.lvmama.back.sweb.prod;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;

/**
 * 跳转action.
 * @author yangbin
 *
 */
@Results({
	@Result(name="PAGE_NOT_FOUND",location="/WEB-INF/pages/back/prod/page_not_found.jsp"),
	@Result(name="ADD_TICKET",location="/prod/toAddTicketProduct.do",type="redirect"),
	@Result(name="ADD_HOTEL",location="/prod/toAddHotelProduct.do",type="redirect"),
	@Result(name="ADD_ROUTE",location="/prod/toAddRouteProduct.do",type="redirect"),
	@Result(name="ADD_TRAFFIC",location="/prod/toAddTrafficProduct.do",type="redirect"),
	@Result(name="ADD_SELFPACK",location="/prod/toAddRouteSelfPackProduct.do",type="redirect"),
	@Result(name="ADD_OTHER",location="/prod/toAddOtherProduct.do",type="redirect"),
    @Result(name="ADD_VISA",location="/prod/toAddAsiaProduct.do",type="redirect"),
	@Result(name="EDIT_TICKET",location="/prod/editTicketProduct.do?productId=${productId}",type="redirect"),
	@Result(name="EDIT_HOTEL",location="/prod/editHotelProduct.do?productId=${productId}",type="redirect"),
	@Result(name="EDIT_ROUTE",location="/prod/editRouteProduct.do?productId=${productId}",type="redirect"),
	@Result(name="EDIT_ROUTE_SELFPACK",location="/prod/editRouteSelfPackProduct.do?productId=${productId}",type="redirect"),
	@Result(name="EDIT_OTHER",location="/prod/editOtherProduct.do?productId=${productId}",type="redirect"),
	@Result(name="EDIT_TRAFFIC",location="/prod/editTrafficProduct.do?productId=${productId}",type="redirect"),
    @Result(name="EDIT_VISA",location="/prod/editAsiaProduct.do?productId=${productId}",type="redirect")
})
public class ProdProductForwardAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7549749295458845849L;
	private String productType;
	private Long productId;
	private ProdProductService prodProductService;  
	/**
	 * 
	 * @return
	 */
	@Action("/prod/toEditProduct")
	public String toEditProduct(){
		String page="PAGE_NOT_FOUND";
		if(productId!=null&&productId>0){
			ProdProduct product=prodProductService.getProdProduct(productId);
			if(product!=null){
				page="EDIT_".concat(product.getProductType());
				if(product.hasSelfPack()){//自主打包的产品
					page+="_SELFPACK";
				}
				
				//做敏感词标识
				if(StringUtils.isEmpty(product.getHasSensitiveWord())) {
					prodProductService.markProductSensitive(productId, null);
				}
			}
		}
		return page;		
	}
	
	/**
	 * productType为对应的产品类型.
	 * 			类型分别为
	 * 			TICKET,ROUTE,HOTEL,OTHER,SELFPACK
	 * 			
	 * @return
	 */
	@Action("/prod/toAddProduct")
	public String toAddProduct(){
		String page="PAGE_NOT_FOUND";
		if(StringUtils.isNotEmpty(productType)){
			page="ADD_".concat(productType);
		}
		return page;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	} 
	
}
