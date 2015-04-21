package com.lvmama.clutter.web.html5;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.econtract.EcontractUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 移动端专用 from v3 - 和订单相关一些html5页面 
 * 需要登录.
 * @author qinzubo
 *
 */
@Results({ 
	@Result(name = "contract_info", location = "/WEB-INF/pages/html5/order/html5_contract_info.html", type="freemarker"),
	@Result(name = "error", location = "/WEB-INF/pages/html5/html5_error.html", type="freemarker"),
	@Result(name = "group_econtract", location = "/WEB-INF/contract/group_econtract.json",type="freemarker"),
	@Result(name = "other_econtract", location = "/WEB-INF/contract/other_econtract.json",type="freemarker"),
	@Result(name = "pre_pay_econtract", location = "/WEB-INF/contract/pre_pay_econtract.json",type="freemarker"),
	@Result(name = "downPdfContract", 
		params = { "contentType", "application/pdf;charset=UTF-8", "inputName",
		"pdfStream", "contentDisposition", "attachment;filename=${filename}", "bufferSize", "4096" },
		type="stream"),
	@Result(name = "downloadTravel", 
		params = { "contentType", "application/html;charset=UTF-8", "inputName",
		"inputStream", "contentDisposition", "attachment;filename=${filename}", "bufferSize", "4096" },
		type="stream"),
	@Result(name = "groupAdviceNote", 
		params = { "contentType", "application/html;charset=UTF-8", "inputName",
		"pdfStream", "contentDisposition", "attachment;filename=${filename}", "bufferSize", "4096" },
		type="stream")
})
@Namespace("/mobile/html5/order")
public class OrderAction extends BaseAction {
     
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 订单合同. 
	protected OrdEContractService ordEContractService;
	
	// 文件系统 
	protected FSClient fsClient;

	private ComAffixService comAffixService;
	
	private EContractClient contractClient;
	
	// 订单id. 
	private String orderId;
	
	// 产品id 
	private Long productId;

	private String objectType;

	// 产品名称 
	private String productName; 
	private InputStream pdfStream; // 文件输出流 
	private String filename; // 文件名称
	private InputStream inputStream;
	
	/**
	 * 订单服务
	 */
	protected OrderService orderServiceProxy;
	
