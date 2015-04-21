package com.lvmama.tnt.front.home;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.tnt.front.BaseController;

/**
 * 分销平台
 * 
 * @author zenglei
 *
 */

@Controller
public class BaseHomeController extends BaseController{
	
	protected String provinceId;
	
	protected Long fromPlaceId;
	protected String fromPlaceCode;
	protected String fromPlaceName;
	protected String stationName;
	
	protected String cityId;
	protected String cityName;
	
	public void init(HttpServletRequest request,HttpServletResponse response,String channel){
		calculationForfromPlaceId(channel,request,response);
		if(fromPlaceName==null){
			fromPlaceName = PindaoPageUtils.getFromPlaceName(stationName,channel);
		}
	}	
	
	/**
	 * 处理设置fromPlaceId
	 * @return
	 * @author nixianjun 2013-12-24
	 * @throws UnsupportedEncodingException 
	 */
	protected void calculationForfromPlaceId(String channel,HttpServletRequest request,HttpServletResponse response)  {
		//切分站逻辑--请求有参数逻辑
	    if(StringUtils.isNotBlank(cityId)&&StringUtils.isNotBlank(provinceId)&&StringUtils.isNotBlank(stationName)){
			
			ServletUtil.addCookie(response, Constant.IP_CITY_PLACE_ID,
					cityId, 30);
			ServletUtil.addCookie(response, Constant.IP_PROVINCE_PLACE_ID,
					provinceId, 30);
			try {
				ServletUtil.addCookie(response, Constant.IP_CITY_NAME, URLEncoder.encode(stationName,"utf-8"), 30);
			} catch (UnsupportedEncodingException e) {
 				e.printStackTrace();
			}
		}else{
			/**
			 * 先取cookie里的值，再去请求里的值
			 */
			cityId = (String) ServletUtil.getCookieValue(request,
					Constant.IP_CITY_PLACE_ID);
			provinceId = (String) ServletUtil.getCookieValue(request,
					Constant.IP_PROVINCE_PLACE_ID);
			try {
				if(null==ServletUtil.getCookie(request, Constant.IP_CITY_NAME)){
					stationName="";
				}else{
					stationName = java.net.URLDecoder.decode(ServletUtil.getCookie(request, Constant.IP_CITY_NAME).getValue(),"utf-8");
				}
			} catch (UnsupportedEncodingException e) {
 				e.printStackTrace();
			}
			if (StringUtils.isBlank(cityId)) {
				cityId = (String) request.getAttribute(
						Constant.IP_CITY_PLACE_ID);
			}
			if (StringUtils.isBlank(provinceId )) {
				provinceId = (String) request.getAttribute(
						Constant.IP_PROVINCE_PLACE_ID);
			}
			if(StringUtils.isBlank(stationName)){
				stationName= (String) request.getAttribute(
						Constant.IP_CITY_NAME);
			}
		}
	}
}
