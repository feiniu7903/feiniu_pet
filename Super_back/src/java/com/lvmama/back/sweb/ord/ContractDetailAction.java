package com.lvmama.back.sweb.ord;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.ContractContentDataShowUtil;
import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.econtract.EcontractUtil;

@Results( {
	@Result(name = "downPdfContract", 
			params = { "contentType", "application/octet-stream;charset=UTF-8", "inputName",
			"pdfStream", "contentDisposition", "attachment;filename=${filename}", "bufferSize", "4096" },
			type="stream")
})
public class ContractDetailAction extends BaseAction {

	private static final long serialVersionUID = 3643384954617528510L; 
	private static Logger logger = Logger.getLogger(ContractDetailAction.class);
	
	/**
	 * 订单远程服务接口
	 */
	private OrderService orderServiceProxy;
	private OrdEContractService ordEContractService;
	
	private EContractClient contractClient;
	private FSClient fsClient;
	private Long orderId;
	private Long productId;
    private InputStream pdfStream;
    private String filename;
    
	private Map<String,Object> contentData;
	private Map<String,Object> complementData;
	private String template;
    /**
     * 订单为已支付，
     * @return
     */
    @Action("/ord/downPdfContractDetail")
	public String  viewPdfContractDetail(){
		 try{
			 OrdEContract ordEcontract = ordEContractService.queryByOrderId(orderId);
			 ComFile comFile = fsClient.downloadFile(ordEcontract.getFixedFileId());
		 	 this.pdfStream = EcontractUtil.createInPutContractPdf(new String(comFile.getFileData(),"UTF-8"));
		 	 filename=orderId+"_contract.pdf";
		 	 return "downPdfContract";  
		 } catch(RuntimeException e){
			 logger.error("根据订单ID("+orderId+")及产品ID("+productId+")取得合同数据出错!"+e);
			 return getTemplateError("取得合同数据出错");
		 }catch(Exception e){
			 logger.error("根据订单ID("+orderId+")及产品ID("+productId+")取得合同数据出错!"+e);
			 return getTemplateError("取得合同数据出错");
		 }
	}
    @Action("/ord/downAdditionDetail")
	public String  viewAdditionDetail(){
		 try{
			 OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			 String complement =ordEContractService.getContractComplement(orderId, EcontractUtil.getComplementTemplate(order));
		 	 this.pdfStream =  EcontractUtil.createInPutContractPdf(complement); 
		 	 filename=orderId+"_complement.pdf";
		 	 return "downPdfContract";  
		 } catch(RuntimeException e){
			 logger.error("根据订单ID("+orderId+")及产品ID("+productId+")取得合同附加条款出错!"+e);
			 return getTemplateError("取得合同附加条款出错");
		 }
	}
    @Action("/ord/downOrderTravel")
    public String viewOrderTravel(){
    	try{
    		String travel =contractClient.loadRouteTravel(orderId);
		 	this.pdfStream = new ByteArrayInputStream(travel.getBytes());
		 	filename=orderId+"_Travel.html";
		 	return "downPdfContract";  
		 } catch(RuntimeException e){
			 logger.error("根据订单ID("+orderId+")及产品ID("+productId+")取得订单行程出错!"+e);
			 return getTemplateError("取得订单行程出错");
		 } catch (Exception e) {
			 logger.error("根据订单ID("+orderId+")及产品ID("+productId+")取得订单行程出错!"+e);
			 return getTemplateError("取得订单行程出错");
		}
    }
    @Action("/ord/downPreviewOrderContract")
	public String ContractPdfPreview(){
		try{
			Iterator<String> iterator = complementData.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				complementData.put(key, ContractContentDataShowUtil.Object2String(complementData.get(key)));
			}
			iterator = contentData.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				contentData.put(key, ContractContentDataShowUtil.Object2String(contentData.get(key)));
			}
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			String complementTemplate = EcontractUtil
					.getTemplateContent(EcontractUtil.PDF_DIRECT_RELATIVELY_URL
							+ EcontractUtil
									.getContractComplementTemplate(order)
							+ ".xml");
			String[] array=complementTemplate.split("<body>");
			if(array.length>1){
				String[] endArray = array[1].split("</body>");
				if(endArray.length>1){
					complementTemplate = endArray[0].replace("txt-hetong", "").replace("buchongbox", "");
				}
			}
			String contentTemplate = EcontractUtil
					.getContractConentTemplate(template);
			String complement = StringUtil.composeMessage(complementTemplate,
					complementData);
			contentData.put("complement", complement);
			contentData.put("imageUrl", EcontractUtil
					.getStampleUrl(template));
			String content = StringUtil.composeMessage(contentTemplate,
					contentData);
			this.pdfStream = EcontractUtil.createInPutContractPdf(content);
			filename = orderId + "_contract.pdf";
			return "downPdfContract";
		}catch(Exception e){
			e.printStackTrace();
			logger.error("根据订单ID("+orderId+")及产品ID("+productId+")取得订单行程出错!"+e);
			return getTemplateError("取得订单行程出错");
		}
	}
    public String getTemplateError(String message){
    	String jsoncallback=this.getRequest().getParameter("jsoncallback");
		getResponse().setContentType("text/json; charset=utf-8");
		try {
			getResponse().getWriter().write(jsoncallback+"({'info':[texterror:"+message+"]})");
		} catch (IOException e) {
			logger.error("下载PDF合同   发送失败消息  ***失败***："+e.getMessage());
		}
		return null;
    }
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
    public InputStream getPdfStream() {  
         return pdfStream;  
    }
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}
	public FSClient getFsClient() {
		return fsClient;
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public Map<String, Object> getContentData() {
		return contentData;
	}
	public void setContentData(Map<String, Object> contentData) {
		this.contentData = contentData;
	}
	public Map<String, Object> getComplementData() {
		return complementData;
	}
	public void setComplementData(Map<String, Object> complementData) {
		this.complementData = complementData;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}
}
