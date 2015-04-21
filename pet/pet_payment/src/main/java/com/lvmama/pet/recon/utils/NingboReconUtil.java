package com.lvmama.pet.recon.utils;

import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infosec.NetSignServer;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.XmlToBeanUtil;
import com.lvmama.pet.vo.NingboPaymentReconVO;
import com.lvmama.pet.vo.NingboRefundReconVO;
import com.lvmama.pet.vo.NingboResponseBean;

public class NingboReconUtil {
	protected final static Log log =LogFactory.getLog(NingboReconUtil.class.getName());
	
	public static String initRequestParamsMap(String reqCustomerId,Date startDate,Date endDate,String dnHead,String dnTail){
		String reqTime=DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");
		String sd=signature(reqCustomerId,startDate,endDate,dnHead,dnTail);
		StringBuffer sendMessage=new StringBuffer();
		sendMessage.append("<?xml version=\"1.0\" encoding=\"gbk\" ?>");
		sendMessage.append("<root>");
			sendMessage.append("<reqServiceId>NCTR04Comm</reqServiceId>");
			sendMessage.append("<reqTime>"+reqTime+"</reqTime>");
			sendMessage.append("<reqFlowNo></reqFlowNo>");
			sendMessage.append("<reqCustomerId>"+reqCustomerId+"</reqCustomerId>");
			sendMessage.append("<reqChannelId>NC</reqChannelId>");
			sendMessage.append("<reqDataView>SML</reqDataView>");
			sendMessage.append("<cd>");
				sendMessage.append("<reqCustomerId>"+reqCustomerId+"</reqCustomerId>");
				sendMessage.append("<gmt_create_start>"+DateUtil.formatDate(startDate, "yyyyMMdd")+"</gmt_create_start>");
				sendMessage.append("<gmt_create_end>"+DateUtil.formatDate(endDate, "yyyyMMdd")+"</gmt_create_end>");
			sendMessage.append("</cd>");
			sendMessage.append("<sd>"+sd+"</sd>");
		sendMessage.append("</root>");
	    return sendMessage.toString();
	}
	
	public static String initQueryRequestParamsMap(String reqCustomerId,String trade_no,String out_trade_no,String dnHead,String dnTail){
		String reqTime=DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");
		String sd=signatureQuery(trade_no,out_trade_no,dnHead,dnTail);
		StringBuffer sendMessage=new StringBuffer();
		sendMessage.append("<?xml version=\"1.0\" encoding=\"gbk\" ?>");
		sendMessage.append("<root>");
			sendMessage.append("<reqServiceId>NCTR03Comm</reqServiceId>");
			sendMessage.append("<reqTime>"+reqTime+"</reqTime>");
			sendMessage.append("<reqFlowNo></reqFlowNo>");
			sendMessage.append("<reqCustomerId>"+reqCustomerId+"</reqCustomerId>");
			sendMessage.append("<reqChannelId>NC</reqChannelId>");
			sendMessage.append("<reqDataView>XML</reqDataView>");
			sendMessage.append("<cd>");
				sendMessage.append("<trade_no>"+trade_no+"</trade_no>");
				sendMessage.append("<out_trade_no>"+out_trade_no+"</out_trade_no>");
			sendMessage.append("</cd>");
			sendMessage.append("<sd>"+sd+"</sd>");
		sendMessage.append("</root>");
	    return sendMessage.toString();
	}

