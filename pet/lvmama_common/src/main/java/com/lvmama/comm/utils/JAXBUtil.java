package com.lvmama.comm.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
/**
 * Xml解析 
 * 将需要Xml2bean或者bean2Xml的vo添加注解才可使用
 * @author gaoxin
 *
 */
public class JAXBUtil {
	
	/**
	 * @param xml 
	 * @param clazz toBean
	 * @return
	 * @throws Exception
	 */
	public static Object xml2Bean(String xml,Class clazz) throws Exception{
		InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		JAXBContext context = JAXBContext.newInstance(clazz);
		return context.createUnmarshaller().unmarshal(is);
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static Object bean2Xml(Class clazz) throws Exception {
		JAXBContext context = JAXBContext.newInstance(clazz);
		StringWriter sw = new StringWriter();
		 Marshaller m = context.createMarshaller(); 
         m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); 
         m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8"); 
         m.marshal(clazz, sw);
		return  sw.toString();
	}
}
