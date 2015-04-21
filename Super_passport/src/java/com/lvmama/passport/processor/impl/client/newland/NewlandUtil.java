package com.lvmama.passport.processor.impl.client.newland;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lvmama.passport.processor.impl.client.newland.model.CredentialSyncReq;
import com.lvmama.passport.processor.impl.client.newland.model.Order;
import com.lvmama.passport.processor.impl.client.newland.model.OrderResend;
import com.lvmama.passport.processor.impl.client.newland.model.ResendRes;
import com.lvmama.passport.processor.impl.client.newland.model.RollbackReq;
import com.lvmama.passport.processor.impl.client.newland.model.SubmitRes;
import com.lvmama.passport.processor.impl.client.newland.model.VerifyReq;
import com.lvmama.passport.utils.Configuration;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
public class NewlandUtil {

	/**
	 * 申请码响应对象
	 * @param XmlRequest
	 * @return
	 */
	public static SubmitRes getSubmitRes(String xmlResponse){
		XStream xstream = getSubmitResXstreamObject();
		SubmitRes obj = (SubmitRes) xstream.fromXML(xmlResponse);
		return obj;
	}
	

	/**
	 * 验证码请求对象
	 * @param XmlRequest
	 * @return
	 */
	public static VerifyReq getVerifyReq(String xmlResponse,boolean isOnline){
		XStream xstream = getVerifyReqXstreamObject(isOnline);
		VerifyReq obj = (VerifyReq) xstream.fromXML(xmlResponse);
		return obj;
	}
	
	/**
	 * 回退请求对象
	 * @param XmlRequest
	 * @return
	 */
	public static RollbackReq getRollbackReq(String xmlResponse){
		XStream xstream = getRollbackReqXstreamObject();
		RollbackReq obj = (RollbackReq) xstream.fromXML(xmlResponse);
		return obj;
	}

	/**
	 * 重发短信对象
	 * @param XmlRequest
	 * @return
	 */
	public static ResendRes getResendRes(String xmlResponse){
		XStream xstream = getResendResXstreamObject();
		ResendRes obj = (ResendRes) xstream.fromXML(xmlResponse);
		return obj;
	}
	/**
	 * 一步请求参数
	 * @param xmlResponse
	 * @return
	 */
	public static CredentialSyncReq getCredentialSyncReq(String xmlResponse){
		XStream xstream =getCredentialSyncReqXstreamObject();
		CredentialSyncReq obj = (CredentialSyncReq) xstream.fromXML(xmlResponse);
		return obj;
	}
	
	/**
	 * 重发超过字数短信
	 * @param XmlRequest
	 * @return
	 */
	public static OrderResend getResendSmsReq(String xmlResponse)throws Exception{
		Document dom = DocumentHelper.parseText(xmlResponse); 
		Element root = dom.getRootElement();
		Iterator it=root.elementIterator();
		List<Order> orders=new ArrayList<Order>();
		while(it.hasNext()){
			Element e=(Element)it.next();
			Order order=new Order();
			order.setOrderId(e.getText());
			orders.add(order);
		}
		OrderResend orderResend=new OrderResend();
		orderResend.setOrders(orders);
		return orderResend;
	}
	
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getSubmitResXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("SubmitRes",com.lvmama.passport.processor.impl.client.newland.model.SubmitRes.class);
		xStream.alias("Status",com.lvmama.passport.processor.impl.client.newland.model.Status.class);
		/** *****定义类中属性********** */
		xStream.aliasField("TransactionID",com.lvmama.passport.processor.impl.client.newland.model.SubmitRes.class,"transactionId");
		xStream.aliasField("ISSPID",com.lvmama.passport.processor.impl.client.newland.model.SubmitRes.class,"is_sp_id");
		xStream.aliasField("MessageID",com.lvmama.passport.processor.impl.client.newland.model.SubmitRes.class,"messageId");
		xStream.aliasField("Wbmp",com.lvmama.passport.processor.impl.client.newland.model.SubmitRes.class,"wbmp");
		
