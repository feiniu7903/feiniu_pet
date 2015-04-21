/**
 * 
 */
package com.lvmama.comm.utils.pdf;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 模板的内容
 * 
 * @author yangbin
 *
 */
public class ContentTemplate {

	private String ftl;
	private Template template;
	private Configuration cfg;
	private Map<String,Object> map=new HashMap<String, Object>();
	
	public ContentTemplate(Configuration cfg,String ftl)throws IOException
	{
		this.cfg=cfg;
		this.ftl=ftl;
		init();
	}	
	
	private void init()throws IOException
	{		
		cfg.setEncoding(Locale.CHINA, "UTF-8");
		cfg.setDefaultEncoding("UTF-8");
		template=cfg.getTemplate(ftl);
	}
	
	/**
	 * 添加需要在模板当中的变量信息
	 * @param key
	 * @param value
	 */
	public void put(String key,Object value)
	{
		map.put(key, value);
	}
	
	
	public String build()throws IOException
	{
		try
		{
			StringWriter sw=new StringWriter();
			template.process(map, sw);
			String buffer=sw.toString();
			return buffer;
		}catch(TemplateException ex)
		{
			logger.warning("生成模板内容错误"+ex.getMessage());
			return null;
		}
	}
	
	private Logger logger=Logger.getLogger("com.lvmama.pdf.ContentTemplate");
	
	public String toString()
	{		
		return "ContentTemplate::"+ftl; 
	}
}
