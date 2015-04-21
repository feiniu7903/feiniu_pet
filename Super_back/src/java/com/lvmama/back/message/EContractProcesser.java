package com.lvmama.back.message;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdEcontractSignLog;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.SendContractEmailService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.pet.vo.EmailAttachmentData;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.econtract.EcontractUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ECONTRACT_TEMPLATE;
/**
 * 发送电子签约的监听器
 * 
 * 当订购电子签约产品的订单被支付，那么就需要发送电子合同给指定的邮箱。
 * @author Brian
 *
 */
public class EContractProcesser extends CreateContractProcesser {
	private static final Log LOG = LogFactory.getLog(EContractProcesser.class);
	private SendContractEmailService sendContractEmailService;
	private OrdEContractService ordEContractService;
	private ProdProductService prodProductService;
	private ComLogService comLogService;
	private EmailClient emailClient;
	private FSClient fsClient;
	private ComSmsTemplateService comSmsTemplateRemoteService;
	private EContractClient contractClient;
	private SmsRemoteService smsRemoteService;
	private String from; //发件人邮箱地址
	private String subject;  //邮件主题
	private String personal; //发件人昵称
	
	private EContractProcesser(final String from, final String subject, final String personal) {
		this.from = from;
		this.subject = subject;
		this.personal = personal;
	}
	
	
	@Override
	public void process(Message message) {
		LOG.info(message);
		if(message.isEContractUpdateAgreeItemMsg()){
			Long orderId =message.getObjectId();
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if (null == order) {
				LOG.error("根据订单号 "+orderId+" 查询订单为空，不能发送电子合同");
				return;
			}
			String addition = message.getAddition();
			String[] agrees = addition.split(",");
			boolean updated = contractClient.updateEContract(order, Boolean.valueOf(agrees[0]), Boolean.valueOf(agrees[1]), Boolean.valueOf(agrees[2]), Boolean.valueOf(agrees[3]), "system");
			if(updated){
				LOG.info("订单号 "+orderId+" 的电子合同同意项已更新成功.");
			}else{
				LOG.info("订单号 "+orderId+" 的电子合同同意项已更新失败.");
			}
			/**
			 * 发送合同到游客邮箱
			 */
			orderMessageProducer.sendMsg(MessageFactory.newOrderSendEContract(order.getOrderId()));
		}else if (message.isSendEContractMsg() || message.isSendRefreshContractMsg()){
			Long orderId =message.getObjectId();
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if (null == order) {
				LOG.error("根据订单号 "+orderId+" 查询订单为空，不能发送电子合同");
				return;
			}
			ProdEContract prodContract = prodProductService.getProdEContractByProductId(order.getMainProduct().getProductId());
			if(message.isSendRefreshContractMsg() && ECONTRACT_TEMPLATE.PRE_PAY_ECONTRACT.name().equals(prodContract.getEContractTemplate())){
				LOG.info("订单号 "+orderId+" 更新订单信息,不进行预付款协议重新发送.");
				return;
			}
			
			if (!order.isCanceled() && order.isNeedEContract()) {
				OrdEContract ordEContract = ordEContractService.queryByOrderId(orderId);
				if(null==ordEContract){
					contractClient.createEContract(order,"JMS MESSAGE");
					ordEContract = ordEContractService.queryByOrderId(orderId);
				}
				File eContract = getOrdEContract(orderId,ordEContract);
				if (null == ordEContract || null == eContract) {
					LOG.warn("无法成功找到电子合同记录或电子合同，操作失败!");
					return;
				}
				File travel =getOrdTravel(orderId,order.getMainProduct().getProductId(), order.getMainProduct().getMultiJourneyId());
				if(!order.isEContractConfirmed()){
					OrdEcontractSignLog signLog = new OrdEcontractSignLog();
					signLog.setEcontractNo(ordEContract.getEcontractNo());
					signLog.setSignMode(Constant.ECONTRACT_SIGN_TYPE.ONLINE_SIGN.name());
					signLog.setSignUser(order.getUserId()); //默认为下单人签约
					ordEContractService.signContract(orderId, signLog);
					orderServiceProxy.updateOrdEContractStatusToConfirmed(orderId);
				}
				List<File> fileList = new ArrayList<File>();
				fileList.add(eContract);
				String smsTemplateKey = "SMS_ORD_CONTRACT_CREATE";
				String emailTemplateUrl = "CONTRACT_EMAIL.html";
				String emailTemplateUrlTravel = "TRAVEL_EMAIL.html";
				if(ECONTRACT_TEMPLATE.PRE_PAY_ECONTRACT.name().equals(prodContract.getEContractTemplate())){
					emailTemplateUrl ="PRE_PAY_EMAIL.html";
					smsTemplateKey = "SMS_ORD_PRE_PAY_CONTRACT_CREATE";
				}
				if(message.isSendRefreshContractMsg() ){
					smsTemplateKey = "SMS_ORD_CONTRACT_REFRESH";
					emailTemplateUrl = "REFRESH_CONTRACT_EMAIL.html";
				}
				if(!order.isCanceled()){
					this.sendMailAndSms(order, ordEContract, fileList, from, personal, subject, smsTemplateKey, EcontractUtil.PDF_DIRECT_RELATIVELY_URL+emailTemplateUrl);
					if(null!=travel&&travel.exists() && !EcontractUtil.isSimpleTemplate(ordEContract.getTemplateName())){
						fileList = new ArrayList<File>();
						fileList.add(travel);
						this.sendMailAndSms2(order, fileList, from, personal, "来自驴妈妈的行程", EcontractUtil.PDF_DIRECT_RELATIVELY_URL+emailTemplateUrlTravel);
					}
				}
				comLogService.insert(
						"ORD_ECONTRACT",
						orderId,
						orderId,
						"系统JMS",
						Constant.COM_LOG_CONTRACT_EVENT.sendContract.name(),
						"发送电子合同",
						"发送电子合同给邮箱"+order.getContact().getEmail(),"ORD_ECONTRACT");
			}
		}
	}
	/**
	 * 发送行程单
	 * @param order
	 * @param fileList
	 * @param from
	 * @param personal
	 * @param subject
	 * @param emailTemplateUrl
	 */
	public void sendMailAndSms2(OrdOrder order, 
			List<File> fileList, String from, String personal, String subject, String emailTemplateUrl) {

		if (null == order) {
			LOG.info("订单为空，无法发送行程单 订单号:"+order.getOrderId());
			return;
		}
		if (null == fileList || (null!=fileList && fileList.size() == 0)) {
			LOG.info("行程单不存在，无法发送行程单 订单号:"+order.getOrderId());
			return;
		}
		
		try {
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("userName", StringUtils.isEmpty(order.getContact().getName()) ? "" : order.getContact().getName());
			parameters.put("productName",StringUtils.isEmpty(order.getMainProduct().getProductName()) ? "" :order.getMainProduct().getProductName());
			parameters.put("date", DateUtil.getFormatDate(new Date(), "yyyy年MM月dd日"));
			parameters.put("orderId", order.getOrderId());
			if (null == order.getContact() || StringUtils.isEmpty(order.getContact().getEmail())) {
				LOG.info("订单联系人的邮箱为空，无法发送行程单 订单号:"+order.getOrderId());
				return;
			}
			
			List<EmailAttachmentData> files = new ArrayList<EmailAttachmentData>();
			// 增加附件
			for(int i=0;i<fileList.size();i++){
				EmailAttachmentData data = new EmailAttachmentData(fileList.get(i));
				files.add(data);
			}
			try {
				String _content = EcontractUtil.getTemplateContent(emailTemplateUrl);
				_content = StringUtil.composeMessage(_content, parameters);
				EmailContent email = new EmailContent();
				email.setFromAddress(from);
				email.setFromName(personal);
				email.setSubject(subject);
				email.setToAddress(order.getContact().getEmail());
				email.setContentText(_content);
				email.setCreateTime(new java.util.Date());
				emailClient.sendEmailDirect(email, files);
				LOG.info("订单号:" + order.getOrderId() + "发行程单成功!");
			} catch (Exception e) {
				LOG.info("订单号:" + order.getOrderId() + "发送行程单失败!\r\n"+e.getMessage());
			}
		} catch (Exception e) {
			LOG.info("订单号:" + order.getOrderId() + "发送行程单失败!\r\n"+e.getMessage());
		}
	}
	
