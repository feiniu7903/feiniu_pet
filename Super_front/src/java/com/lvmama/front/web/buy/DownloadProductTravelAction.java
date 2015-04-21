package com.lvmama.front.web.buy;
/**
 * 订单下载行程
 * 尚正元
 * 2011-12-05
 */
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.service.ord.TravelDescriptionService;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.econtract.EcontractUtil;
import com.lvmama.comm.utils.ord.TemplateFillDataUtil;
import com.lvmama.front.web.BaseAction;
@Results( {
	@Result(name = "downloadTravel", 
			params = { "contentType", "application/html;charset=UTF-8", "inputName",
			"inputStream", "contentDisposition", "attachment;filename=${filename}", "bufferSize", "4096" },
			type="stream")
})
public class DownloadProductTravelAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2367601925254356655L;
	
	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}
	private TravelDescriptionService travelDescriptionService;
	private FSClient fsClient;
	private EContractClient contractClient;
	private Long orderId;
	private String productName;
	private InputStream inputStream;
	private String filename;
	private Long productId;
    @Action("/ord/downloadTravel")
    public String viewOrderTravel(){
    	if(!isLogin()){
    		return ERROR;
    	}/*
    	String orderTravel;
    	String subTravelTemplate = EcontractUtil.getOrderTravelSubTemplate();
		String travelTemplate = EcontractUtil.getOrderTravelTemplate();
		try {
			Long fileId = travelDescriptionService.viewOrderTravel(orderId);
			ComFile comFile = fsClient.downloadFile(fileId);
			String xmlData = new String(comFile.getFileData());
			orderTravel = TemplateFillDataUtil.deserializeMap(xmlData, subTravelTemplate, travelTemplate);
			this.inputStream = new ByteArrayInputStream(orderTravel.getBytes());
		 	filename=orderId+"_Travel.html";
		} catch (Exception e) {
			orderTravel = "<div>没有取到订单行程，系统出错</div>";
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		} */
    	String orderTravel = contractClient.loadRouteTravel(orderId);
    	this.inputStream = new ByteArrayInputStream(orderTravel.getBytes());
	 	filename=orderId+"_Travel.html";
	 	return "downloadTravel";  
    }
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public TravelDescriptionService getTravelDescriptionService() {
		return travelDescriptionService;
	}
	public void setTravelDescriptionService(
			TravelDescriptionService travelDescriptionService) {
		this.travelDescriptionService = travelDescriptionService;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public FSClient getFsClient() {
		return fsClient;
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}
}