	@Action("contract_info")
	public String  getEContractInfo(){
		String contractContent = "<div>合同暂时没有生成，请稍后刷新</div>";
		try {
			OrdEContract ordEcontract = ordEContractService.queryByOrderId(Long.valueOf(orderId));
			if(null != ordEcontract) {
				ComFile comFile = fsClient.downloadFile(ordEcontract.getFixedFileId());
				if(null != comFile.getFileData()) {
					contractContent = new String(comFile.getFileData(),"UTF-8");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if(null!=contractContent){
			contractContent = contractContent.replaceAll("<traveller>.*</traveller>", "");
			// 合同的宽度改为100%
			contractContent = contractContent.replaceAll("width:635px", "width:100%");
		} 
		// 合同内容 .
		getRequest().setAttribute("contractContent", contractContent);
		return "contract_info";
	}
	
	@Action("contract_options")
	public String getEContractOptions(){
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		OrdEContract ordEcontract = ordEContractService.queryByOrderId(Long.valueOf(orderId));
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
		if(order!=null){
			this.getRequest().setAttribute("productId", String.valueOf(order.getMainProduct().getProductId()));
			if(ordEcontract==null){
				throw new LogicException("合同没生成。"); 
			}
			this.getRequest().setAttribute("templateName",ordEcontract.getTemplateName());
			
		}
		if(ordEcontract!=null && ordEcontract.getTemplateName().contains("GROUP")){
			return "group_econtract";
		} else if(Constant.ECONTRACT_TEMPLATE.PRE_PAY_ECONTRACT.name().equals(ordEcontract.getTemplateName())){
			return "pre_pay_econtract";
		} else {
			return "other_econtract";
		}
	}
	
	
	/**
	 * 下载电子合同 
	 * @return
	 */
	 @Action("getDownLoadContract")
	public String  getDownLoadContract(){
		 StringBuffer htmlBuffer = new StringBuffer();
		 
		if(null == this.getUser()) {
			htmlBuffer.append("用户没有登录");
		} else {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
			if ( order==null || !this.getUser().getUserId().equals(order.getUserId()) ) {
			   LOG.info("订单"+orderId+"被userId="+this.getUser().getUserId()+"非法访问");
			   htmlBuffer.append("无法找到对应的订单");
			} else {
				try{
					OrdEContract contract = ordEContractService.queryByOrderId(Long.valueOf(orderId));
					ComFile comFile = fsClient.downloadFile(contract.getFixedFileId());
					//隐藏手机号码
					String htmlContent = new String(comFile.getFileData(), "UTF-8");
					
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
				 } catch(RuntimeException e){
					 e.printStackTrace();
					 log.error("根据订单ID("+orderId+")及产品ID("+productId+")取得合同数据出错RuntimeException!"+e);
				 } catch (UnsupportedEncodingException e) {
					 e.printStackTrace();
					 log.error("根据订单ID("+orderId+")及产品ID("+productId+")取得合同数据出错 UnsupportedEncodingException!"+e);
				} catch (Exception e) {
					e.printStackTrace();
					StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
				}
			 }
		}
    	
    	this.pdfStream = EcontractUtil.createInPutContractPdf(htmlBuffer.toString());
		filename = orderId + "_contract.pdf";
		return "downPdfContract";
	}
	 
	 /**
	  * 下载行程单 
	  * @return
	  */
	@Action("getDownLoadTralvel")
    public String getDownLoadTralvel(){
    	String orderTravel = contractClient.loadRouteTravel(Long.valueOf(orderId));
    	if(!StringUtils.isEmpty(orderTravel)) {
    		orderTravel= orderTravel.replaceAll("width:690px", "width:100%").replaceAll("width:640px", "width:100%");
    	}
    	this.inputStream = new ByteArrayInputStream(orderTravel.getBytes());
	 	filename=orderId+"_Travel.html";
	 	return "downloadTravel";  
    }
	
	 /**
	  * 下载出团通知书 
	  * @return
	  */
	@Action("getGroupAdviceNote")
   public String getGroupAdviceNote(){
		// / 附件类
		ComAffix affix = new ComAffix();
		affix.setObjectId(Long.valueOf(orderId));
		affix.setObjectType(objectType);
		affix.setFileType("GROUP_ADVICE_NOTE");// 文件类型：出团通知书
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("objectId", affix.getObjectId());
		parameter.put("objectType", affix.getObjectType());
		parameter.put("fileType", affix.getFileType());

		long count = comAffixService.selectCountByParam(parameter);
		if (count > 0) {
			ComAffix com = comAffixService.selectLatestRecordByParam(parameter);
			ComFile resultFile = fsClient.downloadFile(com.getFileId());
			this.pdfStream = new ByteArrayInputStream(resultFile.getFileData());
			/*log.info("．．．resultFile.getFileData()．．．" );
			if(null != resultFile.getFileData()) {
				this.pdfStream = EcontractUtil.createInPutContractPdf(new String(resultFile.getFileData()));
			}
			filename = orderId + "_GroupAdviceNote.pdf";*/
			try {
				filename = java.net.URLEncoder.encode(com.getName(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.pdfStream = new ByteArrayInputStream("出团通知书暂未生成".getBytes());
		}
	 	return "groupAdviceNote";  
   }
	
	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}

	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}


	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	public InputStream getPdfStream() {
		return pdfStream;
	}

	public void setPdfStream(InputStream pdfStream) {
		this.pdfStream = pdfStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	

	public void setComAffixService(ComAffixService comAffixService) {
		this.comAffixService = comAffixService;
	}

	// 
}
