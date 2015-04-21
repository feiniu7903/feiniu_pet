package com.lvmama.comm.pet.client;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.TravelDescriptionService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.XmlObjectUtil;
import com.lvmama.comm.utils.econtract.EcontractUtil;
import com.lvmama.comm.utils.ord.TemplateFillDataUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ECONTRACT_TEMPLATE;

public class EContractClient {
	private static final Log LOG = LogFactory.getLog(EContractClient.class);
	private OrdEContractService ordEContractService;
	private ProdProductService prodProductService;
	private OrderService orderServiceProxy;
	private FSClient fsClient;
	
	private TravelDescriptionService travelDescriptionService;
	
	/**
	 * 生成合同xml文件及补充条款xml文件，并新增合同信息
	 * @param order
	 * @param operater
	 * @return
	 */
	public boolean createEContract(final OrdOrder order,final String operater){
		try{
			ProdEContract prodEContract = prodProductService.getProdEContractByProductId(order.getMainProduct().getProductId());
			Map<String,Object> parameters = ordEContractService.getDataStore(order,prodEContract);
			if(Constant.CHANNEL.BACKEND.name().equals(order.getChannel())){//后台下单全部默认同意
				parameters.put("agree1", "是");
				parameters.put("agree2", "没有");
				parameters.put("agree3", "同意");
				parameters.put("agree4", "同意");
				parameters.put("agree5", "同意");
				parameters.put("agree6", "同意");
			}else{
				parameters.put("agree1", "");
				parameters.put("agree2", "");
				parameters.put("agree3", "");
				parameters.put("agree4", "");
				parameters.put("agree5", "");
				parameters.put("agree6", "");
			}
			String dataXml = XmlObjectUtil.bean2Xml(parameters);
			Long contentFileId = fsClient.uploadFile(order.getOrderId()+"_content.xml",dataXml.getBytes(), "eContract");
			Map<String,Object> complementDataMap =null;
			if(!EcontractUtil.isSimpleTemplate((String)parameters.get("template"))){
				complementDataMap = EcontractUtil.getComplementDataMap(order,prodEContract);
			}
			String content = EcontractUtil.getContractContentAndComplement(parameters,complementDataMap);
			Long fixedFileId = fsClient.uploadFile(order.getOrderId()+"_content_fixed.xhtml",content.getBytes(), "eContract");
			OrdEContract contract = ordEContractService.createOrderContract(order,prodEContract,contentFileId,fixedFileId,operater);
			return null!=contract;
		}catch(Exception e){
			LOG.warn("create contract content is out error\r\n"+e);
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}
	
	public boolean updateEContract(final OrdOrder order,boolean agree3,boolean agree4,boolean agree5,boolean agree6,final String operater){
			ProdEContract prodEContract = prodProductService.getProdEContractByProductId(order.getMainProduct().getProductId());
			Map<String,Object> parameters = ordEContractService.getDataStore(order,prodEContract);
			parameters.put("agree1", "是");
			parameters.put("agree2", "没有");
			parameters.put("agree3", agree3 ? "同意":"不同意");
			parameters.put("agree4", agree4 ? "同意":"不同意");
			parameters.put("agree5", agree5 ? "同意":"不同意");
			parameters.put("agree6", agree6 ? "同意":"不同意");
			Map<String,Object> complementDataMap = EcontractUtil.getComplementDataMap(order,prodEContract);
			return this.updateEContract(order, parameters, complementDataMap, operater);
	}
	public boolean updateEContract(final OrdOrder order,final Map<String,Object> contractData,final Map<String,Object> complementData,final String operater){
		try{
			OrdEContract contract = ordEContractService.getOrdEContractByOrderId(order.getOrderId());
			if(null==contract && null!=order.getOrderId()){
				createEContract(order,operater);
				return Boolean.TRUE;
			}
			String complementXml =null;
			if(!EcontractUtil.isSimpleTemplate((String)contractData.get("template"))){
				complementXml = XmlObjectUtil.bean2Xml(complementData);
			}
			String contractXml = XmlObjectUtil.bean2Xml(contractData);
			Long contentFileId = fsClient.uploadFile(contract.getOrderId()+"_"+contract.getContractVersion()+"_content.xml",contractXml.getBytes(), "eContract");
			String content = EcontractUtil.getContractContentAndComplement(contractData,complementData);
			Long fixedFileId = fsClient.uploadFile(contract.getOrderId()+"_"+contract.getContractVersion()+"_content_fixed.xhtml",content.getBytes(), "eContract");
			//更新操作
			contract = ordEContractService.updateOrderContract(contract,order,complementXml,contentFileId,fixedFileId,operater);
			return null!=contract;
		}catch(Exception e){
			e.printStackTrace();
			LOG.warn("update contract content is out error\r\n"+e);
		}
		return Boolean.FALSE;
	}
	public String loadRouteTravel(final Long orderId){
		OrdOrder order  = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		return loadRouteTravel(orderId,order.getMainProduct().getProductId(), order.getMainProduct().getMultiJourneyId());
	}
	public String loadRouteTravel(final Long orderId,final Long productId, final Long multiJourneyId){
		ProdEContract prodEContract = prodProductService.getProdEContractByProductId(productId);
		String orderTravel="";
    	String subTravelTemplate = EcontractUtil.getOrderTravelSubTemplate();
    	String travelTemplate =null;
    	if (ECONTRACT_TEMPLATE.BJ_ONEDAY_ECONTRACT.name().equals(prodEContract.getEContractTemplate())) {//北京一日游取单独的旅游行程
    		travelTemplate = EcontractUtil.getOrderTravelTemplateBJONEDAY();
    	}else{
    		travelTemplate = EcontractUtil.getOrderTravelTemplate();
    	}
		try {
			Long fileId= travelDescriptionService.viewOrderTravel(orderId);
			String xmlData;
			String xmlDataGB2312=null;
			if(null!= fileId){
				ComFile comFile = fsClient.downloadFile(fileId);
				xmlData = new String(comFile.getFileData());
				try{
					xmlDataGB2312 = new String(comFile.getFileData(),"GB2312");
				}catch(Exception e){
					
				}
			}else{
				xmlData = travelDescriptionService.queryContentTravelByOrderId(orderId);
			}
			if(StringUtil.isEmptyString(xmlData)){
				xmlData = travelDescriptionService.getTravelDesc(productId, multiJourneyId);
			}
			orderTravel = TemplateFillDataUtil.deserializeMap(xmlData, subTravelTemplate, travelTemplate);
			if(StringUtil.isEmptyString(orderTravel) && !StringUtil.isEmptyString(xmlDataGB2312)){
				orderTravel = TemplateFillDataUtil.deserializeMap(xmlDataGB2312, subTravelTemplate, travelTemplate);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn("order ("+orderId+")update contract content is out error\r\n"+e);
		} 
		return orderTravel;
	}
	public Map<String,Object> loadRouteTravelObject(final Long orderId){
		Long fileId= travelDescriptionService.viewOrderTravel(orderId);
		String xmlData;
		if(null!= fileId){
			ComFile comFile = fsClient.downloadFile(fileId);
			xmlData = new String(comFile.getFileData());
		}else{
			xmlData = travelDescriptionService.queryContentTravelByOrderId(orderId);
		}
		try {
			return TemplateFillDataUtil.deserializeMap(xmlData);
		} catch (DocumentException e) {
			LOG.warn("loadRouteTravelObject is out error\r\n"+e);
		}
		return null;
	}
	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}
	public void setTravelDescriptionService(
			TravelDescriptionService travelDescriptionService) {
		this.travelDescriptionService = travelDescriptionService;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
}
