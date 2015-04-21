package com.lvmama.search.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.SearchConstants.FILTER_PARAM_TYPE;
import com.lvmama.comm.utils.StringUtil;

/**
 * URL生成工具
 * 
 * @author YangGan
 *
 */
public class FilterParamUtil {
	
	
	public static String initPageURL(String filterStr){
		String res = initURL(filterStr, "P", "{p}", true, true, true, false);
		res = res.equals("")? "":"-"+res;
		return res;
	}
	
	public static String initURLRepeat(String filterStr,String paramTypes,String paramVals,boolean singleModel,boolean with_p,boolean with_s,boolean remove){
		String[] pts = paramTypes.split(",");
		String[] pvs;
		String fs = filterStr;
		if(remove){
			for(int i=0;i<pts.length;i++){
				fs = initURL(fs, pts[i], "", singleModel, with_p, with_s, remove);
			}
		}else{
			pvs = paramVals.split(",");
			for(int i=0;i<pts.length;i++){
				String val;
				if(pvs.length == 1 ){
					val = pvs[0];
				}else if(pvs.length == pts.length){
					val = pvs[i];
				}else{
					throw new RuntimeException(" paramTypes size is not equals paramVals size!!!");
				}
				boolean r = remove;
				if("remove".equals(val)){
					r = true;
				}
				fs = initURL(fs, pts[i], val, singleModel, with_p, with_s, r);
			}
		}
		return fs;
	}
	/**
	 * 生成URL
	 * @param filterStr 原始的参数
	 * @param paramType 替换的参数类型
	 * @param paramVal 参数值
	 * @param singleModel 单选模式
	 * @param with_p 是否包含分页信息
	 * @param with_s 是否包含排序信息
	 * @return
	 */
	public static String initURL(String filterStr,String paramType,Object paramVal,boolean singleModel,boolean with_p,boolean with_s,boolean remove){
		if(!remove){
			paramVal = CommonUtil.codeParams(paramVal.toString());
			//替换vaue中的大写字母为 \A
			Matcher m1=Pattern.compile("([A-Z])").matcher(paramVal.toString());
			List<String> replacedList = new ArrayList<String>();
			while(m1.find()){
				String f = m1.group(1);
				if(!replacedList.contains(f)){
					paramVal = paramVal.toString().replaceAll(f, "/"+f);
					replacedList.add(f);
				}
			}
			String param = paramType + paramVal;
//			if(filterStr.indexOf(param) == -1 ){
				StringBuffer regex = new StringBuffer("(?<!/)");
				if(singleModel){//单选模式
					regex.append(paramType);
					regex.append(SearchConstants.FILTER_PARAM_REGEX);
					Pattern p = Pattern.compile(regex.toString());
					Matcher m=p.matcher(filterStr); 
					if(m.find()){
						filterStr = filterStr.replaceAll(regex.toString(), param);
					}else{
						singleModel = false;
					}
				}
				
				if(!singleModel){//多选模式
					List<String> list = new ArrayList<String>();
					Pattern p2 = Pattern.compile("(([A-Z])("+SearchConstants.FILTER_PARAM_REGEX+"))");
					Matcher m2 =p2.matcher(filterStr);
					while(m2.find()){
						list.add(m2.group(1));
					}
					list.add(param);
					Collections.sort(list);
					StringBuffer sbres = new StringBuffer();
					for(String s:list){
						sbres.append(s);
					}
					filterStr = sbres.toString();
				}
//			}
		}else{
			if(singleModel || paramVal==null || paramVal == null || StringUtil.isEmptyString(paramVal.toString())){
				filterStr = filterStr.replaceAll("(?<!/)"+paramType+SearchConstants.FILTER_PARAM_REGEX, "");
			}else{
				paramVal = CommonUtil.codeParams(paramVal.toString());
				filterStr = filterStr.replaceAll(paramType+paramVal, "");
			}
		}
		if(!with_p){
			filterStr = filterStr.replaceAll(FILTER_PARAM_TYPE.P+SearchConstants.FILTER_PARAM_REGEX_2, "");
		}
		if(!with_s){
			filterStr = filterStr.replaceAll(FILTER_PARAM_TYPE.S+SearchConstants.FILTER_PARAM_REGEX_2, "");
		}
		return filterStr;
	}
}
