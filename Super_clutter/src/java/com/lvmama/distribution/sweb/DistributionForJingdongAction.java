package com.lvmama.distribution.sweb;

import java.util.Date;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.distribution.model.jd.Body;
import com.lvmama.distribution.model.jd.Head;
import com.lvmama.distribution.model.jd.Request;
import com.lvmama.distribution.model.jd.Response;
import com.lvmama.distribution.model.jd.Result;
import com.lvmama.distribution.service.DistributionForJingdongService;
import com.lvmama.distribution.util.DistributionUtil;
import com.lvmama.distribution.util.JdUtil;

/**
 * 京东分销接口实现
 * 
 * @author gaoxin
 * 
 */

public class DistributionForJingdongAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5959937731392515147L;
	private DistributionForJingdongService distributionForJingdongService;
	private String msg;
	private String cmd;

	@Action("/jingdong/test")
	 public void distributionTest() {
		distributionForJingdongService.addResources();
		distributionForJingdongService.addProducts();
		distributionForJingdongService.updateResources();
		distributionForJingdongService.updateProducts();
		distributionForJingdongService.onOffLineProduct();
		distributionForJingdongService.updateDailyPrice();
	  
	 }


	/**
	 * 京东调用接口
	 */
	@Action("/distribution/distributionForJingdong")
	public void distributionForJingdong() {
		if (cmd == null || "".equals(cmd)||msg == null || "".equals(msg)) {
			msg= buildErrorXml();
		} else {
			int distCmd = Integer.parseInt(cmd);
			switch (distCmd) {
			case 20002:// 下订单
				submitOrder();
				break;
			case 20003:// 查订单
				queryOrder();
				break;
			case 20004:// 重发短信
				reSendSMS();
				break;
			case 20006:// 查询每日价格
				queryDailyPrices();
				break;
			default:
				
				break;
			}
		}
		sendXmlResult(msg);
	}

	/**
	 * 下单
	 */
	//@Action("/jingdong/submitOrder")
	public void submitOrder() {
		Request request = null;
		log.info("Request submitOrderXML:" + msg);
		try {
			request = JdUtil.getRequestOrder(msg);
		} catch (Exception e) {
			log.info("submitOrder Error:" + e);
		}
		cmd = "20002";
		msg = distributionForJingdongService.getSumbitOrderResXml(request);
	}

	/**
	 * 订单查询
	 */
	//@Action("/jingdong/queryOrder")
	public void queryOrder() {
		Request request = null;
		log.info("Request queryOrderXML:" + msg);
		try {
			request = JdUtil.getQueryOrder(msg);
		} catch (Exception e) {
			log.info("queryOrder Error:" + e);
		}
		cmd = "20003";
		msg = distributionForJingdongService.getQueryOrderResXml(request);
	}

	/**
	 * 重发短信
	 */
	//@Action("/jingdong/reSendSMS")
	public void reSendSMS() {
		Request request = null;
		log.info("Request reSendSMSXML:" + msg);
		try {
			request = JdUtil.getReSendSMS(msg);
		} catch (Exception e) {
			log.info("queryOrder Error:" + e);
		}
		cmd = "20004";
		msg = distributionForJingdongService.getReSendSMSXml(request);
	}

	/**
	 * 查询每日价格
	 */
	//@Action("/jingdong/queryDailyPrices")
	public void queryDailyPrices() {
		Request request = null;
		log.info("Request queryDailyPricesXML:" + msg);
		try {
			request = JdUtil.getDailyPrices(msg);
		} catch (Exception e) {
			log.info("queryOrderXml Exception:" + e);
		}
		cmd = "20006";
		msg = distributionForJingdongService.getDailyPrices(request);
	}

	private String buildErrorXml() {
		log.info("request Error: 请求消息为空");
		String dealTime = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmssSSS");
		Result result = new Result("false", "200", "必要元素为空", dealTime);
		String version = DistributionUtil.getPropertiesByKey("jingdong.version");
		Head head = new Head(version , "ErrorResponse");
		Body body = new Body(result);
		Response res = new Response(head, body);
		return res.buildErrorMsg();
	}
	
	public DistributionForJingdongService getDistributionForJingdongService() {
		return distributionForJingdongService;
	}

	public void setDistributionForJingdongService(DistributionForJingdongService distributionForJingdongService) {
		this.distributionForJingdongService = distributionForJingdongService;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

}
