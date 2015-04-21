package com.lvmama.comm.utils.freemarker;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;

/** 
 * 增加自定义业务标签，此list中的string必须继承于TemplateDirectiveModel，并且配置在SPRING上下文中 
 */ 
public class CustomFreemarkerManager extends FreemarkerManager { 
 
    protected Configuration createConfiguration(ServletContext servletContext) throws TemplateException { 
        Configuration cfg = super.createConfiguration(servletContext); 
       ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
       Map<String,TemplateDirectiveModel> beans=(Map<String,TemplateDirectiveModel>)ctx.getBeansOfType(TemplateDirectiveModel.class); 
        for (String key : beans.keySet()) { 
        	TemplateDirectiveModel bean = beans.get(key); 
        	cfg.setSharedVariable(key, bean);   
        } 
        return cfg; 
    } 
 
 
} 
