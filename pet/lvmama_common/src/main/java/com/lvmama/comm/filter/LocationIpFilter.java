package com.lvmama.comm.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.ComIps;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.ComIpsAreaData;
import com.lvmama.comm.vo.Constant;

public class LocationIpFilter implements Filter {
	private Log log = LogFactory.getLog(this.getClass());
	private ComIpsAreaData comIpsAreaData;
	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		comIpsAreaData = ComIpsAreaData.getInstance();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
		    String ip = req.getHeader("X-Real-IP");
			if (ip == null) {
				ip = req.getRemoteAddr();
			}
 			Cookie cookieAreaLocation = ServletUtil.getCookie(req, Constant.IP_AREA_LOCATION);
			Cookie cookieIpLocation = ServletUtil.getCookie(req, Constant.IP_LOCATION);
			Cookie fromPlaceIdCookie = ServletUtil.getCookie(req, Constant.IP_FROM_PLACE_ID);
			//loger.info("ipEEEEEE cookieIpLocation "+cookieIpLocation + ";ipcookieAreaLocation is" + cookieAreaLocation + ";ipfromPlaceIdCookie is" + fromPlaceIdCookie);
			
			String changeIp = "N";
			String location = null;
			Long fromPlaceId = null;
			String fromPlaceName = null;
			if (cookieIpLocation == null || (!ip.equals(cookieIpLocation.getValue())) || cookieAreaLocation == null || fromPlaceIdCookie == null) {
				changeIp = "Y";
				
				String ipProvincePlaceId = "";
                String ipCityPlaceId = "";
                String ipCityName="";
                
				ComIps comIpsArea = comIpsAreaData.selectComIpsAreaByIp(ip);
				if (comIpsArea != null) {
					location = comIpsArea.getAreaLocation();
					fromPlaceId = comIpsArea.getPlaceId();
					ipProvincePlaceId = StringUtils.defaultString(comIpsArea.getCapitalId()).trim();//ip所在的省
					ipCityPlaceId = StringUtils.defaultString(comIpsArea.getCityId()).trim();//ip所在的市
					ipCityName=StringUtils.defaultString(comIpsArea.getCityName()).trim();//ip所在的市名字
   				}
				if (location == null) {
					location = "SH";
				}
				if (fromPlaceId == null) {
					fromPlaceId = 79L;
					fromPlaceName = "上海";
				} else {
				    fromPlaceName = ComIpsAreaData.getPlaceName(fromPlaceId);
					if (fromPlaceName == null) {
						fromPlaceName = "上海";
					}
				}
			 
				req.setAttribute(Constant.IP_AREA_LOCATION, location);
				req.setAttribute(Constant.IP_FROM_PLACE_ID, fromPlaceId);
				req.setAttribute(Constant.IP_FROM_PLACE_NAME, fromPlaceName);
				req.setAttribute(Constant.IP_PROVINCE_PLACE_ID, ipProvincePlaceId);
				req.setAttribute(Constant.IP_CITY_PLACE_ID, ipCityPlaceId);
 				req.setAttribute(Constant.IP_CITY_NAME, ipCityName);
 				
				ServletUtil.addCookie(res, Constant.IP_FROM_PLACE_ID, fromPlaceId.toString(), 30);
				ServletUtil.addCookie(res, Constant.IP_FROM_PLACE_NAME, URLEncoder.encode(fromPlaceName,"utf-8"), 30);
				ServletUtil.addCookie(res, Constant.IP_AREA_LOCATION, location, 30);
				ServletUtil.addCookie(res, Constant.IP_LOCATION, ip, 30);
				ServletUtil.addCookie(res, Constant.IP_PROVINCE_PLACE_ID, ipProvincePlaceId, 30);
				ServletUtil.addCookie(res, Constant.IP_CITY_PLACE_ID, ipCityPlaceId, 30);
				ServletUtil.addCookie(res, Constant.IP_CITY_NAME, URLEncoder.encode(ipCityName,"utf-8"), 30);
				
 
				//loger.info("ip location "+location + "IPfromPlaceId is" + fromPlaceId);
			} else {
				req.setAttribute(Constant.IP_AREA_LOCATION, ServletUtil.getCookie(req, Constant.IP_AREA_LOCATION).getValue());
				req.setAttribute(Constant.IP_FROM_PLACE_ID, Long.valueOf(ServletUtil.getCookie(req, Constant.IP_FROM_PLACE_ID).getValue()));
                if (ServletUtil.getCookie(req, Constant.IP_FROM_PLACE_NAME) != null) {
                    req.setAttribute(Constant.IP_FROM_PLACE_NAME, URLDecoder.decode(ServletUtil.getCookie(req, Constant.IP_FROM_PLACE_NAME).getValue(),"utf-8")); 
                } else {
                    req.setAttribute(Constant.IP_FROM_PLACE_NAME, "上海"); 
                }
                if (ServletUtil.getCookie(req, Constant.IP_CITY_NAME) != null) {
                    req.setAttribute(Constant.IP_CITY_NAME, URLDecoder.decode(ServletUtil.getCookie(req, Constant.IP_CITY_NAME).getValue(),"utf-8")); 
                } else {
                    req.setAttribute(Constant.IP_CITY_NAME, "上海"); 
                }
                if (ServletUtil.getCookie(req, Constant.IP_PROVINCE_PLACE_ID) != null) {
                    req.setAttribute(Constant.IP_PROVINCE_PLACE_ID, ServletUtil.getCookie(req, Constant.IP_PROVINCE_PLACE_ID).getValue());
                } else {
                    req.setAttribute(Constant.IP_PROVINCE_PLACE_ID, "");
                }
                if(ServletUtil.getCookie(req, Constant.IP_CITY_PLACE_ID) != null) {
                	req.setAttribute(Constant.IP_CITY_PLACE_ID, ServletUtil.getCookie(req, Constant.IP_CITY_PLACE_ID).getValue());
                } else {
                    req.setAttribute(Constant.IP_CITY_PLACE_ID, "");
                }
				if (ServletUtil.getCookie(req, Constant.DEFAULT_PROVINCE_PLACE_ID) != null) {
				    req.setAttribute(Constant.DEFAULT_PROVINCE_PLACE_ID, ServletUtil.getCookie(req, Constant.DEFAULT_PROVINCE_PLACE_ID).getValue());
				}
				if (ServletUtil.getCookie(req, Constant.DEFAULT_CITY_PLACE_ID) != null) {
				    req.setAttribute(Constant.DEFAULT_CITY_PLACE_ID, ServletUtil.getCookie(req, Constant.DEFAULT_CITY_PLACE_ID).getValue());
				}
				//loger.info("ipEEEEEE location "+location + "IPfromPlaceId is" + fromPlaceId);
			}
			
			req.setAttribute(Constant.IP_IF_CHANGED, changeIp);
		} catch (Exception e) {
			log.error(" captured Exception error ref url: " + req.getHeader("referer"));
			log.error(" captured Exception error url: " + req.getRequestURL().toString());
			e.printStackTrace();
		}
		chain.doFilter(req, res);
	}
}
