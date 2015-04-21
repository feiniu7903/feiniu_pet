package com.lvmama.order.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.prd.dao.ProdChannelSmsDAO;

public abstract class AbstractOrderSmsCreator {
	private static final Log log = LogFactory.getLog(AbstractOrderSmsCreator.class);
	protected String mobile;
	protected Long objectId;	//为什么业务对象发送短信，现在只应该是OrderId
	protected String orderChannel;
	protected OrderDAO orderDAO = (OrderDAO)SpringBeanProxy.getBean("orderDAO");
	protected ProdChannelSmsDAO prodChannelSmsDAO = (ProdChannelSmsDAO)SpringBeanProxy.getBean("prodChannelSmsDAO");
	
	
	
	public AbstractOrderSmsCreator() {
		super();
	}
	
	

	public AbstractOrderSmsCreator(Long objectId, String mobile) {
		super();
		this.objectId = objectId;
		this.mobile = mobile;
	}



	public ComSms createSingleSms() {
		ComSms sms = mergeData(new ComSms());
		if(sms!=null){
			sms.setMobile(mobile);
			sms.setObjectId(objectId);
		}
		return sms;
	}
	
	private ComSms mergeData(ComSms sms) {
		ProdChannelSms prodChannelSms = this.getSmsTemplate();
		if(prodChannelSms!=null){
			OrdOrder order=orderDAO.selectByPrimaryKey(objectId);
			String content = prodChannelSms.getContent();
			Map<String, Object> data = getContentData(); //复制销售产品的内容到Map中
			if(data == null) {
				return null;
			}
			log.info("ContentData:"+data.toString());
			
			Iterator<String> iter = data.keySet().iterator();
			while(iter.hasNext()) {
				String key = iter.next();
				content = StringUtils.replace(content, "${"+key+"}", data.get(key)!=null?data.get(key).toString():"");
			}
			//去掉重复的逗号
			if(StringUtils.isNotEmpty(content)){
				content=content.replaceAll("，，", "，");
			}
			//去掉重复的换行符
			content = content.replaceAll("[\n]{2}","\n");
			
			sms.setTemplateId(prodChannelSms.getTemplateId());
			
			StringBuffer content_channels = new StringBuffer(content);
			content_channels.append("#{");
			if(StringUtils.isNotEmpty(prodChannelSms.getChannel())){
				content_channels.append(prodChannelSms.getChannel());
			}
			content_channels.append("|");
			if(StringUtils.isNotEmpty(prodChannelSms.getChannelCMCC())){
				content_channels.append(prodChannelSms.getChannelCMCC());
			}
			content_channels.append("|");
			if(StringUtils.isNotEmpty(prodChannelSms.getChannelCUC())){
				content_channels.append(prodChannelSms.getChannelCUC());
			}
			content_channels.append("|");
			if(StringUtils.isNotEmpty(prodChannelSms.getChannelCT())){
				content_channels.append(prodChannelSms.getChannelCT());
			}
			content_channels.append("}#");
			content = content_channels.toString();
			   
			sms.setContent(content);
			if(order.hasSelfPack()){
				Date sendTime=(Date)data.get("sendTime");
				if(sendTime != null){
					sms.setSendTime(DateUtil.getDateBeforeHours(sendTime, 5));
				}
			}
			if(data.get("codeImage")!=null) {
				sms.setCodeImage((byte[])data.get("codeImage"));
				sms.setMms("true");
			}else{
				sms.setMms("false");
			}
			return sms;
		}
		return null;
	}
	
	Map<String, Object> getContentData() {
		return new HashMap<String, Object>();
	}
	
	abstract ProdChannelSms getSmsTemplate();
}