		xStream.aliasField("Status",com.lvmama.passport.processor.impl.client.newland.model.SubmitRes.class,"status");
		xStream.aliasField("StatusCode",com.lvmama.passport.processor.impl.client.newland.model.Status.class,"status_code");
		xStream.aliasField("StatusText",com.lvmama.passport.processor.impl.client.newland.model.Status.class,"status_text");
		return xStream;
	}
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getResendResXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("ResendRes",com.lvmama.passport.processor.impl.client.newland.model.ResendRes.class);
		xStream.alias("Status",com.lvmama.passport.processor.impl.client.newland.model.Status.class);
		/** *****定义类中属性********** */
		xStream.aliasField("TransactionID",com.lvmama.passport.processor.impl.client.newland.model.ResendRes.class,"transactionId");
		xStream.aliasField("ISSPID",com.lvmama.passport.processor.impl.client.newland.model.ResendRes.class,"is_sp_id");
		xStream.aliasField("MessageID",com.lvmama.passport.processor.impl.client.newland.model.ResendRes.class,"messageId");
		xStream.aliasField("Wbmp",com.lvmama.passport.processor.impl.client.newland.model.ResendRes.class,"wbmp");
		
		xStream.aliasField("Status",com.lvmama.passport.processor.impl.client.newland.model.ResendRes.class,"status");
		xStream.aliasField("StatusCode",com.lvmama.passport.processor.impl.client.newland.model.Status.class,"status_code");
		xStream.aliasField("StatusText",com.lvmama.passport.processor.impl.client.newland.model.Status.class,"status_text");
		return xStream;
	}
	
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getVerifyReqXstreamObject(boolean isOnline){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("VerifyReq",com.lvmama.passport.processor.impl.client.newland.model.VerifyReq.class);
		xStream.alias("GoodsRecord",com.lvmama.passport.processor.impl.client.newland.model.GoodsRecord.class);
		xStream.alias("PGoodsList",com.lvmama.passport.processor.impl.client.newland.model.PGoodsList.class);
		/** *****定义类中属性********** */
		xStream.aliasField("PosID",com.lvmama.passport.processor.impl.client.newland.model.VerifyReq.class,"pos_id");
		xStream.aliasField("Customization",com.lvmama.passport.processor.impl.client.newland.model.VerifyReq.class,"customization");
		xStream.aliasField("ReqSequence",com.lvmama.passport.processor.impl.client.newland.model.VerifyReq.class,"req_sequence");
		if(!isOnline){
			xStream.aliasField("PosTransTime",com.lvmama.passport.processor.impl.client.newland.model.VerifyReq.class,"posTransTime");
		}
		xStream.aliasField("CredentialClass",com.lvmama.passport.processor.impl.client.newland.model.VerifyReq.class,"credential_class");
		xStream.aliasField("Credential",com.lvmama.passport.processor.impl.client.newland.model.VerifyReq.class,"credential");

		
		xStream.aliasField("GoodsRecord",com.lvmama.passport.processor.impl.client.newland.model.VerifyReq.class,"goodsRecord");
		xStream.aliasField("PGoodsGroup",com.lvmama.passport.processor.impl.client.newland.model.GoodsRecord.class,"pGoodsGroup");
		xStream.aliasField("ApplyAmount",com.lvmama.passport.processor.impl.client.newland.model.GoodsRecord.class,"apply_amount");
		xStream.aliasField("ApplyPassword",com.lvmama.passport.processor.impl.client.newland.model.GoodsRecord.class,"applyPassword");
		xStream.aliasField("PGoodsList",com.lvmama.passport.processor.impl.client.newland.model.GoodsRecord.class,"pGoodsList");
		xStream.aliasField("PGoodsID",com.lvmama.passport.processor.impl.client.newland.model.PGoodsList.class,"pgoods_id");
		return xStream;
	}
	
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getRollbackReqXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("RollbackReq",com.lvmama.passport.processor.impl.client.newland.model.RollbackReq.class);
		xStream.alias("GoodsRecord",com.lvmama.passport.processor.impl.client.newland.model.GoodsRecord.class);
		/** *****定义类中属性********** */
		xStream.aliasField("PosID",com.lvmama.passport.processor.impl.client.newland.model.RollbackReq.class,"pos_id");
		xStream.aliasField("Customization",com.lvmama.passport.processor.impl.client.newland.model.RollbackReq.class,"customization");
		xStream.aliasField("ReqSequence",com.lvmama.passport.processor.impl.client.newland.model.RollbackReq.class,"req_sequence");
		xStream.aliasField("CredentialClass",com.lvmama.passport.processor.impl.client.newland.model.RollbackReq.class,"credential_class");
		xStream.aliasField("Credential",com.lvmama.passport.processor.impl.client.newland.model.RollbackReq.class,"credential");
		xStream.aliasField("OrgSequence",com.lvmama.passport.processor.impl.client.newland.model.RollbackReq.class,"org_sequence");
		xStream.aliasField("Comment",com.lvmama.passport.processor.impl.client.newland.model.RollbackReq.class,"comment");

		
		xStream.aliasField("GoodsRecord",com.lvmama.passport.processor.impl.client.newland.model.RollbackReq.class,"goodsRecord");
		xStream.aliasField("PGoodsID",com.lvmama.passport.processor.impl.client.newland.model.GoodsRecord.class,"pgoods_id");
		xStream.aliasField("ApplyAmount",com.lvmama.passport.processor.impl.client.newland.model.GoodsRecord.class,"apply_amount");

		return xStream;
	}
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getCredentialSyncReqXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("CredentialSyncReq",com.lvmama.passport.processor.impl.client.newland.model.CredentialSyncReq.class);
		/** *****定义类中属性********** */
		xStream.aliasField("ReqSequence",com.lvmama.passport.processor.impl.client.newland.model.CredentialSyncReq.class,"reqSequence");
		xStream.aliasField("CurrentPage",com.lvmama.passport.processor.impl.client.newland.model.CredentialSyncReq.class,"currentPage");
		xStream.aliasField("PageSize",com.lvmama.passport.processor.impl.client.newland.model.CredentialSyncReq.class,"pageSize");
		xStream.aliasField("LastSyncTime",com.lvmama.passport.processor.impl.client.newland.model.CredentialSyncReq.class,"lastSyncTime");
		return xStream;
	}
	
	
	/**
	 * 返回当前时间戳
	 * 
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS") ;
		String str = sdf.format(date) ;
		return str ;
	}
	/**
	 * 生成唯一编号
	 * 
	 * @return
	 */
	public static String getNewUUID() {
		String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}



    public static String getTestData(){
		Configuration configuration = Configuration.getConfiguration();

		StringBuilder data = new StringBuilder();
		InputStream in = configuration.getResourceAsStream("/data.properties");
		InputStreamReader inRead = null;
		BufferedReader readBuf = null;
		OutputStreamWriter writer = null;
		FileOutputStream fout = null;
		try {
			inRead = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码
			String inStr;

			// 组合控制台输出信息字符串
			readBuf = new BufferedReader(inRead);
			while ((inStr = readBuf.readLine()) != null) {
				data.append(inStr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (inRead != null) {
					inRead.close();
				}
				if (readBuf != null) {
					readBuf.close();
				}
				if (writer != null)
					writer.close();
				if (fout != null)
					fout.close();
			} catch (Exception e) {

			}
		}
		return data.toString();
    }
	public static void main(String[] args) {
		Configuration configuration = Configuration.getConfiguration();

		StringBuilder data = new StringBuilder();
		InputStream in = configuration.getResourceAsStream("/data.properties");
		InputStreamReader inRead = null;
		BufferedReader readBuf = null;
		OutputStreamWriter writer = null;
		FileOutputStream fout = null;
		try {
			inRead = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码
			String inStr;

			// 组合控制台输出信息字符串
			readBuf = new BufferedReader(inRead);
			while ((inStr = readBuf.readLine()) != null) {
				data.append(inStr);
			}
			String s = data.toString();
			System.out.println(s);
			//Request response=StringUtil.getRequest(s);

			OrderResend response=NewlandUtil.getResendSmsReq(data.toString());
			System.out.println(response.getOrders().get(0).getOrderId());
			
//			byte[] bs=Base64.decode(s);
//			System.out.println(s.getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (inRead != null) {
					inRead.close();
				}
				if (readBuf != null) {
					readBuf.close();
				}
				if (writer != null)
					writer.close();
				if (fout != null)
					fout.close();
			} catch (Exception e) {

			}
		}
		
	}
}
