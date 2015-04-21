package com.lvmama.pet.payment.callback.web;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.utils.Dom4jUtil;
import com.lvmama.pet.payment.service.SandPosService;
import com.lvmama.pet.utils.StringDom4jUtil;
/**
 * POS机支付处理.
 * @author liwenzhan
 *
 */
@Result(name = "success", location = "/WEB-INF/pages/pos/sand_pos_pay.ftl", type = "freemarker")
public class SandPosCallbackAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2796225935564066578L;
	/**
	 * LOG.
	 */
	private static final Logger log = Logger.getLogger(SandPosCallbackAction.class);
	/**
	 * 接受的数据.
	 */
	private String message;
	/**
	 * 返回的数据.
	 */
	private String returnMessages = "";
	/**
	 * 交通银行POS处理的SERVICE.
	 */
	private SandPosService sandPosService;
	/**
	 * 签到.
	 */
	private static String SAND_POS_LOGIN="LMM001";
	/**
	 * 订单查询.
	 */
	private static String SAND_POS_SEARCH="LMM002";
	/**
	 * 支付通知.
	 */
	private static String SAND_POS_PAY="LMM004";

	
	/**
	 * 通知返回的报文.
	 */
    private String xmlContent = "";	
	/**
	 * 接收到POS机的信息进行处理并返回字符串给POS机.
	 * @return
	 */
	@Action("/pos/sandNotifyPos")
	public String notifyPos() {
		try {
			log.info("SANDPOS message="+message);
			if (StringUtils.isNotBlank(message)) {
				Map<String, String> xmlMap = StringDom4jUtil.getMapByDocument(message);
				String headStr = xmlMap.get("transaction_id");		
				if (SAND_POS_LOGIN.equals(headStr)) {
					returnMessages = sandPosService.posUserLogin(xmlMap);
				}
				if (SAND_POS_SEARCH.equals(headStr)) {
					returnMessages = sandPosService.queryOrderAmountByOrderId(xmlMap);
				}
				if (SAND_POS_PAY.equals(headStr)) {
					returnMessages = sandPosService.orderPayNotice(xmlMap);
				}
				log.info("SANDPOS returnMessages:"+returnMessages);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
	/**
	 * 加载接受的数据.
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置接受的数据.
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public SandPosService getSandPosService() {
		return sandPosService;
	}

	public void setSandPosService(SandPosService sandPosService) {
		this.sandPosService = sandPosService;
	}

	/**
	 * 加载返回的数据.
	 * @return
	 */
	public String getReturnMessages() {
		return returnMessages;
	}

	/**
	 * 设置返回的数据.
	 * @param returnMessages
	 */
	public void setReturnMessages(String returnMessages) {
		this.returnMessages = returnMessages;
	}

	public String getXmlContent() {
		return xmlContent;
	}


	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}


	public static void main(String[] args) throws Exception {
//		String message="MzAwIDMwMDAzMDEzMTAwNDcyMjcyMzU2NDQwOTAxMTAwNDQzOTIwMTIxMjEzMTA0NTA3MjAxMjEyMTMwMDAzOTEgICAgICAgICAgICAgICAgICAgIDYyMjE2ODIyMzEzMDI4OTkgICAwMTA1MDAwMSAgICAwMDEwNDUwNzY1MDE4NTAwOTk1MjAwMDA1MzE5MDI3NTYwMDEgICAgICAgICAgMDAwMDAwNTg4NDAwMDAwMDAwMDAwNyAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA1OTJCNzNGQw==";		
//		String code=notifyMessage(message);
//		System.out.println(code);
//		System.out.println(code.substring(174,184));
//		System.out.println(code.substring(244,252));
		
		
		
		String loginMessage="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
						"<Transaction>"+
						"<Transaction_Header>"+
						"<transaction_id>NGS002</transaction_id>"+
						"<requester>1111111111</requester>"+  //商户编号
						"<target>000000000000000</target>"+   //应答方,采用公司英文名称
						"<request_time>20121228140015</request_time>"+   //请求时间,格式为yyyyMMddHHmmss
						"<terminal_eqno>1347081101760007</terminal_eqno>"+ //终端设备号
						"<terminal_id>62000101</terminal_id>"+   //物流终端号
						"<system_serial>121228002814</system_serial>"+ //系统流水号YYMMDD+（00000000-99999999）
						"<version>V020</version>"+ //版本号,目前默认为1.0  V020
						"<ext_attributes>"+                                                 
						"<delivery_man>2000102</delivery_man>"+//签到柜员
						"<settle_account>000001</settle_account>"+//物流批次号
						"</ext_attributes>"+
						"</Transaction_Header>"+
						"<Transaction_Body>"+
						"<company_id>dddd</company_id>"+ //公司ID
						"<delivery_man>426581</delivery_man>"+//员工号
						"<password>426581</password>"+//员工登录密码,32位md5摘要(大写)
						"<check_value>E75A09179D6019739B327F1F3DBD22A4</check_value>"+
						"</Transaction_Body>"+
						"</Transaction>";
		Map<String, String> xmlString = Dom4jUtil.AnalyticXml(loginMessage);
		System.out.println((String)xmlString.get("Transaction_Header"));
		System.out.println((String)xmlString.get("requester"));
		System.out.println((String)xmlString.get("company_id"));
	}
	
}
