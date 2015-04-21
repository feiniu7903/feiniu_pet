
package com.lvmama.pet.payment.phonepay;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import chinapnr.SecureLink;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.InfoBase64Coding;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 整体发送到汇付天下 bean.
 * @author sunruyi
 */
public class CardAndOrderObject {
	
	protected transient final static Log log = LogFactory.getLog(CardAndOrderObject.class);
	
	private CardObject card;
	private OrderObject order;
	
	private String md5Sign;
	private String timeStamp;
	private String opsId;
	private String parasigned;
	/**
	 * 商户号.
	 */
	private String CHINAPNR_MERID ;
	/**
	 * 汇付交易类型.
	 */
	private String CHINAPNR_PAYMENT_TRANSTYPE ;
	/**
	 * 版本号.
	 */
	private String CHINAPNR_VERSION ;
	/**
	 * 异步（后台）返回交易结果接收地址.
	 */
	private String CHINAPNR_RETURL ;
	/**
	 * 商户私钥文件名.
	 */
	private String CHINAPNR_MP_PATH ;
	/**
	 * 电话支付平台的公钥文件名.
	 */
	private String CHINAPNR_PG_PATH = PaymentConstant.getInstance().getProperty("CHINAPNR_PG_PATH");
	
	
	public static CardAndOrderObject createInstance(String xmlRequest) {
		RequestObject requestobj = StringUtil.getRequestObject(xmlRequest);// 先获取请求Body信息
		RequestHeadObject requestheadobj = requestobj.getHeadobj();
		String body = requestobj.getBody();
		log.info("ENCRYPTED:" + body);
		body = InfoBase64Coding.decrypt(body);// 解析加密
		log.info("TEXT1:" + body);
		CardAndOrderObject obj = extractInstance(body);
		obj.setParasigned(requestheadobj.getSigned());
		obj.init();
		return obj;
	}
	/**
	 * 由xmlReqeust生成CardAndOrderObject对象.
	 * @param xmlRequest ivr传来的报文.
	 * @return
	 */
	private static CardAndOrderObject extractInstance(String xmlRequest) {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("body",CardAndOrderObject.class);
		xStream.aliasField("timestamp",CardAndOrderObject.class,"timeStamp");
		xStream.aliasField("card",CardObject.class,"Card");
		xStream.aliasField("order",OrderObject.class,"Order");
		/** *****定义类中属性********** */
		xStream.aliasField("orderid",OrderObject.class,"orderId");
		xStream.aliasField("paytotal",OrderObject.class,"paytotal");
		xStream.aliasField("bankgate",OrderObject.class,"bankgate");
		xStream.aliasField("mobilenumber",OrderObject.class,"mobilenumber");
		xStream.aliasField("csno",OrderObject.class,"csNo");
		
		xStream.aliasField("cardno",CardObject.class,"cardNo");
		xStream.aliasField("validdate",CardObject.class,"validDate");
		xStream.aliasField("cvv2",CardObject.class,"cvv2");
		xStream.aliasField("idtype",CardObject.class,"idType");
		xStream.aliasField("idno",CardObject.class,"idNo");
		xStream.aliasField("name",CardObject.class,"name");
		return (CardAndOrderObject)xStream.fromXML(xmlRequest.toLowerCase());
	}
	
	public void init() {
		/**
		 * 商户号.
		 */
		 CHINAPNR_MERID = PaymentConstant.getInstance().getProperty("CHINAPNR_MERID");
		/**
		 * 汇付交易类型.
		 */
		 CHINAPNR_PAYMENT_TRANSTYPE = PaymentConstant.getInstance().getProperty("CHINAPNR_TRANSTYPE");
		/**
		 * 版本号.
		 */
		 CHINAPNR_VERSION = PaymentConstant.getInstance().getProperty("CHINAPNR_VERSION");
		/**
		 * 异步（后台）返回交易结果接收地址.
		 */
		 CHINAPNR_RETURL = PaymentConstant.getInstance().getProperty("CHINAPNR_RETURL");
		/**
		 * 商户私钥文件名.
		 */
		 CHINAPNR_MP_PATH = PaymentConstant.getInstance().getProperty("CHINAPNR_MP_PATH");
		/**
		 * 电话支付平台的公钥文件名.
		 */
		 CHINAPNR_PG_PATH = PaymentConstant.getInstance().getProperty("CHINAPNR_PG_PATH");
	}
	
