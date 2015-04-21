package com.lvmama.back.sweb.prod;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductRelationService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.util.List;

/**
 * 附加产品
 * @author yuzhibing
 *
 */
@Results({
	@Result(name = "input", location = "/WEB-INF/pages/back/prod/product_additional.jsp"),
	@Result(name = "auditingShow", location = "/WEB-INF/pages/back/prod/auditing/product_additional_auditing_show.jsp")
	})
public class ProdProductAdditionalAction extends ProductAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7329380331991910285L;
	private ProdProductRelationService prodProductRelationService;
	private ProdProductBranchService prodProductBranchService;
	private List<ProdProductRelation> productList;
	private Long relationId;
	private Long prodBranchId;
	private String saleNumType;
	
	
	public ProdProductAdditionalAction() {
		super();
		setMenuType("additional");		
	}

    @Action(value="/prod/toProductAdditionalAuditingShow")
    public String toProductAdditionalAuditingShow(){
        this.goEdit();
        return "auditingShow";
    }
	@Override
	@Action(value="/prod/toProductAdditional")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		productList = prodProductRelationService.getRelatProduct(productId);		
		return goAfter();
	}

	/**
	 * 保存添加的关系类别.
	 * 之后返回该对象.
	 */
	@Override
	@Action("/prod/editProductAdditional")
	public void save() {
		JSONResult result=new JSONResult(getResponse());
				
		ProdProductBranch branch=prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
		if(branch==null){
			result.raise("类别不存在").output();
			return;
		}
		if(branch.getProductId().equals(productId)){
			result.raise("不可以打包自己的类别").output();
			return;
		}
		ProdProduct mainProduct = prodProductService.getProdProduct(productId);
		ProdProduct relationProduct = prodProductService.getProdProduct(branch.getProductId());
		//主产品支付给景区,不可绑定支付给驴妈妈的附加产品
		if("true".equalsIgnoreCase(mainProduct.getPayToSupplier()) && "true".equalsIgnoreCase(relationProduct.getPayToLvmama())) {
			result.raise("支付给景区的产品不可绑定支付给驴妈妈的附加产品").output();
			return;
		}
		//主产品支付给驴妈妈,不可绑定支付给景区的附加产品
		if("true".equalsIgnoreCase(mainProduct.getPayToLvmama()) && "true".equalsIgnoreCase(relationProduct.getPayToSupplier())) {
			result.raise("支付给驴妈妈的产品不可绑定支付给景区的附加产品").output();
			return;
		}
		ProdProductRelation relation=prodProductRelationService.addRelation(productId,branch,getOperatorNameAndCheck());
		relation=prodProductRelationService.getProdRelationDetail(relation.getRelationId());
		result.put("relation", ProdProductDTO.conver(relation));
		
		result.output();
	}
	@Action("/prod/deleteProdAdditional")
	public void deleteRelation(){
		JSONResult result=new JSONResult(getResponse());
		if(relationId==null){
			result.raise("附加产品不存在").output();
			return;
		}
		prodProductRelationService.deleteRelation(relationId,getOperatorNameAndCheck());
		
		result.output();
	}
	
	@Action("/prod/updateSaleNumType")
	public void updateRequire(){
		JSONResult result=new JSONResult();
		ResultHandle flag=this.prodProductRelationService.updateSaleNumType(relationId, saleNumType);
		if(flag.isFail()){
			result.raise(flag.getMsg());
		}
		result.output(getResponse());
	}
	
	/**
	 * @return the productList
	 */
	public List<ProdProductRelation> getProductList() {
		return productList;
	}


	/**
	 * @param prodBranchId the prodBranchId to set
	 */
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}


	/**
	 * @param prodProductRelationService the prodProductRelationService to set
	 */
	public void setProdProductRelationService(
			ProdProductRelationService prodProductRelationService) {
		this.prodProductRelationService = prodProductRelationService;
	}


	/**
	 * @param prodProductBranchService the prodProductBranchService to set
	 */
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}


	/**
	 * @param relationId the relationId to set
	 */
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}


	/**
	 * @param saleNumType the saleNumType to set
	 */
	public void setSaleNumType(String saleNumType) {
		this.saleNumType = saleNumType;
	}


	

	public List<CodeItem> getProductTypeList(){
		return ProductUtil.getOtherSubTypeList(true);
	}

}
