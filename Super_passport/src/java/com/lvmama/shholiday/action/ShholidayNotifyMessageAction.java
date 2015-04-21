/**
 * 
 */
package com.lvmama.shholiday.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;
import com.lvmama.comm.bee.service.ord.OrdOrderSHHolidayService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.FreeMarkerConfiguration;
import com.lvmama.comm.utils.XmlUtils;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.shholiday.NotifyResult;
import com.lvmama.shholiday.ShholidayNotify;
import com.lvmama.shholiday.service.ShHolidayProductService;
import com.opensymphony.oscache.util.StringUtil;

import freemarker.template.TemplateException;

/**
 * @author yangbin
 *
 */
public class ShholidayNotifyMessageAction extends BaseAction{

	private final static Log LOG=LogFactory.getLog(ShholidayNotifyMessageAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 2789214759578257993L;
	/**
	 * 请求的参数
	 */
	private String messageXML;
	
	private Document document;
	private FreeMarkerConfiguration shholidayNotifyFreeMarkerConfiguration;

	private String userId;
	private String password;
	private String unique;
	private String externalUserID;
	private String externalUserName;
	private ShHolidayProductService shholidayProductService;
	private OrdOrderSHHolidayService ordOrderSHHolidayService;
	
	private String productId;
	@Action("/shholiday/notify")
	public void executeNotify(){
		String responseXml="";
		LOG.info("messageXML = " + messageXML);		
		try {
			document = XmlUtils.createDocument(messageXML);
			validateXML();
			Element root = document.getRootElement();
			String transactionName = parseTransctionName(root);
			ShholidayNotify shholidayNotify = findNotifyHandle(transactionName);
			if(shholidayNotify!=null){
				//不存在处理时的处理
				NotifyResult result = shholidayNotify.process(root);
				Map<String,Object> rootMap = new HashMap<String, Object>();
				rootMap.put("header", createHeader(result));
				rootMap.put("body",result.getBody());
				
				responseXml = shholidayNotifyFreeMarkerConfiguration.getContent(result.getTransactionName().toLowerCase()+".xml", rootMap);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		JSONOutput.writeJSON(getResponse(), responseXml);
	}

	@Action("/shholiday/update")
	public void doUpdate(){
		sendAjaxMsg("请求已发送！");
		Date startDate = DateUtil.getTodayYMDDate();
		Date endDate = DateUtils.addMonths(startDate, 1);
		try {
			shholidayProductService.updateAllProductInfo(startDate, endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Action("/shholiday/updateProductInfo")
	public void doUpdateProductInfo() {
		sendAjaxMsg("请求已发送！");
		shholidayProductService.saveMetaProductForUnStocked(productId);
	}
	
	@Action("/shholiday/findOrderNo")
	public void findOrderNo() {
		
		if(StringUtil.isEmpty(userId)){
			userId = "0";
		}
		OrdOrderSHHoliday osh = new OrdOrderSHHoliday(Long.parseLong(userId),null,password);
		List<OrdOrderSHHoliday> list = ordOrderSHHolidayService.selectByPara(osh);
		StringBuffer str = new StringBuffer();
		if(list!=null){
			for(OrdOrderSHHoliday o :list){
				str.append(o.getString()).append("<br/>");
			}
		}
		sendAjaxMsg(str.toString());
	}
	
	public void setMessageXML(String messageXML) {
		this.messageXML = messageXML;
	}

	/**
	 * 验证xml的正确性
	 */
	private void validateXML(){
		
	}
	
	private String parseTransctionName(Element root){
		return XmlUtils.getChildElementContent(root, "TransactionName");
	}
	
	/**
	 * 通知处理类的规则为TransactionName去掉OTA_以及后面加上Handle
	 * @param str
	 * @return
	 */
	private ShholidayNotify findNotifyHandle(String str){
		try {
			String handle=str.substring(4);
			if(!ArrayUtils.contains(HANDLES, handle)){
				return null;
			}
			Class<?> clazz = Class.forName("com.lvmama.shholiday.notify."+handle+"Handle");
			Object obj = clazz.newInstance();
			if(obj instanceof ShholidayNotify){
				return (ShholidayNotify)obj;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private static final String[] HANDLES={
		"TourProductNotifyRQ","TourOrderStatusNotifyRQ","TourOrderModifyNotifyRQ",
		"TourOrderFlightTicketNotifyRQ","TourOrderTakeoffNotifyRQ",
	};
	private Map<String,Object> createHeader(NotifyResult result){
		initUser();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("success",result.getSuccess());
		map.put("userId", userId);
		map.put("password", password);
		map.put("externalUserID", externalUserID);
		map.put("externalUserName", externalUserName);
		map.put("uniqueID", unique);
		map.put("timeStamp", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		map.put("transactionName", result.getTransactionName());
		return map;
	}

	private void initUser(){
		this.userId=WebServiceConstant.getProperties("shholiday.userid");
		this.password=WebServiceConstant.getProperties("shholiday.password");
		this.externalUserID=WebServiceConstant.getProperties("shholiday.externalUserInfo.externalUserID");
		this.externalUserName=WebServiceConstant.getProperties("shholiday.externalUserInfo.ExternalUserName");
		this.unique=WebServiceConstant.getProperties("shholiday.source.uniqueID");
	}
	public void setShholidayNotifyFreeMarkerConfiguration(FreeMarkerConfiguration shholidayNotifyFreeMarkerConfiguration) {
		this.shholidayNotifyFreeMarkerConfiguration = shholidayNotifyFreeMarkerConfiguration;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public void setExternalUserID(String externalUserID) {
		this.externalUserID = externalUserID;
	}

	public void setExternalUserName(String externalUserName) {
		this.externalUserName = externalUserName;
	}


	public void setShholidayProductService(ShHolidayProductService shholidayProductService) {
		this.shholidayProductService = shholidayProductService;
	}

	
	public void setOrdOrderSHHolidayService(
			OrdOrderSHHolidayService ordOrderSHHolidayService) {
		this.ordOrderSHHolidayService = ordOrderSHHolidayService;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
