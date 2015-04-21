package com.lvmama.finance.base.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.finance.base.Constant;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.PageSearchContext;
import com.lvmama.finance.base.annotation.PageSearch;
import com.lvmama.finance.base.annotation.Version;

/**
 * 方法拦截器
 * 
 * @author yanggan
 * 
 */
@Aspect
@Component
public class ActionAspect {

	@Pointcut("execution(* com.lvmama.finance..*Action.*(..))")
	public void myCut() {
	}

	@Around("myCut()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
		Method method = joinPointObject.getMethod();

		PageSearch pageSearch = method.getAnnotation(PageSearch.class);
		// 是否是分页查询
		if (pageSearch != null) {
			PageSearchContext pageSearchContext = this.initPageSearchContext(pageSearch.autobind());
			FinanceContext.putValue("PageSearchContext", pageSearchContext);
		}
		Object[] args = pjp.getArgs();
		Boolean hasModel = false;
		for(Object o:args){
			if(o != null){
				if(BindingAwareModelMap.class.getName().equals(o.getClass().getName())){
					Model model = (Model)o;
					FinanceContext.putValue("model", model);
					hasModel = true;
				}
			}
		}
		Version version = method.getAnnotation(Version.class);
		Object res = null;
		if( version != null){
			try{
				res = pjp.proceed();
			}catch(UncategorizedSQLException e){
				if(e.getSQLException().getErrorCode() == 20000){
					if(hasModel == false){
						throw new RuntimeException("@Version配置错误，没有发现Model参数！");
					}
					Model model = FinanceContext.getModel();
					model.addAttribute("version_error", true);
				}
			}
		}else{
			res = pjp.proceed();
		}
		
		return res;
	}

	/**
	 * 从上下文中读取分页查询信息
	 * 
	 * @return
	 */
	private PageSearchContext initPageSearchContext(boolean autobind) {
		Map<String, Object> context = new HashMap<String, Object>();
		Map<String, Object> pageinfo = new HashMap<String, Object>();
		if (autobind) {
			Map<String, String[]> tmp = FinanceContext.getRequest().getParameterMap();
			for (Map.Entry<String, String[]> entry : tmp.entrySet()) {
				String key = entry.getKey();
				String values = null;
				String[] value = entry.getValue();
				if (value.length > 1) {
					StringBuffer strbuf = new StringBuffer();
					for (int s = 0; s < value.length; s++) {
						strbuf.append(",").append(value[s]);
					}
					strbuf.deleteCharAt(0).toString();
					values = strbuf.toString();
				} else {
					values = value[0];
				}
				if (key.equals(Constant.PAGE_CURRPAGE) || key.equals(Constant.PAGE_PAGESIZE)) {
					pageinfo.put(key, Integer.parseInt(values));
				} else if (key.equals(Constant.PAGE_ORDER) || key.equals(Constant.PAGE_ORDERBY)) {
					pageinfo.put(key, values);
				} else {
					context.put(key, values);
				}
			}
		}
		Integer currpage = null;
		int pagesize = 10;
		String p2 = FinanceContext.getRequest().getParameter(Constant.PAGE_PAGESIZE);
		if (!StringUtil.isEmptyString(p2)) {
			pagesize = Integer.parseInt(p2);
		}
		String p1 = FinanceContext.getRequest().getParameter(Constant.PAGE_CURRPAGE);
		if (StringUtil.isEmptyString(p1)) {
			currpage = 1;
		} else {
			currpage = Integer.parseInt(p1);
		}
		int start = ((currpage - 1) * pagesize) + 1;
		int end = currpage * pagesize;
		pageinfo.put(Constant.PAGE_START, start);
		pageinfo.put(Constant.PAGE_END, end);
		PageSearchContext pageSearchContext = new PageSearchContext();
		pageSearchContext.setContext(context);
		pageSearchContext.setPageinfo(pageinfo);
		return pageSearchContext;
	}
}
