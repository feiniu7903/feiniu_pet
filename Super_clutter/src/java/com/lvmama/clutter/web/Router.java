package com.lvmama.clutter.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.commons.beanutils.BeanUtils;
import javax.swing.text.InternationalFormatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.ResultPath;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.exception.NoDataException;
import com.lvmama.clutter.exception.NotFoundException;
import com.lvmama.clutter.exception.ValidateArgException;
import com.lvmama.clutter.web.place.AppBaseAction;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@ParentPackage("logicRouterInterceptorPackage")
@ResultPath("/logicRouterInterceptor")
public class Router extends AppBaseAction{
	private static final Log log = LogFactory.getLog(Router.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 8633301531899559748L;
	
	private String method;//com.user.getUser
	private String format;
	/**
	 * 为授权api做的预留参数
	 */
	private String appKey;
	private String appSercurt;
	private String accessToken;
	private String lvversion;// 版本
	private String beanName;
	private String targetMethod;
	private String latitude;// 经纬度 
	private String longitude; // 经纬度 

	public static enum CLIENT_ERROR_CODE {
		NO_PRODUCT("-2"),//无产品或者产品不可售
		NO_DATA("-3"),//无数据
		VALIDATE_ARG_ERROR("-4");//验证参数失败
		private String cnName;
		CLIENT_ERROR_CODE(String name){
			this.cnName=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getCnName(){
			return this.cnName;
		}
		public static String getCodeName(String code){
			for(CLIENT_ERROR_CODE item:CLIENT_ERROR_CODE.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}
		public String toString(){
			return this.name();
		}
	}

	
	@Action(value="/router/rest", interceptorRefs = {
			@InterceptorRef(value = "logicRouterInterceptor"),
			@InterceptorRef(value = "defaultStack") })
	public void rest(){
		log.info("request method:"+this.getRequest().getMethod());
		if(method!=null){
			Map<String,Object> resultMap = super.resultMapCreator();
			Object object = SpringBeanProxy.getBean(beanName);		
			if(object==null){
				throw new RuntimeException("Error : bean is null");
			}
			Object result = null;
			 Class<?> clazz = object.getClass(); 
			 Map<String,Object> requestMap = this.getRequestMap();
			 try {
				// bugs 参数修复  
				 this.repairBugs(requestMap);
				//Method m =  clazz.getDeclaredMethod(serviceMethod, Map.class);	
				Method m = this.getMethod(clazz, targetMethod, Map.class);
				result = m.invoke(object, requestMap);
				
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Throwable t = e.getTargetException();
				try {
					throw t;
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					String msg = e1.getMessage();
					if(e1 instanceof LogicException){	
						this.putLogicExceptionMessage(resultMap, msg);
					} else if(e1 instanceof NotFoundException) {
						this.putMessageWithErrorCode(resultMap, msg,CLIENT_ERROR_CODE.NO_PRODUCT.cnName);
					} else if(e1 instanceof NoDataException){
						this.putMessageWithErrorCode(resultMap, msg,CLIENT_ERROR_CODE.NO_DATA.cnName);
					} else if(e1 instanceof ValidateArgException){
						this.putMessageWithErrorCode(resultMap, msg,CLIENT_ERROR_CODE.VALIDATE_ARG_ERROR.cnName);
					} else {
						this.putExceptionMessage(resultMap, msg);
					}
					log.error("...request paramters:"+requestMap.toString());
					//e1.printStackTrace();
				}
				
			
			}  finally {
				if("xml".equals(format)){
						this.objectToXml(resultMap, result);
				} else {
					/**
					 * 检测版本
					 */
					if(requestMap.get("checkVersion")!=null){
						this.sendResultV3(resultMap, result,true);
					}
					if(requestMap.get("queryType")!=null&&result instanceof List){
						if("count".equals(requestMap.get("queryType"))){
							List<?> list = (List<?>)result;
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("count", list.size());
							this.sendResultV3(resultMap, map,false);
						} else {
							this.sendResultV3(resultMap, result,false);
						}
					} else {
						this.sendResultV3(resultMap, result,false);
					}
				}
			}
		
			
		}

		
	}
	
	/**
	 * bugs修复 
	 * @param requestMap
	 */
	private void repairBugs(Map<String, Object> requestMap) {
		if(null != requestMap 
				&& "api.com.search.placeSearch".equals(this.method) 
				&& "5.0.0".equals(this.lvversion) ) {
			requestMap.put("latitude", this.latitude);
			requestMap.put("longitude", this.longitude);
		}
	}

	@Action("/router/ztProxy")
	public void ztProxy(){
		String name = this.getRequest().getParameter("name");
		String result = HttpsUtil.requestGet("http://m.lvmama.com/static/zt/3.0.0/"+name+".html");
		this.sendAjaxMsg(result);
	}
	
	protected Map<String,Object> getRequestMap(){
		UserUser user = this.getUser();
		//error:No modifications are allowed to a locked ParameterMap
		@SuppressWarnings("unchecked")
		Map<String, Object> parameterMap = new HashMap<String, Object>(super.getRequest().getParameterMap());
		Map<String,Object> map = new HashMap<String,Object>();
		//解决单参数和数组参数的问题
		for(Entry<String, Object> entry:parameterMap.entrySet()){
			Object objValue = entry.getValue();
			if(objValue instanceof String){
				String value = (String) objValue;
				if(!StringUtils.isEmpty(value)){
					map.put(entry.getKey(), (String)entry.getValue());
				}else{
					map.put(entry.getKey(), null);
				}
				
			}else{
				String[] values = (String[])objValue;
				if(values.length>1){
					map.put(entry.getKey(),values);
				}else{
					if(!StringUtils.isEmpty(values[0])){
						map.put(entry.getKey(), values[0]);
					}else{
						map.put(entry.getKey(), null);
					}
				}
			}
		}
		if(user!=null){
			map.put("userId", user.getId());
			map.put("userNo", user.getUserNo());
		}
		lvversion = (String) map.get("lvversion");
		
		Long appVersion=0L;
		if(map.get("lvversion")!=null){
			appVersion = Long.valueOf(lvversion.replaceAll("[.]", ""));
		}
//		/**
//		 * 处理用户输入的关键字 最多60个字符
//		 */
//		if(map.get("keyword")!=null){
//			map.put("keyword", StringUtil.subStringStrNoSuffix(map.get("keyword").toString(), 60));
//		}
//		if(map.get("toDest")!=null){
//			map.put("toDest", StringUtil.subStringStrNoSuffix(map.get("toDest").toString(), 60));
//		}
		map.put("appVersion", appVersion);
		map.put("isAndroid", this.isAndroid());
		map.put("isIphone", this.isIPhone());
		map.put("userAgent",this.getRequest().getHeader("User-Agent"));
		String ip = InternetProtocol.getRemoteAddr(getRequest());
		log.info("x-for-ward :"+ip);
		if(StringUtil.isNotEmptyString(ip)&&InternetProtocol.isInnerIP(ip)){
			ip = getRequest().getRemoteAddr();
			log.info("servlet remoteAddr :"+ip);
		}
		map.put("ip",InternetProtocol.getRemoteAddr(getRequest()));
		log.debug("request paramters:"+map.toString());
		return map;	
	}
	
	/** 
	* 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。 
	* 
	* @param clazz 
	* 目标类 
	* @param methodName 
	* 方法名 
	* @param parameterTypes 
	* 方法参数类型 
	* @return 方法对象 
	 * @throws NoSuchMethodException 
	*/ 
	public  Method getMethod(Class<?> clazz, String methodName, 
	final Class<?> ... parameterTypes) throws NoSuchMethodException { 
		Method method = null; 
		try { 
			method = clazz.getDeclaredMethod(methodName, parameterTypes); 
		} catch (NoSuchMethodException e) { 
			if (clazz.getSuperclass() == null) { 
				throw new NoSuchMethodException();
			} else { 
				method = getMethod(clazz.getSuperclass(), methodName, parameterTypes); 
			} 
		} 
		return method; 
	} 

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSercurt() {
		return appSercurt;
	}

	public void setAppSercurt(String appSercurt) {
		this.appSercurt = appSercurt;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getLvversion() {
		return lvversion;
	}



	public void setLvversion(String lvversion) {
		this.lvversion = lvversion;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getTargetMethod() {
		return targetMethod;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
