package com.lvmama.front.web.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.utils.homePage.HomePageUtils;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.vo.Constant;

/**
 * 网站频道-出境
 * @author nixianjun 2013-6-13
 * @version 1.0
 */
@Results({
	@Result(name = "channelAbroad", location = "/WEB-INF/pages/www/channel/abroad/channelAbroad.ftl", type = "freemarker"),
	@Result(name = "recommendScenicDiv", location = "/WEB-INF/pages/www/channel/abroad/recommendScenicDiv.ftl", type = "freemarker")
 })
public class ChannelAbroadHomeAction extends ChannelBaseHomeAction{

	private static final long serialVersionUID = -154127556674799378L;
	
	//模块id
	private Long commonBlockId = PindaoPageUtils.ABROAD_COMMONBLOCKID;
	private String channelPage = PindaoPageUtils.ABROAD_CHANNELPAGE;	
	private String containerCode =PindaoPageUtils.ABROAD_CONTAINERCODE ;
	
	
	
	@Action("/homePage/channelAbroadAction")
	public String execute() {
		//初始化
 		this.initExcute(Constant.CHANNEL_ID.CH_ABROAD.name());
		
		//获取推荐数据
		if (null != fromPlaceId && fromPlaceId.longValue() != DEFAULT_FROM_PLACE_ID.longValue()) {
			//如果出发地已经是上海，则不需要使用默认出发地当做备用字段,不是上海的分站
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, DEFAULT_FROM_PLACE_ID);
		} else {
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, null);
		}
		return "channelAbroad";
	}
    /**
     *ajax请求获取推荐景点 参数paramDataCode
     * @return
     * @author:nixianjun 2013-7-2
     */
    @Action("/homePage/ajaxGetRecommendScenicForAbroad")
	public String  ajaxGetRecommendScenic() {
    	if(paramDataCode!=null&&StringUtils.isNotEmpty(paramDataCode)){
    		this.recommendInfoForScenicList= getRecommendInfoData(this.channelPage, null, this.containerCode,paramDataCode);
    	}
    	return "recommendScenicDiv";
    }
	 /**
     * 热门推荐（获取首页限时热卖-前两条）
     * 
     * @author:nixianjun 2013-7-2
     */
    public List<RecommendInfo> getHotRecommendXianShiTeMaiSet(){
    	List<RecommendInfo> list=new ArrayList<RecommendInfo>();
    	List<RecommendInfo> xianShiTeMai= getRecommendInfoData(PindaoPageUtils.HOME_CHANNELPAGE, null, PindaoPageUtils.HOME_CONTAINERCODE, PindaoPageUtils.XIANSHITEMAI);
 	    if(xianShiTeMai!=null&&!xianShiTeMai.isEmpty()){
	    	 list.addAll(HomePageUtils.getFrontDataTeMaiByType(xianShiTeMai, HomePageUtils.ABROAD));
	    }
	    return list;
    }
    /**
     * 热门推荐（获取热销排行-前两条）
     * 
     * @author:nixianjun 2013-7-2
     */
    public List<RecommendInfo> getHotRecommendReXiaoPaiHangSet(){
    	List<RecommendInfo> list=new ArrayList<RecommendInfo>();
     	List<RecommendInfo> reXiaoPaiHang= getRecommendInfoData(PindaoPageUtils.HOME_CHANNELPAGE, null, PindaoPageUtils.HOME_CONTAINERCODE, PindaoPageUtils.REXIAOPAIHANG);
	    if(reXiaoPaiHang!=null&&!reXiaoPaiHang.isEmpty()){
	    	 list.addAll(HomePageUtils.getFrontDataReXiaoPaiHangByType(reXiaoPaiHang,HomePageUtils.ABROAD));
	    }
	    return list;
    }

	public String getChannelPage() {
		return channelPage;
	}

}
