package com.lvmama.passport.processor.impl;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.impl.client.time100.SanYaMinJianBean;
import com.lvmama.passport.processor.impl.client.time100.Time100Client;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import com.lvmama.passport.utils.WebServiceConstant;

/**
 * 三亚民间
 * 
 * @author zenglei
 * 
 */

public class SanYaMinJianProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor {
	private static final Log log = LogFactory.getLog(SanYaMinJianProcessorImpl.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");

	/**
	 * 下单
	 */
	@Override
	public Passport apply(PassCode passCode) {
		log.info("SanYa apply serialNo: " + passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
		passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
		try {
			SanYaMinJianBean sanyaBean = this.fillSaveOrderDetailBean(passCode);
			String apiKEY = WebServiceConstant.getProperties("sanyaminjian.key");
			String apiPSW = WebServiceConstant.getProperties("sanyaminjian.pwd");
			String linkman = sanyaBean.getName();
			String mobile = sanyaBean.getMb();
			String remark = "";
			String orderDetails = JSONArray.fromObject(sanyaBean).toString();
			log.info("SanYa apply orderDetails: " + orderDetails);
			startTime=System.currentTimeMillis();
			String rspXml = Time100Client.addOrderBestStr(apiKEY, apiPSW, linkman, mobile, remark, orderDetails);
			log.info("SanYa apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("SanYa apply response: " + rspXml);
			
			String isSuccess = TemplateUtils.getElementValue(rspXml, "//return/success"); // 返回状态
			if ("true".equalsIgnoreCase(isSuccess)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
				String oid = TemplateUtils.getElementValue(rspXml, "//return/data/oid"); // 对方订单ID
				passport.setExtId(oid);
			} else {
				String message = TemplateUtils.getElementValue(rspXml, "//return/message");
				passport.setComLogContent("供应商返回异常：" + message);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
				this.reapplySet(passport, passCode.getReapplyCount());
				log.info("SanYa apply fail message: " + message);
			}
		} catch (Exception e) {
			log.error("sanya Apply serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.toString());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			this.reapplySet(passport, passCode.getReapplyCount());
			log.error("SanYa apply error message", e);
		}
		return passport;
	}

	@Override
	public Passport destroy(PassCode passCode) {
		log.info("SanYa destroy serialNo: " + passCode.getSerialNo());
		long startTime=0L;
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		try {
			String apiKEY = WebServiceConstant.getProperties("sanyaminjian.key");
			String apiPSW = WebServiceConstant.getProperties("sanyaminjian.pwd");
			String oid = passCode.getExtId();
			log.info("SanYa destroy oid: " + oid);
			startTime=System.currentTimeMillis();
			String rspXml = Time100Client.orderCancelStr(apiKEY, apiPSW, oid);
			log.info("SanYa destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("SanYa destroy response: " + rspXml);
			String errtype = TemplateUtils.getElementValue(rspXml, "//return/errtype");
			if ("0".equalsIgnoreCase(errtype)) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				String message = TemplateUtils.getElementValue(rspXml, "//return/message");
				passport.setComLogContent("供应商返回异常：" + message);
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				log.info("SanYa destroy fail message: " + errtype + " " + message);
			}
		} catch (Exception e) {
			log.error("sanya destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			log.error("SanYa destroy error message", e);
		}
		return passport;
	}

	/**
	 * 重新下订单
	 */
	public void reapplySet(Passport passport, long times) {
		OrderUtil.init().reapplySet(passport, times);
	}

	private SanYaMinJianBean fillSaveOrderDetailBean(PassCode passCode) {
		OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		OrdPerson ordperson = OrderUtil.init().getContract(ordorder);
		OrdOrderItemMeta itemMeta = OrderUtil.init().getItemMeta(ordorder, passCode);
		SanYaMinJianBean saveOrderDetailBean = new SanYaMinJianBean();
		saveOrderDetailBean.setTid(itemMeta.getProductIdSupplier()); // 产品ID
		saveOrderDetailBean.setIndate(DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd HH:mm:ss")); // 游玩时间
		saveOrderDetailBean.setPayWay("1"); // 支付方式 0:代收代付,1:挂帐,2:现付
		saveOrderDetailBean.setNum(String.valueOf(itemMeta.getTotalQuantity())); // 客人数量
		saveOrderDetailBean.setName(ordperson.getName()); // 客人名字
		saveOrderDetailBean.setMb(ordperson.getMobile()); // 手机号码
		return saveOrderDetailBean;
	}
}
