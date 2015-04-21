package com.lvmama.passport.processor.impl.client.gmedia;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.passport.processor.impl.client.gmedia.model.Body;
import com.lvmama.passport.processor.impl.client.gmedia.model.Gts;
import com.lvmama.passport.processor.impl.client.gmedia.model.Mo;
import com.lvmama.passport.processor.impl.client.gmedia.model.Request;
import com.lvmama.passport.processor.impl.client.gmedia.model.Response;
import com.lvmama.passport.utils.Configuration;
import com.lvmama.passport.utils.WebServiceConstant;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
public class GmediaUtil {
	private  static final Log log = LogFactory.getLog(GmediaUtil.class);
	public static Properties properties=null;

	/**
	 * 转换请求参数为类
	 * @param XmlRequest
	 * @return
	 */
	public static Body getResponseBody(String xmlResponse) {
		byte[] bs=Base64.decode(xmlResponse);
		byte[] bytes =DES3.decryptMode(WebServiceConstant.getProperties("gmedia_key").getBytes(), bs);
		XStream xstream = getBodyXstreamObject();
		Body body=null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("Gmedia Response Message Body :"
						+ new String(bytes, "UTF-8"));
			}
			body = (Body) xstream.fromXML(new String(bytes,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return body;
	}
	/**
	 * 转换请求参数为类
	 * @param XmlRequest
	 * @return
	 */
	public static Body getRequestBody(String xmlResponse) {
		byte[] bs=Base64.decode(xmlResponse);
		byte[] bytes =DES3.decryptMode(WebServiceConstant.getProperties("gmedia_key").getBytes(), bs);
		XStream xstream = getBodyXstreamObject();
		Body body=null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("Gmedia Request Message Body :"
						+ new String(bytes, "UTF-8"));
			}
			body = (Body) xstream.fromXML(new String(bytes,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return body;
	}
	/**
	 * 解析响应数据转化为对象
	 * @param XmlRequest
	 * @return
	 */
	public static Response getResponse(String xmlResponse){
		XStream xstream = getResponseXstreamObject();
		Response obj = (Response) xstream.fromXML(xmlResponse);
		return obj;
	}
	
	/**
	 * 解析请求数据转化为对象
	 * @param XmlRequest
	 * @return
	 */
	public static Request getRequest(String xmlRequest){
		XStream xstream =getRequestXstreamObject();
		Request obj = (Request) xstream.fromXML(xmlRequest);
		return obj;
	}
	/**
	 * 解析成人数和儿童数数据转化为对象
	 * @param XmlRequest
	 * @return
	 */
	
	public static Gts getGts(String xmlRequest){
		XStream xstream =getGtsXstreamObject();
		Gts obj = (Gts) xstream.fromXML(xmlRequest);
		return obj;
	}
	/**
	 * 解析成人数和儿童数数据转化为对象
	 * @param XmlRequest
	 * @return
	 */
	
	public static Mo getMo(String xmlRequest)throws Exception {
		XStream xstream =getMoXstreamObject();
		Mo obj = (Mo) xstream.fromXML(xmlRequest);
		return obj;
	}
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getResponseXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("Response",com.lvmama.passport.processor.impl.client.gmedia.model.Response.class);
		xStream.alias("Head",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class);
		/** *****定义类中属性********** */
		xStream.aliasField("Body",com.lvmama.passport.processor.impl.client.gmedia.model.Response.class,"body");
		xStream.aliasField("Head",com.lvmama.passport.processor.impl.client.gmedia.model.Response.class,"head");
		xStream.aliasField("Version",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"version");
		xStream.aliasField("SequenceId",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"sequenceId");
		xStream.aliasField("Signed",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"signed");
		xStream.aliasField("Message",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"message");
		xStream.aliasField("StatusCode",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"statusCode");
		xStream.aliasField("PartnerCode",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"partnerCode");
		return xStream;
	}
	
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getRequestXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("Request",com.lvmama.passport.processor.impl.client.gmedia.model.Request.class);
		xStream.alias("Head",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class);
		/** *****定义类中属性********** */
		xStream.aliasField("Body",com.lvmama.passport.processor.impl.client.gmedia.model.Request.class,"reqBody");
		xStream.aliasField("Head",com.lvmama.passport.processor.impl.client.gmedia.model.Request.class,"head");
		xStream.aliasField("Version",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"version");
		xStream.aliasField("SequenceId",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"sequenceId");
		xStream.aliasField("Signed",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"signed");
		xStream.aliasField("Message",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"message");
		xStream.aliasField("StatusCode",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"statusCode");
		xStream.aliasField("PartnerCode",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"partnerCode");
		xStream.aliasField("CommandId",com.lvmama.passport.processor.impl.client.gmedia.model.Head.class,"commandId");
		return xStream;
	}
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getBodyXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("Body",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class);
		/** *****定义类中属性********** */
		xStream.aliasField("TimeStamp",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"timeStamp");
		xStream.aliasField("AssistCode",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"assistCode");
		xStream.aliasField("TCode",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"tCode");
		xStream.aliasField("CodeImage",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"codeImage");
		xStream.aliasField("ImageType",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"imageType");
		xStream.aliasField("Barcode",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"barcode");
		xStream.aliasField("DeviceId",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"deviceId");
		xStream.aliasField("ExtContent",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"extContent");
		xStream.aliasField("Content",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"content");
		xStream.aliasField("VoucherId",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"voucherId");
		xStream.aliasField("DealTime",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"dealTime");
		xStream.aliasField("Status",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"status");
		xStream.aliasField("ReqCount",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"reqCount");
		