	public void sendMailAndSms(OrdOrder order, OrdEContract ordEContract,
			List<File> fileList, String from, String personal, String subject,
			String templateKey, String emailTemplateUrl) {
		String smsTemplate=null;
 		ComSmsTemplate template =  comSmsTemplateRemoteService.selectSmsTemplateByPrimaryKey(templateKey);
 		if(null != template){
 			smsTemplate = template.getContent();
 		}

		if (null == order) {
			LOG.info("订单为空，无法发送电子合同 订单号:");
			return;
		}
		if (null == fileList || (null!=fileList && fileList.size() == 0)) {
			LOG.info("电子合同不存在，无法发送电子合同 订单号:"+order.getOrderId());
			return;
		}
		
		try {
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			String contractNo = ordEContract.getEcontractNo();
			parameters.put("userName", StringUtils.isEmpty(order.getContact().getName()) ? "" : order.getContact().getName());
			parameters.put("productName",StringUtils.isEmpty(order.getMainProduct().getProductName()) ? "" :order.getMainProduct().getProductName());
			parameters.put("date", DateUtil.getFormatDate(new Date(), "yyyy年MM月dd日"));
			parameters.put("econtractNo", contractNo);
			parameters.put("orderId", order.getOrderId());
			if(contractNo.matches("^[0-9-]+[B-Z]$")){
				String oldContractNo = "";
				if(contractNo.matches("^[0-9-]+[A-Z]$")){
					char end =(char)(contractNo.charAt(contractNo.length()-1)-1);
					oldContractNo = contractNo.replaceFirst("^([0-9-]+)[A-Z]$", "$1")+end;
				}
				parameters.put("oldContractNo", oldContractNo);
				parameters.put("newContractNo", contractNo);
			}else{
				parameters.put("oldContractNo", contractNo);
				parameters.put("newContractNo", contractNo);
			}
			if(!StringUtil.isEmptyString(smsTemplate) && !StringUtil.isEmptyString(order.getContact().getMobile())){
				String _content = StringUtil.composeMessage(smsTemplate, parameters);
				smsRemoteService.sendSms(_content, order.getContact().getMobile());
				LOG.info("订单号:" + order.getOrderId() + "手机号："+order.getContact().getMobile()+"发送短信成功!");
			}

			if (null == order.getContact() || StringUtils.isEmpty(order.getContact().getEmail())) {
				LOG.info("订单联系人的邮箱为空，无法发送电子合同 订单号:"+order.getOrderId());
				return;
			}
			
			List<EmailAttachmentData> files = new ArrayList<EmailAttachmentData>();
			// 增加附件
			for(int i=0;i<fileList.size();i++){
				EmailAttachmentData data = new EmailAttachmentData(fileList.get(i));
				files.add(data);
			}
			try {
				String _content = EcontractUtil.getTemplateContent(emailTemplateUrl);
				_content = StringUtil.composeMessage(_content, parameters);
				EmailContent email = new EmailContent();
				email.setFromAddress(from);
				email.setFromName(personal);
				email.setSubject(subject);
				email.setToAddress(order.getContact().getEmail());
				email.setContentText(_content);
				email.setCreateTime(new java.util.Date());
				emailClient.sendEmailDirect(email, files);
				LOG.info("订单号:" + order.getOrderId() + "发送电子合同成功!");
			} catch (Exception e) {
				LOG.info("订单号:" + order.getOrderId() + "发送电子合同失败!\r\n"+e.getMessage());
			}
		} catch (Exception e) {
			LOG.info("订单号:" + order.getOrderId() + "发送电子合同失败!\r\n"+e.getMessage());
		}
	}
	
	
	
