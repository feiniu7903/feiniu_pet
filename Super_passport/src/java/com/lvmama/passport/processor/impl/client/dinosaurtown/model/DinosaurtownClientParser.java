package com.lvmama.passport.processor.impl.client.dinosaurtown.model;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

import com.lvmama.passport.utils.Configuration;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DinosaurtownClientParser {
	private static Properties properties = null;

	/**
	 * 获取配置文件属性
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfigByKey(String key) {
		String value = "";
		Configuration configuration = Configuration.getConfiguration();
		if (properties == null) {
			properties = configuration.getConfig("/perform.properties");
		}
		value = properties.getProperty(key);
		return value.trim();
	}

	/**
	 * 申请码响应对象
	 * 
	 * @param XmlRequest
	 * @return
	 */
	public static Response getResponse(String xmlResponse) {
		XStream xstream = getResponseXstreamObject();
		Response obj = (Response) xstream.fromXML(xmlResponse);
		return obj;
	}

	/**
	 * 初始化xml配置，并返回效应请求XStream对象
	 * 
	 * @return
	 */
	private static XStream getResponseXstreamObject() {
		XStream xStream = new XStream(new DomDriver());
		xStream.registerConverter(new OrderConverter());
		xStream.alias("Response", com.lvmama.passport.processor.impl.client.dinosaurtown.model.Response.class);
		return xStream;
	}

	/**
	 * 解析Xml
	 * 
	 * @author chenlinjun
	 * 
	 */
	private static class OrderConverter implements Converter {

		public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
		}

		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			Response response = new Response();
			Order order = new Order();
			reader.moveDown();
			order.setNo(reader.getAttribute("No"));
			reader.moveDown();
			Ticket ticket = new Ticket();
			ticket.setNo(reader.getAttribute("No"));
			ticket.setName(reader.getAttribute("Name"));
			ticket.setHasChild(reader.getAttribute("HasChild"));
			ticket.setHasMan(reader.getAttribute("HasMan"));
			order.setTicket(ticket);
			response.setOrder(order);
			return response;
		}

		@SuppressWarnings("rawtypes")
		public boolean canConvert(Class clazz) {
			return clazz.equals(com.lvmama.passport.processor.impl.client.dinosaurtown.model.Response.class);
		}
	}
	/**
	 * 检测用户
	 * @param user
	 * @param check
	 * @return
	 */
	public static boolean validate(String user, String check){
		boolean falg=getConfigByKey("dinosaurtown_user").trim().equals(user.trim())&&getConfigByKey("dinosaurtown_MD5").trim().equals(check.trim());
		return falg;
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
			//log.info("dsfsdfsdf" + response.getOrder().getTicket().getName());
			// VerifyReq response=StringUtil.getVerifyReq(s);
			// System.out.println(response.getGoodsRecord().getPGoodsList().getPgoods_id());
			// System.out.println(response.getCredential_class());

			// byte[] bs=Base64.decode(s);
			// System.out.println(s.getBytes());

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
