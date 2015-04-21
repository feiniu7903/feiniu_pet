/**
 * 驴妈妈首页
 */
package com.lvmama.front.web.home;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.vo.HomePageLastestOrder;
import com.lvmama.comm.utils.HttpRequestDeviceUtils;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.homePage.HomePageUtils;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.vo.Constant;


@Results({
	@Result(name = "success", location = "/WEB-INF/pages/www/homepage.ftl", type = "freemarker"),
 	@Result(name = "wap", location = "http://m.lvmama.com", type = "redirect")
})
public class HomeAction extends ChannelBaseHomeAction {
	private static final long serialVersionUID = -1814572599244208664L;
	private Long commonBlockId = PindaoPageUtils.HOME_COMMONBLOCKID;
	private String channelPage =PindaoPageUtils.HOME_CHANNELPAGE;
	private String containerCode = PindaoPageUtils.HOME_CONTAINERCODE;
	
	
	@Action("/homePage/homeAction")
	public String new_execute() {
		if (null == this.getCookieValue("wap_to_lvmama")&&HttpRequestDeviceUtils.isMobileDevice(getRequest())) {
			return "wap";
		}
		this.initExcute(Constant.CHANNEL_ID.CH_INDEX.name());
		
		if (null != fromPlaceId && fromPlaceId.longValue() != DEFAULT_FROM_PLACE_ID.longValue()) {
			//如果出发地已经是上海，则不需要使用默认出发地当做备用字段
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, DEFAULT_FROM_PLACE_ID);
		} else {
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, null);
		}
		
 		return "success";
	}
	
	@Action("/homePage/getLastOrderData")
	public void getLastOrderData () {
		Map<String, String> m=new HashMap<String, String>();
		HomePageLastestOrder homePageLastestOrder  =(HomePageLastestOrder) MemcachedUtil.getInstance().get("HOME_PAGE_LASTEST_ORDER");
		if( null != homePageLastestOrder && homePageLastestOrder.getAllNotEmpty()){
			m.put("success", "true");
			m.put("message", homePageLastestOrder.toString());
			sendAjaxResultByJson(JSONObject.fromObject(m).toString());
		}else{
			m.put("success", "false");
 			sendAjaxResultByJson(JSONObject.fromObject(m).toString());

		}
	}
	/**
	 * 初始化最新订单信息
	 * @return
	 * @author:nixianjun 2013-6-19
	 */
	public String getInitLastOrderData(){
 		HomePageLastestOrder homePageLastestOrder  =(HomePageLastestOrder) MemcachedUtil.getInstance().get("HOME_PAGE_LASTEST_ORDER");
		if( null != homePageLastestOrder && homePageLastestOrder.getAllNotEmpty()){
 			 return  homePageLastestOrder.toString() ;
 		}else{
			 return  "";
		}
	}
	
	@Override
	protected void resortData(final Map<String, List<RecommendInfo>> recommendInfoMap) { 
		//限时特卖排序
		if (null != recommendInfoMap && null != recommendInfoMap.get(channelPage + "_XianShiTeMai")) {
			List<RecommendInfo> recommendInfosToTeMai = recommendInfoMap.get(channelPage + "_XianShiTeMai");
		     //限定10条
			List<RecommendInfo> showInfosToTeMai=HomePageUtils.filterDataToTeMai(recommendInfosToTeMai);
			//排序
			Collections.sort(showInfosToTeMai, new Comparator<RecommendInfo>(){
				public int compare(RecommendInfo o1, RecommendInfo o2) {
					if (Integer.parseInt(o1.getRemark()) != Integer.parseInt(o2.getRemark())) {
						if(StringUtils.isNotBlank(o1.getRemark())&&StringUtils.isNotBlank(o2.getRemark())){
							try{
								return Integer.parseInt(o1.getRemark()) >= Integer.parseInt(o2.getRemark())? 1 : -1;
							}catch(Exception e){
								return 0;
							 }
						}
					} else {
						if(StringUtils.isNotBlank(o1.getBakWord3())&&StringUtils.isNotBlank(o2.getBakWord3())){
							try{
								return Float.parseFloat(o1.getBakWord3()) >= Float.parseFloat(o2.getBakWord3()) ? 1 : -1; 
							}catch(Exception e){
								return 0;
							 }
						} 
					}
					return 1;
				}
			});
			recommendInfoMap.put(channelPage + "_XianShiTeMai", showInfosToTeMai);
		}
		
		//热销排行
		if (null != recommendInfoMap && null != recommendInfoMap.get(channelPage + "_ReXiaoPaiHang")) {
			List<RecommendInfo> recommendInfosToReXiao = recommendInfoMap.get(channelPage + "_ReXiaoPaiHang");
			//限定10条
			List<RecommendInfo> showInfosToReXiao=HomePageUtils.filterDataToReXiao(recommendInfosToReXiao);
			//排序
  			Collections.sort(showInfosToReXiao, new Comparator<RecommendInfo>(){
				public int compare(RecommendInfo o1, RecommendInfo o2) {
					if (Integer.parseInt(o1.getRemark()) != Integer.parseInt(o2.getRemark())) {
						if(StringUtils.isNotBlank(o1.getRemark())&&StringUtils.isNotBlank(o2.getRemark())){
							try{return Integer.parseInt(o1.getRemark()) >= Integer.parseInt(o2.getRemark())? 1 : -1;
							}catch(Exception e){
								return 0;
							 }
						}
					} else {
					    if(StringUtils.isNotBlank(o1.getBakWord3())&&StringUtils.isNotBlank(o2.getBakWord3())){
						 try{return (Integer.parseInt(o1.getBakWord2()) <= Integer.parseInt(o2.getBakWord2())) ? 1 : -1; 
						 }catch(Exception e){
							return 0;
						 }
						}
					}
					return 1;
				}
			});
			recommendInfoMap.put(channelPage + "_ReXiaoPaiHang", showInfosToReXiao);
		}
	}
	
	public int getTricker() {
		java.util.Random r = new java.util.Random();
		return r.nextInt(10);
	}

	public String getChannelPage() {
		return channelPage;
	}	
}