	/**
	 * 获取电子合同
	 * @param order
	 * @return
	 */
	private File getOrdEContract(final Long orderId,final OrdEContract ordEcontract) {
		String targetFilename = EcontractUtil.getContractFilePath(orderId);
		File targetFile = new File(targetFilename);
		if (!targetFile.exists() || ordEContractService.existByOrderId(orderId)>0) {
			try {
				ComFile comFile = fsClient.downloadFile(ordEcontract.getFixedFileId());
				EcontractUtil.createPdf(new String(comFile.getFileData()), targetFilename);
				targetFile = new File(targetFilename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return targetFile.exists() ? targetFile : null;
	}
	
	/**
	 * 获取电子合同
	 * @param order
	 * @return
	 */
	private File getOrdTravel(final Long orderId,final Long productId, final Long multiJourneyId) {
		try{
			String travel =contractClient.loadRouteTravel(orderId,productId, multiJourneyId);
			String targetFilename = Constant.getInstance().getEContractDir() + "/Travel(" + orderId + ").html";
			return getFileFromBytes(travel,targetFilename);
		}catch(Exception e){
			LOG.error("订单行程生成PDF文件  Exception:" + e);
			return null;
		}
	}
	/** 
     * 把字节数组保存为一个文件
     * @Author Sean.guo
     * @EditTime 2007-8-13 上午11:45:56
      */ 
     private static File getFileFromBytes( String content, String outputFile)  {
        BufferedOutputStream stream  =   null ;
        OutputStreamWriter filerWriter = null;
        File file  =   null ;
         try   {
            file  =   new  File(outputFile);
            filerWriter = new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8");
            filerWriter.write(content);
        }  catch (IOException e) {
        	LOG.error("合同生成PDF文件  IOException:" + e);
		} finally {
			IOUtils.closeQuietly(stream);
			IOUtils.closeQuietly(filerWriter);
		}
         return  file;
    }
     
	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getPersonal() {
		return personal;
	}


	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public OrdEContractService getOrdEContractService() {
		return ordEContractService;
	}


	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}


	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public SendContractEmailService getSendContractEmailService() {
		return sendContractEmailService;
	}

	public void setSendContractEmailService(
			SendContractEmailService sendContractEmailService) {
		this.sendContractEmailService = sendContractEmailService;
	}
	
	public void setComSmsTemplateRemoteService(
			ComSmsTemplateService comSmsTemplateRemoteService) {
		this.comSmsTemplateRemoteService = comSmsTemplateRemoteService;
	}


	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}


	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}


	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}


	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}


	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}
	
}
