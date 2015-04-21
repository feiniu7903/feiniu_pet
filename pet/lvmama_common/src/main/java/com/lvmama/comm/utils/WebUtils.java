/**
 * 
 */
package com.lvmama.comm.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;

/**
 * http请求相关操作
 * @author yangbin
 *
 */
public abstract class WebUtils {

	
	public static String getUrl(HttpServletRequest req,boolean skipPageParam,Map<String,String> parameter){
		Map<String,String> skipParam = new HashMap<String, String>();
		if(skipPageParam){
			skipParam.put("page", "page");
			skipParam.put("perPageRecord", "perPageRecord");
		}
		return initUrl( req,parameter,skipParam);
	}

	/**
	 * 跳过分页的参数
	 * @param req
	 * @return
	 */
	public static String getPageUrl(HttpServletRequest req,Map<String,String> skipParam){
		return initUrl(req,null,skipParam);
	}
	
	/**
	 * 默认的数据加入query当中
	 * @param req
	 * @param defaultV 默认要加入的内容，如果之前已经存在需要替换
	 * @return
	 */
	public static String getUrl(HttpServletRequest req,Map<String,String> defaultV){
		return getUrl(req, true,defaultV);
	}
	/**
	 * 从请求生成url地址
	 * @param req
	 * @param skipPageParam 是否跳过分页
	 * @param parameter 需要添加在后面的默认的参数列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String initUrl(HttpServletRequest req,Map<String,String> parameter,Map<String,String> skipParam){		
		StringBuffer sb=new StringBuffer();		
		Enumeration<String> it=req.getParameterNames();
		int pos=0;
		Set<String> keys=new HashSet<String>();
		while(it.hasMoreElements()){
			String key=it.nextElement();
			if (skipParam != null && skipParam.containsKey(key)){
				continue;
			}
			if(parameter!=null&&parameter.containsKey(key))//如果在默认参数列表当中已经
				continue;
			if (keys.contains(key))// 去掉重复的key
				continue;
			keys.add(key);
			String values[]=req.getParameterValues(key);
			if(!ArrayUtils.isEmpty(values)){
				for(String v:values){
					if(pos++>0){
						sb.append("&");
					}
					sb.append(key);
					sb.append("=");
					sb.append(v);
				}
			}
		}
		if(parameter!=null&&!parameter.isEmpty()){
			for(String key:parameter.keySet()){
				if(pos++>0){
					sb.append("&");
				}
				sb.append(key);
				sb.append("=");
				sb.append(parameter.get(key));
			}
		}
		if(sb.length()>0){
			sb.append("&");
		}
		sb.append("page=");
		StringBuffer uri=new StringBuffer();
		uri.append(req.getRequestURI());
		if(sb.length()>1){
			uri.append("?");
			uri.append(sb.toString());
		}
		
		//去掉url当中出现多次/的情况
		String url=uri.toString().replaceAll("/{1,}", "/");
//		if(url.startsWith("/")){
//			url=url.substring(1);
//		}
		
		return url;
		
	}
	
	/**
	 * 跳过分页的参数
	 * @param req
	 * @return
	 */
	public static String getUrl(HttpServletRequest req){
		return getUrl(req,true,null);
	}
	
	/**
	 * 
	 * @param requestURI
	 * @param skipPageParam
	 * @param param
	 * @return
	 */
	public static String getUrlByParam(String requestURI,boolean skipPageParam,Map<String,Object> param){
		StringBuffer sb=new StringBuffer();
		int pos=0;
		for(String key:param.keySet()){
			if (skipPageParam
						&& ("page".equals(key) || "perPageRecord".equals(key))){
					continue;
			}
			if(pos++>0){
				sb.append("&");
			}
			sb.append(key);
			sb.append("=");
			sb.append(param.get(key).toString());
		}
		if(sb.length()>0){
			sb.insert(0, "?");
		}
		sb.insert(0,requestURI);		
		return sb.toString();
	}
	
	
	/**
	 * 按key,value传入参数生成query字符串,数量不为偶数时去掉最后一个
	 * @param req
	 * @param fields 需要在query当中出现的kv值
	 * @return
	 */
	public static String getUrlByKeyValue(HttpServletRequest req,String...fields){
		if(fields==null||fields.length==0)
			return getUrl(req);
		
		Map<String,String> param=new HashMap<String, String>();
		for(int i=0;i<fields.length;i+=2){
			try{
				param.put(fields[i], fields[i+1]);
			}catch(ArrayIndexOutOfBoundsException ex){				
			}
		}
		return getUrl(req,true,param);
	}
}
