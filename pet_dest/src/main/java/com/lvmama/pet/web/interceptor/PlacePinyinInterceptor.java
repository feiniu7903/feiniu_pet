package com.lvmama.pet.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.ognl.OgnlValueStack;

public class PlacePinyinInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -1823729784664659470L;
	private PlaceService placeService;

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		Map<String, Object> param = actionContext.getParameters();
		Object pinyin = param.get("pinyin");
		Object id = param.get("id");
		
		if (pinyin == null&&id==null) {
			return invocation.invoke();
		}
		if(pinyin!=null){
			String[] pinYin = (String[]) pinyin;
			if (pinYin.length > 0 && pinYin[0] != null) {
				Place place = placeService.getPlaceByPinYinWithOutCLOB(pinYin[0]);
				processParam(place,param,actionContext);
			}
		}
		if(id!=null){
			String[] ids = (String[]) id;
			if (ids.length > 0 && ids[0] != null) {
				Place place = placeService.queryPlaceByPlaceIdWithOutCLOB(Long.parseLong(ids[0]));
				processParam(place,param,actionContext);
			}
		}

		return invocation.invoke();
	}
	private void processParam(Place place,Map<String, Object> param,ActionContext actionContext){
		if (place != null) {
			HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
			OgnlValueStack stack = (OgnlValueStack) request.getAttribute("struts.valueStack");
			stack.setValue("place", place);
			stack.setValue("id", place.getPlaceId());

			//获取指定要求的出发地， 用于SEO的TDK的不同模板
			String[] fromDestId = (String[]) param.get("fromDestId");
			if (null != fromDestId && fromDestId.length > 0) {
				stack.setValue("hasInitDestId", true);
			} else {
				stack.setValue("hasInitDestId", false);
			}
			actionContext.setValueStack(stack);
		}
	}

}
