package com.lvmama.comm.utils;

import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 公用头文件
 * @author Administrator
 *
 */
public final class CommHeaderUtil {
	private static Configuration configuration = CommHeaderUtil.getConfiguration();
	
	private static Configuration getConfiguration() {
		Configuration configuration  =new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setOutputEncoding("UTF-8");
		configuration.setNumberFormat("###");
		configuration.setClassicCompatible(true);
		configuration.setClassForTemplateLoading(CommHeaderUtil.class, "/template");
		return configuration;
	}
	
	/**
	 * 获取头文件内容
	 * @return
	 */
	public static void getHeadContent(Writer out) {
		try {
			Template template = configuration.getTemplate("header.htm");
			template.process(null, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