	public static String signature(String reqCustomerId,Date startDate,Date endDate,String dnHead,String dnTail) {
		StringBuffer sendMessage=new StringBuffer();
		sendMessage.append("<cd>");
			sendMessage.append("<reqCustomerId>"+reqCustomerId+"</reqCustomerId>");
			sendMessage.append("<gmt_create_start>"+DateUtil.formatDate(startDate, "yyyyMMdd")+"</gmt_create_start>");
			sendMessage.append("<gmt_create_end>"+DateUtil.formatDate(endDate, "yyyyMMdd")+"</gmt_create_end>");
		sendMessage.append("</cd>");
		log.info("recon signature sendMessage="+sendMessage);
		String signMsg="";
		try {
			String bankDN = dnHead + dnTail;
			NetSignServer nss = new NetSignServer();
			nss.NSSetPlainText(sendMessage.toString().getBytes("gbk"));
			byte bSignMsg[] = nss.NSDetachedSign(bankDN);  
			int i = nss.getLastErrnum();
			log.info("verifyCode...." +i);
			signMsg=new String(bSignMsg,"gbk");
			log.info("signMsg...." + signMsg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signMsg;
	}

	public static String signatureQuery(String trade_no,String out_trade_no,String dnHead,String dnTail) {
		StringBuffer sendMessage=new StringBuffer();
		sendMessage.append("<cd>");
			sendMessage.append("<cd>");
			sendMessage.append("<trade_no>"+trade_no+"</trade_no>");
			sendMessage.append("<out_trade_no>"+out_trade_no+"</out_trade_no>");
		sendMessage.append("</cd>");
		log.info("recon signature sendMessage="+sendMessage);
		String signMsg="";
		try {
			String bankDN = dnHead + dnTail;
			NetSignServer nss = new NetSignServer();
			nss.NSSetPlainText(sendMessage.toString().getBytes("gbk"));
			byte bSignMsg[] = nss.NSDetachedSign(bankDN);  
			int i = nss.getLastErrnum();
			log.info("verifyCode...." +i);
			signMsg=new String(bSignMsg,"gbk");
			log.info("signMsg...." + signMsg);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return signMsg;
	}
	public static Boolean isDownloadSuccess(String resultXML){
		String ec=getContent("<ec>", "</ec>", resultXML);
		if("0000".equals(ec)){
			return true;
		}
		else{
			return false;
		}
	}
	public static String getDownloadErrorMsg(String resultXML){
		return getContent("<ec>", "</ec>", resultXML)+":"+getContent("<em>", "</em>", resultXML);
	}
	public static Boolean checkSignature(String resultXML){
		String content=getContent("<cd>", "</cd>", resultXML);
		String sign=getContent("<sd>", "</sd>", resultXML);
		try {
			NetSignServer nss = new NetSignServer();
			nss.NSDetachedVerify(sign.getBytes("GBK"),("<cd>"+content+"</cd>").getBytes("GBK"));
			int i = nss.getLastErrnum();
			log.info("refund async callback verifyCode...." +i);
			if(i==0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 根据开始字符+结束字符 返回两字符中的内容
	 * @author ZHANG Nan
	 * @param startName
	 * @param endName
	 * @param sourceContent
	 * @return
	 */
	public static String getContent(String startName,String endName,String sourceContent){
		return sourceContent.substring(sourceContent.indexOf(startName)+startName.length(), sourceContent.indexOf(endName));
	}
	
	
	
	
	/**
	 * 将对账明细封装为对账对象
	 * 
	 * @author: ranlongfei 2012-7-2 下午05:39:06
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static NingboResponseBean parseResponseBean(String data){
		Reader in = new StringReader(data); 
		Object parseResult = null;
		try {
			parseResult = XmlToBeanUtil.parseXml(buildXmlParserNew(), in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (NingboResponseBean)parseResult;
	}
	
	/**
	 * 支付宝数据解析器
	 * 
	 * @author: ranlongfei 2012-7-2 下午04:54:13
	 * @return
	 */
	private static Digester buildXmlParserNew() {
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate("root", NingboResponseBean.class);
		digester.addCallMethod("root/repServiceId", "setRepServiceId", 0);
		digester.addCallMethod("root/repFlowNo", "setRepFlowNo", 0);
		digester.addCallMethod("root/ec", "setEc", 0);
		digester.addCallMethod("root/em", "setEm", 0);
		digester.addCallMethod("root/sd", "setSd", 0);
		
		digester.addCallMethod("root/cd/batch_num", "setBatchNum", 0);
		digester.addCallMethod("root/cd/count", "setCount", 0);
		digester.addCallMethod("root/cd/gmt_create_start", "setGmtCreateStart", 0);
		digester.addCallMethod("root/cd/gmt_create_end", "setGmtCreateEnd", 0);
		
		digester.addObjectCreate("*/iCreatePayFlow/record", NingboPaymentReconVO.class);
			digester.addCallMethod("*/iCreatePayFlow/record/reqCustomerId", "setReqCustomerId",0);
			digester.addCallMethod("*/iCreatePayFlow/record/out_trade_no", "setOutTradeNo",0);
			digester.addCallMethod("*/iCreatePayFlow/record/total_fee", "setTotalFee",0);
			digester.addCallMethod("*/iCreatePayFlow/record/trade_status", "setTradeStatus",0);
			digester.addCallMethod("*/iCreatePayFlow/record/subject", "setSubject",0);
			digester.addCallMethod("*/iCreatePayFlow/record/paymethod", "setPaymethod",0);
			digester.addCallMethod("*/iCreatePayFlow/record/body", "setBody",0);
			digester.addCallMethod("*/iCreatePayFlow/record/extra_common_param", "setExtraCommonParam",0);
			digester.addCallMethod("*/iCreatePayFlow/record/reqTime", "setReqTime",0);
			digester.addCallMethod("*/iCreatePayFlow/record/trade_no", "setTradeNo",0);
		digester.addSetNext("*/iCreatePayFlow/record", "addNingboPaymentReconVOList");
		
		digester.addObjectCreate("*/iCreateRefundFlow/record", NingboRefundReconVO.class);
			digester.addCallMethod("*/iCreateRefundFlow/record/reqCustomerId", "setReqCustomerId",0);
			digester.addCallMethod("*/iCreateRefundFlow/record/gmt_refund", "setGmtRefund",0);
			digester.addCallMethod("*/iCreateRefundFlow/record/refund_status", "setRefundStatus",0);
			digester.addCallMethod("*/iCreateRefundFlow/record/refund_fee", "setRefundFee",0);
			digester.addCallMethod("*/iCreateRefundFlow/record/trade_no", "setTradeNo",0);
			digester.addCallMethod("*/iCreateRefundFlow/record/batch_no", "setBatchNo",0);
			digester.addCallMethod("*/iCreateRefundFlow/record/out_order_no", "setOutOrderNo",0);
		digester.addSetNext("*/iCreateRefundFlow/record", "addNingboRefundReconVOList");
		return digester;
	}
	public static void main(String[] args) {
//		NingboResponseBean ningboResponseBean=parseResponseBean("<?xml version=\"1.0\" encoding=\"GBK\" ?><root>	<repServiceId>NCTR04Comm</repServiceId>	<repFlowNo></repFlowNo>	<ec>0000</ec>	<em></em>	<cd>		<iCreatePayFlow>			<record>				<reqCustomerId>0000178459</reqCustomerId>				<out_trade_no>201305291656578571319243</out_trade_no>				<total_fee>0.01</total_fee>				<trade_status>TRADE_SUCCESS</trade_status>				<subject>www.lvmama.com</subject>				<paymethod>bankPay</paymethod>				<body>www.lvmama.com</body>				<extra_common_param></extra_common_param>				<reqTime>20130529164856</reqTime>				<trade_no>2013052907282174</trade_no>			</record>			<record>				<reqCustomerId>0000178459</reqCustomerId>				<out_trade_no>201305291713252561319247</out_trade_no>				<total_fee>0.01</total_fee>				<trade_status>TRADE_SUCCESS</trade_status>				<subject>www.lvmama.com</subject>				<paymethod>bankPay</paymethod>				<body>www.lvmama.com</body>				<extra_common_param></extra_common_param>				<reqTime>20130529170520</reqTime>				<trade_no>2013052904482157</trade_no>			</record>			<record>				<reqCustomerId>0000178459</reqCustomerId>				<out_trade_no>201305291717404471319248</out_trade_no>				<total_fee>0.01</total_fee>				<trade_status>TRADE_SUCCESS</trade_status>				<subject>www.lvmama.com</subject>				<paymethod>bankPay</paymethod>				<body>www.lvmama.com</body>				<extra_common_param></extra_common_param>				<reqTime>20130529170936</reqTime>				<trade_no>2013052907599074</trade_no>			</record>			<record>				<reqCustomerId>0000178459</reqCustomerId>				<out_trade_no>201305291639132611319231</out_trade_no>				<total_fee>0.01</total_fee>				<trade_status>TRADE_SUCCESS</trade_status>				<subject>www.lvmama.com</subject>				<paymethod>bankPay</paymethod>				<body>www.lvmama.com</body>				<extra_common_param></extra_common_param>				<reqTime>20130529163108</reqTime>				<trade_no>2013052929420563</trade_no>			</record>		</iCreatePayFlow>		<batch_num>1</batch_num>		<gmt_create_start>20130529</gmt_create_start>		<gmt_create_end>20130530</gmt_create_end>		<count>4</count>		<iCreateRefundFlow>			<record>				<reqCustomerId>0000178459</reqCustomerId>				<gmt_refund>20130529173441</gmt_refund>				<refund_status>SUCCESS</refund_status>				<refund_fee>0.01</refund_fee>				<trade_no>2013052929420563</trade_no>				<batch_no>2013052910000040</batch_no>			</record>		</iCreateRefundFlow>	</cd>	<sd>MIIEdQYJKoZIhvcNAQcCoIIEZjCCBGICAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA5wwggOYMIIDAaADAgECAhB0qH9YrzyyhJcqb1Vyz46FMA0GCSqGSIb3DQEBBQUAMCoxCzAJBgNVBAYTAkNOMRswGQYDVQQKExJDRkNBIE9wZXJhdGlvbiBDQTIwHhcNMTIxMjA3MDExNjU1WhcNMTUwMTA1MDEzNTQ3WjCBjjELMAkGA1UEBhMCQ04xGzAZBgNVBAoTEkNGQ0EgT3BlcmF0aW9uIENBMjENMAsGA1UECxMETkJDQjEUMBIGA1UECxMLRW50ZXJwcmlzZXMxPTA7BgNVBAMeNAAwADQAMQBAADgAMABAW4Fs4pT2iEyAoU79ZwmWUFFsU/gAQAAwADAAMAAwADAAMAAwADEwXDANBgkqhkiG9w0BAQEFAANLADBIAkEAv+grpzocLXWFw2aCS+QLoWbVkNV0kCzBVllx4Z945MtbcCsv8B1TDWtlXqgDSThMPg4gPzhX3cOyWapF1ek14QIDAQABo4IBnDCCAZgwHwYDVR0jBBgwFoAU8I3ts0G7++8IHlUCwzE37zwUTs0wHQYDVR0OBBYEFOzkwgHlkoxBUYv2GoqTNzBG3JIqMAsGA1UdDwQEAwIE8DAMBgNVHRMEBTADAQEAMDsGA1UdJQQ0MDIGCCsGAQUFBwMBBggrBgEFBQcDAgYIKwYBBQUHAwMGCCsGAQUFBwMEBggrBgEFBQcDCDCB/QYDVR0fBIH1MIHyMFagVKBSpFAwTjELMAkGA1UEBhMCQ04xGzAZBgNVBAoTEkNGQ0EgT3BlcmF0aW9uIENBMjEMMAoGA1UECxMDQ1JMMRQwEgYDVQQDEwtjcmwxMDRfNjk2MzCBl6CBlKCBkYaBjmxkYXA6Ly9jZXJ0ODYzLmNmY2EuY29tLmNuOjM4OS9DTj1jcmwxMDRfNjk2MyxPVT1DUkwsTz1DRkNBIE9wZXJhdGlvbiBDQTIsQz1DTj9jZXJ0aWZpY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Y2xhc3M9Y1JMRGlzdHJpYnV0aW9uUG9pbnQwDQYJKoZIhvcNAQEFBQADgYEAPZQuFHRpFyJGwSRxmypyCzbZruNa+xvF4OsxPiVKPWfaK7y1t6hx7pZ2BH8WXhx5yE02K7BxJbouki6Lyds+W5Bll8Tj73uwQo1mBXVPULE9E04zNo+yvMNBsOZmKVKS7nAaejnCoDgCHnaMTGPbtj6xikMN3iVI+gqG0LEyBj8xgaIwgZ8CAQEwPjAqMQswCQYDVQQGEwJDTjEbMBkGA1UEChMSQ0ZDQSBPcGVyYXRpb24gQ0EyAhB0qH9YrzyyhJcqb1Vyz46FMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEQJh3xdpI6qHKiUGMv8TzGXl22YouSvcBr27pDGuGMT5hwuECGRpCPOw3RBbkDV6jUo/P0Ims+3n4VxEbwrwg7KI=	</sd></root>");
//		System.out.println(ningboResponseBean);
//		System.out.println(PriceUtil.convertToFen("0.01"));
		System.out.println(checkSignature("<?xml version=\"1.0\" encoding=\"GBK\" ?><root><repServiceId>NCTR04Comm</repServiceId><repFlowNo></repFlowNo><ec>0000</ec><em></em><cd><iCreatePayFlow></iCreatePayFlow><gmt_create_start>20130531</gmt_create_start><gmt_create_end>20130601</gmt_create_end><count>0</count></cd><sd>MIIEdQYJKoZIhvcNAQcCoIIEZjCCBGICAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA5wwggOYMIIDAaADAgECAhB0qH9YrzyyhJcqb1Vyz46FMA0GCSqGSIb3DQEBBQUAMCoxCzAJBgNVBAYTAkNOMRswGQYDVQQKExJDRkNBIE9wZXJhdGlvbiBDQTIwHhcNMTIxMjA3MDExNjU1WhcNMTUwMTA1MDEzNTQ3WjCBjjELMAkGA1UEBhMCQ04xGzAZBgNVBAoTEkNGQ0EgT3BlcmF0aW9uIENBMjENMAsGA1UECxMETkJDQjEUMBIGA1UECxMLRW50ZXJwcmlzZXMxPTA7BgNVBAMeNAAwADQAMQBAADgAMABAW4Fs4pT2iEyAoU79ZwmWUFFsU/gAQAAwADAAMAAwADAAMAAwADEwXDANBgkqhkiG9w0BAQEFAANLADBIAkEAv+grpzocLXWFw2aCS+QLoWbVkNV0kCzBVllx4Z945MtbcCsv8B1TDWtlXqgDSThMPg4gPzhX3cOyWapF1ek14QIDAQABo4IBnDCCAZgwHwYDVR0jBBgwFoAU8I3ts0G7++8IHlUCwzE37zwUTs0wHQYDVR0OBBYEFOzkwgHlkoxBUYv2GoqTNzBG3JIqMAsGA1UdDwQEAwIE8DAMBgNVHRMEBTADAQEAMDsGA1UdJQQ0MDIGCCsGAQUFBwMBBggrBgEFBQcDAgYIKwYBBQUHAwMGCCsGAQUFBwMEBggrBgEFBQcDCDCB/QYDVR0fBIH1MIHyMFagVKBSpFAwTjELMAkGA1UEBhMCQ04xGzAZBgNVBAoTEkNGQ0EgT3BlcmF0aW9uIENBMjEMMAoGA1UECxMDQ1JMMRQwEgYDVQQDEwtjcmwxMDRfNjk2MzCBl6CBlKCBkYaBjmxkYXA6Ly9jZXJ0ODYzLmNmY2EuY29tLmNuOjM4OS9DTj1jcmwxMDRfNjk2MyxPVT1DUkwsTz1DRkNBIE9wZXJhdGlvbiBDQTIsQz1DTj9jZXJ0aWZpY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Y2xhc3M9Y1JMRGlzdHJpYnV0aW9uUG9pbnQwDQYJKoZIhvcNAQEFBQADgYEAPZQuFHRpFyJGwSRxmypyCzbZruNa+xvF4OsxPiVKPWfaK7y1t6hx7pZ2BH8WXhx5yE02K7BxJbouki6Lyds+W5Bll8Tj73uwQo1mBXVPULE9E04zNo+yvMNBsOZmKVKS7nAaejnCoDgCHnaMTGPbtj6xikMN3iVI+gqG0LEyBj8xgaIwgZ8CAQEwPjAqMQswCQYDVQQGEwJDTjEbMBkGA1UEChMSQ0ZDQSBPcGVyYXRpb24gQ0EyAhB0qH9YrzyyhJcqb1Vyz46FMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEQAvY9D+vb9LaUlGNXQbyeTcljg+DzH7k4D2Bc9lXTybMnrukERCATLguUmCf9DmAjOLjH691CoyufGiHRnSg6WM=</sd></root>"));
	}
}
