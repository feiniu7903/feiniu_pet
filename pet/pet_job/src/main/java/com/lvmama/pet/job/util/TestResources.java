package com.lvmama.pet.job.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.lvmama.comm.utils.StringUtil;

/**
 * 获取测试资源配置文件信息
 * 
 * @author likun
 * 
 */
public class TestResources {

	private static Logger logger = Logger.getLogger(TestResources.class);

	private static TestResources instance = new TestResources();

	public static TestResources getInstance() {
		return instance;
	}

	private TestResources() {
		init();
	}

	private Map<String, String> propertiesMap = null;

	@SuppressWarnings("rawtypes")
	private void init() {
		try {
			logger.info("加载test.properties start");
			propertiesMap = new HashMap<String, String>();
			Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("/test.properties"));
			Enumeration enumeration = properties.propertyNames();
			while (enumeration.hasMoreElements()) {
				Object object = (Object) enumeration.nextElement();
				propertiesMap.put(object.toString(),
						properties.getProperty(object.toString()));
			}
			logger.info("test.properties:" + propertiesMap);
			logger.info("加载test.properties end");
		} catch (Exception e) {
			logger.error("加载测试资源配置文件(test.properties)出错:" + e.getMessage(), e);
		}
	}

	public Map<String, String> getPropertiesMap() {
		return propertiesMap;
	}

	public String get(String key) {
		return getPropertiesMap().get(key);
	}

	/**
	 * 获取是否是测试发送邮件
	 * 
	 * @return
	 */
	public boolean emailJobIsTest() {
		return "false".equals(get("emailJob.isSend"));
	}

	/**
	 * 获取测试邮箱
	 * 
	 * @return
	 */
	public String getTestToEmail() {
		return get("emailJob.testToEmail");
	}

	/**
	 * 获取短信是否是测试发送
	 * 
	 * @return
	 */
	public boolean getSmsJobIsTest() {
		return "false".equals(get("smsJob.isSend"));
	}
	
	/**
	 * 获取所有测试的手机号码
	 * @return
	 */
	public List<String> getTestMobiles(){
		List<String> list = new ArrayList<String>();
		String s = get("smsJob.testMobiles");
		if(StringUtil.isNotEmptyString(s)){
			String [] testMobiles = s.split(";");
			for (String string : testMobiles) {
				list.add(string);
			}
		}
		return list;
	}
	/**
	 * 获取点点客短信是否是测试发送 
	 * 
	 * @author zenglei
	 */
	public boolean getDDCaSmsJobIsTest() {
		return "false".equals(get("ddcSmsJob.isSend"));
	}
	
	public static void main(String[] args) {
		// System.out.println(JSONObject.fromObject(getPropertiesMap()));
		System.out.println(TestResources.getInstance().emailJobIsTest());
		System.out.println(TestResources.getInstance().getTestToEmail());
		System.out.println(TestResources.getInstance().getSmsJobIsTest());
		System.out.println((TestResources.getInstance().getSmsJobIsTest()
				&& !TestResources.getInstance().getTestMobiles()
						.contains("1580092553227")));
	}
}