		xStream.aliasField("CurrentPage",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"currentPage");
		xStream.aliasField("FetchSize",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"fetchSize");
		xStream.aliasField("Scope",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"scope");
		xStream.aliasField("LastDate",com.lvmama.passport.processor.impl.client.gmedia.model.Body.class,"lastDate");
		return xStream;
	}
	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getGtsXstreamObject(){
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("Gts",com.lvmama.passport.processor.impl.client.gmedia.model.Gts.class);
		xStream.alias("Gt",com.lvmama.passport.processor.impl.client.gmedia.model.Gt.class);
		/** *****定义类中属性********** */
		xStream.aliasField("Cn",com.lvmama.passport.processor.impl.client.gmedia.model.Gt.class,"cn");
		xStream.aliasField("Tn",com.lvmama.passport.processor.impl.client.gmedia.model.Gt.class,"tn");
		xStream.addImplicitCollection(com.lvmama.passport.processor.impl.client.gmedia.model.Gts.class, "gt");  
		return xStream;
	}

	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * @return
	 */
	private static XStream getMoXstreamObject() throws Exception {
		XStream xStream = new XStream(new DomDriver());
		/** *****定义类名******* */
		xStream.alias("Mo",com.lvmama.passport.processor.impl.client.gmedia.model.Mo.class);
		xStream.alias("Fd",com.lvmama.passport.processor.impl.client.gmedia.model.Fd.class);
		/** *****定义类中属性********** */
		xStream.aliasField("Id",com.lvmama.passport.processor.impl.client.gmedia.model.Fd.class,"id");
		xStream.aliasField("Ne",com.lvmama.passport.processor.impl.client.gmedia.model.Fd.class,"ne");
		xStream.aliasField("Ve",com.lvmama.passport.processor.impl.client.gmedia.model.Fd.class,"ve");
		xStream.addImplicitCollection(com.lvmama.passport.processor.impl.client.gmedia.model.Mo.class, "fds");  
		xStream.aliasField("Asy",com.lvmama.passport.processor.impl.client.gmedia.model.Mo.class,"asy");
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
			//Mo mo=StringUtil.getMo(data.toString());
			String temp=data.toString();
			int index=temp.indexOf("</Gts>");
			System.out.println(index);
			temp=temp.substring(0, index);
			System.out.println(temp+"</Gts>");
			//temp=temp.substring(index, temp.length()-1);
			//System.out.println(temp);
			// 解析儿童数，成人数
			//Gts gts = StringUtil.getGts(temp+"</Gts>");
			
//			String s = data.toString();
//			System.out.println(s);
//			//Request response=StringUtil.getRequest(s);
//			Response response=StringUtil.getResponse(s);
//			System.out.println(response.getBody());
//
//			Body body=StringUtil.getResponseBody(response.getBody());
//			System.out.println(body.getTimeStamp());
			
//			byte[] bs=Base64.decode(s);
		//System.out.println(mo);
			
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
