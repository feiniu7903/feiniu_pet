package com.lvmama.front.web.buy;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.econtract.EcontractUtil;
import com.lvmama.front.web.BaseAction;

@Results( {
	@Result(name = "downPdfContract", 
			params = { "contentType", "application/pdf;charset=UTF-8", "inputName",
			"pdfStream", "contentDisposition", "attachment;filename=${filename}", "bufferSize", "4096" },
			type="stream")
})
public class ContractDetailAction extends BaseAction {

	private static final long serialVersionUID = 3643384954617528510L; 
	private static Logger logger = Logger.getLogger(ContractDetailAction.class);
	
	/**
	 * 订单合同服务接口
	 */
	private OrdEContractService ordEContractService;
	protected OrderService orderServiceProxy;
	private FSClient fsClient;
	private Long orderId;
	private Long productId;
    private InputStream pdfStream;
    private String filename;
    @Action("/ord/downPdfContractDetail")
	public String  viewPdfContractDetail(){
    	if(!isLogin()){
    		return ERROR;
    	}
    	OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if ( order==null || null == this.getBookerUserId() || !this.getBookerUserId().equals(order.getUserId()) ) {
		   LOG.info("订单"+orderId+"被userId="+this.getUserId()+"非法访问");
		   return ERROR;
		}
    	try{
			OrdEContract contract = ordEContractService.queryByOrderId(orderId);
			ComFile comFile = fsClient.downloadFile(contract.getFixedFileId());
			//隐藏手机号码
			String htmlContent = new String(comFile.getFileData(), "UTF-8");
			StringBuffer htmlBuffer = new StringBuffer();
			Pattern pattern = Pattern.compile("(1[0-9]{10,})");
			Matcher matcher = pattern.matcher(htmlContent);
			while (matcher.find()) {
				String term = matcher.group(1);
				if (term.length() == 11) {
					String temp = term.substring(0, 3) + "****"+ term.substring(7, 11);
					matcher.appendReplacement(htmlBuffer, temp);
				}
			}
			matcher.appendTail(htmlBuffer);
			
			this.pdfStream = EcontractUtil.createInPutContractPdf(htmlBuffer.toString());
			filename = orderId + "_contract.pdf";
			return "downPdfContract";
		 } catch(RuntimeException e){
			 logger.error("根据订单ID("+orderId+")及产品ID("+productId+")取得合同数据出错RuntimeException!"+e);
		 } catch (UnsupportedEncodingException e) {
			 logger.error("根据订单ID("+orderId+")及产品ID("+productId+")取得合同数据出错 UnsupportedEncodingException!"+e);
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}
    	return getTemplateError("取得合同数据出错");
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
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	 
}
