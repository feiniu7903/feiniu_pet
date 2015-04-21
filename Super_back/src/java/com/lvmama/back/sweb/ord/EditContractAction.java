package com.lvmama.back.sweb.ord;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.ContractContentDataShowUtil;
import com.lvmama.back.web.ord.eContract.ListOrdEContractAction;
import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.XmlObjectUtil;
import com.lvmama.comm.utils.econtract.EcontractUtil;

@Results({
	@Result(name = "detailEcontract", location = "/WEB-INF/pages/back/ord/editContract.jsp")
	})
public class EditContractAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1902536282649325053L;
	private Log logger=LogFactory.getLog(ListOrdEContractAction.class);
	
	/**
	 * 合同服务类 
	 */
	private OrdEContractService ordEContractService;
	/**
	 * 订单远程服务接口
	 */
	private OrderService orderServiceProxy;
	private ProdProductService prodProductService;
	private FSClient fsClient;
	private EContractClient contractClient;
	private Long orderId;
	private String content;
	private Map<String,String> contentData;
	private Map<String,String> complementData;
	private Map<String,Object> complementMap;
	private String template;
	private String contentFirstFix;
	@Action("/ordEcontract/detailEcontract")
	public String execute() throws Exception{
		OrdEContract orderEcontract = ordEContractService.getOrdEContractByOrderId(orderId);
		template = orderEcontract.getTemplateName();
		String complentDataXml = orderEcontract.getComplementXml();
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		Map<String,Object> contractData;
		if(StringUtil.isEmptyString(template)){
			ProdEContract prodEContract = prodProductService.getProdEContractByProductId(order.getMainProduct().getProductId());
			contractData = ordEContractService.getDataStore(order, prodEContract);
			complentDataXml = ordEContractService.initComplementXml(order, prodEContract);
		}
		String complementTemplate = EcontractUtil.getComplementTemplate(order);
		String[] array=complementTemplate.split("<body>");
		if(array.length>1){
			String[] endArray = array[1].split("</body>");
			if(endArray.length>1){
				complementTemplate = endArray[0].replace("txt-hetong", "").replace("buchongbox", "");
			}
		}
		complementMap = (Map<String,Object>)XmlObjectUtil.xml2Bean(complentDataXml, java.util.HashMap.class);
		String complement = ContractContentDataShowUtil.composeMessage(complementTemplate, complementMap, "complementData");
		
		ComFile comFile = fsClient.downloadFile(orderEcontract.getContentFileId());
		contractData = (Map<String,Object>)XmlObjectUtil.xml2Bean(new String(comFile.getFileData(),"UTF-8"), java.util.HashMap.class);
		contractData.put("complement", complement);
		contractData.put("imageUrl", EcontractUtil.getStampleUrl(orderEcontract.getTemplateName()));
		content= ContractContentDataShowUtil.composeMessage(EcontractUtil.getContractConentTemplate(orderEcontract.getTemplateName()),contractData,"contentData");//前缀是map参数名
		array=content.split("<body>");
		if(array.length>1){
			contentFirstFix = array[0]+"<body>";
			String[] endArray = array[1].split("</body>");
			if(endArray.length>1){
				content = endArray[0];
			}
		}
		contentData = XmlObjectUtil.map2Type(contractData);
		return "detailEcontract";
	}
	@Action("/ordEcontract/saveEcontract")
	public void updateContractDetail(){
		try{
			final String  operatorName=super.getOperatorName();
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			boolean isUpdated = contractClient.updateEContract(order, XmlObjectUtil.mapEntry2Object(contentData) , XmlObjectUtil.mapEntry2Object(complementData), operatorName);
			if(isUpdated){
				this.sendAjaxResult("true");
			}else{
				this.sendAjaxResult("false");
			}
		}catch(Exception e){
			logger.warn("更新合同失败:\r\n"+e);
			this.sendAjaxResult("false");
		}
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Map<String, String> getContentData() {
		return contentData;
	}
	public void setContentData(Map<String, String> contentData) {
		this.contentData = contentData;
	}
	public Map<String, String> getComplementData() {
		return complementData;
	}
	public void setComplementData(Map<String, String> complementData) {
		this.complementData = complementData;
	}
	public String getContentFirstFix() {
		return contentFirstFix;
	}
	public void setContentFirstFix(String contentFirstFix) {
		this.contentFirstFix = contentFirstFix;
	}
	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}
	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}
	public Map<String, Object> getComplementMap() {
		return complementMap;
	}
	public void setComplementMap(Map<String, Object> complementMap) {
		this.complementMap = complementMap;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
}
