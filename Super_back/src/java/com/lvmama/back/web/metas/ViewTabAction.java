package com.lvmama.back.web.metas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Include;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.vo.Constant;

public class ViewTabAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5278791836655148047L;
	
	private Long metaProductId;
	private Long metaBranchId;
	private String productType;
	Include meta;
	private String metaUrl;
	private String tagartUrlSettment;
	private String tagatUrlPerformt;
	private String tagatUrlCertificate;
	private MetaProductService metaProductService;
	private PerformTargetService performTargetService;
	private SettlementTargetService settlementTargetService;
	private BCertificateTargetService bCertificateTargetService;
	public void doBefore(){
		if (Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
			metaUrl = "/metas/view_ticket.zul?metaProductId="+metaProductId+"&metaBranchId="+metaBranchId+"&productType="+productType;
		}
		if (Constant.PRODUCT_TYPE.HOTEL.name().equals(productType)) {
			metaUrl = "/metas/view_hotel.zul?metaProductId="+metaProductId+"&metaBranchId="+metaBranchId+"&productType="+productType;
		}
		if (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)) {
			metaUrl = "/metas/view_route.zul?metaProductId="+metaProductId+"&metaBranchId="+metaBranchId+"&productType="+productType;
		}
		if (Constant.PRODUCT_TYPE.OTHER.name().equals(productType)) {
			metaUrl = "/metas/view_other.zul?metaProductId="+metaProductId+"&metaBranchId="+metaBranchId+"&productType="+productType;
		}
		if (Constant.PRODUCT_TYPE.TRAFFIC.name().equals(productType)) {
			metaUrl = "/metas/view_traffic.zul?metaProductId="+metaProductId+"&metaBranchId="+metaBranchId+"&productType="+productType;
		}
		MetaProduct mp = metaProductService.getMetaProduct(metaProductId);
		Map<String, Long> param = new HashMap<String, Long>();
		param.put("supplierId", mp.getSupplierId());
		List<SupPerformTarget> listperformTarget = performTargetService.findSuperSupPerformTargetByMetaProductId(metaProductId);
		List<SupSettlementTarget> settlementTargetList = settlementTargetService.getSuperSupSettlementTargetByMetaProductId(metaProductId);
		List<SupBCertificateTarget> supBCertificateTargetList= bCertificateTargetService.selectSuperMetaBCertificateByMetaProductId(metaProductId);
		if(settlementTargetList.size()!=0){
			tagartUrlSettment = "/targets/settlementtarget/detailsettlementtarget.zul?targetId="+settlementTargetList.get(0).getTargetId();	
		}
		if(listperformTarget.size()!=0){
			tagatUrlPerformt = "/targets/performtarget/detailperformtarget.zul?targetId="+listperformTarget.get(0).getTargetId();
		}
		if(supBCertificateTargetList.size()!=0){
			tagatUrlCertificate ="/targets/certificatetarget/detailcertificatetarget.zul?targetId="+supBCertificateTargetList.get(0).getTargetId();
		}
		//meta.setSrc(url);
	}
	public Long getMetaProductId() {
		return metaProductId;
	}
	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getMetaUrl() {
		return metaUrl;
	}
	public void setMetaUrl(String metaUrl) {
		this.metaUrl = metaUrl;
	}
	public String getTagatUrlPerformt() {
		return tagatUrlPerformt;
	}
	public void setTagatUrlPerformt(String tagatUrlPerformt) {
		this.tagatUrlPerformt = tagatUrlPerformt;
	}
	public String getTagatUrlCertificate() {
		return tagatUrlCertificate;
	}
	public void setTagatUrlCertificate(String tagatUrlCertificate) {
		this.tagatUrlCertificate = tagatUrlCertificate;
	}
	public String getTagartUrlSettment() {
		return tagartUrlSettment;
	}
	public void setTagartUrlSettment(String tagartUrlSettment) {
		this.tagartUrlSettment = tagartUrlSettment;
	}
	public Long getMetaBranchId() {
		return metaBranchId;
	}
	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}
	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}
	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

}
