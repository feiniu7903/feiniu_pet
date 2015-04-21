/**
 * 
 */
package com.lvmama.back.sweb.meta;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.phoneorder.ProdProductBranchTarget.PerformTarget;
import com.lvmama.comm.pet.po.pub.ComFaxTemplate;
import com.lvmama.comm.pet.po.sup.MetaBCertificate;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.po.sup.MetaSettlement;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/meta/edit_target.jsp"),
	@Result(name="target_list",location="/WEB-INF/pages/back/meta/target_list.jsp")
})
public class MetaProductTargetAction extends MetaProductFormAction{

	public MetaProductTargetAction() {
		super("target");
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2157415651080281541L;
	private PerformTargetService performTargetService;
	private SettlementTargetService settlementTargetService;
	private BCertificateTargetService bCertificateTargetService;
	
	private List<SupPerformTarget> supplierPerformTargetList;
	private List<SupSettlementTarget> supplierSettlementTargetList;
	private List<SupBCertificateTarget> supplierBCertificateTargetList;
	private String faxTemplate = "不使用传真模板";
	
	private String type;
	private Long targetId;	
	private Long preTargetId;
	private List<SupPerformTarget> performTargetList;
	
	private List<SupSettlementTarget> metaSettlementList;
	
	private List<SupBCertificateTarget> metaBCertificateList;
	
	private<T> T getProperty(Object obj,String name){
		try{
			return (T)PropertyUtils.getProperty(obj, name);
		}catch(Exception x){
			return null;
		}
	}
	
	private JSONArray conver(List<?> list){
		JSONArray array=new JSONArray();
		for(Iterator<?> it=list.iterator();it.hasNext();){			
			Object bean=it.next();
			JSONObject obj=new JSONObject();
			
			obj.put("targetId", getProperty(bean, "targetId"));
			obj.put("name", getProperty(bean, "name"));			
			if ("META_PERFORM".equals(type)) {
				obj.put("paymentInfo", getProperty(bean, "paymentInfo"));
				obj.put("performInfo", getProperty(bean, "performInfo"));
				obj.put("zhCertificateType", getProperty(bean, "zhCertificateType"));
			}else if("META_B_CERTIFICATE".equals(type)){
				obj.put("viewBcertificate", getProperty(bean, "viewBcertificate"));
				obj.put("memo", getProperty(bean, "memo"));
			}else if("META_SETTLEMENT".equals(type)){
				obj.put("zhSettlementPeriod", getProperty(bean, "zhSettlementPeriod"));
				obj.put("memo", getProperty(bean, "memo"));
			}
			array.add(obj);			
		}
		return array;
	}
	@Action("/meta/changeTarget")
	public void changeTarget(){
		JSONResult result=new JSONResult();
		List<?> list = Collections.EMPTY_LIST;
		try{
			Assert.notNull(metaProductId,"采购产品不存在");
			Assert.notNull(targetId,"关联对象不存在");
			Assert.notNull(type,"对象类型不存在");
			metaProduct = metaProductService.getMetaProduct(metaProductId);
			if("META_PERFORM".equals(type)) {
				//不定期产品必须验证履行对象是否为短信
				if(metaProduct.IsAperiodic()) {
					SupPerformTarget supPerformTarget = performTargetService.getSupPerformTarget(targetId);
					if(StringUtils.isEmpty(supPerformTarget.getCertificateType()) || (!Constant.CCERT_TYPE.SMS.name().equalsIgnoreCase(supPerformTarget.getCertificateType()) 
							&& !Constant.CCERT_TYPE.DIMENSION.name().equalsIgnoreCase(supPerformTarget.getCertificateType()))) {
						throw new IllegalArgumentException("不定期产品绑定的履行对象的履行方式必须为普通短信或二维码！");
					}
				}
				MetaPerform metaPerform = new MetaPerform();
				metaPerform.setMetaProductId(metaProductId);
				if(null != preTargetId) {
					metaPerform.setTargetId(preTargetId);
					performTargetService.deleteMetaRelation(metaPerform,getOperatorNameAndCheck());
				}
				
				metaPerform.setTargetId(targetId);
				performTargetService.addMetaRelation(metaPerform,getOperatorNameAndCheck());
				
				list =	performTargetService.findSuperSupPerformTargetByMetaProductId(metaProductId);
			}else if("META_B_CERTIFICATE".equals(type)) {
				/**
				 * 验证传真发送策略
				 * 酒店类型且资源审核后发送传真，所绑的凭证对象必须为立即发送
				 */
				SupBCertificateTarget supBCertificateTarget = bCertificateTargetService.getBCertificateTargetByTargetId(targetId);
				//不定期产品必须验证凭证对象是否为供应商
				if(metaProduct.IsAperiodic()) {
					if(supBCertificateTarget.hasSendFax() || (!supBCertificateTarget.hasSupplier() && !supBCertificateTarget.hasDimension())) {
						throw new IllegalArgumentException("不定期产品绑定的凭证对象的B凭证方式必须为供应商审核或者二维码！");
					}
				}
				if("HOTEL".equals(metaProduct.getProductType()) && "true".equals(metaProduct.getIsResourceSendFax())){
					if(!"IMMEDIATELY".equals(supBCertificateTarget.getFaxStrategy())){
						throw new IllegalArgumentException("酒店类型采购产品为资源审核后发送传真，所绑的凭证对象必须为立即发送！");
					}
				}
				
				MetaBCertificate metaBCertificate = new MetaBCertificate();
				metaBCertificate.setMetaProductId(metaProductId);
				if(null != preTargetId) {
					metaBCertificate.setTargetId(preTargetId);
					bCertificateTargetService.deleteMetaRelation(metaBCertificate,getOperatorNameAndCheck());
				}
				
				metaBCertificate.setTargetId(targetId);
				bCertificateTargetService.insertSuperMetaBCertificate(metaBCertificate,getOperatorNameAndCheck());
				
				list = bCertificateTargetService.selectSuperMetaBCertificateByMetaProductId(metaProductId);
				setFaxTemplateCotent(list);
				result.put("faxTemplate", faxTemplate);
			}else if("META_SETTLEMENT".equals(type)) {
				//不定期产品必须验证结算对象是否为月结
				if(metaProduct.IsAperiodic()) {
					SupSettlementTarget supSettlementTarget = settlementTargetService.getSettlementTargetById(targetId);
					if(StringUtils.isEmpty(supSettlementTarget.getSettlementPeriod()) || !Constant.SETTLEMENT_PERIOD.PERMONTH.name().equalsIgnoreCase(supSettlementTarget.getSettlementPeriod())) {
						throw new IllegalArgumentException("不定期产品绑定的结算对象的结算周期必须为月结！");
					}
				}
				MetaSettlement metaSettlement = new MetaSettlement();
				metaSettlement.setMetaProductId(metaProductId);
				if(null != preTargetId) {
					metaSettlement.setTargetId(preTargetId);
					settlementTargetService.deleteMetaRelation(metaSettlement,getOperatorNameAndCheck());
				}
				
				metaSettlement.setTargetId(targetId);
				settlementTargetService.addMetaRelation(metaSettlement,getOperatorNameAndCheck());
				
				list = settlementTargetService.getSuperSupSettlementTargetByMetaProductId(metaProductId);
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.put("list", conver(list));
		result.output(getResponse());
	}
	
	@Action("/meta/showTargetList")
	public String showTargetList(){
		try{
			doBefore();
			Map<String,Object> map = new HashMap<String, Object>();
			Long supplierId = metaProduct.getSupplierId();
			map.put("supplierId", supplierId);
			if("META_PERFORM".equals(type)) {
				supplierPerformTargetList = performTargetService.findAllSupPerformTarget(map); 
			}else if("META_B_CERTIFICATE".equals(type)) {
				supplierBCertificateTargetList = bCertificateTargetService.findBCertificateTarget(map);
			}else if("META_SETTLEMENT".equals(type)) {
				supplierSettlementTargetList = settlementTargetService.findSupSettlementTarget(map);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "target_list";
	}

	@Override
	@Action("/meta/toEditTarget")
	public String toEdit() {
		doBefore();
		Map<String,Object> map=new HashMap<String, Object>();
		Long supplierId=metaProduct.getSupplierId();
		map.put("supplierId", supplierId);
		performTargetList =	performTargetService.findSuperSupPerformTargetByMetaProductId(metaProductId);
		metaSettlementList = settlementTargetService.getSuperSupSettlementTargetByMetaProductId(metaProductId);
		metaBCertificateList = bCertificateTargetService.selectSuperMetaBCertificateByMetaProductId(metaProductId);
		setFaxTemplateCotent(metaBCertificateList);
		return goAfter();
	}
	
	private void setFaxTemplateCotent(List<?> bcList){
		SupBCertificateTarget sbTarget=null;
		if(CollectionUtils.isNotEmpty(bcList)){
			sbTarget=(SupBCertificateTarget)bcList.get(0);
		}
		if(sbTarget!=null&&StringUtils.equals("true", sbTarget.isSendFax())){
			if(!StringUtils.equals(sbTarget.getFaxTemplate(),Constant.FAX_TEMPLATE.SYSTEM.name())){
				faxTemplate=sbTarget.getZhfaxTemplate();
			}else{//如果是没有凭证对象或凭证对象为系统自动选择时自动计算
				ComFaxTemplate ft = new ComFaxTemplate();
				ft = this.bCertificateTargetService.getFaxTemplate(this.selectFaxTemplate());
				if(ft!=null){
					faxTemplate = ft.getTemplateName();
				}
			}
		}
	}
	
	
	/**
	 * 系统自动选择传真模板
	 * @return
	 */
	private String selectFaxTemplate() {
		//MetaProduct mp = metaProductService.getMetaProduct(metaProductId);
		
		String subProductType = metaProduct.getSubProductType();
		String productType = metaProduct.getProductType();
		String paymentTarge = metaProduct.getPaymentTarget();

		if (Constant.PAYMENT_TARGET.TOLVMAMA.name().equals(paymentTarge)) {
			if (Constant.PRODUCT_TYPE.HOTEL.name().equals(productType)) {
				return "HOTEL_SINGEL";
			}
			if (Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
				return "TICKET";
			}
			if (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)
					&& (Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(
							subProductType) || Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN
							.name().equals(subProductType))) {
				return "ROUTE_FREENESS";
			}
			if (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)
					&& (Constant.SUB_PRODUCT_TYPE.GROUP.name().equals(
							subProductType) || Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN
							.name().equals(subProductType))) {
				return "ROUTE";
			}
			
			if (Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType)&&
					(Constant.SUB_PRODUCT_TYPE.FLIGHT.name().equals(subProductType))){
				return "FLIGHT";
			}
				
		} else {
			if (Constant.PRODUCT_TYPE.HOTEL.name().equals(productType)) {
				return "HOTEL_SINGEL_SUPPLIER";
			}
			if (Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
				return "TICKET_SUPPLIER";
			}
			if (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)) {
				return "ROUTE_SUPPLIER";
			}
		}
		return null;
	}
	
	/**
	 * 含有多个通关码的凭证对象
	 * @param targetId
	 * @return
	 */
	private boolean isMixedPerformTarget(Long targetId){
		List<SupPerformTarget> list=performTargetService.findSuperSupPerformTargetByMetaProductId(metaProductId);
		SupPerformTarget performTarget = performTargetService.getSupPerformTarget(targetId);
		list.add(performTarget);
		int count = 0;
		for(SupPerformTarget supPerformTarget:list){
  			if(supPerformTarget.getCertificateType().equals(Constant.CCERT_TYPE.DIMENSION.name())){
  				count++;
			}
		}
		if (count>1) {
			return true;
		}
		if (list.size()>1 && count==1 ) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return the faxTemplate
	 */
	public String getFaxTemplate() {
		return faxTemplate;
	}

	/**
	 * @return the supplierPerformTargetList
	 */
	public List<SupPerformTarget> getSupplierPerformTargetList() {
		return supplierPerformTargetList;
	}

	/**
	 * @return the supplierSettlementTargetList
	 */
	public List<SupSettlementTarget> getSupplierSettlementTargetList() {
		return supplierSettlementTargetList;
	}

	/**
	 * @return the supplierBCertificateTargetList
	 */
	public List<SupBCertificateTarget> getSupplierBCertificateTargetList() {
		return supplierBCertificateTargetList;
	}

	/**
	 * @param performTargetService the performTargetService to set
	 */
	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	/**
	 * @param settlementTargetService the settlementTargetService to set
	 */
	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param targetId the targetId to set
	 */
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public List<SupPerformTarget> getPerformTargetList() {
		return performTargetList;
	}

	public List<SupSettlementTarget> getMetaSettlementList() {
		return metaSettlementList;
	}

	public List<SupBCertificateTarget> getMetaBCertificateList() {
		return metaBCertificateList;
	}

	@Override
	public void save() {
	}

	public Long getTargetId() {
		return targetId;
	}

	public Long getPreTargetId() {
		return preTargetId;
	}

	public void setPreTargetId(Long preTargetId) {
		this.preTargetId = preTargetId;
	}
}
