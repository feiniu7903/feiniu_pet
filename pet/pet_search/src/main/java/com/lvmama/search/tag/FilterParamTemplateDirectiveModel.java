package com.lvmama.search.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.lvmama.search.util.FilterParamUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class FilterParamTemplateDirectiveModel  implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map paramsMap, TemplateModel[] loopVars,  
            TemplateDirectiveBody body) throws TemplateException, IOException {  
		 // 获取传递进来的参数  
        Iterator it = paramsMap.entrySet().iterator();  
        String filterStr = null;
        String type = null;
        String val =null;
        boolean remove = false;
        boolean single = true;
        boolean p = false;
        boolean s = false;
        boolean repeat = false;
        while (it.hasNext()) {  
        	Entry entry = (Entry) it.next();  
        	String paramName = entry.getKey().toString();  
            TemplateModel paramValue = (TemplateModel) entry.getValue();  
            if("filter".equalsIgnoreCase(paramName)){
            	filterStr = paramValue.toString();
            }else if("type".equalsIgnoreCase(paramName)){
            	type = paramValue.toString();
            }else if("val".equalsIgnoreCase(paramName)){
            	val = paramValue.toString();
            }else if("single".equalsIgnoreCase(paramName)){
            	single = ((TemplateBooleanModel)paramValue).getAsBoolean();
            }else if("p".equalsIgnoreCase(paramName)){
            	p = ((TemplateBooleanModel)paramValue).getAsBoolean();
            }else if("s".equalsIgnoreCase(paramName)){
            	s = ((TemplateBooleanModel)paramValue).getAsBoolean();
            }else if("remove".equalsIgnoreCase(paramName)){
            	remove = ((TemplateBooleanModel)paramValue).getAsBoolean();
            }else if("repeat".equalsIgnoreCase(paramName)){
            	repeat = ((TemplateBooleanModel)paramValue).getAsBoolean();
            }
        }
        String res ;
        if(repeat){
        	 res = FilterParamUtil.initURLRepeat(filterStr, type, val, single, p, s, remove);
        }else{
        	 res = FilterParamUtil.initURL(filterStr, type, val, single, p, s, remove);
        }
        Writer out = env.getOut();
        res = res.equals("")? "":"-"+res;
        out.write(res);
	}  

}
