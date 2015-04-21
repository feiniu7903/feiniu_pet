package com.lvmama.comm.utils;

import java.io.InputStream;
import java.io.Reader;

import org.apache.commons.digester.Digester;
/**
 * XML解析工具类
 * @author ranlongfei 2012-6-20
 * @version
 */
public class XmlToBeanUtil {

	/**
	 * 将XML文件流解析为对象
	 * 
	 * @author: ranlongfei 2012-6-20 下午06:52:06
	 * @param digester
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Object parseXml(Digester digester, InputStream is) throws Exception {
		digester.setValidating(false);
		return digester.parse(is);
	}
	/**
	 * 根据digester定义的模板解析XML流
	 * 
	 * @author: ranlongfei 2012-6-21 上午11:01:20
	 * @param digester
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Object parseXml(Digester digester, Reader is) throws Exception {
		digester.setValidating(false);
		return digester.parse(is);
	}
}
