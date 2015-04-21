package com.lvmama.bee.web.product;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.service.ebooking.EbkProdProductService;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.Constant;

@Results({ @Result(name = "login", location = "/login.do",type="redirect"),
	@Result(name = "query", location = "/product/query.do?ebkProductViewType=${ebkProductViewType}",type="redirect"),
	@Result(name ="errorMessage",location = "/WEB-INF/pages/ebooking/product/errorMessage.jsp"),
	@Result(name = "editEbkProductInit", location ="/ebooking/product/editEbkProductInit.do?ebkProductViewType=${ebkProductViewType}",type="redirect")
})
public class BaseEbkProductAction extends EbkBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4631624884023276235L;
	@Autowired
	protected EbkProdProductService ebkProdProductService;
	@Autowired
	protected ComLogService comLogService;
	
	protected EbkProdProduct ebkProdProduct = new EbkProdProduct();
	protected Long ebkProdProductId;
	protected String ebkProductViewType;
	protected String toShowEbkProduct;
	protected String errorMessage;
	protected TopicMessageProducer productMessageProducer;
	
	/**
	 * 是否为该供应商产品
	 * @param supplierId
	 * @param ebkProdProduct
	 * @return
	 */
	public boolean isSupplierEbkProductJson(){
		Long supplierId =getCurrentSupplierId();
		if(null==supplierId){
			JSONObject json=new JSONObject();
			json.put("success", Boolean.FALSE);
			json.put("message", 0);
			JSONOutput.writeJSON(getResponse(), json);
			return Boolean.FALSE;
		}
		if(null==ebkProdProductId){
			JSONObject json=new JSONObject();
			json.put("success", Boolean.FALSE);
			json.put("message", 1);
			JSONOutput.writeJSON(getResponse(), json);
			return Boolean.FALSE;
		}
		
		ebkProdProduct = ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		if(null==ebkProdProduct){
			JSONObject json=new JSONObject();
			json.put("success", Boolean.FALSE);
			json.put("message", 2);
			JSONOutput.writeJSON(getResponse(), json);
			return Boolean.FALSE;
		}
		if(StringUtil.isEmptyString(ebkProductViewType)){
			ebkProductViewType = ebkProdProduct.getProductType();
		}else if(ebkProductViewType.equals(ebkProdProduct.getProductType())){
			ebkProductViewType = ebkProdProduct.getProductType();
		}
		if(null==ebkProdProduct.getSupplierId()){
			JSONObject json=new JSONObject();
			json.put("success", Boolean.FALSE);
			json.put("message", 2);
			JSONOutput.writeJSON(getResponse(), json);
			return Boolean.FALSE;
		}
		if(supplierId.longValue() != ebkProdProduct.getSupplierId().longValue()){
			JSONObject json=new JSONObject();
			json.put("success", Boolean.FALSE);
			json.put("message",3);
			JSONOutput.writeJSON(getResponse(), json);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	public boolean isSupplierEditEbkProductJson(){
		Long supplierId =getCurrentSupplierId();
		if(null==supplierId){
			JSONObject json=new JSONObject();
			json.put("success", Boolean.FALSE);
			json.put("message", 0);
			JSONOutput.writeJSON(getResponse(), json);
			return Boolean.FALSE;
		}
		if(null==ebkProdProductId){
			ebkProdProductId = ebkProdProduct.getEbkProdProductId();
		}
		if(null!=ebkProdProductId && ebkProdProductId.longValue()!=0){
			EbkProdProduct ebkProdProduct = ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
			if(null==ebkProdProduct){
				JSONObject json=new JSONObject();
				json.put("success", Boolean.FALSE);
				json.put("message", 2);
				JSONOutput.writeJSON(getResponse(), json);
				return Boolean.FALSE;
			}
			if(null==ebkProdProduct.getSupplierId()){
				JSONObject json=new JSONObject();
				json.put("success", Boolean.FALSE);
				json.put("message", 2);
				JSONOutput.writeJSON(getResponse(), json);
				return Boolean.FALSE;
			}
			if(supplierId.longValue() != ebkProdProduct.getSupplierId().longValue()){
				JSONObject json=new JSONObject();
				json.put("success", Boolean.FALSE);
				json.put("message",3);
				JSONOutput.writeJSON(getResponse(), json);
				return Boolean.FALSE;
			}
			if(!Constant.EBK_PRODUCT_AUDIT_STATUS.UNCOMMIT_AUDIT.name().equalsIgnoreCase(ebkProdProduct.getStatus())){
				JSONObject json=new JSONObject();
				json.put("success", Boolean.FALSE);
				json.put("message",-79);
				JSONOutput.writeJSON(getResponse(), json);
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	public String isSupplierEbkProd(){
		Long supplierId =getCurrentSupplierId();
		if(null==supplierId){
			return "login";
		}
		if(null!=ebkProdProduct && ebkProdProductId==null){
			ebkProdProductId = ebkProdProduct.getEbkProdProductId();
		}
		if(null==ebkProdProductId){
			if(null==ebkProductViewType){
				errorMessage = "你没有权限查看、操作此产品";
				return "errorMessage";
			}
			return "editEbkProductInit";
		}
		ebkProdProduct = ebkProdProductService.findEbkProdProductAndBaseByPrimaryKey(ebkProdProductId);
		if(null==ebkProdProduct){
			if(null==ebkProductViewType){
				errorMessage = "你没有权限查看、操作此产品";
				return "errorMessage";
			}
			return "query";
		}
		ebkProductViewType = ebkProdProduct.getProductType();
		if(null==ebkProdProduct.getSupplierId()){
			errorMessage = "你没有权限查看、操作此产品";
			return "errorMessage";
		}else if(supplierId.longValue() != ebkProdProduct.getSupplierId().longValue()){
			errorMessage = "你没有权限查看、操作此产品";
			return "errorMessage";
		}
		return null;
	}
	public String isSupplierEditEbkProd(){
		Long supplierId =getCurrentSupplierId();
		if(null==supplierId){
			return "login";
		}
		if(null!=ebkProdProduct && ebkProdProductId==null){
			ebkProdProductId = ebkProdProduct.getEbkProdProductId();
		}
		if(null!=ebkProdProductId){
			ebkProdProduct = ebkProdProductService.findEbkProdProductAndBaseByPrimaryKey(ebkProdProductId);
			if(null==ebkProdProduct){
				if(null==ebkProductViewType){
					errorMessage = "你没有权限查看、操作此产品";
					return "errorMessage";
				}
				return "query";
			}
			ebkProductViewType = ebkProdProduct.getProductType();
			if(null==ebkProdProduct.getSupplierId()){
				errorMessage = "你没有权限查看、操作此产品";
				return "errorMessage";
			}else if(supplierId.longValue() != ebkProdProduct.getSupplierId().longValue()){
				errorMessage = "你没有权限查看、操作此产品";
				return "errorMessage";
			}
			if(Constant.EBK_PRODUCT_AUDIT_STATUS.PENDING_AUDIT.name().equalsIgnoreCase(ebkProdProduct.getStatus()) && toShowEbkProduct==null){
				errorMessage = "此产品正在审核中，暂时不能编辑产品";
				return "errorMessage";
			}
		}
		return null;
	}
	
	public boolean isEditEbkEditStatusJson(){
		if(Constant.EBK_PRODUCT_AUDIT_STATUS.PENDING_AUDIT.name().equalsIgnoreCase(ebkProdProduct.getStatus())){
			JSONObject json=new JSONObject();
			json.put("success", Boolean.FALSE);
			json.put("message",-79);
			JSONOutput.writeJSON(getResponse(), json);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	public String isEditEbkEditStatus(){
		if(Constant.EBK_PRODUCT_AUDIT_STATUS.PENDING_AUDIT.name().equalsIgnoreCase(ebkProdProduct.getStatus())){
			errorMessage = "此产品正在审核中，暂时不能编辑产品";
			return "errorMessage";
		}
		return null;
	}
	
	public void initEbkProductViewType(){
		ebkProdProduct = ebkProdProductService.findEbkProdProductAndBaseByPrimaryKey(ebkProdProductId);
		if(null!=ebkProdProduct){
			ebkProductViewType = ebkProdProduct.getProductType();
		}
	}
	public String getEbkProductViewTypeCh(){
		return Constant.EBK_PRODUCT_VIEW_TYPE.getCnNameByCode(ebkProductViewType);
	}
	public Long getEbkProdProductId() {
		return ebkProdProductId;
	}
	public void setEbkProdProductId(Long ebkProdProductId) {
		this.ebkProdProductId = ebkProdProductId;
	}
	public String getEbkProductViewType() {
		return ebkProductViewType;
	}
	public void setEbkProductViewType(String ebkProductViewType) {
		this.ebkProductViewType = ebkProductViewType;
	}
	public EbkProdProduct getEbkProdProduct() {
		return ebkProdProduct;
	}
	public void setEbkProdProduct(EbkProdProduct ebkProdProduct) {
		this.ebkProdProduct = ebkProdProduct;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getToShowEbkProduct() {
		return toShowEbkProduct;
	}
	public void setToShowEbkProduct(String toShowEbkProduct) {
		this.toShowEbkProduct = toShowEbkProduct;
	}

	public EbkProdProductService getEbkProdProductService() {
		return ebkProdProductService;
	}

	public void setEbkProdProductService(EbkProdProductService ebkProdProductService) {
		this.ebkProdProductService = ebkProdProductService;
	}

	public TopicMessageProducer getProductMessageProducer() {
		return productMessageProducer;
	}

	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}
}
