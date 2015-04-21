package com.lvmama.ord.logic;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.ord.dao.OrdEContractDAO;

public class SendContractEmailLogic{
	private static final Logger LOG = Logger.getLogger(SendContractEmailLogic.class);
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy年MM月dd日");
	protected  CommonOrderContractLogic orderContractLogic;
	private SmsRemoteService smsRemoteService;
	private OrdEContractDAO ordEContractDAO;
	/**
	 * 发送签约的合同及短信提醒服务类
	 * @param javaMailSender 邮件发送类
	 * @param order 订单
	 * @param ordEContract 订单合同信息
	 * @param fileList 文件列表
	 * @param from  发件箱
	 * @param personal 发送人
	 * @param subject 主题
	 * @param smsTemplateUrl 短信模板
	 * @param emailTemplateUrl 邮件模板地址
	 */
	public void sendMailAndSms(final OrdOrder order, final OrdEContract ordEContract, 
			final List<File> fileList,final String from,final String personal,final String subject,final String smsTemplate,final String emailTemplateUrl) {
	}
	public void sendCancelContractSms(final OrdOrder order,final String smsTemplate) {
		if(null ==  order){
			return;
		}
		Long orderId = order.getOrderId();
		OrdEContract contract = ordEContractDAO.queryByOrderId(orderId);
		if(null == contract || StringUtil.isEmptyString(order.getContact().getMobile())||!order.isNeedEContract() || !order.isPaymentSucc()){
			return;
		}
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("orderId", orderId);
		parameters.put("econtractNo", contract.getEcontractNo());
		try {
			String _content = StringUtil.composeMessage(smsTemplate, parameters);
			smsRemoteService.sendSms(_content, order.getContact().getMobile());
			LOG.info("订单作废时发送合同作废短信 成功 订单号:" + orderId);
		} catch (Exception e) {
			LOG.info("订单作废时发送合同作废短信出错 订单号:" + orderId + " \r\n"+e);
		}
	}
	
	public static byte[] filetobyte(final String path){
		try {
				File file=new File(path);
				if(file!=null){
				   FileInputStream fis=new FileInputStream (file);
				   if(fis!=null){
					  int len=fis.available();
					  byte[] xml=new byte[len];
					  fis.read(xml);
					  return xml;
				   }
			     }
			} catch (Exception e) {
				LOG.info("合同附件转换出错:"+e);
			} 
			return null;
	}
	
	public SmsRemoteService getSmsRemoteService() {
		return smsRemoteService;
	}
	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}
	public OrdEContractDAO getOrdEContractDAO() {
		return ordEContractDAO;
	}
	public void setOrdEContractDAO(OrdEContractDAO ordEContractDAO) {
		this.ordEContractDAO = ordEContractDAO;
	}
	public CommonOrderContractLogic getOrderContractLogic() {
		return orderContractLogic;
	}
	public void setOrderContractLogic(CommonOrderContractLogic orderContractLogic) {
		this.orderContractLogic = orderContractLogic;
	}
}