	/**
	 * 较验IVR签名
	 * @return
	 */
	public boolean verifySignature() {
		//取对方发送回来的md5进行比对
		StringBuffer sb = new StringBuffer("");
		sb.append(card.getCardNo())
		  .append(card.getValidDate())
		  .append(order.getOrderId())
		  .append(order.getCsNo())
		  .append(getTimeStamp());
		log.info("TEXT2:" + sb.toString());
		String composeSign =InfoBase64Coding.encrypt(sb.toString());
		composeSign = composeSign.replaceAll("\n", "");
		composeSign = composeSign.replaceAll("\r", "");
		//比对不成功，返回错误
		log.info("mySign:" + composeSign);
		log.info("outSign:" + parasigned);
		return parasigned.equals(composeSign);
	}
	/**
	 * 初始化支付记录.
	 * @return	PayPayment
	 */
	public PayPayment initPayPayment() {
		String bankgate = order.getBankgate();
		PayPayment payment = new PayPayment();
		payment.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
		payment.setObjectType(Constant.PAYMENT_OBJECT_TYPE.ORD_ORDER.name());
		payment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
		payment.setObjectId(Long.valueOf(order.getOrderId()));
		payment.setPaymentGateway(Constant.PAYMENT_GATEWAY.CHINAPNR.name());
		payment.setGateId(bankgate);
		payment.setAmount(order.getPayTotalFen());
		payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
		payment.setSerial(payment.geneSerialNo());
		opsId = payment.getSerial();
		payment.setPaymentTradeNo(SerialUtil.generate15ByteSerial());
		return payment;
	}
	/**
	 * 在提交到汇付前，构建map数据集合
	 * @param ordId
	 * @return
	 */
	public Map<String, String> createDataMap(String ordId) {
		Map<String, String> info = new HashMap<String,String>();
		String gateId = order.getBankgate(); // 支付网关号
		String ordAmt = PriceUtil.trans2YuanStr(PriceUtil.convertToFen(order.getPaytotal()));
		info.put("Version", CHINAPNR_VERSION);
		info.put("CmdId",CHINAPNR_PAYMENT_TRANSTYPE);
		info.put("MerId", CHINAPNR_MERID);
		info.put("OrdId", ordId);
		info.put("OrdAmt", ordAmt);
		info.put("GateId", gateId);
		info.put("MerPriv","");
		info.put("DivDetails","");
		info.put("BgRetUrl", CHINAPNR_RETURL);
		info.put("PartnerCode","");
		// 构造签名数据体
		String chkdata = CHINAPNR_VERSION+CHINAPNR_PAYMENT_TRANSTYPE + CHINAPNR_MERID + ordId + ordAmt + gateId+""+""+CHINAPNR_RETURL;
		SecureLink secureLink = new SecureLink();
		// 构造签名是否成功
		String chkValue = signMsg(secureLink, chkdata, CHINAPNR_MERID);
		String encryptCard = buildCard(secureLink);
		info.put("CardInfo", encryptCard);
		info.put("ChkValue", chkValue);
		return info;
	}
	
	/**
	 * 签名信息
	 * @param t
	 * @param chkdata
	 * @param merkeyfile
	 * @param merId
	 * @return
	 */
	private String signMsg(SecureLink t, String chkdata,
			String merId) {
		int signflag = t.SignMsg(merId, CHINAPNR_MP_PATH, chkdata);
		if (signflag != 0) {
			log.error("chinapnr pay generateSign fail!");
			return null;
		}
		String chkvalue = t.getChkValue();
		log.info("chkvalue:" + chkvalue);
		return chkvalue;
	}
	
	/**
	 * 构造个人信用卡基本信息
	 * 
	 * @param destmap
	 * @param cardinfo
	 * @return
	 */
	private String buildCard(SecureLink secureLink) {
		StringBuffer sb = new StringBuffer("");
		String cardNo = card.getCardNo();// 卡号
		String validDate = card.getValidDate();// 有效期(有效期 MMyy)
		String cvv2 = card.getCvv2();// CVV2 码
		String idType = "00";// 00－身份证（通过沟通直接写00）
		//卡对应的身份证号码
		String idNo =this.card.getIdNo().toUpperCase();
		String name =  card.getName(); // 持卡人姓名
		sb.append(cardNo).append("|").append(validDate).append("|")
				.append(cvv2).append("|").append(idType).append("|").append(
						idNo).append("|").append(name);
		String src = sb.toString();
  		int flag = secureLink.EncMsg(CHINAPNR_PG_PATH, src);
		if (flag == 0) {
			return secureLink.getEncMsg();
		}
		return "";
	}
 
	public String getMd5Sign() {
		return md5Sign;
	}

	public void setMd5Sign(String md5Sign) {
		this.md5Sign = md5Sign;
	}

	public String getParasigned() {
		return parasigned;
	}

	public void setParasigned(String parasigned) {
		this.parasigned = parasigned;
	}

	public CardObject getCard() {
		return card;
	}

	public void setCard(CardObject card) {
		this.card = card;
	}

	public OrderObject getOrder() {
		return order;
	}

	public void setOrder(OrderObject order) {
		this.order = order;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getOpsId() {
		return opsId;
	}

	public void setOpsId(String opsId) {
		this.opsId = opsId;
	}
}
