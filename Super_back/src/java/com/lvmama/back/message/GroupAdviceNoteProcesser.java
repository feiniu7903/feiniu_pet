package com.lvmama.back.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ViewTravelTips;
import com.lvmama.comm.bee.service.GroupAdviceNoteService;
import com.lvmama.comm.bee.service.ord.IGroupAdviceNoteService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.view.ViewTravelTipsService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.pet.vo.EmailAttachmentData;
import com.lvmama.comm.utils.Configuration;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class GroupAdviceNoteProcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(GroupAdviceNoteProcesser.class);
	private EmailClient emailClient;
	private OrderService orderServiceProxy;
	private FSClient fsClient;
	private GroupAdviceNoteService groupAdviceNoteService;
	private IGroupAdviceNoteService groupAdviceNoteServiceProxy;
	private ComAffixService comAffixService;
	private ComLogService comLogService;
	private ViewTravelTipsService viewTravelTipsService;
	
	private String from;
	private String subject;
	private String fromName;
	private String mailContentTemplate;
	
	@Override
	public void process(Message message) {
		if (message.isGroupAdviceNoteMail()){
			log.info("出团通知书消息处理，发送出团通知书邮件。订单号：" + message.getObjectId());
			readConfig();
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			try {
				//邮件头
				EmailContent email = new EmailContent();
				email.setFromAddress(from);
				email.setFromName(fromName);
				email.setSubject(subject);
				if(StringUtil.isEmptyString(order.getContact().getEmail())){
					log.error("发送出团通知书，无法获取订单联系人邮箱地址。");
					return ;
				}
				email.setToAddress(order.getContact().getEmail());
				//邮件内容
				Map<String,String> parameters = new HashMap<String,String>();
				parameters.put("username", StringUtils.isEmpty(order.getContact().getName()) ? "" : order.getContact().getName());
				parameters.put("orderId", String.valueOf(order.getOrderId()));
				parameters.put("visitTime", DateUtil.getFormatDate(order.getVisitTime(),"yyyy年MM月dd日"));
				parameters.put("productName",StringUtils.isEmpty(order.getMainProduct().getProductName()) ? "" : order.getMainProduct().getProductName()); 
				parameters.put("productId", "" + (order.getMainProduct().getProductId() == null ? "" : order.getMainProduct().getProductId()));
				parameters.put("sendDate", DateUtil.getFormatDate(new Date(),"yyyy年MM月dd日"));
				String content = StringUtil.buildStringByTemplate(mailContentTemplate,"{","}", parameters);
				email.setContentText(content);
				email.setCreateTime(new java.util.Date());
				//附件
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("objectId", order.getOrderId());
				parameter.put("objectType", "ORD_ORDER");
				parameter.put("fileType", ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);
				List<ComAffix> fileList = comAffixService.selectListByParam(parameter);
				
				//附件中添加旅行须知
				List<ViewTravelTips> viewTravelTipsList =  viewTravelTipsService.selectByProductId(order.getMainProduct().getProductId());
				List<Long> travelTipsIdsList = new ArrayList<Long>();
				for(ViewTravelTips viewTravelTips : viewTravelTipsList){
					travelTipsIdsList.add(viewTravelTips.getTravelTipsId());
				}			
				List<ComAffix> caList = new ArrayList<ComAffix>();
				if(!travelTipsIdsList.isEmpty()){
					parameter.clear();
					parameter.put("objectIds", travelTipsIdsList);
					parameter.put("objectType",Constant.COM_LOG_OBJECT_TYPE.TRAVEL_TIPS.name());
					caList = comAffixService.selectListByObjectIds(parameter);
				}
				
				if(fileList == null || fileList.size() == 0){
					log.error("发送出团通知书邮件时，无法下载出团通知书。订单号：" + order.getOrderId());
					return;
				}
				ComAffix lastFile = null;
				for(ComAffix f : fileList){
					if(lastFile == null || lastFile.getCreateTime().before(f.getCreateTime())){
						lastFile = f;
					}
				}
				ComFile comFile = fsClient.downloadFile(lastFile.getFileId());
				List<EmailAttachmentData> attachmentDatas = new ArrayList<EmailAttachmentData>();
				attachmentDatas.add(new EmailAttachmentData(lastFile.getName(),comFile.getFileData(),null)); 
				
				//旅游须知附件列表添加
				if(!caList.isEmpty()){
					for(ComAffix comAffix :caList){
						String path = Constant.REMOTE_FILE_URL+comAffix.getPath();
						
						attachmentDatas.add(new EmailAttachmentData(comAffix.getName()+".docx",HttpsUtil.getHttpClientResponseByteArray(path),null));
					}
				}
				
				emailClient.sendEmailDirect(email, attachmentDatas);
				log.info("出团通知书邮件发送成功。订单号:" + order.getOrderId());
				comLogService.insert("ORD_ORDER", null, order.getOrderId(), message.getAddition(), "GROUP_ADVICE_NOTE_MAIL", "发送出团通知书邮件", null, null);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("发送出团通知书邮件异常。订单号：" + message.getObjectId(),e);
			}
		}
	}
	private void readConfig(){
		if(StringUtil.isEmptyString(from)){
			from = Configuration.getConfiguration().getPropertyValue("mail.properties", "mail.from");
		}
		if(StringUtil.isEmptyString(subject)){
			subject = groupAdviceNoteServiceProxy.getPropertyValue("groupAdviceNote.properties", "groupAdviceNote.subject");
		}
		if(StringUtil.isEmptyString(fromName)){
			fromName = groupAdviceNoteServiceProxy.getPropertyValue("groupAdviceNote.properties", "groupAdviceNote.fromName");
		}
		if(StringUtil.isEmptyString(mailContentTemplate)){
			mailContentTemplate = groupAdviceNoteServiceProxy.getPropertyValue("groupAdviceNote.properties", "groupAdviceNote.mailContentTemplate");
		}
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public FSClient getFsClient() {
		return fsClient;
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	public GroupAdviceNoteService getGroupAdviceNoteService() {
		return groupAdviceNoteService;
	}

	public void setGroupAdviceNoteService(GroupAdviceNoteService groupAdviceNoteService) {
		this.groupAdviceNoteService = groupAdviceNoteService;
	}
	
	public void setComAffixService(ComAffixService comAffixService) {
		this.comAffixService = comAffixService;
	}
	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	public EmailClient getEmailClient() {
		return emailClient;
	}
	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}
	public void setGroupAdviceNoteServiceProxy(
			IGroupAdviceNoteService groupAdviceNoteServiceProxy) {
		this.groupAdviceNoteServiceProxy = groupAdviceNoteServiceProxy;
	}
	public void setViewTravelTipsService(ViewTravelTipsService viewTravelTipsService) {
		this.viewTravelTipsService = viewTravelTipsService;
	}	
	
}
